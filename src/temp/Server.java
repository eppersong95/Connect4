package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import game.Game;
import game.NewGame;
import game.NewMove;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

//class for handling communicatino with client
public class Server extends AbstractServer {
	private int gameID;
	private LinkedList<ConnectionToClient> readyClients;
	private HashMap<ConnectionToClient, String> clientNames;
	private ArrayList<Game> games;
	
	public Server(int port) {
		super(port);
		gameID = 0;
		readyClients = new LinkedList<ConnectionToClient>();
		clientNames = new HashMap<ConnectionToClient,String>();
		games = new ArrayList<Game>();
	}

	@Override
	protected void handleMessageFromClient(Object arg0, ConnectionToClient arg1) {
		//user connected, ready to play
		if(arg0 instanceof User) {
			User temp = (User)arg0;
			clientNames.put(arg1, temp.getUsername());
			//if someone in queue, create new game
			if(readyClients.size() != 0) {
				ConnectionToClient ctc = readyClients.removeFirst();
				newGame(ctc,arg1);
			}
			//if queue empty, add to queue
			else {
				readyClients.add(arg1);
			}
		}
		
		//process client move
		else if (arg0 instanceof NewMove) {
			NewMove temp = (NewMove)arg0;
			int id = temp.getID();
			Game activeGame = games.get(id);
			boolean valid = activeGame.processMove(temp.getCol());
			if(valid) {
				activeGame.checkWin(temp.getCol());
				activeGame.checkTie();
			}
		}
	}
	
	//creates new game with two opponents
	private void newGame(ConnectionToClient p1, ConnectionToClient p2) {
		String p1name = clientNames.get(p1);
		String p2name = clientNames.get(p2);
				
		try {
			games.add(new Game(p1, p2));
			p1.sendToClient(new NewGame(gameID, p2name, true));
			p2.sendToClient(new NewGame(gameID, p1name, false));
			gameID++;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	protected void clientConnected(ConnectionToClient arg0) {
	}
	
	@Override
	protected void clientDisconnected(ConnectionToClient arg0) {
		
	}

}
