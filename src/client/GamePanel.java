package client;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import game.GameListener;
import game.NewMove;
import game.Piece;
import server.User;

//this class is a JPanel where the game will be played
//consists of the game board and buttons to control moves
public class GamePanel extends JPanel {
	private JButton[] buttons;
	private ClientGUI cg;
	private JLabel status;
	private String opponent;
	private int gameID;
	private boolean isMyTurn;
	private ArrayList<Piece> pieces;
	private JButton playAgain;
	private JButton exit;
	
	//format frame and components
	public GamePanel(ClientGUI cg) {
		this.setLayout(null);
		this.setSize(800,720);
		this.cg = cg;
		JLabel board = new JLabel();
		board.setIcon(new ImageIcon("resources/images/board.png"));
		board.setBounds(80,150,640,480);	
		setButtons();
		pieces = new ArrayList<Piece>();
		status = new JLabel();
		this.add(status);
		
		this.add(board);
		this.setVisible(true);
	}
	
	//sets and centers the status
	public void setStatus(String msg, Color color) {
		Font font = new Font("Comic Sans", Font.PLAIN, 24);
		int mid = 400;
		
		status.setFont(font);
		status.setText(msg);
		int len = status.getPreferredSize().width + 10;
		
		status.setBounds(400-(len/2),20,len,status.getPreferredSize().height);
		status.setForeground(color);
	}
	
	//sets the buttons for controls and replay
	private void setButtons()
	{
		buttons = new JButton[7];
	    Integer i = 1;
	    int x = 90;
	    int y = 640;
	    int xIncr = 92;
	    int bWidth = 70;
	    int bHeight = 20;
	    GameListener listener = new GameListener(this);
	    for (JButton b : buttons)
	    {
	      b = new JButton(i.toString());
	      b.setBounds(x,y,bWidth,bHeight);
	      b.addActionListener(listener);
	      this.add(b);
	      x+=xIncr;
	      i++;
	    }
	    
	    playAgain = new JButton("Play Again");
	    exit = new JButton("Exit");
	    
	    playAgain.addActionListener(listener);
	    exit.addActionListener(listener);
	    
	    playAgain.setBounds(230,100,150,30);
	    exit.setBounds(420,100,150,30);
	    
	    playAgain.setVisible(false);
	    exit.setVisible(false);
	    
	    this.add(playAgain);
	    this.add(exit);
	}
	
	//used to send message to server
	public void sendToServer(int col) {
		Client c = cg.getClient();
		NewMove temp = new NewMove(gameID,col);
		try {
			c.sendToServer(temp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//swaps turn and status 
	public void setTurn(boolean turn) {
		this.isMyTurn = turn;
		if(turn) {
			setStatus("Your turn!", Color.GREEN);
		}
		else {
			String msg = opponent + "'s Turn";
			setStatus(msg, Color.RED);
		}
	}
	
	//gets isMyTurn
	public boolean getTurn() {
		return isMyTurn;
	}
	
	//runs if user wins
	public void win() {
		isMyTurn = false;
		setStatus("YOU WIN!", Color.GREEN);
		enableButtons(true);
	}
	
	//runs is user loses
	public void lose() {
		isMyTurn = false;
		setStatus("YOU LOSE!", Color.RED);
		enableButtons(true);
	}
	
	//runs if user ties
	public void tie() {
		isMyTurn = false;
		setStatus("Tie Game!", Color.BLACK);
		enableButtons(true);
	}
	
	//shows replay buttons after game ends
	public void enableButtons(boolean state) {
		playAgain.setVisible(state);
		exit.setVisible(state);
	}
	
	//sets game id
	public void setID(int id) {
		gameID = id;
	}
	
	//gets game id
	public int getID() {
		return gameID;
	}
	
	//sets opponent username
	public void setOpponent(String opp) {
		opponent = opp;
	}
	
	//runs if move isn't valid
	public void invalidMove() {
		setStatus("Invalid Move - Try Again", Color.RED);
	}
	
	//add piece to board
	public void addPiece(Piece p) {
		pieces.add(p);
		this.add(p);
		int x = p.getX();
		int y = p.getY();
		p.setBounds(x,y,p.WIDTH,p.HEIGHT);
		setTurn(!isMyTurn);
	}
	
	//remove pieces from board
	public void clearPieces() {
		for(Piece p : pieces) {
			this.remove(p);
		}
		
		pieces.clear();
	}
	
	//runs upon opening a new game
	public void newGame() {
		enableButtons(false);
		clearPieces();
		String username = cg.getUsername();
		
		User u = new User(username);
		Client c = cg.getClient();
		try {
			c.sendToServer(u);
			setStatus("Waiting for new game...", Color.GREEN);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
