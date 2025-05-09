package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import entity.Zombie;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{

	
	
	//Screen settings
	final int originalTileSize = 20;//16x16 tiles
	final int scale = 3;//scale the size of character
	
	public final int tileSize = originalTileSize * scale;// 48 * 48
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
	public final int screenHeight = tileSize * maxScreenRow; // 576 pixels
	
	//World settings
	
	public final int maxWorldCol = 100;
	public final int maxWorldRow = 100;
//	public final int worldWidth = tileSize * maxWorldCol;
//	public final int worldHeight = tileSize * maxWorldRow;
	
	
	//FOR FULL SCREEN
	int screenWidth2 = screenWidth;
	int screenHeight2 =screenHeight;
	BufferedImage tempScreen;
	Graphics2D g2;
	
	int FPS = 60;
	
	TileManager tileM = new TileManager(this);
	public KeyHandler KeyH = new KeyHandler(this);
	Sound se = new Sound();
	Sound music = new Sound();
	
	public CollisionChecker cChecker = new CollisionChecker(this);
	

	public AssetSetter aSetter = new AssetSetter(this);
	public UI ui = new UI(this);
	Thread gameThread;

	
	//ENTITY AND OBJECTS
	public Player player = new Player(this, KeyH);
	public Entity obj[] = new Entity[100];
	public Entity[] monsters = new Zombie[20]; // Adjust size if needed

	ArrayList<Entity> entityList = new ArrayList<>();
	public ArrayList<Entity> projectiles = new ArrayList<>();
	
	
	//GAME STATE
	public int gameState;
	public final static int titleState=0;
	public final static int playState = 1;
	public final static int pauseState=2;
	public final static int characterState =3;
	
			
		
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true); // helps improve the rendering performance
		this.addKeyListener(KeyH);
		
		this.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mousePressed(java.awt.event.MouseEvent e) {
		        if (gameState == playState) {
		            player.shoot(e.getX(), e.getY());
		        }
		    }
		});

		this.setFocusable(true);
	}
	
	public void setupGame() {
		aSetter.setObject();
		playMusic(0);
		gameState = playState;
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();//automatically calls the run method
	}

	public void setFullScreen() {
	    java.awt.GraphicsEnvironment ge = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment();
	    java.awt.GraphicsDevice gd = ge.getDefaultScreenDevice();
	    gd.setFullScreenWindow(Main.window);

	    screenWidth2 = Main.window.getWidth();
	    screenHeight2 = Main.window.getHeight();
	}
	
	@Override
	public void run() { //Game loop
		
		double drawInterval = 1000000000/FPS;// 0.01666
		double delta =0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
	
		
		while(gameThread != null) {// 1 UPDATE the information such as character positions //2 DRAW the screen with the updated info
			
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			if(delta >= 1) {
				update(); //calls update method		
				repaint();// calls paintComponent method
				delta--;
				drawCount++;
			}
			if(timer >= 1000000000) {
//				System.out.println("FPS: " + drawCount);
				drawCount =0;
				timer =0;
			}
		
		
		}
		
	}
	public void update() {
		
	        if (gameState == playState) {
	            player.update();
	 
	            for (int i = 0; i < monsters.length; i++) {
	                if (monsters[i] != null) {
	                    ((Zombie) monsters[i]).update();
	                }
	            }
	            for (int i = 0; i < projectiles.size(); i++) {
	                Entity projectile = projectiles.get(i);
	                if (projectile != null) projectile.update();
	            }


	          
	        }

	    
	    if(gameState == pauseState) {

	    }
	    if(gameState == characterState) {
	       
	    }
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);

	    Graphics2D g2 = (Graphics2D) g;

	    // Calculate scaling factor
	    double scaleX = (double) screenWidth2 / screenWidth;
	    double scaleY = (double) screenHeight2 / screenHeight;
	    double scale = Math.min(scaleX, scaleY); // maintain aspect ratio

	    // Center the scaled game screen
	    int xOffset = (int) ((screenWidth2 - (screenWidth * scale)) / 2);
	    int yOffset = (int) ((screenHeight2 - (screenHeight * scale)) / 2);

	    g2.translate(xOffset, yOffset);   // move origin to center
	    g2.scale(scale, scale);           // scale the virtual resolution

	    // Draw game content at virtual resolution (768x576)
	    tileM.draw(g2);
	    for (Entity monster : monsters) {
	        if (monster != null) entityList.add(monster);
	    }
	    entityList.add(player);
	    for (Entity objEntity : obj) {
	        if (objEntity != null) entityList.add(objEntity);
	    }

	    Collections.sort(entityList, Comparator.comparingInt(e -> e.worldY));
	    for (Entity e : entityList) e.draw(g2);
	    entityList.clear();

	    for (Entity bullet : projectiles) {
	        bullet.draw(g2);
	    }
	    ui.draw(g2);
	    

	    g2.dispose();
	}

	
	public void playMusic(int i) {
		music.setFile(i);
//		music.start();
//		music.loop();
		
	}
	
	public void stopMusic() {
		
		music.stop();
	}
	
	public void playSoundEffect(int i) {
		
//		se.setFile(i);
//		se.start();
	}
	
	
}











