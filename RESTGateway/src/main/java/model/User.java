package model;

import java.io.Serializable;

public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String nick;
	private String password;
	private boolean registered;

	public User(){
		id = 0;
		nick = new String();
		password = new String();
		registered = false;
	}

	public User(int id, String nick, String password, boolean registered){
		this.id = id;
		this.nick = nick;
		this.password = password;
		this.registered = registered;
	}

	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isRegistered() {
		return registered;
	}
	public void setRegistered(boolean isRegistered) {
		this.registered = isRegistered;
	}
	
	public String toString(){
		return id + " " + nick + " " + password + " " + registered;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}