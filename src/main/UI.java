package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;
import javax.swing.JButton;

import entity.Entity;
import object.OBJ_Key;

public class UI {
	GamePanel gp;
	Graphics2D g2;
	Font arial_25,arial_80B;
	
	public int commandNum =0;
	
//	BufferedImage keyImage;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter=0;
	public boolean gameFinished = false;
	
	double playTime;
	DecimalFormat dFormat = new DecimalFormat("#0.00");
	
	
	public int slotCol =0;
	public int slotRow = 0;
	
	public UI(GamePanel gp) {
		this.gp = gp;
		arial_25 = new Font("Arial", Font.PLAIN, 25);
		arial_80B = new Font("Arial", Font.BOLD, 80);
//		keyImage = key.image;
	}
	
	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}
	
	public void draw(Graphics2D g2) {
		this.g2 = g2;//We do this so that we can use this g2 in other methods of this class.
		g2.setFont(arial_25);
		g2.setColor(Color.WHITE);
		
		if(gp.gameState == gp.playState) {
			drawGun();
			drawPlayScreen(gp.player.worldX, gp.player.worldY);
		}
		if(gp.gameState == gp.pauseState) {
			drawPauseScreen();
		}
		if(gp.gameState == gp.titleState) {
			drawTitleScreen();
		}if(gp.gameState == gp.deadState) {
			drawDeadScreen();
		}if(gp.gameState == gp.winState) {
			drawWinScreen();
		}
		
		
		
		
		//CHARACTER STATE
		if(gp.gameState ==gp.characterState) {
			drawCharacterScreen();
			drawInventory();
		}
	}
	
	public void drawCharacterScreen() {
		//CREATE A FRAME
		final int frameX =gp.tileSize *2;
		final int frameY = gp.tileSize;
		final int frameWidth = gp.tileSize *5;
		final int frameHeight = gp.tileSize *10;
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		
		//TEXT
		g2.setColor(Color.WHITE);
		g2.setFont(g2.getFont().deriveFont(32F));
		int textX = frameX + 20;
		int textY = frameY +gp.tileSize;
		final int lineHeight = 32;
		
		// NAMES
		g2.drawString("Level", textX, textY);
		textY += lineHeight;
		g2.drawString("Health", textX, textY);
		textY += lineHeight;
		g2.drawString("Strength", textX, textY);
		textY += lineHeight;
		g2.drawString("Defense", textX, textY);
		textY += lineHeight;
		g2.drawString("Exp", textX, textY);
		textY += lineHeight;
		g2.drawString("Money", textX, textY);
		textY += lineHeight;

		//VALUES
		int endX = (frameX + frameWidth) - 30;
		
		textY = frameY+ gp.tileSize;
		String value;
		
		value = String.valueOf(gp.player.level);
		textX = getXForRightJustify(value, endX);
		g2.drawString(value, textX, textY);
		
		value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
		textX = getXForRightJustify(value, endX);
		textY += lineHeight;
		g2.drawString(value, textX, textY);
		
		value = String.valueOf(gp.player.strength);
		textX = getXForRightJustify(value, endX);
		textY += lineHeight;
		g2.drawString(value, textX, textY);
		
		value = String.valueOf(gp.player.defense);
		textX = getXForRightJustify(value, endX);
		textY += lineHeight;
		g2.drawString(value, textX, textY);
		
		value = String.valueOf(gp.player.exp);
		textX = getXForRightJustify(value, endX);
		textY += lineHeight;
		g2.drawString(value, textX, textY);
		
		value = String.valueOf(gp.player.money);
		textX = getXForRightJustify(value, endX);
		textY += lineHeight;
		g2.drawString(value, textX, textY);
	}
	
	public void drawSubWindow(int x, int y, int width, int height) {
		Color c =new Color(0,0,0,210);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height,  35,35);
		
		c = new Color(255,255,255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x+5, y+5,  width-10, height-10, 25, 25);
	}
	
	public void drawInventory() {
		
		//FRAME
		int frameX = gp.tileSize *9;
		int frameY = gp.tileSize;
		int frameWidth = gp.tileSize *6;
		int frameHeight = gp.tileSize *5;
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		//SLOT 
		final int slotXStart = frameX + 20;
		final int slotYStart = frameY +20;
		int slotX = slotXStart;
		int slotY = slotYStart;
		int slotSize = gp.tileSize+3;
		
		//DRAW PLAYER ITEMS
		for(int i=0; i<gp.player.inventory.size(); i++) {
			
			//EQUIP CURSOR
			if(gp.player.inventory.get(i) == gp.player.currentWeapon ||gp.player.equippedWeapons.contains(gp.player.inventory.get(i))){// I can add a second condition for a secondary weapon
				g2.setColor(new Color(240,190,90));
				g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
			}
			
			g2.drawImage(gp.player.inventory.get(i).down1, slotX, slotY,gp.tileSize, gp.tileSize, null);
			slotX += slotSize;
			if(i ==4 || i == 9 || i ==14) {
				slotX =slotXStart;
				slotY += gp.tileSize;
			}
		}
		
		// CURSOR
		int cursorX = slotXStart + (slotSize * slotCol) ;
		int cursorY = slotYStart + (slotSize * slotRow);
		int cursorWidth =gp.tileSize;
		int cursorHeight = gp.tileSize;
		
		g2.setColor(Color.white);
		g2.setStroke(new BasicStroke(1));
		g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
		
		
		//DESCRIPTION WINDOW
		int dFrameX = frameX;
		int dFrameY = frameY +frameHeight +20;
		int dFrameWidth = frameWidth;
		int dFrameHeight = gp.tileSize * 3;
		
		//DRAW TEXT
		int textX = dFrameX +20;
		int textY= dFrameY + gp.tileSize;
		g2.setFont(g2.getFont().deriveFont(28F));
		int itemIndex = getItemIndexOnSlot();
		if(itemIndex < gp.player.inventory.size()) {
			drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
			for(String line: gp.player.inventory.get(itemIndex).description.split("\n")){
				g2.drawString(line, textX, textY);
				textY += 32;
			}
			
		}
	}
	
	public int getItemIndexOnSlot() {
		int itemIndex = slotCol + (slotRow *5);
		return itemIndex;
	}
	
	public void drawPauseScreen() {
		String text = "Paused";
		int x = getXforCenterText(text);
		int y = gp.screenHeight/2;
		
		g2.drawString(text, x, y);
	}
	
	public void drawPlayScreen(int worldX, int worldY) {
	    String text = "X = " + (worldX / gp.tileSize) + "  Y = " + (worldY / gp.tileSize);
	    int x = gp.tileSize * 1;
	    int y = 100;
	    int monsterCount =0;
	    // Draw coordinates
	    g2.drawString(text, x, y);
	    for (int i = 0; i < gp.monsters.length; i++) {
            Entity monster = gp.monsters[i];
            if (monster != null) {
               monsterCount++;
   
            }
        }
	    
	    if(monsterCount ==0) {
	    	gp.win();
	    	}
	    text = "Zombies Remaining: " + monsterCount;
	    x= getXforCenterText(text);
	    g2.drawString(text, x, y);
	    
	
	    // Calculate health level from 0 to 5
	    int healthLevel = (int)(((double)gp.player.life / gp.player.maxLife) * 5);
	    healthLevel = Math.max(0, Math.min(5, healthLevel)); // Clamp between 0 and 5

	    BufferedImage healthBarImage = null;
	    BufferedImage bigGunContainerImage = null;
	    BufferedImage smallGunContainerImage1 = null;
	    BufferedImage smallGunContainerImage2 = null;
	    try {
	        healthBarImage = ImageIO.read(getClass().getResourceAsStream("/healthBar/hpBar_" + healthLevel + ".png"));
	        bigGunContainerImage = ImageIO.read(getClass().getResourceAsStream("/weapon_containers/big1.png"));
	        smallGunContainerImage1 = ImageIO.read(getClass().getResourceAsStream("/weapon_containers/small1.png"));
	        smallGunContainerImage2 = ImageIO.read(getClass().getResourceAsStream("/weapon_containers/small2.png"));
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    if(bigGunContainerImage != null || smallGunContainerImage1 != null|| smallGunContainerImage2 != null) {
	    	g2.drawImage(bigGunContainerImage, gp.tileSize * 11 +25, gp.tileSize * 9, 300, 200,null);
	    	g2.drawImage(smallGunContainerImage1, (gp.tileSize * 12) + 50, gp.tileSize * 9 -75, 200, 150,null);
	    	g2.drawImage(smallGunContainerImage2, (gp.tileSize * 12) + 50, gp.tileSize * 9 -150, 200, 150,null);
	    	
	    	
	    	if(gp.player.equippedWeapons.get(0) != null) {
	    		g2.drawImage(gp.player.equippedWeapons.get(0).down1, (gp.tileSize * 11) + 135, gp.tileSize * 9 +65, 75, 75,null);
	    	}
	    	
	    	g2.drawString(gp.player.currentWeapon.currentAmmo + "/" + gp.player.currentWeapon.maxClip,gp.tileSize * 11 ,  gp.tileSize * 9 + 150);

	    }
	    
	    for (Entity zombie : gp.monsters) {
	        if (zombie == null) continue;

	        // Draw health bar
	        int barWidth = 40;
	        int barHeight = 6;
	        int healthBarX = zombie.worldX - gp.player.worldX + gp.screenWidth/2 - barWidth/2 + zombie.solidArea.width/2 -20;
	        int healthBarY = zombie.worldY - gp.player.worldY + gp.screenHeight/2 - 40;

	        float healthPercent = (float) zombie.life / (float) zombie.maxLife;

	        g2.setColor(Color.DARK_GRAY);
	        g2.fillRect(healthBarX, healthBarY, barWidth, barHeight);

	        g2.setColor(Color.GREEN);
	        g2.fillRect(healthBarX, healthBarY, (int)(barWidth * healthPercent), barHeight);

	        g2.setColor(Color.BLACK);
	        g2.drawRect(healthBarX, healthBarY, barWidth, barHeight);
	        
	        g2.setFont(new Font("Arial", Font.PLAIN, 15));
	        g2.drawString(zombie.life + "/" + zombie.maxLife, healthBarX, healthBarY);// + barWidth/2
	    }

	    
	    // Draw health bar on screen
	    if (healthBarImage != null) {
	        g2.drawImage(healthBarImage, 25, -25, gp.tileSize * 4, gp.tileSize *3, null);
	    }

	}

 	public void drawTitleScreen() {
		String text = "Zombie Fight";
		int x = getXforCenterText(text);
		int y = gp.tileSize *3;
		
		
		
		g2.drawString(text, x, y);
		
		int width = gp.tileSize *5;
		int height = gp.tileSize *3;
		BufferedImage image = null;
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/title.png"));
		} catch (IOException e) {
	
			e.printStackTrace();
		}
		g2.drawImage(image, 0, 0, gp.tileSize * gp.maxScreenCol, gp.tileSize * gp.maxScreenRow,null);
		
		g2.setColor(new Color(10,10,10));
		g2.fillRect((gp.tileSize * gp.maxScreenCol)/2 -(width /2), (gp.tileSize * gp.maxScreenRow)/2 -(height/2) + 25, width, height);
		g2.setColor(new Color(255,255,255));
		y = (gp.tileSize * gp.maxScreenRow)/2;
		text = "PLAY";
		g2.drawString(text, x, y);
		text = "QUIT";
		g2.drawString(text, x, y +35);
		text = "Future feature";
		g2.drawString(text, x, y +70);
		
		if(commandNum ==0) {
			g2.drawString(">", x - 20, y);
		}else if(commandNum ==1){
			g2.drawString(">", x - 20, y +35);
		}else {
			g2.drawString(">", x - 20, y +70);
		}
		
	}
	
	public void addMessage(String text) {
		int x =getXforCenterText(text);
		int y = gp.screenHeight/2;
		
		g2.drawString(text, x, y);
	}
	
	public int getXforCenterText(String text) {
		int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth() ;
		int x = gp.screenWidth/2 - length/2;
		return x;
	}
	
	public int getXForRightJustify(String text, int endX) {
		int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth() ;
		int x = endX - length;
		return x;
	}
	
	public void drawGun() {
		
	    // Get the center of the screen (and the gun position)
	    int centerX = (gp.screenWidth / 2) - (gp.tileSize / 2)+ 30; 
	    int centerY = (gp.screenHeight / 2) - (gp.tileSize / 2)+30; 

	    // Get the mouse coordinates
//	    PointerInfo a = MouseInfo.getPointerInfo();
	    if(MouseInfo.getPointerInfo() != null) {
	    	Point point = gp.getMousePosition();
	    	
	    	if(point != null) {
	    		int mouseX = (int) point.getX();
			    int mouseY = (int) point.getY();

			    // Calculate the angle between the gun and the mouse
			    double angle = Math.atan2(mouseY - centerY, mouseX - centerX);

			    // Draw the line from the center of the screen to the mouse position
			    g2.setColor(new Color(255,0,0));
//			    g2.drawLine(centerX, centerY, mouseX, mouseY);

			    // Save the original transformation state
			    AffineTransform original = g2.getTransform();

			    // Apply the rotation to the gun image around its center
			    g2.rotate(angle, centerX, centerY);
			    
			    
			    int radiusX =50;
			    int radiusY =50;
			   
			    
			    if(mouseX -centerX < 0) {
			    	radiusX = -radiusX;
			    }
			    
			    if(mouseY -centerY > 0) {
			    	radiusY = -radiusY;
				    
			    }
		
			    
			    
			    
			    int gunFlashX=  (int) (radiusX * Math.cos(angle));
			    int gunFlashY=(int) (radiusY * Math.sin(angle));
			    // Draw the rotated gun image at the center position
			    // Make sure to adjust the position of the gun relative to its size
			 	 g2.drawImage(gp.player.inventory.get(0).down1, 
		                 centerX - (gp.tileSize / 2) , 
		                 centerY - (gp.tileSize / 2) , 
		                 gp.tileSize, gp.tileSize, null);
			 	 
			    if(gp.player.currentWeapon.showGunFlash) {
			     	 g2.drawImage(gp.player.inventory.get(0).shooting,
			                 (centerX  +gunFlashX)- (gp.tileSize / 2), 
			                 (centerY  +gunFlashY)- (gp.tileSize / 2) , 
			                 gp.tileSize, gp.tileSize, null);
			     	 
			     	   if(gp.player.currentWeapon.currentCount> 0) {
					    	gp.player.currentWeapon.currentCount--;
					    }else if(gp.player.currentWeapon.currentCount <=0){
					    	gp.player.currentWeapon.currentCount = gp.player.currentWeapon.flashCounter;
					    	gp.player.currentWeapon.showGunFlash= false;
			    }
			   
		
			    }
			    
//			 System.out.println(angle);
			    // Restore the original transformation
			    g2.setTransform(original);
			    g2.setColor(new Color(255,255,255));
	    	}else {
	    		System.out.print("Mouse is outside of Window");
	    	}
	        
			}
	
	}
	
	public void drawDeadScreen() {
	String text = "You Died";
	int x = getXforCenterText(text);
	int y = y = (gp.tileSize * gp.maxScreenRow)/2;
	
	
	
	int width = gp.tileSize *5;
	int height = gp.tileSize *3;
	
	
	g2.setColor(new Color(10,10,10));
	g2.fillRect((gp.tileSize * gp.maxScreenCol)/2 -(width /2), (gp.tileSize * gp.maxScreenRow)/2 -(height/2), width, height);
	g2.setColor(new Color(255,255,255));
	g2.drawString(text, x, y);
}

	public void drawWinScreen() {
	String text = "You Won";
	int x = getXforCenterText(text);
	int y = (gp.tileSize * gp.maxScreenRow)/2;
	
	
	
	int width = gp.tileSize *5;
	int height = gp.tileSize *3;
	
	
	g2.setColor(new Color(10,10,10));
	g2.fillRect((gp.tileSize * gp.maxScreenCol)/2 -(width /2), (gp.tileSize * gp.maxScreenRow)/2 -(height/2), width, height);
	g2.setColor(new Color(255,255,255));
	g2.drawString(text, x, y);
}

	public void shoot() {
		if(gp.player.currentWeapon.currentAmmo > 0) {
			gp.playSoundEffect(6);
			gp.player.currentWeapon.currentAmmo--;
//		    Graphics2D g2 = (Graphics2D) gp.getGraphics(); // optional if you want to draw for debug
		    
		    int centerX = (gp.screenWidth / 2) - (gp.tileSize / 2)+ 30; 
		    int centerY = (gp.screenHeight / 2) - (gp.tileSize / 2)+30; 
		    
		    BufferedImage image = null;
		   try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/key.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		    if(MouseInfo.getPointerInfo() != null) {
		    	Point point = gp.getMousePosition();
		    	
		    	if(point != null) {
		    		int mouseX = (int) point.getX();
				    int mouseY = (int) point.getY();
		    double angle = Math.atan2(mouseY - centerY, mouseX - centerX);
		    
		    // Hitbox dimensions
		    int rectWidth = gp.tileSize * 7;
		    int rectHeight = 26;

		    // Center of player in world coordinates
		    int playerCenterX = gp.player.worldX + gp.tileSize / 2;
		    int playerCenterY = gp.player.worldY + gp.tileSize / 2;

		    // Create the unrotated hitbox rectangle starting from player center


		    // Create an AffineTransform for rotation
		    AffineTransform transform = new AffineTransform();
		    // Rotate around player's center
		    double angleRad = Math.toRadians(angle); // set this to your desired angle
		    transform.rotate(angle, playerCenterX, playerCenterY);
		   
		
		    
		    Rectangle2D.Float hitboxRect = new Rectangle2D.Float(
			        playerCenterX,
			        playerCenterY - rectHeight / 2,
			        rectWidth,
			        rectHeight
			    );

		

		    // Create the rotated hitbox
		    Shape rotatedHitbox = transform.createTransformedShape(hitboxRect);
		    Area hitboxArea = new Area(rotatedHitbox);

		    // Optional: draw for debug
		     g2.setColor(Color.RED);
		     g2.draw(rotatedHitbox);
		     g2.drawRect(playerCenterX, playerCenterY, rectWidth, rectHeight);

		    for (Entity zombie : gp.monsters) {
		        if (zombie == null) continue;

		        Rectangle2D.Float zombieRect = new Rectangle2D.Float(
		            zombie.worldX,
		            zombie.worldY,
		            zombie.solidArea.width,
		            zombie.solidArea.height
		        );

		        Area zombieArea = new Area(zombieRect);

		        if (hitboxArea.intersects(zombieRect.getBounds2D())) {
		        	//Get the distance from the player to the zombie 
		        	double dx = zombie.worldX - gp.player.worldX;
		        	double dy = zombie.worldY - gp.player.worldY;
		        	double distance = Math.sqrt((dx * dx) + (dy * dy));
		        	int powerReduction = (int) (distance * 2)/gp.tileSize;
		        	
		            System.out.println("Zombie hit!");
		            zombie.life -= gp.player.attack - powerReduction;
		        }
		    }
		}
		    }}

	;
		}
		
}
