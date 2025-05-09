package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Knife extends Entity {

	public OBJ_Knife(GamePanel gp) {
		super(gp);
		type = type_weapon;
		name = "Tactical knife";
		down1 = setup("/objects/knife");
		attackValue = 1;
		attackArea.width = 36;
		attackArea.height = 36;
		description ="[" +name +"]\nA reliable knife"; 

	}
}
