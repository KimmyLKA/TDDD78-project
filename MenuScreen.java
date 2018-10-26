package com.company;


import java.awt.*;

import static com.company.Main.*;

/**
 *
 * Handles the rendering of the Menu screen and uses MenuOption class to
 * render the different options seen on the first main menu upon game start
 *
 */
public class MenuScreen
{

    // menuOption buttons used to write them out on the screen and then in mouseinput read them
    protected MenuOption[] menuOption;

    //set up the menuoptions on the main menu
    public MenuScreen(){

        menuOption = new MenuOption[2];

        menuOption[0] = new MenuOption(WIDTH/2,HEIGHT/3,100,100,"Start Game");
	menuOption[1] = new MenuOption(WIDTH/2,HEIGHT/2,100,100,"Exit Game");
    }

    //render the menuoptions on the main menu using graphics objs
    public void render(Graphics g){
	for (final MenuOption menuOption : menuOption) {
	    menuOption.render(g);
	}
	g.setColor(Color.WHITE);
	g.setFont(new Font("Arial",Font.BOLD, BIGFONTSIZE));
	drawCenteredString("Princess Pirate Queen vs Zombie Island", WIDTH,100,g);


    }
}