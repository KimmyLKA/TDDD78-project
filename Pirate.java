package com.company;

import java.awt.*;
import java.awt.event.KeyEvent;

import static com.company.EntityType.*;
import static com.company.Main.killed;
import static com.company.Main.looseLife;
import static com.company.Main.spriteSheet;
import static com.company.PirateMode.*;
import static java.awt.event.KeyEvent.VK_SPACE;

/**
 * Contains:
 * - keyinputs in regards to pirate movements
 * - rendering of pirate
 * - logic (gravity, jumping, collision) related to pirate
 */
public class Pirate extends Entity
{

    //private used final ints
    private static final int THREESEC = 180;
    private static final int BULLETSIZE = 24;
    private static final int BULLETYOFFSET = 12;

    //sprite animations
    private static Sprite[] gunslinger = null;
    private static Sprite[] pirate = null;
    private static Sprite[] invincible = null;
    private static Sprite[] swordwielder = null;

    //private boolean to check if player is able to jump
    private boolean canJump = false;

    //to limit the length of invincibility of player when in INVICINBLE mode
    private int invincibilityTime = 0;

    protected Pirate(final int x, final int y, final int width, final int height, final EntityType entityType,
		     final Handler handler)
    {
	super(x, y, width, height, entityType, handler);
    }

    public static void init() {
	pirate = new Sprite[8];
	gunslinger = new Sprite[8];
	invincible = new Sprite[8];
	swordwielder = new Sprite[8];

	//todo!! fixa redundant kod

	for (int i = 0; i < pirate.length; i++) {
	    pirate[i] = new Sprite(spriteSheet, i + 1, 1);
	}
	for (int i = 0; i < gunslinger.length; i++) {
	    gunslinger[i] = new Sprite(spriteSheet, i + 1, 2);
	}
	for (int i = 0; i < invincible.length; i++) {
	    invincible[i] = new Sprite(spriteSheet, i + 1, 4);
	}
	for (int i = 0; i < swordwielder.length; i++) {
	    swordwielder[i] = new Sprite(spriteSheet, i + 1, 5);
	}
    }

    //used to get the animations working in the right direction dependant on facing and mode of pirate
    @Override public void render(final Graphics g) {

	Sprite[] switcher = new Sprite[0]; //used as local variable to be rid of redundancy in code

	switch (mode) {
	    case DEFAULT:
		switcher = pirate;
		break;
	    case GUNSLINGER:
		switcher = gunslinger;
		break;
	    case INVINCIBLE:
		switcher = invincible;
		break;
	    case SWORDWIELDER:
		switcher = swordwielder;
		break;
	}
	if (facing == 0) {
	    g.drawImage(switcher[frame].getBufferedImage(), x, y, width, height, null);
	}
	if (facing == 1) {
	    g.drawImage(switcher[frame + 4].getBufferedImage(), x, y, width, height, null);
	}
    }

    //check collisions of pirate and tiles, and makes pirate act accoordingly
    public void tileCollisison() {
	for (int i = 0; i < handler.tile.size(); i++) {
	    Tile ti = handler.tile.get(i);
	    if (ti.isSolid()) {
		if (getLeftBounds().intersects(ti.getBounds())) {
		    x = ti.getX() + width; //add width to not go through wall obj
		}
		if (getRightBounds().intersects(ti.getBounds())) {
		    x = ti.getX() - width;
		}
		if (getUpperBounds().intersects(ti.getBounds())) {
		    deltaY = ti.getY() + height;
		} else falling = true;
		if (getLowerBounds().intersects(ti.getBounds())) {
		    canJump = true;
		    falling = false;
		    deltaY = 0;
		}
	    }
	}
    }

    //make the pirate pick up powerups/points and sets mode if should
    public void powerUpCollision() {
	for (int i = 0; i < handler.tile.size(); i++) {
	    Tile ti = handler.tile.get(i);
	    if (!ti.isSolid() && getBounds().intersects(ti.getBounds())) {
		ti.die();
		switch (ti.getTileType()) {
		    case HP:
			Main.setLives(Main.lives + 1);
			break;
		    case MONEY:
			Main.setMoney(Main.money + 1);
			break;
		    case GUN:
			setMode(GUNSLINGER);
			break;
		    case SWORD:
			setMode(SWORDWIELDER);
			break;
		    case WIN:
			Main.setWinner(true);
			break;
		    case FALL:
			Main.setGameOver();
			break;
		}
	    }
	}
    }

    //dependant on pirate mode the collisions of pirate and enemy is different
    public void enemyCollision() {
	for (int i = 0; i < handler.entity.size(); i++) {
	    Entity e = handler.entity.get(i);
	    if (e.getEntityType() == ENEMY && getBounds().intersects(e.getBounds())) {
		switch (getMode()) {
		    case DEFAULT:
			looseLife();
			break;
		    case SWORDWIELDER:
			e.die();
			Main.setKilled(killed+1); //sword kills zombie ==  points
			setMode(DEFAULT); //use sword once on enemy, then back to default mode
			break;
		    case GUNSLINGER:
			setMode(INVINCIBLE); //loose gun not life
			break;
		}
	    }
	}
    }

    //player controls uses KeyEvent inputs
    public void controls() {

	if (KeyInput.isKeyDown(KeyEvent.VK_UP)) {
	    	    jump(10);
	}
	if (KeyInput.isKeyDown(KeyEvent.VK_RIGHT)) {
	    setVelX(5);
	    facing = 0;
	}
	if (KeyInput.isKeyDown(KeyEvent.VK_LEFT)) {
	    setVelX(-5);
	    facing = 1;
	}
	if (KeyInput.isKeyDown(VK_SPACE)) {
	    if (mode == GUNSLINGER) {
		switch (facing) { //if we have a gun we can shoot, unlimited ammo because that's cool
		    case 0:
			handler.addEntity(
				new Bullet(getX() + BULLETSIZE, getY() + BULLETYOFFSET,
					   BULLETSIZE, BULLETSIZE, BULLET, handler, facing));
			break;
		    case 1:
			handler.addEntity(
				new Bullet(getX(), getY() + BULLETYOFFSET, BULLETSIZE,
					   BULLETSIZE, BULLET, handler, facing));
			break;
		}
	    }
	}
    }

    //jump algorithm, makes the pirate able to jump upon key input
    public void jump(double jumpHeight) {
	if (canJump) {
	    deltaY = (int) -jumpHeight;
	    canJump = false;
	}
    }

    @Override public void update() {
        //set movement
	x += velX; //update position
	y += (int) deltaY;

	//init all the collision/fall/control algorithms
	tileCollisison();
	powerUpCollision();
	enemyCollision();
	fall();
	controls();

	if (mode == INVINCIBLE) {
	    invincibilityTime++;
	    if (invincibilityTime >= THREESEC) { //invincible for 3 sec upon hit
		setMode(DEFAULT);
		invincibilityTime = 0;
	    }
	}
	animation(); //same framerate for every animation (enemy/pirate class) taken from Entity Class
    }
}
