package com.company;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Handler contains the logic of the general level rendering as well as the remove and add functions for the
 * different tile and entity objects. Handler handles the lists and the map, handler used in all Entity
 * and Tile classes to be able to access the lists
 */
public class Handler
{
    //get right dimensions on the sprites
    private static final int PIXEL = 64;

    //create list of tiles (used to render)
    protected List<Tile> tile = new LinkedList<>();

    //create list of entity objects (used to render)
    protected List<Entity> entity = new LinkedList<>();


    //draw every tile and entity object on the map if in frame
    public void render(Graphics g){
        for(int i = 0; i<tile.size();i++){
            Tile ti = tile.get(i);
	    if(Main.getFrame() != null && ti.getBounds().intersects(Main.getFrame()))
		ti.render(g);
	}
        for(int i = 0; i<entity.size();i++){
	    Entity en = entity.get(i);
	    if(Main.getFrame() != null && en.getBounds().intersects(Main.getFrame()))
	        en.render(g);
        	}

    }

    //update the entity objects on the map, as they are moving
    public void update(){
	for(int i = 0; i<entity.size();i++){
	    Entity en = entity.get(i);
              en.update();
        }
    }


    //add and delete list objects
    public void addTile(Tile ti){
        tile.add(ti);
    }
    public void removeTile(Tile ti){
            tile.remove(ti);
        }

    public void addEntity(Entity en){
        entity.add(en);
    }
    public void removeEntity(Entity en){
        entity.remove(en);
    }



    public void mapCreator(){
        //iterates over the map from the Map class to render a level using the numbers as cases in switch
	//change test later
        for (int x = 0; x <Map.map.length; x++){
            for(int y  = 0; y < Map.map[x].length; y++){
                switch(Map.map[x][y]){
		    case 0:
		        break;
		    case 1:
			addTile(new TileRender(y*PIXEL,x*PIXEL,PIXEL,PIXEL,true,TileType.FLOOR, this));
			break;
		    case 2:
			addEntity(new Pirate(y *PIXEL, x * PIXEL, PIXEL, PIXEL, EntityType.PIRATE, this));
			break;
		    case 3:
		        addEntity(new Enemy(y *PIXEL, x * PIXEL, PIXEL, PIXEL, EntityType.ENEMY, this));
		        break;
		    case 4:
			addTile(new TileRender(y*PIXEL,x*PIXEL,PIXEL,PIXEL,false,TileType.GUN, this));
		        break;
		    case 5:
			addTile(new TileRender(y*PIXEL,x*PIXEL,PIXEL,PIXEL,false,TileType.HP, this));
		        break;
		    case 6:
			addTile(new TileRender(y*PIXEL,x*PIXEL,PIXEL,PIXEL,false,TileType.MONEY, this));
		        break;
		    case 7:
			addTile(new TileRender(y*PIXEL,x*PIXEL,PIXEL,PIXEL,false,TileType.WIN, this));
		        break;
		    case 8:
			addTile(new TileRender(y*PIXEL,x*PIXEL,PIXEL,PIXEL,false,TileType.SWORD, this));
		        break;
		    case 9:
			addTile(new TileRender(y*PIXEL,x*PIXEL, PIXEL, PIXEL, false, TileType.FALL, this));
		        break;
                }
            }
        }
    }

    //removes all tiles and entity objects from the map
    public void clearLevel(){
        tile.clear();
        entity.clear();
    }


}
