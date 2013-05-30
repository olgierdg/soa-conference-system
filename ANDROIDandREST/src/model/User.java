package model;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String nick;
	private String password;
	private List<Integer> idsConferences;

	public User() {
		id = 0;
		nick = new String();
		password = new String();
		idsConferences = new ArrayList<Integer>();
	}

	public User(int id, String nick, String password, ArrayList<Integer> idsConferences) {
		this.id = id;
		this.nick = nick;
		this.password = password;
		this.idsConferences = idsConferences;
	}

	public static String hashPasswordWithMD5(String password)
			throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes());
		byte[] bytes = md.digest();

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
					.substring(1));
		}

		return sb.toString();
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

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < idsConferences.size(); i++){
			sb.append(idsConferences.get(i)).append(" ");
		}
		return id + " " + nick + " " + password + " " + sb;
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

	public void setIdsConferences(List<Integer> list) {
		this.idsConferences = list;
	}
}
