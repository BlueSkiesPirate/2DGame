package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {
GamePanel gp;
public Tile[] tile;
public int mapTileNum[][];

public TileManager(GamePanel gp) {
	this.gp = gp;
	tile = new Tile[160];
 mapTileNum =new int[gp.maxWorldCol][gp.maxWorldRow];
	getTileImage();
	
	//MAP
	loadMap("/maps/world03.txt"); 
}
public void getTileImage() {
	


		ArrayList<Integer> BlackListedTiles = new ArrayList<>(Arrays.asList(19,29,39,99,104,105,106,107,159)); //Null tiles
		ArrayList<Integer> TilesWithCollision = new ArrayList<>(Arrays.asList(114,115,135,141)); //Null tiles
		int row =0;
		int col =0;
		boolean collision = false;
		
			setup(0, "tile_"+ row +"_0", false);

			
	//I KNOW THERE ARE 160 FILES IN THIS FOLDER
			for(int i =0; i<160; i++ ) {
				
				if(!BlackListedTiles.contains(i)) {
					if(TilesWithCollision.contains(i)) {//DRAW THE COLLISIONS FOR THE TILES
						collision=true;
					}
					setup(i , "tile_" + row + "_"+col, collision); 
					
				}
				
				if(col == 9){//CHECK IF WE SHOULD INCREMENT ROWS and reset col =0;
					row++;
					col =0;
				}else {
					col++;
				}
				collision=false;
		
			}
}



public void setup(int index, String imagePath, boolean collision) {
	
	UtilityTool UTool =new UtilityTool();
	
	try {
		tile[index] = new Tile();
		tile[index].image = ImageIO.read(getClass().getResourceAsStream("/TerrainTiles/" + imagePath + ".png"));
		tile[index].image =UTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
		tile[index].collision = collision;
	}catch(IOException e) {
		e.printStackTrace();
	}
}





public void loadMap(String file) {
	try {
		InputStream is = getClass().getResourceAsStream(file);
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		
		int col =0;
		int row =0;
		
		while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
			String line = br.readLine();
			
			while(col < gp.maxWorldCol) {
				String numbers[] = line.split(","); //CHANGED TO "," FROM " "
				
				int num  = Integer.parseInt(numbers[col]);
				
				mapTileNum[col][row] =num;
				col++;
			}
			if(col ==gp.maxWorldCol) {
				col =0;
				row++;
			}
		}
		br.close();
		}catch(Exception e) {}
}

public void draw(Graphics2D g2) {
	

	int WorldCol =0;
	int WorldRow = 0;
	
	while(WorldRow<gp.maxWorldCol && WorldRow < gp.maxWorldRow) {
		
		int tileNum = mapTileNum[WorldCol][WorldRow];
		int worldX = WorldCol * gp.tileSize;
		int worldY = WorldRow * gp.tileSize;
		
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY -gp.player.worldY +gp.player.screenY;
		
		if(worldX + gp.tileSize> gp.player.worldX - gp.player.screenX && 
				worldX - gp.tileSize< gp.player.worldX +  gp.player.screenX &&
				worldY + gp.tileSize> gp.player.worldY - gp.player.screenY && 
				worldY- gp.tileSize < gp.player.worldY + gp.player.screenY) {
			
			g2.drawImage(tile[tileNum].image,screenX,screenY,gp.tileSize,gp.tileSize,null);
		}
			
	
		WorldCol++;

		if(WorldCol == gp.maxWorldCol) {
			WorldCol = 0;
			WorldRow++;
	
		}
	}
}
}
