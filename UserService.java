package com.cpe.springboot.user.controller;

import static org.mockito.Matchers.booleanThat;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.object.UpdatableSqlQuery;
import org.springframework.stereotype.Service;

import com.cpe.springboot.user.model.User;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private Hashtable<String, Double> loginToken = new Hashtable<String, Double>();    
    private Date dateLastResquest;
 
    /**
     *
     * @return liste users
     */
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
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
    
    public void updadeQueue() {
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
    public double giveToken(User user) {
    	List<User> user2=userRepository.findByLoginAndPassword(user.getLogin(), user.getPassword());
        user= user2.get(0);
    	Integer id=user.getId();
        Double token;
        double retour = 0;
    
        //Si user est connecté
        if (isLoginIn(user))
        {
            token=Math.random();
            loginToken.put(user.getLogin(), token);
            retour = token;

        }
        return retour;
    
    }

    public boolean verifyUser(String login, Double token) {
        boolean retour = false;
        //On regarde si l'id existe
        if (loginToken.containsKey(login)) {
            //Puis on vérikie que le token correspond bien à l'id
            if (token==loginToken.get(login)) {
                retour = true;
            }
            
        }
        
        return retour;
    }
    public boolean verifyAdmin(String login, Double token) {
    	if (verifyUser(login, token)) {
    	User user = userRepository.findByLogin(login);
    	return this.isUserAdmin(user);
    	}
    	return false;
    }
    public boolean askingConnection(String login, Double token) {
    	boolean ret =false;
    	if (verifyUser(login, token)) {
    		afkMainUser();
    		verifyQueue();
    		ret=true;
    	}
    	return ret;
    	
    }
	public boolean afkMainUser(){
		boolean ret=false;
		Date currentDate= new Date();
    	if (currentDate.compareTo(dateLastResquest)>30) {
    		updadeQueue();
    		ret=true;
    	}
    	return ret;
    }
	public boolean verifyQueue() {
		boolean ret=false;
		
		return ret;
	}



    


}


