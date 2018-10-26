package com.company;

import java.awt.image.BufferedImage;

/**
 * Sprite contains the methods used for accessing individual sprites of spritesheet
 */
public class Sprite
{


    private BufferedImage bufferedImage = null;

    public Sprite(SpriteSheet spriteSheet, int x, int y){
        //set the sprite to a bufferedimage from the spritesheet
        bufferedImage = spriteSheet.getSprite(x,y);
    }

    public BufferedImage getBufferedImage(){
        return bufferedImage;
    }

}
