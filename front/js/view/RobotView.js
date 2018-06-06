import * as dir from '../model/directions.js';

export const tileWidth = 64;
export const tileHeight = 64;

class RobotView {

    constructor(canvas, spriteSrc, height, width, nbFrames, x, y, scale, speed) {
    
        this.canvas = canvas;
        this.height = height; this.width = width;
        this.x = x * tileWidth;
        this.y = y * tileHeight;
        this.nextTiles = [];
        this.scale = scale;
        this.speed = speed;
        
        this.orientation = 0;
        
        this.frame = 0;
        this.nbFrames = nbFrames;
        
        this.sprite = $("<img />", {src: spriteSrc, height: this.height, width: this.width})[0];
    }
    
    //Refresh the robots informations such as real coords, frame
    refresh() {
        
        //Changing the current frame 
        this.frame = (this.frame + 1) % this.nbFrames;
        
        //Looking for the next targeted position
        if(this.nextTiles.length > 0) {
            
            let dx = this.nextTiles[0].getXCoord() - this.x;
            let dy = this.nextTiles[0].getYCoord() - this.y;
            
            if(dx == 0 && dy == 0) {
                
                //The current position is the same as the goal
                this.nextTiles.shift();
                return;
            }
            
            let xOrientation = Math.sign(dx);
            let yOrientation = Math.sign(dy);
            
            //Setting the orientation
            if(xOrientation > 0)
                this.orientation = dir.EAST;
            else if(xOrientation < 0)
                this.orientation = dir.WEST;
            else if(yOrientation > 0)
                this.orientation = dir.SOUTH;
            else if(yOrientation < 0)
                this.orientation = dir.NORTH;
            
            //Verifying the remaining distance to the next position
            if(Math.abs(dx) > this.speed) {
                
                this.x += this.speed * xOrientation;
            } else {
                
                this.x = this.nextTiles[0].getXCoord();
            }
        
            if(Math.abs(dy) > this.speed) {
                
                this.y += this.speed * yOrientation;
            } else {
                
                this.y = this.nextTiles[0].getYCoord();
            }
        }
        else
            this.orientation = 0;
    }

    draw() {
        
        this.refresh();
        
        this.canvas.drawImage(this.sprite, this.width*this.frame, this.height * this.orientation, this.height, this.width, this.x, this.y, this.height*this.scale, this.width * this.scale);
    }
    
    goTo(x, y) {
        this.nextTiles.push(new Tile(x, y));
    }
}

class Tile {
    
    constructor(x, y) {
        
        this.x = x;
        this.y = y;
    }
    
    getXCoord() {
        return this.x * tileWidth;
    }
    
    getYCoord() {
        return this.y * tileHeight;
    }
}

export {RobotView};
export {Tile};