import {SceneController} from './controller/SceneController.js';
import {RemoteController} from './controller/RemoteController.js';
import {User} from './User.js';

$(function() 
{
    //Verify if the user is connected
    let user = localStorage.getItem('user');
    
    if(user == undefined || user == null)
    {
        //window.location.replace("index.html");
        localStorage.setItem('error', 'Vous n\'êtes pas connectés');
    } else {
        user.verify(userVerif);
    }
    
    var c = $("canvas")[0].getContext("2d");
    var sceneController = new SceneController(c);
    var remoteController = new RemoteController(sceneController);
    
    setInterval(function(){
        sceneController.drawRobot();
    }, 50);

});

function userVerif(v) {
    
    console.log(v);
}