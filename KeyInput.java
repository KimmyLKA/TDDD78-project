package com.company;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * KeyInput handles the logic of the control input and their effect on the pirate character
 */
public class KeyInput extends KeyAdapter
{
    private static final boolean[] KEYS = new boolean[256];

    public void keyPressed(final KeyEvent e) {
	KEYS[e.getKeyCode()] = true;
    }

    public void keyReleased(final KeyEvent e) {
	KEYS[e.getKeyCode()] = false;
    }

    public static boolean isKeyDown(int keyCode){
        return KEYS[keyCode];
    }

}
