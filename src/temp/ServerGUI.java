package server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

//server interface which allows server to listen, stop listening, and close
public class ServerGUI extends JFrame {
	private Server server;
	private JPanel buttonPanel;
	private JButton listen;
	private JButton stopListen;
	private JButton close;
	private JLabel status;
	
	public ServerGUI(int port) {
		//instantiate data members
		server = new Server(port);
		
		//format frame
		this.setTitle("Connect 4 Server");
		this.setLayout(new BorderLayout());
		this.setSize(300, 90);
		
		ServerListener sl = new ServerListener(this);
		
		//format contents
		status = new JLabel();
		setStatus("Not Connected", Color.RED);
		this.add(status, BorderLayout.NORTH);
		
		buttonPanel = new JPanel(new FlowLayout());
		listen = new JButton("Listen");
		listen.addActionListener(sl);
		close = new JButton("Close");
		close.addActionListener(sl);
		stopListen = new JButton("Stop Listening");
		stopListen.addActionListener(sl);
		buttonPanel.add(listen);
		buttonPanel.add(stopListen);
		buttonPanel.add(close);
		
		this.add(buttonPanel, BorderLayout.SOUTH);
		
		this.setVisible(true);
	}
	
	public Server getServer() {
		return server;
	}
	
	public void setStatus(String msg, Color color) {
		int buff = 32;
		String temp = "Not Connected";
		int baseLen = temp.length();
		int actualLen = msg.length();
		String buffer ="";
		
		for(int i = 0; i < 32 + (baseLen-actualLen); i++) {
			buffer+=" ";
		}
		
		String message = buffer + msg;
		status.setText(message);
		status.setForeground(color);
	}

	public static void main(String[] args) {
		int port = Integer.parseInt(args[0]);
		new ServerGUI(port);
	}
}
