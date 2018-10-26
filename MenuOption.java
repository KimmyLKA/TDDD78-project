package com.company;

import java.awt.*;

/**
 * MenuOption contains the logic of the Main Menu
 */
public class MenuOption
{
    private static final int FONTSIZE = 25;
    private int x,y,height,width;
    private String label;

    //superclass for menu
      protected MenuOption(int x, int y, int width, int height, String label) {
          this.x = x;
          this.y = y;
          this.width = width/2; //to get it centered
          this.height = height;
          this.label = label;
      }

      //set up the headline
      public void render(Graphics g) {
          g.setColor(Color.WHITE);
          g.setFont(new Font("Ariel", 1, FONTSIZE));
          FontMetrics fm = g.getFontMetrics();
          int stringX = (this.width - fm.stringWidth(this.label)) / 2;
          int stringY = fm.getAscent() + (this.height - (fm.getAscent() + fm.getDescent())) / 2;
	  g.drawString(this.label, this.x + stringX, this.y + stringY);

      }

      //make things happen when clicking buttons on main menu upon game start
      public void triggerEvent() {
          if(this.label.toLowerCase().contains("start")){
              Main.setPlaying(true);
              Main.setShowMenu(false);
          }
	  if(this.label.toLowerCase().contains("exit")) {
              System.exit(0);
          }
      }

      //getters and setters
      public int getX() {
          return this.x;
      }
      public int getY() {
          return this.y;
      }
      public int getWidth() {
          return this.width;
      }
      public int getHeight() {
          return this.height;
      }


  }

