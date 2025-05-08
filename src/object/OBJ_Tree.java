package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Tree extends Entity {
	
	int numberOfImages =6;
	int col =0;
	int row =0;
	
	int maxCol=2;
	int maxRow =3;
	
	int location=0;
	
	
	Entity currentEntity;
	
    public OBJ_Tree(GamePanel gp, int worldX, int worldY, int indexStart) {
        super(gp);
        
        name = "Tree";
  
        
        
        
        for(int i =0; i<numberOfImages;i++) {
        	currentEntity = new Entity(gp);
        	currentEntity.name = "Tree";
            
            currentEntity.down1 = setup("/assetTiles/tile_" +(location + row)+"_"+ (location + col));
//            System.out.println("/assetTiles/tile_" +(location + row)+"_"+ (location + col));
            
            currentEntity.worldX = worldX + (gp.tileSize * col);
            currentEntity.worldY = worldY + (gp.tileSize * row);
            currentEntity.pickUpAble = false;
            
//            if(row ==maxRow) {
//                currentEntity.collision = true;
//                currentEntity.collision = true;
//            }else {
//                currentEntity.collisionOn = false;
//            }
            
            gp.obj[indexStart + i] = currentEntity;
        	
        	if(col <maxCol -1) {
        		col++;
        	}else {
        		row++;
        		col =0;
        	}
        	
        	
        }
        
    }
}
