package main;

import entity.Zombie;
import object.OBJ_Axe;
import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;
import object.OBJ_Rifle;
import object.OBJ_Shutgun;
import object.OBJ_Tree;
import object.OBJ_UnkownFruit;
import object.OBJ_Uzi;

public class AssetSetter {

	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	public void setObject() {
	    int i = 0;

	    gp.obj[i] = new OBJ_Door(gp);
	    gp.obj[i].worldX = gp.tileSize * 50;
	    gp.obj[i].worldY = gp.tileSize * 22;
	    i++;

	    gp.obj[i] = new OBJ_Key(gp);
	    gp.obj[i].worldX = gp.tileSize * 50;
	    gp.obj[i].worldY = gp.tileSize * 32;
	    i++;

	    gp.obj[i] = new OBJ_Key(gp);
	    gp.obj[i].worldX = gp.tileSize * 50;
	    gp.obj[i].worldY = gp.tileSize * 32;
	    i++;

	    gp.obj[i] = new OBJ_Key(gp);
	    gp.obj[i].worldX = gp.tileSize * 50;
	    gp.obj[i].worldY = gp.tileSize * 32;
	    i++;
	    gp.obj[i] = new OBJ_Shutgun(gp);
	    gp.obj[i].worldX = gp.tileSize * 40;
	    gp.obj[i].worldY = gp.tileSize * 32;
	    i++;
	    
	    gp.obj[i] = new OBJ_Uzi(gp);
	    gp.obj[i].worldX = gp.tileSize * 30;
	    gp.obj[i].worldY = gp.tileSize * 32;
	    i++;
	    
	    gp.obj[i] = new OBJ_Rifle(gp);
	    gp.obj[i].worldX = gp.tileSize * 35;
	    gp.obj[i].worldY = gp.tileSize * 32;
	    i++;

	    gp.obj[i] = new OBJ_Axe(gp);
	    gp.obj[i].worldX = gp.tileSize * 50;
	    gp.obj[i].worldY = gp.tileSize * 40;
	    i++;
	    
	  

	    gp.monsters[i] = new Zombie(gp);
	    gp.monsters[i].worldX = (int) ((Math.random() * (35 - 30)) +30) * gp.tileSize;// (int) (gp.tileSize * ((Math.random() * 35) +30))
	    gp.monsters[i].worldY =  (int) ((Math.random() * (35 - 30)) +30) * gp.tileSize;;
	    i++;

	    
	    new OBJ_Tree(gp, gp.tileSize * 30, gp.tileSize * 20, i);
	    i += 6;

	    gp.obj[i] = new OBJ_UnkownFruit(gp);
	    gp.obj[i].worldX = 46 * gp.tileSize;
	    gp.obj[i].worldY = 50 * gp.tileSize;
	    i++;
	}

}
