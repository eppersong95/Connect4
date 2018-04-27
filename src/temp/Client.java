package client;

import java.io.IOException;

import game.GameOver;
import game.InvalidMove;
import game.NewGame;
import game.Piece;
import game.TieGame;
import ocsf.client.AbstractClient;

//class used for server communication
public class Client extends AbstractClient {
	private ClientGUI cg;
	
	public Client(String host, int port) {
		super(host, port);
	}
	
	//set client gui
	public void setCG(ClientGUI cg) {
		this.cg = cg;
	}
	
	//attempts to open connection based on user input
	public boolean tryConnection (String host, String port) {
		
		try {
			this.setHost(host);
			this.setPort(Integer.parseInt(port));
			this.openConnection();
		} catch (IOException e) {
			return false;
		}
		
		return true;
	}

	//handles messages from server
	@Override
	protected void handleMessageFromServer(Object arg0) {
		//handles new game
		if(arg0 instanceof NewGame) {
			NewGame temp = (NewGame)arg0;
			GamePanel gp = cg.getGP();
			gp.setID(temp.getID());
			gp.setOpponent(temp.getOpp());
			gp.setTurn(temp.getTurn());
			cg.toGame();
		}
		
		//handles new move
		else if(arg0 instanceof Piece) {
			Piece temp = (Piece)arg0;
			GamePanel gp = cg.getGP();
			gp.addPiece(temp);
		}
		
		//handles invalid mvoe
		else if(arg0 instanceof InvalidMove) {
			GamePanel gp = cg.getGP();
			gp.invalidMove();
		}
		
		//handles end of game
		else if(arg0 instanceof GameOver) {
			GameOver temp = (GameOver)arg0;
			GamePanel gp = cg.getGP();
			if(temp.getWinner()) {
				gp.win();
			}
			else {
				gp.lose();
			}
		}
		
		//handles tie
		else if(arg0 instanceof TieGame) {
			GamePanel gp = cg.getGP();
			gp.tie();
		}
	}
	
}
