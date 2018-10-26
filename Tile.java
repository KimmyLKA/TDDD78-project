package com.company;
import java.awt.*;

/**
 * class holds the variables for tile objects
 * used by TileRender
 */
public abstract class Tile
{
    //dimensions and position
    protected int x,y,width,height;

    private boolean solid;
    private TileType tileType;
    private Handler handler;

    protected Tile(int x, int y, int width, int height, boolean solid, TileType tileType, Handler handler){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.solid = solid;
        this.tileType = tileType;
    	this.handler = handler;

    }

    //getters
    public int getX() {
	return x;
    }
    public int getY() {
	return y;
    }
    public TileType getTileType() {
	return tileType;
    }

    public abstract void render(Graphics g);

    public void die(){
        handler.removeTile(this);
    }

    public boolean isSolid() {
	return solid;
    }

    //create object to check bounds
    public Rectangle getBounds(){
        return new Rectangle(x,y,width,height+10);
    }
}
