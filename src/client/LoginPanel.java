package client;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import server.User;

//this class creates a JPanel where users can connect to server and start a game
public class LoginPanel extends JPanel {
	private JLabel serverIPLabel;
	private JLabel serverPortLabel;
	private JLabel usernameLabel;
	private JTextField serverIP;
	private JTextField serverPort;
	private JTextField username;
	private JLabel background;
	private JLabel status;
	private JButton connect;
	private ClientGUI cg;
	
	//formats panel and adds components
	public LoginPanel(ClientGUI gui) {
		this.setSize(800,720);
		this.setLayout(null);
		
		this.cg = gui;
		
		setLabels();
		setFields();
		
		status = new JLabel();
		this.add(status);
		setStatus("Not Connected", Color.RED);
		setConnect();
		setBackground();
	}
	
	//sets the "Connect4" background, using an JLabel w/ Icon
	private void setBackground() {
		String path = "resources/images/oldLogo.png";
		background = new JLabel();
		background.setIcon(new ImageIcon(path));
		background.setBounds(27,10,background.getPreferredSize().width,background.getPreferredSize().height);
		this.add(background);
	}
	
	//adds JLabels of text fields to panel
	private void setLabels() {
		Font labelFont = new Font("Comic Sans",Font.PLAIN,24);
		
		//instantiate server ip label
		serverIPLabel = new JLabel("Server IP Address:");
		serverIPLabel.setFont(labelFont);
		
		//instantiate server port label
		serverPortLabel = new JLabel("Port #:");
		serverPortLabel.setFont(labelFont);
		
		//instantiate username label
		usernameLabel = new JLabel("Username:");
		usernameLabel.setFont(labelFont);
		
		//place and add serverIP
		serverIPLabel.setBounds(350-serverIPLabel.getPreferredSize().width,400,serverIPLabel.getPreferredSize().width, serverIPLabel.getPreferredSize().height);
		this.add(serverIPLabel);

		//place and add server port
		serverPortLabel.setBounds(350-serverPortLabel.getPreferredSize().width, 440, serverPortLabel.getPreferredSize().width,serverIPLabel.getPreferredSize().height);
		this.add(serverPortLabel);
		
		//place and add username
		usernameLabel.setBounds(350-usernameLabel.getPreferredSize().width, 480, usernameLabel.getPreferredSize().width,usernameLabel.getPreferredSize().height);
		this.add(usernameLabel);
	}
	
	//adds JTextfields to panel
	private void setFields() {
		//formatting variables
		int len = 250;
		int x = 355;
		int height = 32;
		Font font = new Font("Comic Sans",Font.PLAIN,24);
		
		//instantiate fields
		serverIP = new JTextField();
		serverIP.setFont(font);
		serverPort = new JTextField();
		serverPort.setFont(font);
		username = new JTextField();
		username.setFont(font);
		
		//place and add serverIP
		serverIP.setBounds(x,serverIPLabel.getY(),len,height);
		this.add(serverIP);
		
		//place and add serverport
		serverPort.setBounds(x,serverPortLabel.getY(),len,height);
		this.add(serverPort);
		
		//place and add username
		username.setBounds(x,usernameLabel.getY(),len,height);
		this.add(username);
	}
	
	//sets status label, centering on screen
	public void setStatus(String msg, Color color) {
		int mid = 400;
		int y = 325;
		int len = 0;
		Font font = new Font("Comic Sans", Font.PLAIN, 20);
		status.setFont(font);
		
		status.setText(msg);
		len = status.getPreferredSize().width;
		
		status.setBounds(mid-((len+10)/2),y,len + 10 ,status.getPreferredSize().height);
		status.setForeground(color);
	}

	//sets connection button
	private void setConnect() {
		Font font = new Font("Comic Sans",Font.PLAIN, 18);
		int mid = 400;
		int y = 550;
		LoginPanelListener list = new LoginPanelListener(this);
		
		connect = new JButton("Connect");
		connect.addActionListener(list);
		
		connect.setFont(font);
		int len = connect.getPreferredSize().width;
		
		connect.setBounds(mid-((len+10)/2),y,len+10,connect.getPreferredSize().height);
		this.add(connect);
	}
	//gets server ip
	public String getIP() {
		return serverIP.getText();
	}

	//gets server port
	public String getPort() {
		return serverPort.getText();
	}

	//get username
	public String getUsername() {
		return username.getText();
	}

	//attempt connection to server
	public void tryConnect() {
		if((getIP().equals("")) || (getPort().equals(""))) {
			setStatus("Invalid server credentials", Color.RED);
			return;
		}
		
		else if(getUsername().equals("")) {
			setStatus("Username cannot be blank", Color.RED);
			return;
		}
		
		Client c = cg.getClient();
		boolean temp = c.tryConnection(getIP(), getPort());
		if(temp) {
			setStatus("Connected: Waiting for Game", Color.GREEN);
			try {
				c.sendToServer(new User(getUsername()));
				serverIP.setEnabled(false);
				serverPort.setEnabled(false);
				username.setEnabled(false);
				connect.setEnabled(false);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			setStatus("Unable to Connect to Server", Color.RED);
		}
	}
}
