class MapView {
    constructor(canvas,layers) {
        this.c=canvas;
        this.layers=layers;
        this.tileset={};
        this.ready_load=0;
        this.scale_factor=1;
        
        this.renderLayer=this.renderLayer.bind(this);
        this.renderLayers=this.renderLayers.bind(this);
    }

    renderLayer(layer){
        let img_test=$("#img_test_id")[0];
        
        if (layer.type !== "tilelayer" || !layer.opacity) { return; }
        //let s = this.c.canvas.cloneNode();
        let size = this.data.tilewidth;
        //s = s.getContext("2d");
        if (this.layers.length < this.data.layers.length) {
            let s_y_t_1=0;
            layer.data.forEach(function(tile_idx, i) {
                if (!tile_idx) { return; }
                let img_x, img_y, s_x, s_y,s_index_x,s_index_y;
                //CAUTION current index of the image is the index-1
                let image_loaded = this.tileset[tile_idx-1];
                //index into map of the current elt
                s_index_x=(i % layer.width);
                s_index_y=~~(i / layer.width);
              
                s_x = (s_index_x)  * size/this.scale_factor;
                s_y = (s_index_y) * (size)/this.scale_factor;
                //draw the current image with an adjusted y position according to the difference between used tiledheight and current image heigt
                this.c.drawImage(image_loaded,
                            s_x, (s_y),image_loaded.width/this.scale_factor, image_loaded.height/this.scale_factor);
               //draw current image only for test
            //this.c.drawImage(s.canvas, 0, 0);
                
          }.bind(this));
          //this.layers.push(s.canvas.toDataURL());
          //s.drawImage(s.canvas, 0, 0);
        }
        else {
          this.layers.forEach(function(src) {
            let i = $("<img />", { src: src })[0];
            this.c.drawImage(i, 0, 0);
          });
        }
      }


      renderLayers(layers) {
        layers = $.isArray(layers) ? layers : this.data.layers;
        layers.forEach(this.renderLayer);
      }
    
      rerenderall(){
        //clear old display
        this.c.clearRect(0, 0, this.c.canvas.width, this.c.canvas.height);
        //remove scene layerrs
        this.layers=[];
        //ask for rerender
        this.renderLayers(this.data.layers);
      }

      loadready(){
        //check if all images are loaded
        this.ready_load++;
        if(this.ready_load>=Object.keys(this.tileset).length){
            //if all images are loaded launch the render
            this.renderLayers(this.data.layers);
            }
        }
    
      loadTileset(json) {
        this.data = json;
        //get back tilesets from json map (image URL/ code)
        this.tileset = json.tilesets[0].tiles;
        let keys = Object.keys(this.tileset);
        //replace all object into tiled set by dom images    
        keys.forEach(function(key){
          this.tileset[key]=$("<img />", { src:this.tileset[key].image, height:'64px', width:'64px'})[0];
          //each time an image is loaded check if all images are loaded
          this.tileset[key].onload=$.proxy(this.loadready, this)
        }.bind(this));
      }
    
     load(name) {
        return $.ajax(
            {
                url: "./data/" + name + ".json"
            }
        ).done($.proxy(this.loadTileset, this));
     }
}

export {MapView};