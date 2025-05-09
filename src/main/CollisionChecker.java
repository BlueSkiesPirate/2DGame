package main;

import entity.Entity;

public class CollisionChecker {

	GamePanel gp;
	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
	}
	public void checkTile(Entity ent) {
	
		  int entityLeftWorldX = ent.worldX + ent.solidArea.x;
		    int entityRightWorldX = ent.worldX + ent.solidArea.x + ent.solidArea.width;
		    int entityTopWorldY = ent.worldY + ent.solidArea.y;
		    int entityBottomWorldY = ent.worldY + ent.solidArea.y + ent.solidArea.height;

		    // Calculate tile coordinates (row and column)
		    int entityLeftCol = entityLeftWorldX / gp.tileSize;
		    int entityRightCol = entityRightWorldX / gp.tileSize;
		    int entityTopRow = entityTopWorldY / gp.tileSize;
		    int entityBottomRow = entityBottomWorldY / gp.tileSize;

		    // Ensure valid tile coordinates
		    if (entityLeftCol < 0 || entityRightCol < 0 || entityTopRow < 0 || entityBottomRow < 0 ||
		        entityLeftCol >= gp.maxWorldCol || entityRightCol >= gp.maxWorldCol ||
		        entityTopRow >= gp.maxWorldRow || entityBottomRow >= gp.maxWorldRow) {
		        return; // Skip checking if coordinates are out of bounds
		    }

		    int tileNum1, tileNum2;

		
		switch(ent.direction) {
		case "upLeft":
			entityTopRow = (entityTopWorldY - ent.speed) / gp.tileSize;
			entityLeftCol = (entityLeftWorldX - ent.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
				ent.collisionOn = true;
			}
			break;
		case "upRight":
			entityTopRow = (entityTopWorldY - ent.speed) / gp.tileSize;
			entityRightCol = (entityRightWorldX + ent.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
				ent.collisionOn = true;
			}
			break;
		case "downLeft":
			entityBottomRow = (entityBottomWorldY + ent.speed) / gp.tileSize;
			entityLeftCol = (entityLeftWorldX - ent.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
				ent.collisionOn = true;
			}
			break;
		case "downRight":
			entityBottomRow = (entityBottomWorldY + ent.speed) / gp.tileSize;
			entityRightCol = (entityRightWorldX + ent.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
				ent.collisionOn = true;
			}
			break;
		case "up":
			entityTopRow = (entityTopWorldY - ent.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
				ent.collisionOn = true;
			}
			break;
		case "down":
			entityBottomRow = (entityBottomWorldY + ent.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
				ent.collisionOn = true;
			}
			break;
		case "left":
			entityLeftCol = (entityLeftWorldX - ent.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
				ent.collisionOn = true;
			}
			break;
		case "right":
			entityRightCol= (entityRightWorldX + ent.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
				ent.collisionOn = true;
			}
			break;
			
		}
	}
	
	public int checkObject(Entity ent, boolean player) {
		int index = 999;

		
		for(int i =0; i< gp.obj.length; i++) {
			if(gp.obj[i] != null && gp.obj[i].pickUpAble) {
				//get entity's solid area position
				ent.solidArea.x = ent.worldX + ent.solidArea.x;
				ent.solidArea.y = ent.worldY + ent.solidArea.y;
				//Get the object's solid area position
				gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
				gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;
				
				switch(ent.direction) {
				case "up":
					ent.solidArea.y -= ent.speed;
					if(ent.solidArea.intersects(gp.obj[i].solidArea)) {
						if(gp.obj[i].collision) {
							ent.collisionOn =true;
							
						}if(player) {
							index= i;
						}
						
					}
					break;
				case "down":
					ent.solidArea.y += ent.speed;
					if(ent.solidArea.intersects(gp.obj[i].solidArea)) {
						if(gp.obj[i].collision) {
							ent.collisionOn =true;
							
						}if(player) {
							index= i;
						}
						
					}
					break;
				case "left":
					ent.solidArea.x -= ent.speed;
					if(ent.solidArea.intersects(gp.obj[i].solidArea)) {
						if(gp.obj[i].collision) {
							ent.collisionOn =true;
							
						}if(player) {
							index= i;
						}
						
					}
					break;
				case "right":
					ent.solidArea.x += ent.speed;
					if(ent.solidArea.intersects(gp.obj[i].solidArea)) {
						if(gp.obj[i].collision) {
							ent.collisionOn =true;
							
						}if(player) {
							index= i;
						}
						
					}
					break;

				}
				ent.solidArea.x = ent.solidAreaDefaultX;
				ent.solidArea.y = ent.solidAreaDefaultY;
				gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
				gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
			}
		}
		return index;
	}
	
	public boolean checkTileSimple(Entity ent) {
	    int entityLeftWorldX = ent.worldX + ent.solidArea.x;
	    int entityRightWorldX = ent.worldX + ent.solidArea.x + ent.solidArea.width;
	    int entityTopWorldY = ent.worldY + ent.solidArea.y;
	    int entityBottomWorldY = ent.worldY + ent.solidArea.y + ent.solidArea.height;

	    int entityLeftCol = entityLeftWorldX / gp.tileSize;
	    int entityRightCol = entityRightWorldX / gp.tileSize;
	    int entityTopRow = entityTopWorldY / gp.tileSize;
	    int entityBottomRow = entityBottomWorldY / gp.tileSize;

	    try {
	        int tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
	        int tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
	        int tileNum3 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
	        int tileNum4 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];

	        return gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision ||
	               gp.tileM.tile[tileNum3].collision || gp.tileM.tile[tileNum4].collision;
	    } catch (ArrayIndexOutOfBoundsException e) {
	        // Treat out-of-bounds bullets as hitting a wall
	        return true;
	    }
	}

	
}


