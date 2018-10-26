package com.company;

import java.awt.*;
import java.util.Random;

import static com.company.Main.spriteSheet;

/**
 *
 * The Enemy Class contains the logic and rendering of the Enemy Object
 * extends and uses the Entity Class, uses a superclass to easily create Enemies
 * in the Handler Class. Is also implemented in bound functions in the Pirate Class
 * as its interactions with the Pirate directly affects said class
 *
 */

public class Enemy extends Entity
{

    protected static Sprite[] enemy = null;

    public Enemy(final int x, final int y, final int width, final int height, final EntityType type, final Handler handler)
    {
	super(x, y, width, height, type, handler);

	final Random rnd = new Random();
	int direction = rnd.nextInt(2); //only two switch cases, so 0 and 1 rnd generated

	switch (direction) { //makes the enemy go in a random direction at start of new game
	    case 0:
		setVelX(2);
		break;
	    case 1:
		setVelX(-2);
		setFacing(1);
		break;
	}
    }

    //init the animation sprites
    public static void init(){
	enemy = new Sprite[8];

	for(int i = 0; i < enemy.length; i++){
	    enemy[i] = new Sprite(spriteSheet,i+1,3);
	         }
    }

    //draw the animation in correct way using correct facing
    @Override public void render(final Graphics g) {
	if (facing == 0) {
	    g.drawImage(enemy[frame].getBufferedImage(), x,
			y, width, height, null);
	} else if (facing == 1) {
	    g.drawImage(enemy[frame+4].getBufferedImage(), x, y, width, height, null);
	}

    }

    //get correct bounds of the enemy in regards to tile objects (turning, falling and not falling through platform)
    public void enemyBounds() {
	for (int i = 0; i < handler.tile.size(); i++) {
	    Tile ti = handler.tile.get(i);
	    if (ti.isSolid()) {
		if (getLowerBounds().intersects(ti.getBounds())) {
		    falling = false;
		    deltaY = 0;
		}else
		    falling = true;
		if (getLeftBounds().intersects(ti.getBounds())) {
		    this.velX = 2; //turn it around if collides with wall (left)
		    facing = 0;
		}
		if (getRightBounds().intersects(ti.getBounds())) {
		    this.velX = -2; //turn it around if collides with wall (right)
		    facing = 1;
		}
	    }
	}
    }

    //update every implementation
    @Override public void update() {
	x += velX; //set the default enemy speed upon update
	y += (int) deltaY;

	enemyBounds();
	animation();
	fall();


    }
}
