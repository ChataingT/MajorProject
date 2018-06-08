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
    public boolean addUser(@RequestBody User user)
    {
	 	if (userService.findByLogin(user.getLogin())==null) {
	 		userService.addUser(user);
	 		return true;
	 	}
        return false;
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
    @RequestMapping(method=RequestMethod.POST,value="/users/login")
    public long isLoginIn(@RequestBody User user) {
        if (userService.isLoginIn(user)) {
        	return (userService.giveToken(user));
        }
        return -1;
        
    }

    

  @RequestMapping(method=RequestMethod.GET, value="/users/{login}/{token}")
    public boolean verifyUser(@PathVariable String login,@PathVariable long token) {
        return userService.verifyUser(login, token);
    }

    @RequestMapping(method=RequestMethod.GET, value="/users/admin/{login}/{token}")
    public boolean verifyAdmin(@PathVariable String login,@PathVariable long token) {
        return userService.verifyAdmin(login, token);
    }

    @RequestMapping(method=RequestMethod.GET, value="/users/demande/{login}/{token}")
    public boolean askingConnection(@PathVariable String login,@PathVariable long token) {
        return userService.askingConnection(login, token);
    }

    /*--------------------------------------------------------------------------------------------------*/
    // Méthodes FrontEnd - BackEnd
    /*--------------------------------------------------------------------------------------------------*/

    /**
     * Start the Simulation without parameters
     * @param
     * @return boolean to verify the good execution
     */
    @RequestMapping(method=RequestMethod.GET,value="/simulation/on/{login}/{token}")
    public boolean startSimulation(@PathVariable String login, @PathVariable long token) {
        boolean ret = false;
        System.out.println("a");
        try
        {
            //Verify user's token/ user is admin
        	 //System.out.println(userService.verifyUser(login,token));
        	 System.out.println(getUser(login));
        	 System.out.println(userService.isUserAdmin(userRepository.findByLogin(login)));
            if (userService.verifyUser(login,token) && userService.isUserAdmin(userRepository.findByLogin(login)))
            {

            	System.out.println("Dans le if");
            	ret = userService.startSimulation(getUser(login));
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
    @RequestMapping(method=RequestMethod.GET,value="/simulation/on/{login}/{token}/{x}/{y}/{nbObstacle}/{nbSpeedBumps}")
    public boolean startSimulation(@PathVariable String login,@PathVariable long token,@PathVariable Integer x,
    		@PathVariable Integer y,@PathVariable Integer nbObstacle, @PathVariable Integer nbSpeedBumps) {
        boolean ret = false;
        try
        {
            //Verify user's token/ user is admin
            if (userService.verifyUser(login,token) && userService.isUserAdmin(getUser(login)))
            {
                ret = userService.startSimulation(getUser(login), x, y, nbObstacle, nbSpeedBumps);
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
    @RequestMapping(method=RequestMethod.GET,value="/simulation/off/{login}/{token}")
    public boolean endSimulation(@PathVariable String login,@PathVariable long token) {
    	System.out.println("test effectue");
        boolean ret = false;
        try
        {
            //Verify user's token/ user is admin
            if (userService.verifyUser(login,token) && userService.isUserAdmin(getUser(login)))
            {
                ret = userService.endSimulation(getUser(login));
            }
        }
        catch(Exception e)
        {
                ret = false;
        }
        System.out.println("test effectue");
        return ret;
    }

    /**
     * Move the Robot at the new position
     * @param user
     * @param direction
     * @return coord of the new position
     */
    @RequestMapping(method=RequestMethod.GET,value="/robot/deplacement/{login}/{token}/{direction}")
    public List<Integer> moveRobot(@PathVariable String login, @PathVariable long token,@PathVariable String direction) {
        List<Integer> newCoord = new ArrayList<Integer>();

        try  {
            //Verify user's token / user is main
            if (userService.verifyUser(login,token) && userService.isUserMain(getUser(login)))
            {
                newCoord = userService.moveRobot(getUser(login), direction);
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
    @RequestMapping(method=RequestMethod.GET,value="/robot/environnementRobot/{login}/{token}")
    public String getEnvtRobot(@PathVariable String login, @PathVariable long token) {
        String envtRobot = "";
        try
        {
            //Verify user'11688s token / user is main
            if (userService.verifyUser(login,token) && userService.isUserMain(getUser(login)))
            {
                envtRobot = userService.getEnvtRobot(getUser(login));
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
    @RequestMapping(method=RequestMethod.GET,value="/robot/environnementTotal/{login}/{token}")
    public String getEnvtGlobal(@PathVariable String login, @PathVariable long token) {
        String envtGlobal = "";
        try
        {
            //Verify user's token / user is main
            if (userService.verifyUser(login,token) && userService.isUserMain(getUser(login)))
            {
                envtGlobal = userService.getEnvtGlobal(getUser(login));
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
    @RequestMapping(method=RequestMethod.GET,value="/robot/environnementActuel/{login}/{token}")
    public String getEnvtActual(@PathVariable String login, @PathVariable long token) {
        String envtActual = "";
        try
        {
            //Verify user's token / user is main
            if (userService.verifyUser(login,token) && userService.isUserMain(getUser(login)))
            {
                envtActual = userService.getEnvtActual(getUser(login));
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
    @RequestMapping(method=RequestMethod.GET,value="/robot/etat/{login}/{token}")
    public String getStateRobot(@PathVariable String login, @PathVariable long token) {
        String actualCoord = "";
        try
        {
            //Verify user's token / user is main
            if (userService.verifyUser(login,token) && userService.isUserMain(getUser(login)))
            {
                actualCoord = userService.getStateRobot(getUser(login));
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
    @RequestMapping(method=RequestMethod.GET,value="/robot/info/{login}/{token}")
    public List<Integer> getData(@PathVariable String login, @PathVariable long token) {
        List<Integer> data = new ArrayList<Integer>();
        try
        {
            //Verify user's token / user is main
            if (userService.verifyUser(login,token) && userService.isUserMain(getUser(login)))
            {
                data = userService.getData(getUser(login));
            }
        }
        catch(Exception ex)
        {
            System.out.println("Error :" + ex);
        }
        return data;
    }
}





