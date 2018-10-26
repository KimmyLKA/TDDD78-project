package com.company;

/**
 * TileType contains the different kinds of enums for the tiles rendered within the game
 * used to make TileRender simpler through a switch case
 */
public enum TileType
{
    /**
      * Enum for the SHIPFLOOR type
      */
    FLOOR,

    /**
     * HP enum used to add ++ to lives
     */
    HP,

    /**
     * SWORD - MAKES PIRATE INVINCIBLE
     */
    MONEY,

    /**
     * GUN - powerup that gives the pirate a gun
     */
    GUN,

    /**
     * winnter block
     */
    WIN,

    /**
     * enum used to get ac
     */
    SWORD,

    /**
     * enum used to get access to the minus hp code, used in powerdown algorithm
     */
    FALL


}