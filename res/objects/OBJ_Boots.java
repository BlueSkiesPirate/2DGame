package objects;

import java.io.IOException;

import javax.imageio.ImageIO;

import object.SuperObject;

public class OBJ_Boots extends SuperObject {

	public OBJ_Boots() {
		name = "Boots";
		try {
			image =ImageIO.read(getClass().getResourceAsStream("/objects/Boots.png"));
		}catch(IOException e) {
			
		}
	}
}
