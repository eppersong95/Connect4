package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//provides functionality to LoginPanel
public class LoginPanelListener implements ActionListener {
	private LoginPanel lp;
	
	public LoginPanelListener(LoginPanel lp) {
		this.lp = lp;
	}
	@Override
	//attempt connection to server
	public void actionPerformed(ActionEvent arg0) {
		lp.tryConnect();
	}

}
