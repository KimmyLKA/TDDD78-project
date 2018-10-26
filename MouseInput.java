package com.company;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * Controls the responses to the mouse movements and clicks preformed during menu screen
 * uses the MenuScreen class to get access to the menu and MenuOption to get access to the
 * different options on said menu (and trigger the events through input)
 *
 */


public class MouseInput extends MouseAdapter


{
    //dimensions
    private int x,y;

    public MouseInput(){
    }

    //read the mouse inputs through iterating over the menu
    @Override public void mousePressed(final MouseEvent e) {
	for (int i = 0; i < Main.menuScreen.menuOption.length; i++) {
	    MenuOption menuOptions = Main.menuScreen.menuOption[i];

	    if (this.x >= menuOptions.getX() && this.y >= menuOptions.getY() && this.x <= menuOptions.getX() + menuOptions
		    .getWidth() &&
		this.y <= menuOptions.getY() + menuOptions.getHeight()) {
		menuOptions.triggerEvent();
	    }
	}
    }

    @Override public void mouseDragged(final MouseEvent e) {
    }

    //get the placement of the mouse on menu
    @Override public void mouseMoved(final MouseEvent e) {
        x = e.getX();
        y = e.getY();

    }
}