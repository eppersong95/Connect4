package game;

import java.io.Serializable;

//sent to server to process a move
public class NewMove implements Serializable {
	private int gameID;
	private int col;
	
	public NewMove(int gameID, int col) {
		this.gameID = gameID;
		this.col = col;
	}
	
	public int getID() {
		return gameID;
	}
	
	public int getCol() {
		return col;
	}
}
