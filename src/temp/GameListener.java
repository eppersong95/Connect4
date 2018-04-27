package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import client.GamePanel;

//provides functionality for Game buttons
public class GameListener implements ActionListener {
	private GamePanel gp;
	
	public GameListener(GamePanel gp) {
		this.gp = gp;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String buttonPressed = e.getActionCommand();
		
		//if play again, create new game
		if (buttonPressed.equals("Play Again")) {
			gp.newGame();
		}
		
		//if exit, close program
		else if(buttonPressed.equals("Exit")) {
			System.exit(-1);
		}
		
		//otherwise, send NewMove to server
		else {
			if(gp.getTurn()) {
			    int temp = Integer.parseInt(buttonPressed) - 1;
			    Integer col = temp;
			    gp.sendToServer(col);
			}
		}
		
	}
	
	

}
