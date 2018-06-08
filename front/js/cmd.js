import {SceneController} from './controller/SceneController.js';
import {RemoteController} from './controller/RemoteController.js';
import {User} from './User.js';

$(function() 
{
    //Verify if the user is connected
    let user = User.restore();

    if(user == null)
    {
        window.location.replace("index.html");
        localStorage.setItem('error', 'Vous n\'êtes pas connectés');
    }

    let c = $("canvas")[0].getContext("2d");
    let sceneController = new SceneController(c);
    let remoteController = new RemoteController(sceneController);

    setInterval(function(){
        sceneController.drawRobot();
    }, 25);
});

function userVerif(v) {
    
    console.log(v);
}