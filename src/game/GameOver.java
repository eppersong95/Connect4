package game;

import java.io.Serializable;

//class that means game over
//if winner, client is winner
public class GameOver implements Serializable {
	private boolean winner;
	
	public GameOver(boolean winner) {
		this.winner = winner;
	}
	
	public boolean getWinner() {
		return winner;
	}
}
