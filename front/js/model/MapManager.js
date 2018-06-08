import {Map} from './Map.js';

class MapManager {
    
    constructor(w, h) {
        
        this.fullJson = null;
        this.maps = [];
        
        this.w = w;
        this.h = h;
    }
    
    generateFullJson(json) {
        
        this.fullJson = json;
        
        this.fullJson.layers = [];
        for(let map of this.maps) {
            console.log(map);
            //this.fullJson.layers.push = this.map.getJson();
        }
        
        console.log(this.fullJson);
    }
    
    getFullJson() {
        
        return this.fullJson;
    }
    
    load(name) {
        return $.ajax(
            {
                url: "./data/mapSqueletton.json"
            }
        ).done($.proxy(this.generateFullJson, this));
     }
    
    addMap(data, name, opacity) {
        
        this.maps.push(new Map(data, this.w, this.h, name, opacity, 0, 0));
    }
}

export {MapManager};