class Map {
    
    constructor(data, w, h, name, opacity, x, y) {
        
        this.parse(data);
        
        this.w = w;
        this.h = h;
        this.x = x;
        this.y = y;
        
        this.name = name;
        this.opacity = opacity;
        this.x = x;
        this.y = y;
    }
    
    parse(data) {
        
        this.data = data;
    }
    
    getJson() {
        
        return {
            "data": this.data,
            "height": this.h,
            "width":this.w,
            "name": this.name,
            "opacity": this.opacity,
            "type":"tilelayer",
            "visible":true,
            "x": this.x,
            "y": this.y
        }
    }
}

export {Map};