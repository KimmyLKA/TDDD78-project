package com.company;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * SpriteSheet class uses BufferedImage to get images from the spritesheet png file
 * Used in Pirate, Enemy, Buller and TileRender classes to access the spritesheet
 * and get the right sprites from it
 */

public class SpriteSheet

{
    private BufferedImage spriteSheet = null;

    public SpriteSheet(String path){
	try {
	    spriteSheet = ImageIO.read(getClass().getResource(path));
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    public BufferedImage getSprite(int x, int y){

	final int spriteSize = 64; //sprites are 64x64 pixlels
	return spriteSheet.getSubimage(spriteSize * x - spriteSize, spriteSize * y - spriteSize, spriteSize, spriteSize); //get dimansions right lmao
	//minus 1 to get right coords, otherwise will run out
    }

}
