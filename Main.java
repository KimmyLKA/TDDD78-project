package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Main Class
 * Contains:
 * - method for drawing the game window and menus
 * - public variables used over classes
 * - initializes the game classes and variables
 * - draws and contains the logic of the winner/loser screens
 */

public class Main extends Canvas implements Runnable
{

    //set values
    protected static final int BIGFONTSIZE = 50;
    private static final int SPRITESIZE = 75;
    private static final int SMALLFONTSIZE = 20;
    protected static final int WIDTH = 1040;
    protected static final int HEIGHT = WIDTH / 14 * 10;
    private static final double NANOSEC = 1000000000.0 / 60.0;

    //point varibles
    protected static int lives = 0;
    protected static int money = 0;
    protected static int killed = 0;

    //booleans
    private static boolean initStart = true;
    protected static boolean gameOver = false;
    protected static boolean winner = false;
    protected static boolean playing = false;
    protected static boolean showMenu = true;
    private boolean running = false;

    protected static Handler handler = new Handler();
    private Thread thread = null;
    private Camera camera = null;
    private BufferedImage background = null;
    protected static MenuScreen menuScreen = new MenuScreen();

    //spritesheet used for rendering
    protected static SpriteSheet spriteSheet = new SpriteSheet("resource/sp4.png");


    //getters and setters
    public static void setLives(final int lives) {
	Main.lives = lives;
    }

    public static void setMoney(final int money) {
	Main.money = money;
    }

    public static void setKilled(final int killed) {
	Main.killed = killed;
    }

    public static void setWinner(final boolean winner) {
	Main.winner = winner;
    }

    public static void setPlaying(final boolean playing) {
	Main.playing = playing;
    }

    public static void setShowMenu(final boolean showMenu) {
	Main.showMenu = showMenu;
    }

    //start a game thread
    private void start() {
	if (running) return;

	running = true;
	thread = new Thread(this, "Game Thread");
	thread.start(); //basic init of thread
    }

    //stop a game thread
    private void stop() {
	if (!running) return;

	running = false;
	try {
	    thread.join(); //stop thread (risky code with catch)
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
    }

    //get the background image
    private void generateBackground() {
	try {
	    background = ImageIO.read(getClass().getResource("resource/bg.png"));
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    //set all the initial values used in start of games
    private static void setStats() {
	initStart = true;
	winner = false;
	gameOver = false;
	lives = 3; //start off with three lives everytime
	money = 0;
	killed = 0;
    }

    //initialize the game
    private void init() {
	camera = new Camera();

	final MouseInput mouseInput = new MouseInput();

	addKeyListener(new KeyInput());
	addMouseListener(mouseInput);
	addMouseMotionListener(mouseInput);

	generateBackground();

	TileRender.init();
	Enemy.init();
	Pirate.init();
	Bullet.init();



    }

    //run the game
    @Override public void run() {

	init();
	//requestFocus(); //brings frame into focus when running
	long lastTime = System.nanoTime();
	double delta = 0;
	while (true) {
	    if (!(running)) break;
	    long now = System.nanoTime();
	    delta += (now - lastTime) / NANOSEC; //set to seconds
	    lastTime = now;
	    while (delta >= 1) { //update each second
		update();
		delta--;
	    }
	    render(); //render while running
	}
	stop();
    }

    //use to set when exit
    public static void notStarting() {
	initStart = false;
    }

    public static void notPlaying() {
	playing = false;
    }

    //update the game (each "tick")
    public void update() {
	if (playing) handler.update();

	for (int i = 0; i < handler.entity.size(); i++) {
	    Entity en = handler.entity.get(i);
	    if (en.getEntityType() == EntityType.PIRATE) {
		camera.update(en);
	    }
	}

	if (initStart) {
	    handler.clearLevel();
	    handler.mapCreator();
	    Entity.setMode(PirateMode.DEFAULT);
	    notStarting();
	}
	if (KeyInput.isKeyDown(KeyEvent.VK_ENTER)) {
	    if (Main.winner || Main.gameOver) Main.setShowMenu(true);
	}

    }


    //set up the screen dimensions
    Main() {
	Dimension size = new Dimension(WIDTH, HEIGHT);
	setPreferredSize(size);
	setMaximumSize(size);
	setMinimumSize(size);
    }

    //use to loose life and to know if to exit game when lives <= 0
    public static void looseLife() {
	lives--;
	Entity.setMode(PirateMode.INVINCIBLE);
	checkGameOver();
    }

    public static void checkGameOver() {
	if(lives <=0)
	    gameOver =true;
    }

    public static void setGameOver(){
        gameOver = true;
    }

    //make the strings in menues centered on screen
    public static void drawCenteredString(String s, int w, int h, Graphics g) {
      FontMetrics fm = g.getFontMetrics();
      int x = (w - fm.stringWidth(s)) / 2;
      int y = (fm.getAscent() + (h - (fm.getAscent() + fm.getDescent())) / 2);
      g.drawString(s, x, y);
    }

    //show the background image, points and menu in game
    public void render() {
	BufferStrategy bs = getBufferStrategy();
	if (bs == null) {
	    createBufferStrategy(3);
	    return;
	}
	Graphics g = bs.getDrawGraphics();
	g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
	if (!gameOver && playing) {

	    g.drawImage(TileRender.money.getBufferedImage(), 10, HEIGHT/(100)-10-10, SPRITESIZE, SPRITESIZE, null);
	    g.setColor(Color.WHITE);
	    g.setFont(new Font("Arial", Font.BOLD, 10));
	    g.drawString("x" + money, WIDTH/10-10*2, HEIGHT/(10+5));

	    g.drawImage(TileRender.healthp.getBufferedImage(), 10, HEIGHT/(10*2)+5, SPRITESIZE, SPRITESIZE, null);
	    g.setColor(Color.WHITE);
	    g.setFont(new Font("Arial", Font.BOLD, 10));
	    g.drawString("x" + lives, WIDTH/10-10*2, HEIGHT/(9));

	    g.drawImage(Enemy.enemy[2].getBufferedImage(), 10, HEIGHT/8, SPRITESIZE, SPRITESIZE, null);
	    g.setColor(Color.WHITE);
	    g.setFont(new Font("Arial", Font.BOLD, 10));
	    g.drawString("x" + killed, WIDTH/10-10*2, HEIGHT/(5));

	    g.translate(camera.getX(), camera.getY());
	    handler.render(g);

	}
	if(gameOver || winner ){
	    g.drawImage(TileRender.money.getBufferedImage(), WIDTH/2-10, HEIGHT/2-10, SPRITESIZE, SPRITESIZE, null);
	    g.setColor(Color.WHITE);
	    g.setFont(new Font("Arial", Font.BOLD, 10));
	    g.drawString("x" + Main.money, WIDTH/2+100/2, HEIGHT/2+100/2);

	    g.drawImage(Enemy.enemy[2].getBufferedImage(), WIDTH/2-10, HEIGHT/3, SPRITESIZE, SPRITESIZE, null);
	    g.setColor(Color.WHITE);
	    g.setFont(new Font("Arial", Font.BOLD, 10));
	    g.drawString("x" + killed, WIDTH/2+100/2, HEIGHT/3+100/2);

	    final int sumPoints = killed * 10 + money * 5;
	    g.setColor(Color.WHITE);
	    g.setFont(new Font("Arial", Font.BOLD, BIGFONTSIZE));
	    drawCenteredString("POINTS: " + sumPoints, WIDTH, HEIGHT/3,g);

	    g.setColor(Color.WHITE);
	    g.setFont(new Font("Arial", Font.BOLD, SMALLFONTSIZE));
	    drawCenteredString("PRESS [ENTER] TO RETURN TO MENU", WIDTH, HEIGHT*2,g);

	    notPlaying();
	}
	if(gameOver) {
	    g.setColor(Color.WHITE);
	    g.setFont(new Font("Arial", Font.BOLD, BIGFONTSIZE));
	    drawCenteredString("GAME OVER", WIDTH, HEIGHT/6,g);

	}
	if(winner){
	    g.setColor(Color.WHITE);
	    g.setFont(new Font("Arial", Font.BOLD, BIGFONTSIZE));
	    drawCenteredString("YOU WIN!", WIDTH, HEIGHT/6,g);
	}
	if(showMenu) {
	    menuScreen.render(g);
	    setStats();
	}
	g.dispose();
	bs.show();
    }

    //focus on the player (use to render the right parts of the game, make rendering work better)
    public static Rectangle getFrame(){
        for(int i = 0; i < handler.entity.size(); i++){
            Entity en = handler.entity.get(i);
            if(en.getEntityType() == EntityType.PIRATE) {
		return new Rectangle(en.getX() - (WIDTH / 2), en.getY() - (HEIGHT / 2), WIDTH, HEIGHT );
	    }
	}
	//never called but used to not make null exception
	return new Rectangle(WIDTH, HEIGHT , WIDTH , HEIGHT);
    }

    //set up the JFrame basics
    public static void main(String[] args) {
        Main game = new Main();
        JFrame frame = new JFrame("Princess Pirate Queen");
        frame.add(game);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        game.start();
	  }
    }