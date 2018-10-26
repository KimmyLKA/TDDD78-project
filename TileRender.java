package com.company;

import java.awt.*;

import static com.company.Main.spriteSheet;

/**
 * TileRender extends the Tile class to access the tile objects and methods
 * main objective of this class is to render the graphic components of the
 * Tile class, inits all the sprites and sets them to values corresponding
 * with the correct values on spritesheet
*/

public class TileRender extends Tile
{

    //sprites used in other classes
    protected static Sprite money = null;
    protected static Sprite healthp = null;
    protected static Sprite fall = null;
    protected static Sprite sword = null;

    //sprites only used in this class
    private static Sprite gun = null;
    private static Sprite floor = null;
    private static Sprite win = null;

    //tile superclass
    protected TileRender(final int x, final int y, final int width, final int height, final boolean solid,
			 final TileType tileType, final Handler handler)
    {
	super(x, y, width, height, solid, tileType,handler);
    }

    @Override public void render(final Graphics g) {

        Sprite switcher = null; //used to make switchcases

        //draw the right tile depending on tiletype
        switch (getTileType()){
	    case HP:
		switcher = healthp;
	        break;
	    case GUN:
		switcher = gun;
	        break;
	    case MONEY:
		switcher = money;
	        break;
	    case FLOOR:
		switcher = floor;
	        break;
	    case WIN:
		switcher = win;
		break;
	    case FALL:
		switcher = fall;
		break;
	    case SWORD:
		switcher = sword;
		break;
	}
	assert switcher != null;
	g.drawImage(switcher.getBufferedImage(), x, y, width, height, null);

    }
    public static void init(){
        //set all tiles sprites from spritesheet
	floor = new Sprite(spriteSheet, 5,6);
	healthp = new Sprite(spriteSheet, 1, 6);
	gun = new Sprite(spriteSheet, 2, 6);
	money =new Sprite(spriteSheet, 4, 6);
	win = new Sprite(spriteSheet, 8,6);
	sword = new Sprite(spriteSheet, 3,6);
	fall = new Sprite(spriteSheet, 7, 6);
    }

}