package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Axe extends Entity{

	public OBJ_Axe(GamePanel gp) {
		super(gp);
		type = type_tool;
		name = "axe";
		down1 = setup("/objects/Axe");
		attackValue = 1;
		description ="[" +name +"]\nA trusty Axe"; 
		attackArea.width = 72;
		attackArea.height = 36;
	}

}
