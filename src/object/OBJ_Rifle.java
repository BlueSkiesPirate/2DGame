package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Rifle extends Entity{
	
	public OBJ_Rifle(GamePanel gp) {
		super(gp);
		
		type = type_weapon;
		name = "Rifle";
		down1 = setup("/objects/Rifle");
		attackValue = 1;
		attackArea.width = 36;
		attackArea.height = 36;
		description ="[" +name +"]\n High damage"; 

	}
}

