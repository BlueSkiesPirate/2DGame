package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{

	
	
	//Screen settings
	final int originalTileSize = 16;//16x16 tiles
	final int scale = 3;//scale the size of character
	
	public final int tileSize = originalTileSize * scale;// 48 * 48
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
	public final int screenHeight = tileSize * maxScreenRow; // 576 pixels
	
	//World settings
	
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
//	public final int worldWidth = tileSize * maxWorldCol;
//	public final int worldHeight = tileSize * maxWorldRow;
	
	int FPS = 60;
	
	TileManager tileM = new TileManager(this);
	KeyHandler KeyH = new KeyHandler();
	Sound se = new Sound();
	Sound music = new Sound();
	
	public CollisionChecker cChecker = new CollisionChecker(this);
	

	public AssetSetter aSetter = new AssetSetter(this);
	public UI ui = new UI(this);
	Thread gameThread;
	//Entity and object
	public Player player = new Player(this, KeyH);
	public SuperObject obj[] = new SuperObject[10];
			
		
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true); // helps improve the rendering performance
		this.addKeyListener(KeyH);
		this.setFocusable(true);
	}
	
	public void setupGame() {
		aSetter.setObject();
		playMusic(0);
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();//automatically calls the run method
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
				System.out.println("FPS: " + drawCount);
				drawCount =0;
				timer =0;
			}
		
		
		}
		
	}
	public void update() {
		player.update();
	}
	
	public void paintComponent(Graphics g) { //standard method
		
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g; //Graphics2D extends the Graphics class
		
		tileM.draw(g2);
		for(int i= 0; i<obj.length; i++) {
			if(obj[i] != null) {
				obj[i].draw(g2, this);
			}
		}
		player.draw(g2);
		ui.draw(g2);
		g2.dispose();// Dispose of this graphics context and release any system recourses that it is using
	}
	
	public void playMusic(int i) {
		music.setFile(i);
		music.start();
		music.loop();
		
	}
	
	public void stopMusic() {
		
		music.stop();
	}
	
	public void playSoundEffect(int i) {
		
		se.setFile(i);
		se.start();
	}
	
	
}











