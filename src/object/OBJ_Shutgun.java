package object;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shutgun extends Entity {
	
	
	public OBJ_Shutgun(GamePanel gp) {
		super(gp);
		
		type = type_weapon;
		name = "Shutgun";
		down1 = setup("/objects/shotgun");
		attackValue = 1;
		attackArea.width = 36;
		attackArea.height = 36;
		description ="[" +name +"]\nA big ol shutgun"; 
		
	

	}
	
	

}
