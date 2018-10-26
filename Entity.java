package com.company;

import java.awt.*;

import static com.company.PirateMode.DEFAULT;

/**
 * Entity contains methods implemented in Pirate, Bullet and Enemy class
 * as well as their shared methods, variables and superclasses.
 *
 */

public abstract class Entity
{

    //set values
    private static final double GRAVITY = 0.3;
    private static final int OFFSETINT = 20;

    //gravity variables
    protected double deltaY = 0.0;
    protected double maxDY;
    protected boolean falling = true;
    protected double gravity;

    //animation variables
    protected int frame = 0;
    protected int frameDelay = 0;
    protected int facing;

    //dimension, position and speed variables
    protected int x,y,width,height;
    protected int velX;

    protected EntityType entityType;
    protected Handler handler;
    protected static PirateMode mode = DEFAULT;

    public static void setMode(final PirateMode mode) {
	Entity.mode = mode;
    }

    protected void setFacing(final int facing) {
	this.facing = facing;
    }

    //superclass definition, shared between all derivative classes
    protected Entity(int x, int y, int width, int height, EntityType entityType, Handler handler){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.entityType = entityType;
        this.handler = handler;

	gravity = GRAVITY;
	maxDY = 8; //speed of falling when jumping
    }

    //abstract functions used by derivatives
    public abstract void render(Graphics g);
    public abstract void update();

    //die func used by all derivatives to remove when collision demands it
    public void die(){
        handler.removeEntity(this);
    }

    //getters and setters
    public static PirateMode getMode() {
	return mode;
    }
    public int getX() {
	return x;
    }
    public int getY() {
	return y;
    }
    public void setVelX(final int velX) {
	this.velX = velX;
    }
    public EntityType getEntityType() {
	return entityType;
    }
    public int getWidth() {
	return width;
    }
    public int getHeight() {
	return height;
    }

    //boundary functions, create rectangle around objects uesd to create collision algorithm in derivative classes
    public Rectangle getUpperBounds() {
  	return new Rectangle(x+10, y, width - OFFSETINT, 5); }
    public Rectangle getLowerBounds(){
      	return new Rectangle(x+10, y+height-5, width - OFFSETINT, 5);
      }
    public Rectangle getLeftBounds(){
          	return new Rectangle(x, y+10, 5, height - OFFSETINT);
          }
    public Rectangle getRightBounds(){
              	return new Rectangle(x+width-5, y+10, 5, height - OFFSETINT);
              }
    public Rectangle getBounds(){
	return new Rectangle(x, y, width, height);
    }

    //general frame used by pirate and enemy class as they have the same amount of sprites/animation
    public void animation(){
	if(velX != 0){
	    frameDelay++;
	    if(frameDelay >= 10){
	        frame++;
	        if(frame >= 4){
	            frame = 0;
		}

		frameDelay = 0;
	    }
	}
    }

    //fall used to get gravity working in enemy and pirate class
    public void fall(){
   	    if(falling){
   		deltaY += gravity;
   	        if (deltaY > maxDY) deltaY = maxDY;
   	    }
   	}
}
