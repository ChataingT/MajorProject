package com.cpe.springboot.user.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.object.UpdatableSqlQuery;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cpe.springboot.user.model.User;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private Hashtable<String, Long> loginToken = new Hashtable<String, Long>();
    private Hashtable<Long, Date> tokenLastRequestUser = new Hashtable<Long, Date>();
    private Date dateLastResquest;
 
    // URL pour le microservice
    private static final String DIALOG_BEJAVA = "http://localhost:8080";

    /**
     *
     * @return liste users
     */
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    public User findByLogin(String login)
    {
        return userRepository.findByLogin(login);
    }

    public User getUser(String id) {
        return userRepository.findOne(Integer.valueOf(id));
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public void updateUser(User user) {
        userRepository.save(user);

    }

    public void deleteUser(String id) {
        userRepository.delete(Integer.valueOf(id));
    }
    
    public void updateQueue() {
        List<User> listqueue=userRepository.findQueue();
        for (User u:listqueue) {
            u.setQueue(u.getQueue()-1);        // enlever 1 a tout les queue de cette liste
            updateUser(u);//update la liste en la revoyant dans update user
        }
        
    }
    public void addQueue(User user) { //ajoute un user dans la queue
        user.setQueue(userRepository.findMaxQueue()+1);
    }
    public boolean isUserMain(User user){
        return user.getQueue()==0; //return true si l'user est le principal
    }
    public boolean isUserAdmin(User user) {
        return user.getAdmin(); //Return true si l'user est admin
    }

    public boolean isLoginIn (User user) {
        return !userRepository.findByLoginAndPassword(user.getLogin(), user.getPassword()).isEmpty();
    }

    /**
     * Permet de donner un token à l'utilisateur qui s'est connecté
     * @param id
     * @param token
     * @return
     */
    public long giveToken(User user) {
    	List<User> user2=userRepository.findByLoginAndPassword(user.getLogin(), user.getPassword());
        user= user2.get(0);
    	Integer id=user.getId();
        long token;
        long retour = 0;
    
        //Si user est connecté
        if (isLoginIn(user))
        {
            Double randomnb=(Math.random()*10000);
            token=randomnb.intValue();
            loginToken.put(user.getLogin(), token);
            retour = token;

        }
        return retour;
    
    }

    public boolean verifyUser(String login, long token) {
        boolean retour = false;
        //On regarde si le login existe
        if (loginToken.containsKey(login)) {
        	System.out.println("le login existe dans logintoken");
        	System.out.println(loginToken);
        	System.out.println(token);
        	System.out.println(loginToken.get(login));
            //Puis on vérikie que le token correspond bien à l'id
            if (token==loginToken.get(login)) {
            	System.out.println("le login correspond a l'id");
                retour = true;
            }
            
        }
        
        return retour;
    }

  public boolean verifyAdmin(String login, long token) {
    	if (verifyUser(login, token)) {
    	User user = userRepository.findByLogin(login);
    	return this.isUserAdmin(user);
    	}
    	return false;
    }
    public boolean askingConnection(String login, long token) {
    	boolean ret =false;
    	if (verifyUser(login, token)) {
    		if (!tokenLastRequestUser.contains(token)) {
    			Date currentDate= new Date();
    			tokenLastRequestUser.put(token, currentDate);
    		}
    		afkMainUser();
    		verifyQueue();
    		ret=true;
    	}
    	return ret;

    }
	public boolean afkMainUser(){
		boolean ret=false;
		Date currentDate= new Date();
    	if (currentDate.compareTo(tokenLastRequestUser.get(loginToken.get(userRepository.findMainuser().getLogin())))>30) { //avoir le token du usermain
    		updateQueue();
    		ret=true;
    	}
    	return ret;
    }
	public boolean verifyQueue() {
		boolean ret=false;
		for (long token : tokenLastRequestUser.keySet()) {
			Date currentDate= new Date();
			if (currentDate.compareTo(tokenLastRequestUser.get(token))>30) { //avoir le token du user
	    		tokenLastRequestUser.remove(token);
	    		ret=true;
	    	}
		}
		return ret;
	}




    /*--------------------------------------------------------------------------------------------------*/
    // Méthodes BackEnd - JAVA
    /*--------------------------------------------------------------------------------------------------*/

    /**
     * Permet de demander l'allumage d'une simulation standard (sans paramètres)
     * @param user
     * @return vrai si effectuée
     */
    public boolean startSimulation(User user) {
        boolean retour = false;
        try
        {
        	System.out.println("Dans le le userService");
            String adminResourceUrl=DIALOG_BEJAVA+"/simulation/on2";
            RestTemplate restTemplate = new RestTemplate();
            Boolean answer = restTemplate.getForObject(adminResourceUrl, Boolean.class);
            retour = answer;
        }
        catch(Exception e) {
            retour = false;
        }

        return retour;
    }

    /**
     * Permet de demander l'allumage d'une simulation avec paramètres
     * @param user
     * @return vrai si effectuée
     */
    public boolean startSimulation(User user, int x, int y, int nbObstacles, int nbSpeedBumps) {
        boolean retour = false;
        try
        {
            String adminResourceUrl=DIALOG_BEJAVA+"/simulation/on1";
            List<Integer> parameters = new ArrayList<Integer>();
            parameters.add(x);
            parameters.add(y);
            parameters.add(nbObstacles);
            parameters.add(nbSpeedBumps);
            RestTemplate restTemplate = new RestTemplate();
            Boolean answer = restTemplate.postForObject(adminResourceUrl, parameters, Boolean.class);
            retour = answer;
        }
        catch(Exception e) {
            retour = false;
        }

        return retour;
    }

/**
 * Permet de demander l'extinction de la simulation
 * @param user
 * @return fichier json
 */
    public boolean endSimulation(User user) {
        boolean retour = false;
        try
        {
            String adminResourceUrl=DIALOG_BEJAVA+"/simulation/off";
            RestTemplate restTemplate = new RestTemplate();
            Boolean answer = restTemplate.getForObject(adminResourceUrl, Boolean.class);
            retour = answer;
        }
        catch(Exception e) {
            retour = false;
        }
        return retour;

    }

    /**
     * Permet de demander l'environnement du robot
     * @param user
     * @return fichier json
     */
    public String getEnvtRobot(User user) {
        String result = null;
        try
        {
            String adminResourceUrl=DIALOG_BEJAVA+"/robot/environnementRobot";
            RestTemplate restTemplate = new RestTemplate();
            String answer = restTemplate.getForObject(adminResourceUrl, String.class);
            result = answer;
        }
        catch(Exception e) {
            System.out.println("Erreur : "+e);
        }
        return result;

    }

    /**
     * Permet de demander l'environnement global
     * @param user
     * @return fichier json
     */
    public String getEnvtGlobal(User user) {
        String result = null;
        try
        {
            String adminResourceUrl=DIALOG_BEJAVA+"/robot/environnementTotal";
            RestTemplate restTemplate = new RestTemplate();
            String answer = restTemplate.getForObject(adminResourceUrl, String.class);
            result = answer;
        }
        catch(Exception e) {
            System.out.println("Erreur : "+e);
        }

        return result;
    }

    /**
     * Ask for the environment seen by the robot at the actual moment
     * @param user
     * @return the map in a String
     */
    public String getEnvtActual(User user) {
        String result = null;
        try
        {
            String adminResourceUrl = DIALOG_BEJAVA+"/robot/environnementActuel";
            RestTemplate restTemplate = new RestTemplate();
            String answer = restTemplate.getForObject(adminResourceUrl, String.class);
            result = answer;
        }
        catch(Exception e) {
            System.out.println("Erreur : "+e);
        }

        return result;
    }

    /**
     * Permet de demander l'etat du robot
     * @param user
     * @return
     */
    public String getStateRobot(User user) {
        String result = null;
        try
        {
            String adminResourceUrl=DIALOG_BEJAVA+"/robot/etat";
            RestTemplate restTemplate = new RestTemplate();
            String answer = restTemplate.getForObject(adminResourceUrl, String.class);
            result = answer;
        }
        catch(Exception e)
        {
            System.out.println("Erreur : "+e);

        }
        return result;
    }

    /**
     * Demande au robot de se déplacer
     * @param user
     * @param direction
     * @return un json avec le deplacement du robot
     */
    public List<Integer> moveRobot(User user, String direction) {
        List<Integer> result = new ArrayList<Integer>();
        try
        {
            String adminResourceUrl=DIALOG_BEJAVA+"/robot/deplacement";
            RestTemplate restTemplate = new RestTemplate();
            Boolean answer = restTemplate.getForObject(adminResourceUrl, Boolean.class);

            if (answer)
            {
                switch(direction)
                {
                    case "up":
                        String adminResourceUrlUp=DIALOG_BEJAVA+"/robot/up";
                        RestTemplate restTemplateUp = new RestTemplate();
                        List answerUp = restTemplateUp.getForObject(adminResourceUrlUp, List.class);
                        result = answerUp;
                        break;
                    case "down":
                        String adminResourceUrlDown=DIALOG_BEJAVA+"/robot/down";
                        RestTemplate restTemplateDown = new RestTemplate();
                        List answerDown = restTemplateDown.getForObject(adminResourceUrlDown, List.class);
                        result = answerDown;
                        break;
                    case "left":
                        String adminResourceUrlLeft=DIALOG_BEJAVA+"/robot/left";
                        RestTemplate restTemplateLeft = new RestTemplate();
                        List answerLeft = restTemplateLeft.getForObject(adminResourceUrlLeft, List.class);
                        result = answerLeft;
                        break;
                    case "right":
                        String adminResourceUrlRight=DIALOG_BEJAVA+"/robot/right";
                        RestTemplate restTemplateRight = new RestTemplate();
                        List answerRight = restTemplateRight.getForObject(adminResourceUrlRight, List.class);
                        result = answerRight;
                        break;
                    default :
                        System.out.println("Direction non lue");
                }
            }

            else
            {
                result.add(-1);
                result.add(-1);
            }

        }
        catch(Exception e){

            System.out.println("erreur"+e);

        }

        return result;
    }

    /**
     * Ask for the Data of the exploration
     * @param user
     * @return the Data
     */
    public List<Integer> getData(User user) {
        List<Integer> result = new ArrayList<Integer>();
        try
        {
            String adminResourceUrl=DIALOG_BEJAVA+"/robot/info";
            RestTemplate restTemplate = new RestTemplate();
            List answer = restTemplate.getForObject(adminResourceUrl, List.class);
            result = answer;
        }
        catch(Exception e)
        {
            System.out.println("Erreur : "+e);

        }
        return result;
    }
}




