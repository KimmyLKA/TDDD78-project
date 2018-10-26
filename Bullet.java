package com.company;

import java.awt.*;

import static com.company.EntityType.*;
import static com.company.Main.killed;
import static com.company.Main.spriteSheet;

/**
 * Bullet class contains logic and rending of bullet object
 * initialized in the Main Class and used in the Pirate Class
 * as a PowerUp implementation.
 * Bullet derives from Entity Class as it uses the same properties
 * as a Pirate or a Enemy in regards to rendering and velocity
 */
public class Bullet extends Entity

{
    private static Sprite bullet = null;

    protected Bullet(final int x, final int y, final int width, final int height, final EntityType entityType,
		     final Handler handler, final int facing)
    {
	super(x, y, width, height, entityType, handler);

	//depending on facing render bullet in the right direction
	switch(facing){
	    case 0:
	        setVelX(5);
	        break;
	    case 1:
	        setVelX(-5);
	        break;
	}
    }
    //init sprite object
    public static void init(){
	bullet = new Sprite(spriteSheet, 6, 6);
    }

    //render bullet obj
    @Override public void render(final Graphics g) {
	g.drawImage(bullet.getBufferedImage(),getX(),getY(),getWidth(),getHeight(),null);
    }

    public void bulletCollisions(){

	//collision with wall
        for(int i=0; i <handler.tile.size();i++){
	    Tile ti = handler.tile.get(i);
	    if(ti.isSolid() && getBounds().intersects(ti.getBounds()))
	        die();
        }
        //collision with enemy
	for(int i=0; i <handler.entity.size();i++){
	    Entity en = handler.entity.get(i);
	    if(en.getEntityType() == ENEMY && getBounds().intersects(en.getBounds())){
		en.die();
		die();
		Main.setKilled(killed+1);
	    }
        }
    }

    @Override public void update() {
        x += velX*5; //make the bullet go superfast

	bulletCollisions();

    }
}
