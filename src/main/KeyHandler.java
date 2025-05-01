package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

	
	public boolean upPressed, downPressed, leftPressed, rightPressed, upLeftPressed, upRightPressed, downLeftPressed, downRightPressed;
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int code = e.getKeyCode();
		
		if (code == KeyEvent.VK_W && code == KeyEvent.VK_A) {
			upLeftPressed = true;
		}
		if (code == KeyEvent.VK_W && code == KeyEvent.VK_D) {
			upRightPressed = true;
		}
		if (code == KeyEvent.VK_S && code == KeyEvent.VK_A) {
			downLeftPressed = true;
		}
		if (code == KeyEvent.VK_S && code == KeyEvent.VK_D) {
			downRightPressed = true;
		}
		if(code == KeyEvent.VK_W) {
			upPressed = true;
		}
		if(code == KeyEvent.VK_S) {
			downPressed = true;
		}
		if(code == KeyEvent.VK_D) {
			rightPressed = true;
		}
		if(code == KeyEvent.VK_A) {
			leftPressed = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	int code = e.getKeyCode();
	
	if (code == KeyEvent.VK_W || code == KeyEvent.VK_A) {
		upLeftPressed = false;
	}
	if (code == KeyEvent.VK_W || code == KeyEvent.VK_D) {
		upRightPressed = false;
	}
	if (code == KeyEvent.VK_S || code == KeyEvent.VK_A) {
		downLeftPressed = false;
	}
	if (code == KeyEvent.VK_S || code == KeyEvent.VK_D) {
		downRightPressed = false;
	}
	if (code == KeyEvent.VK_W) {
		upPressed =false;
	}
	if (code == KeyEvent.VK_S) {
		downPressed = false;
	}
	if (code == KeyEvent.VK_D) {
		rightPressed = false;
	}
	if (code == KeyEvent.VK_A) {
		leftPressed = false;
	}
	
	}

}
