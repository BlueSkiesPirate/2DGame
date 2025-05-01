package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {

	
	GamePanel gp;
	KeyHandler KeyH;
	
	public final int screenX;
	public final int screenY;
	public int hasKey = 0;
	
	
	public Player(GamePanel gp, KeyHandler KeyH) {
		this.gp = gp;
		this.KeyH = KeyH;
		solidArea = new Rectangle(); //x,y,width,height (this is used to set the hit-box)
		solidArea.x= 8;
		solidArea.y= 16;
		solidArea.width = 28;
		solidArea.height = 24;
		
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		setDefaultValues();
		getPlayerImage();
		
		screenX = (gp.screenWidth/2) - (gp.tileSize/2);
		screenY = (gp.screenHeight/2) - (gp.tileSize/2);
	}
	
	public void setDefaultValues() {
		worldX = gp.tileSize * 23;
		worldY=gp.tileSize * 21;
		speed=4;
		direction = "down"; //default direction
	}
	
	public void getPlayerImage() {
		try {
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/pixil-up.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/pixil-up-2.png"));
			
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/pixil-down.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/pixil-down-2.png"));
			
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/pixil-left.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/pixil-left-2.png"));
			
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/player-right.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/pixil-right-2.png"));
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	public void update() {
		if (KeyH.upPressed && KeyH.leftPressed) {
			direction = "upLeft";
		} else if (KeyH.upPressed && KeyH.rightPressed) {
			direction = "upRight";
		} else if (KeyH.downPressed && KeyH.leftPressed) {
			direction = "downLeft";
		}else if (KeyH.downPressed && KeyH.rightPressed) {
			direction = "downRight";
		} else if (KeyH.upPressed) {
			direction = "up";
		} else if (KeyH.downPressed) {
			direction = "down";
		} else if (KeyH.leftPressed) {
			direction = "left";
		} else if (KeyH.rightPressed) {
			direction = "right";
		}
			
			//Check collision
			
			collisionOn = false;
			gp.cChecker.checkTile(this);
			
			//Check object collision
		int objIndex = 	gp.cChecker.checkObject(this, true);
		pickUpObject(objIndex);
			
			if(collisionOn == false) {
				switch(direction) {
				case "upLeft":
					worldX -= speed;
					worldY -= speed;
					break;
				case "upRight":
					worldY -= speed;
					worldX += speed;
					break;
				case "downLeft":
					worldY += speed;
					worldX -= speed;
					break;
				case "downRight":
					worldY += speed;
					worldX += speed;
					break;
				case "up":
					worldY -= speed;
					break;
				case "down":
					worldY += speed;
					break;
				case "left":
					worldX -= speed;
					break;
				case "right":
					worldX += speed;
					break;
					
					
				}
			}
			spriteCounter++;
			if(spriteCounter > 12) {
				if(spriteNum ==1) {
					spriteNum =2;
				} else if(spriteNum  == 2) {
					spriteNum =1;
				}
				spriteCounter =0;
			}
		}
	
	public void pickUpObject(int i) {
		if(i != 999) {//any number is fine so long as it isn't used by the object array index
			String objectName = gp.obj[i].name;
			switch(objectName) {
			case "Key":
				gp.playSoundEffect(1);
				hasKey++;
				gp.obj[i] = null;
				gp.ui.showMessage("You got a key");
				break;
			case "Door":
				if(hasKey >0) {
					gp.playSoundEffect(2);
					gp.obj[i] = null;
					hasKey--;
					gp.ui.showMessage("door opened");
				}else {
					gp.ui.showMessage("looks like you need a key!");
				}
				break;
			case "Chest":
				gp.ui.gameFinished =true;
				gp.stopMusic();
				gp.playSoundEffect(1);
				break;
			case "Boots":
				speed += 2;
				gp.obj[i]= null;
				break;
				
			}
			
		}
	
	
	}
	
	
	public void draw(Graphics2D g2) {

		
		BufferedImage image = null;
		
		switch(direction) {
		case "up":
			if(spriteNum ==1) {
				image = up1;
			}
			if(spriteNum ==2) {
				image = up2;
			}
			break;
		case "down":
			if(spriteNum ==1) {
				image = down1;
			}
			if(spriteNum ==2) {
				image = down2;
			}
			break;
		case "left":
			if(spriteNum ==1) {
				image = left1;
			}
			if(spriteNum ==2) {
				image = left2;
			}
			break;
		case "right":
			if(spriteNum ==1) {
				image = right1;
			}
			if(spriteNum ==2) {
				image = right2;
			}
			break;
		}
		
		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize,null);
		
		
		
		
		
		
	}
	
	
	
}
