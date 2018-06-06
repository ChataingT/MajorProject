import {MapView} from '../view/MapView.js';
import {RobotView} from '../view/RobotView.js';

class SceneController {
    
    constructor(canvas) {
        
        this.mapView = new MapView(canvas, []);
        
        this.mapView.load("customMap");
    
    
        this.robotView = new RobotView(
            canvas,                      //Canvas context
            '..\/img\/robot.png',   //Sprite src
            64,                     //Sprite width
            64,                     //Sprite height
            12,                     //Number of frames for the animation
            0,                      //X coord of the case
            0,                      //Y coord of the case
            1,                      //Scale of the sprite
            3                       //Speed of the robot
        );
    }
    
    drawRobot() {
        
        this.mapView.rerenderall();
        this.robotView.draw();
    }
    
    moveRobot(x, y)
    {
        this.robotView.goTo(x,y);
    }
}

export {SceneController};