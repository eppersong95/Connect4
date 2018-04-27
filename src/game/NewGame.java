package game;

import java.io.Serializable;

//sent to clients to set new game
public class NewGame implements Serializable {
	private int gameID;
	private String opponent;
	private boolean isYourTurn;
	
	public NewGame(int gameID, String opponent, boolean turn) {
		this.gameID = gameID;
		this.opponent = opponent;
		isYourTurn = turn;
	}
	
	public int getID() {
		return gameID;
	}
	
	public String getOpp() {
		return opponent;
	}
	
	public boolean getTurn() {
		return isYourTurn;
	}
}
