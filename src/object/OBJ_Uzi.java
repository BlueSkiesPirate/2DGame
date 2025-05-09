package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Uzi extends Entity{
	
	public OBJ_Uzi(GamePanel gp) {
		super(gp);
		
		type = type_weapon;
		name = "Uzi";
		down1 = setup("/objects/Uzi");
		attackValue = 1;
		attackArea.width = 36;
		attackArea.height = 36;
		description ="[" +name +"]\nA rappid fire gun"; 

	}
}

