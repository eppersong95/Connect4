package server;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

//gives functionality to server gui
public class ServerListener implements ActionListener {
	private ServerGUI gui;
	
	public ServerListener(ServerGUI gui) {
		this.gui = gui;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		String buttonPressed = arg0.getActionCommand();
		Server temp = gui.getServer();
		
		//if listen button, make server listen
		if(buttonPressed.equals("Listen")) {
			temp = gui.getServer();
			try {
				temp.listen();
				gui.setStatus("Listening", Color.GREEN);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.exit(-1);
			}
		}
		
		//if stop listening, have server do the same
		else if(buttonPressed.equals("Stop Listening")) {
			temp.stopListening();
			gui.setStatus("Not Listening", Color.RED);
		}
		
		//otherwise, close server
		else {
			try {
				temp.close();
				gui.setStatus("Server closed", Color.RED);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
