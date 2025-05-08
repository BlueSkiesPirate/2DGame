package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Sword extends Entity {

	public OBJ_Sword(GamePanel gp) {
		super(gp);
		type = type_sword;
		name = "Normal sword";
		down1 = setup("/objects/Sword");
		attackValue = 1;
		attackArea.width = 36;
		attackArea.height = 36;
		description ="[" +name +"]\nAn old sword"; 

	}
}
