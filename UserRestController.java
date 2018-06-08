package com.cpe.springboot.user.controller;
 
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

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
    @RequestMapping(method=RequestMethod.POST,value="/users/login")
    public double isLoginIn(@RequestBody User user) {
        if (userService.isLoginIn(user)) {
        	return (userService.giveToken(user));
        }
        return 0;
        
    }

    

    
    @RequestMapping(method=RequestMethod.GET, value="/users/{login}/{token}")
    public boolean verifyUser(@PathVariable String login,@PathVariable double token) {
        return userService.verifyUser(login, token);
    }
    
    @RequestMapping(method=RequestMethod.GET, value="/users/admin/{login}/{token}")
    public boolean verifyAdmin(@PathVariable String login,@PathVariable double token) {
        return userService.verifyAdmin(login, token);
    }
    @RequestMapping(method=RequestMethod.GET, value="/users/demande/{login}/{token}")
    public boolean askingConnection(@PathVariable String login,@PathVariable double token) {
        return userService.askingConnection(login, token);
    }
    
   
    /*--------------------------------------------------------------------------------------------------*/
    // Méthodes BackEnd - Simulateur
    /*--------------------------------------------------------------------------------------------------*/
}
