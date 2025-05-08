package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity {

	public int worldX, worldY;
	GamePanel gp;
	
	
	public BufferedImage image, image2, image3;
	public boolean collision = false;
	public BufferedImage up1, up2, down1, down2, left1,left2, right1,right2; //It describes an image with an accessible buffer of image data
	public String direction = "down";
	
	
	public int spriteCounter = 0;
	public int spriteNum = 1;
	public Rectangle solidArea = new Rectangle(0,0,48,48);
	public int solidAreaDefaultX, solidAreaDefaultY;
	public boolean collisionOn = false;
	public boolean pickUpAble = true;
	public int imageSize = 64;
	
	//CHARACTER ATTRIBUTES
	public int speed;
	public String name;
	public int maxLife;
	public int life;
	public int level;
	public int strength;
	public int attack;
	public int defense;
	public int exp;
	public int nextLevelup;
	public int money;
	public Entity currentWeapon;
	
	public Rectangle attackArea = new Rectangle();
	
	//ITEM ATTRIBUTES
	public int attackValue;
	public int defenseValue;
	public String description="";
	
	
	
	//TYPE
	public int type;
	public final int type_player = 0;
	public final int type_monster =1;
	public final int type_sword =3;
	public final int type_axe =4;
	public final int type_consumable =6;
	
	
	
	public Entity(GamePanel gp) {
		this.gp = gp;
	}

	public void setAction() {
	}
	
	public void damageReaction() {}

	
	public void draw(Graphics2D g2) {
		BufferedImage image = null;
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		
		if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
		   worldX -gp.tileSize < gp.player.worldX + gp.player.screenX &&
		   worldY + gp.tileSize > gp.player.worldY -gp.player.screenY && 
		   worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
			
			switch(direction) {
			case "up":
				if(spriteNum ==1) {
					image =up1;
					}
				
				if(spriteNum ==2) {
					image =up2;
					}
				break;
			case "down":
				if(spriteNum ==1) {
					image =down1;
					}
				
				if(spriteNum ==2) {
					image =down2;
					}
				break;
			case "left":
				if(spriteNum ==1) {
					image =left1;
					}
				
				if(spriteNum ==2) {
					image =left2;
					}
				break;
			case "right":
				if(spriteNum ==1) {
					image =right1;
					}
				
				if(spriteNum ==2) {
					image =right2;
					}
				break;
				
				}
			if(imageSize == 32) {// THIS IS WHERE I DRAW THE ENTITIES, SOME ARE 32X32 PIXELS SO THEY NEED TO BE RESCALED
				g2.drawImage(image, screenX, screenY, (int) gp.tileSize *1, (int) (gp.tileSize *1.2), null);
			}else {
				g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
			}
		
		}
		
	}
	
	public void use(Entity entity) {}
	
	public BufferedImage setup(String imagePath) {
		UtilityTool UTool = new UtilityTool();
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	
//	public void update() {}
	
}
