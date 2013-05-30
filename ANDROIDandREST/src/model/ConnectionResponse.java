package model;

import java.io.Serializable;

public class ConnectionResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	private int code;
	private String message;

	public ConnectionResponse(){
		code = 0;
		message = new String();
	}
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String toString(){
		return code + " " + message;
	}
}