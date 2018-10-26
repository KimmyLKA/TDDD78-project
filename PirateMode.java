package com.company;

/**
 * PireMode enums are used for the different variables of modes that the
 * player character can have, ultilized in classes which modify these
 */
public enum PirateMode
{
    /**
     * default mode
     */
    DEFAULT,

    /**
     * gunmode for player shoot shoot pow pow
     */
    GUNSLINGER,

    /**
     * Invincible, activiated when player hit by enemy - for 3 seconds
     */
    INVINCIBLE,

    /**
     * Used to get the sword sprites and the sword mode
     */
    SWORDWIELDER
}
