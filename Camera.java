package com.company;

/**
 * Camera contains the logic in regards to the focus of the camera the game
 * i.e makes the game focus and move around the pirate character at all times
 * Is initialized in the Main Class
 */
public class Camera

{
    private int x,y;

    public void update(Entity pirate){
        //get the coords of the player, minus to get the camera to move in the
	//right direction and window wdith/height to get camera in the middle of
	//the screen
	this.x = (-pirate.getX() + Main.WIDTH/2);
	this.y = (-pirate.getY() + Main.HEIGHT/2);
    }

    public int getX() {
	return x;
    }

    public int getY() {
	return y;
    }

}