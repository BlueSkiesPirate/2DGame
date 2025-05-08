package main;

import javax.swing.JFrame;

public class Main {
	public static JFrame window;

	public static void main(String[] args) {
	
		
		 window = new JFrame();
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("2d game");
		
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		window.pack();
		
		window.setLocationRelativeTo(null);
		
//		window.setUndecorated(true); // remove borders manually
//		window.setExtendedState(JFrame.MAXIMIZED_BOTH); // fill screen
		
		window.setVisible(true);
		
	
//		gamePanel.setFullScreen();
		
		gamePanel.setupGame();
		gamePanel.startGameThread();
	}

}
