package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//import javax.xml.bind.annotation.XmlElement;
//import javax.xml.bind.annotation.XmlRootElement;

//@XmlRootElement
public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	
	//@XmlElement(name = "id")
	private int id;
	
	//@XmlElement(name = "nick")
	private String nick;
	
	//@XmlElement(name = "password")
	private String password;
	
	//@XmlElement(name = "idsConferences")
	private List<Integer> idsConferences;

	public User(){
		id = 0;
		nick = new String();
		password = new String();
	}

	public User(int id, String nick, String password){
		this.id = id;
		this.nick = nick;
		this.password = password;
		this.idsConferences = new ArrayList<Integer>();
	}
	
	public User(int id, String nick, String password, List<Integer> idsConferences){
		this.id = id;
		this.nick = nick;
		this.password = password;
		this.idsConferences = idsConferences;
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
	
	public String toString(){
		return id + " " + nick + " " + password;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public List<Integer> getIdsConferences() {
		return idsConferences;
	}

	public void setIdsConferences(List<Integer> idsConferences) {
		this.idsConferences = idsConferences;
	}
}