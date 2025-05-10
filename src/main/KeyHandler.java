package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, upLeftPressed, upRightPressed, downLeftPressed, downRightPressed;
    public boolean key1Pressed, key2Pressed, key3Pressed;


    // Prevents multiple toggles while holding 'P'
    private boolean pPressedLast = false;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // Pause toggle (debounced)
        if (code == KeyEvent.VK_P && !pPressedLast) {
            if (gp.gameState == gp.playState) {
                gp.gameState = gp.pauseState;
                System.out.println("Paused");
            } else if (gp.gameState == gp.pauseState) {
                gp.gameState = gp.playState;
                System.out.println("Unpaused");
            }
            pPressedLast = true; // prevent repeat
            return; // prevent fall-through logic on same press
        }

        // Handle movement and other input only when not paused
        if (gp.gameState == gp.titleState) {
            titleState(code);
        } else if (gp.gameState == gp.characterState) {
            characterState(code);
        } else if (gp.gameState == gp.playState) {
            playState(code);
        } else if (gp.gameState == gp.pauseState) {
            pauseState(code);
        }
    }

    public void titleState(int code) {
        if (code == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = 2;
            }
        }
        if (code == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            if (gp.ui.commandNum > 2) {
                gp.ui.commandNum = 0;
            }
        }
        if (code == KeyEvent.VK_ENTER) {
            if (gp.ui.commandNum == 0) {
                gp.gameState = gp.playState;
            }
            if (gp.ui.commandNum == 1) {
            	 System.exit(0);
               
            }
            if (gp.ui.commandNum == 2) {
            	 // Future feature
            }
        }
    }

    public void playState(int code) {
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
    	    if (code == KeyEvent.VK_W) {
    	        upPressed = true;
    	    }
    	    if (code == KeyEvent.VK_S) {
    	        downPressed = true;
    	    }
    	    if (code == KeyEvent.VK_D) {
    	        rightPressed = true;
    	    }
    	    if (code == KeyEvent.VK_A) {
    	        leftPressed = true;
    	    }

    	    if (code == KeyEvent.VK_Q) {
    	        gp.gameState = gp.characterState;
    	    }

    	    // 🔽 Add this for weapon switching
    	    if (code == KeyEvent.VK_1) {
    	        key1Pressed = true;
    	    }
    	    if (code == KeyEvent.VK_2) {
    	        key2Pressed = true;
    	    }
    	    if (code == KeyEvent.VK_3) {
    	        key3Pressed = true;
    	    }

    }

    public void characterState(int code) {
    	   if (code == KeyEvent.VK_ENTER) {
              gp.player.selectItem();
           }
    	   
        if (code == KeyEvent.VK_Q) {
            gp.gameState = gp.playState;
        }
        if (code == KeyEvent.VK_W) {
        	if(gp.ui.slotRow >0) {
        		 gp.ui.slotRow--;
                 gp.playSoundEffect(1);
        	}
        }
        if (code == KeyEvent.VK_A) {
        	if(gp.ui.slotCol >0) {
        		  gp.ui.slotCol--;
                  gp.playSoundEffect(1);
        	}
          
        }
        if (code == KeyEvent.VK_S) {
        	if(gp.ui.slotRow < 3) {
        		gp.ui.slotRow++;
            	gp.playSoundEffect(1);
        	}
       
        }
        
        if (code == KeyEvent.VK_D) {
        	if(gp.ui.slotCol < 4 ) {
        		 gp.ui.slotCol++;
           	  gp.playSoundEffect(1);
        	}
        	 
        }
        
        
        
        
        
        
        
        
    }

    public void pauseState(int code) {
        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_P) {
            pPressedLast = false;
        }

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
            upPressed = false;
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
        if (code == KeyEvent.VK_1) {
            key1Pressed = false;
        }
        if (code == KeyEvent.VK_2) {
            key2Pressed = false;
        }
        if (code == KeyEvent.VK_3) {
            key3Pressed = false;
        }

    }
}
