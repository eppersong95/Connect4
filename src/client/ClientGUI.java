package client;

import javax.swing.JFrame;

//class that controls client gui and communications with server
public class ClientGUI extends JFrame {
	private Client client;
	private LoginPanel lp;
	private GamePanel gp;
	
	public ClientGUI() {
		this.setTitle("Connect 4");
		this.setLayout(null);
		this.setSize(800, 720);
		this.setVisible(true);
		this.setResizable(false);
		
		client = new Client("localhost",1);
		client.setCG(this);
		lp = new LoginPanel(this);
		gp = new GamePanel(this);
		
		this.setContentPane(lp);
	}
	
	//gets client connection
	public Client getClient() {
		return client;
	}
	
	//sends user to game board
	public void toGame() {
		this.setContentPane(gp);
		this.setVisible(true);
	}
	
	//gets game panel
	public GamePanel getGP() {
		return gp;
	}
	
	//gets username
	public String getUsername() {
		return lp.getUsername();
	}
	public static void main(String[] args) {
		new ClientGUI();
	}
}
