package game;

import java.io.IOException;

import ocsf.server.ConnectionToClient;

//class for managing game between two clients
public class Game {
	private ConnectionToClient p1;
	private ConnectionToClient p2;
	private ConnectionToClient turn;
	private GameLogic gl;
    public final int xIncr = 90;
    public final int yIncr = 80;
	public final int BASEX = 90;
	public final int BASEY = 550;
	
	public Game(ConnectionToClient p1, ConnectionToClient p2) {
		this.p1 = p1;
		this.p2 = p2;
		turn = this.p1;
		gl = new GameLogic();
	}
	
	//returns true if move is valid
	//sends piece to client if valid
	public boolean processMove(int col) {
		Piece temp = gl.newMove(col);
		if (temp == null) {
			try {
				turn.sendToClient(new InvalidMove());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
		else {
			try {
				p1.sendToClient(temp);
				p2.sendToClient(temp);
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
	}
	
	//sends GameOVer object to server if win
	public void checkWin(int col) {
		boolean gameOver =  gl.isWin(col);
		
		if(gameOver) {
			try {
				turn.sendToClient(new GameOver(true));
				swapTurn();
				turn.sendToClient(new GameOver(false));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		swapTurn();
	}
	
	//sends tie game to clients if tie
	public void checkTie() {
		if(gl.isTie()) {
			TieGame temp = new TieGame();
			try {
				p1.sendToClient(temp);
				p2.sendToClient(temp);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//swaps turn
	public void swapTurn() {
		if(turn.equals(p1)) {
			turn = p2;
		}
		else {
			turn = p1;
		}
	}
}
