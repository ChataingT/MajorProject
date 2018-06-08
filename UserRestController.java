package com.cpe.springboot.user.controller;
 
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
 
import com.cpe.springboot.user.model.User;
 
 
@RestController
public class UserRestController {
     
    @Autowired
    private UserService userService;
     
    /**
     *   
     * @return la liste de tous les users avec leurs attributs
     */
    @RequestMapping(method=RequestMethod.GET,value="/users")
    private List<User>getAllUsers() {
        return userService.getAllUsers();
 
    }     
     
    /**
     *   
     * @param id
     * @return l'utilisateur dont l'id est donner en parametre
     */
    @RequestMapping("/users/{id}")
    private User getUser(@PathVariable String id) {
        return userService.getUser(id);
 
    }
     
    @RequestMapping(method=RequestMethod.POST,value="/users")
    public void addUser(@RequestBody User user) {
        userService.addUser(user);
    }
     
    @RequestMapping(method=RequestMethod.PUT,value="/users/{id}")
    public void updateUser(@RequestBody User user,@PathVariable String id) {
        user.setId(Integer.valueOf(id));
        userService.updateUser(user);
    }
     
    @RequestMapping(method=RequestMethod.DELETE,value="/users/{id}")
    public void deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
    }
 
    /**
     *
     * @param user
     * @return true ou false si on trouve le user
     */
    @RequestMapping(method=RequestMethod.POST,value="/login")
    public double isLoginIn(@RequestBody User user) {
        double retour=0;
        if (userService.isLoginIn(user)) {
            retour=giveToken(user);
        }
        return retour;
    }
 
     
    //@RequestMapping(value="user/token")
    public double giveToken(@RequestBody User user) {
        return userService.giveToken(user);
    }
 
     
    //@RequestMapping(method=RequestMethod.GET,value="/user/tokenVerif")
    public boolean compareToken(@RequestBody User user, Double token) {
        return userService.compareToken(user, token);
    }
     
    /*--------------------------------------------------------------------------------------------------*/
    // Méthodes FrontEnd - BackEnd
    /*--------------------------------------------------------------------------------------------------*/
    
    /**
     * Start the Simulation without parameters
     * @param   
     * @return boolean to verify the good execution
     */
    @RequestMapping(method=RequestMethod.POST,value="/simulation/on")
    public boolean startSimulation(@RequestBody User user) {
        boolean ret = false;
        try
        {
            //Verify user's token/ user is admin
            if (userService.compareToken(user,isLoginIn(user)) && userService.isUserAdmin(user))
            {     
                ret = userService.startSimulation(user);
            }
        }
        catch(Exception e)  
        {
            ret = false;
        }
        return ret;
    }
    
    /**
     * Start the Simulation with specific parameters
     * @param   
     * @return boolean to verify the good execution
     */
    @RequestMapping(method=RequestMethod.POST,value="/simulation/on")
    public boolean startSimulation(@RequestBody User user, @RequestBody int x, @RequestBody int y,
                                   @RequestBody int nbObstacles, @RequestBody int nbSpeedBumps) {
        boolean ret = false;
        try
        {
            //Verify user's token/ user is admin
            if (userService.compareToken(user,isLoginIn(user)) && userService.isUserAdmin(user))
            {     
                ret = userService.startSimulation(user, x, y, nbObstacles, nbSpeedBumps);
            }
        }
        catch(Exception e)  
        {
            ret = false;
        }
        return ret;
    }
     
    /**
     * End the Simulation
     * @param user
     * @return boolean to verify the good execution
     */
    @RequestMapping(method=RequestMethod.POST,value="/simulation/off")
    public boolean endSimulation(@RequestBody User user) {
        boolean ret = false;
        try
        {
            //Verify user's token/ user is admin
            if (userService.compareToken(user, isLoginIn(user)) && userService.isUserAdmin(user))
            {     
                ret = userService.endSimulation(user);
            }
        }
        catch(Exception e)  
        {
                ret = false;
        }
        return ret;
    }
     
    /**
     * Move the Robot at the new position
     * @param user
     * @param direction
     * @return coord of the new position
     */
    @RequestMapping(method=RequestMethod.POST,value="/robot/deplacement")
    public List<Integer> moveRobot(@RequestBody User user, @RequestBody String direction) {
        List<Integer> newCoord = new ArrayList<Integer>();
        try  {
            //Verify user's token / user is main
            if (userService.compareToken(user, isLoginIn(user)) && userService.isUserMain(user))
            {
                newCoord = userService.moveRobot(user, direction);
            }
        }
        catch(Exception e)  
        {
            System.out.println("Error :" + e);
        }
        return newCoord;
    }
     
    /**
     * Get the Environment seen by the Robot in String (JSON)
     * @param user
     * @return String for the JSON Map
     */
    @RequestMapping(method=RequestMethod.POST,value="/robot/environnementRobot")
    public String getEnvtRobot(@RequestBody User user) {
        String envtRobot = "";
        try
        {
            //Verify user's token / user is main
            if (userService.compareToken(user, isLoginIn(user)) && userService.isUserMain(user))
            {
                envtRobot = userService.getEnvtRobot(user);
            }
        }
        catch(Exception ex)  
        {
            System.out.println("Error :" + ex);
        }
        return envtRobot;
    }
     
    /**
     * Get the Global Environment in String (JSON)
     * @param user
     * @return String for the JSON Map
     */
    @RequestMapping(method=RequestMethod.POST,value="/robot/environnementTotal")
    public String getEnvtGlobal(@RequestBody User user) {
        String envtGlobal = "";
        try
        {
            //Verify user's token / user is main
            if (userService.compareToken(user, isLoginIn(user)) && userService.isUserMain(user))
            {
                envtGlobal = userService.getEnvtGlobal(user);
            }
        }
        catch(Exception ex)  
        {
            System.out.println("Error :" + ex);
        }
        return envtGlobal;
    }
     
    /**
     * Get the Global Environment in String (JSON)
     * @param user
     * @return String for the JSON Map
     */
    @RequestMapping(method=RequestMethod.POST,value="/robot/environnementActuel")
    public String getEnvtActual(@RequestBody User user) {
        String envtActual = "";
        try
        {
            //Verify user's token / user is main
            if (userService.compareToken(user, isLoginIn(user)) && userService.isUserMain(user))
            {
                envtActual = userService.getEnvtActual(user);
            }
        }
        catch(Exception ex)  
        {
            System.out.println("Error :" + ex);
        }
        return envtActual;
    }
    
    /**
     * Get the Coord of the Robot
     * @param user
     * @return String for the Coord
     */
    @RequestMapping(method=RequestMethod.POST,value="/robot/etat")
    public String getStateRobot(@RequestBody User user) {
        String actualCoord = "";
        try
        {
            //Verify user's token / user is main
            if (userService.compareToken(user, isLoginIn(user)) && userService.isUserMain(user))
            {
                actualCoord = userService.getStateRobot(user);
            }
        }
        catch(Exception ex)  
        {
            System.out.println("Error :" + ex);
        }
        return actualCoord;
    }
    
    /**
     * Get the Data
     * @param user
     * @return List of Integers with the Data
     */
    @RequestMapping(method=RequestMethod.POST,value="/robot/info")
    public List<Integer> getData(@RequestBody User user) {
        List<Integer> data = new ArrayList<Integer>();
        try
        {
            //Verify user's token / user is main
            if (userService.compareToken(user, isLoginIn(user)) && userService.isUserMain(user))
            {
                data = userService.getData(user);
            }
        }
        catch(Exception ex)  
        {
            System.out.println("Error :" + ex);
        }
        return data;
    }
}





