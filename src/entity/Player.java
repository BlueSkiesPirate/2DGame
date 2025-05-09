package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;
import object.OBJ_Sword;
import object.OBJ_Key;

public class Player extends Entity {

	
//	GamePanel gp;
	KeyHandler KeyH;
	
	public final int screenX;
	public final int screenY;
//	public int hasKey = 0;
	
	//PLAYER INVENTORY
	public ArrayList<Entity> inventory = new ArrayList<>();
	public final int maxInventorySize = 20;
	
	public boolean invincible = false;
	int invincibleCounter = 0;
	
	public Player(GamePanel gp, KeyHandler KeyH) {
		super(gp);
		this.KeyH = KeyH;
		solidArea = new Rectangle(); //x,y,width,height (this is used to set the hit-box)
		solidArea.x= 8;
		solidArea.y= 16;
		solidArea.width = 28;
		solidArea.height = 24;
//		//ATTACK AREA
//		attackArea.width =36;
//		attackArea.height = 36;
		
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		setDefaultValues();
		getPlayerImage();
		setItems();
		
		screenX = (gp.screenWidth/2) - (gp.tileSize/2);
		screenY = (gp.screenHeight/2) - (gp.tileSize/2);
		

		}
	
	public void setDefaultValues() {
		worldX = gp.tileSize * 23;
		worldY=gp.tileSize * 21;
		speed=4;
		direction = "down"; //default direction
		
		//PLAYER STATUS
		level =1;
		maxLife =6;
		life = maxLife;
		strength =1;
		defense = getDefense();
		attack = getAttack();
		exp = 0;
		nextLevelup = 5;
		money = 0;
		currentWeapon = new OBJ_Sword(gp);
		
		
	
	}
	
	public void setItems() {

		inventory.add(currentWeapon);
//		inventory.add(new OBJ_Key(gp));
	}
	
	public int getAttack() {
//		attackArea =currentWeapon.attackArea;
		return attack = strength;// * currentWeapon.attackValue;
	};// * 
	
	public int getDefense() {
		return defense = 1;
	}
	
	public void getPlayerImage() {
		
		up1 = setup("up1");
		up2 = setup("up2");
		down1 = setup("down1");
		down2 = setup("down2");
		left1 = setup("left1");
		left2 = setup("left2");
		right1 = setup("right1");
		right2 = setup("right2");
	}
	
	public BufferedImage setup(String imagePath) {
		UtilityTool UTool = new UtilityTool();
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/player/" + imagePath + ".png"));
			image = UTool.scaleImage(image, gp.tileSize , gp.tileSize);
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		return image;
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
		} else if (!(KeyH.upPressed || KeyH.rightPressed || KeyH.downPressed || KeyH.leftPressed)) { // update for key release
			direction = "stationary";
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
			
			if (invincible) {
			    invincibleCounter++;
			    if (invincibleCounter > 60) {
			        invincible = false;
			        invincibleCounter = 0;
			    }
			}

		}
	
	public void pickUpObject(int i) {
		if(i != 999) {//any number is fine so long as it isn't used by the object array index
			String text;
			if(inventory.size() < maxInventorySize) {
				inventory.add(gp.obj[i]);
				gp.playSoundEffect(1);
				text ="Got a" + gp.obj[i].name + "!";
			}else {
				text = "You cannot carry anything else";
			}
			gp.ui.addMessage(text);
			gp.obj[i] = null;
		}
	}
	
	BufferedImage image = null;
	BufferedImage lastUsedImage = null;

	

	public void draw(Graphics2D g2) {
		switch(direction) {
		case "upLeft":
			if(spriteNum ==1) {
				image = left1;
				lastUsedImage = left1;
			}
			if(spriteNum ==2) {
				image = left2;
				lastUsedImage = left2;
			}
			break;
		case "upRight":
			if(spriteNum ==1) {
				image = right1;
				lastUsedImage = right1;
			}
			if(spriteNum ==2) {
				image = right2;
				lastUsedImage = right2;
			}
			break;
			case "downLeft":
			if(spriteNum ==1) {
				image = left1;
				lastUsedImage = left1;
			}
			if(spriteNum ==2) {
				image = left2;
				lastUsedImage = left2;
			}
			break;
		case "downRight":
			if(spriteNum ==1) {
				image = right1;
				lastUsedImage = right1;
			}
			if(spriteNum ==2) {
				image = right2;
				lastUsedImage = right2;
			}
			break;
		case "up":
			if(spriteNum ==1) {
				image = up1;
				lastUsedImage = up1;
			}
			if(spriteNum ==2) {
				image = up2;
				lastUsedImage = up2;
			}
			break;
		case "down":
			if(spriteNum ==1) {
				image = down1;
				lastUsedImage = down1;
			}
			if(spriteNum ==2) {
				image = down2;
				lastUsedImage = down2;
			}
			break;
		case "left":
			if(spriteNum ==1) {
				image = left1;
				lastUsedImage = left1;
			}
			if(spriteNum ==2) {
				image = left2;
				lastUsedImage = left2;
			}
			break;
		case "right":
			if(spriteNum ==1) {
				image = right1;
				lastUsedImage = right1;
			}
			if(spriteNum ==2) {
				image = right2;
				lastUsedImage = right2;
			}
			break;
		case "stationary":
			if(spriteNum ==1) {
				image = lastUsedImage;
			}
			if(spriteNum ==2) {
				image = lastUsedImage;
			}
			break;
		}
		
		g2.drawImage(image, screenX, screenY,null);
		
	}
	public void selectItem() {
		int itemIndex =gp.ui.getItemIndexOnSlot();
		if(itemIndex < inventory.size()) {
			Entity selectedItem = inventory.get(itemIndex);
			if(selectedItem.type ==type_sword || selectedItem.type == type_axe) {
				currentWeapon = selectedItem;
				attack = getAttack();
			}
			if(selectedItem.type == type_consumable) {
				selectedItem.use(this);
			inventory.remove(itemIndex);
			}
		}
		}
	
	
	
}
