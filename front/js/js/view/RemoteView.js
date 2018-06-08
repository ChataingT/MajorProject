import {RemoteController} from '../controller/RemoteController.js';
import * as dir from '../model/directions.js';

class RemoteView {
    
    constructor(remoteController) {
        this.remoteController = remoteController;
        
        $('#upArrow').click($.proxy(this.moveUp, this));
        $('#downArrow').click($.proxy(this.moveDown, this));
        $('#leftArrow').click($.proxy(this.moveLeft, this));
        $('#rightArrow').click($.proxy(this.moveRight, this));
        
        $(document).keyup($.proxy(this.bindKey, this));
    }
    
    bindKey(e)
    {
        switch(e.which) {
                
            case 37:
                this.moveLeft();
                break;
                
            case 38:
                this.moveUp();
                break;
                
            case 39:
                this.moveRight();
                break;
                
            case 40:
                this.moveDown();
                break;
        }
    }
    
    moveUp()
    {
        this.remoteController.move(dir.NORTH);
    }
    moveDown()
    {
        this.remoteController.move(dir.SOUTH);
    }
    moveLeft()
    {
        this.remoteController.move(dir.WEST);
    }
    moveRight()
    {
        this.remoteController.move(dir.EAST);
    }
}

export {RemoteView};