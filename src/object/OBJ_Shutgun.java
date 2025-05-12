package object;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shutgun extends Entity {
	

	public int flashCounter = 60;
	public OBJ_Shutgun(GamePanel gp) {
		super(gp);
		
		type = type_weapon;
		name = "Shutgun";
		down1 = setup("/objects/shotgun");
		shooting = setup("/objects/gunFlash");
		attackValue = 20;
		attackArea.width = 36;
		attackArea.height = 36;
		description ="[" +name +"]\nA big ol shutgun"; 
		maxClip = 15;
		currentAmmo = maxClip;
		
	

	}
	
	@Override
	public void showFLash() {
		showGunFlash =true;
	
	}

}
