package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;

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

	    // Draw coordinates
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
//	    	
////	    	if(gp.player.inventory.get(1)!= null) {
//	    		g2.drawImage(gp.player.inventory.get(0).down1, (gp.tileSize * 12) + 150, gp.tileSize * 9 - 95, 50, 50,null);
////	    	}
//	    
////	    	
//	    	g2.drawImage(gp.player.inventory.get(0).down1, (gp.tileSize * 12) + 150, gp.tileSize * 9 - 20, 50, 50,null);
	   
	    	// Draw equipped weapons (bottom-left corner)


		    
	    }
	    

	    
	    // Draw health bar on screen
	    if (healthBarImage != null) {
	        g2.drawImage(healthBarImage, 25, -25, gp.tileSize * 4, gp.tileSize *3, null);
	    }

	}

	
 	public void drawTitleScreen() {
		String text = "Title";
		int x = getXforCenterText(text);
		int y = gp.screenHeight/2;
		
		g2.drawString(text, x, y);
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
			    g2.drawLine(centerX, centerY, mouseX, mouseY);

			    // Save the original transformation state
			    AffineTransform original = g2.getTransform();

			    // Apply the rotation to the gun image around its center
			    g2.rotate(angle, centerX, centerY);

			    // Draw the rotated gun image at the center position
			    // Make sure to adjust the position of the gun relative to its size
			    g2.drawImage(gp.player.inventory.get(0).down1, 
			                 centerX - (gp.tileSize / 2) , 
			                 centerY - (gp.tileSize / 2) , 
			                 gp.tileSize, gp.tileSize, null);

			    // Restore the original transformation
			    g2.setTransform(original);
	    	}else {
	    		System.out.print("Mouse is outside of Window");
	    	}
	        
			}
	
	    }
	    
;
	
}
