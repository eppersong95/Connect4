package server;

import java.io.Serializable;

//sent to server to signal client is ready to play game
public class User implements Serializable {
	private String username;
	
	public User(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
}
