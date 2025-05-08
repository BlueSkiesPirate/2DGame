package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_UnkownFruit extends Entity {

	GamePanel gp;
	public OBJ_UnkownFruit(GamePanel gp) {
		super(gp);
		this.gp = gp;
		type = type_consumable;
		name = "unknown fruit";
		down1 = setup("/objects/unknown_fruit");//, gp.tileSize, gp.tileSize
		description = "???";
	}
	public void use(Entity entity) {
//		gp.gameState = gp.dialogueState;'
		gp.playSoundEffect(1);
	}
}
