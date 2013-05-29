package model;

import java.io.Serializable;

/**
 * A tu taka klase na odpowiedz z serwera prosta zrobilem, zeby w jednym miejscu
 * miec kod operacji i wiadomosc zwrotna. Jak sie operacja powiedzie to kod jest
 * ID uzytkownika dla ktorego to szlo wszystko, jak sie nie powiedzie to kod bledu.
 * Wiadomosc to wiadomo.
 * 
 * @author olo-laptop
 *
 */
public class ConnectionResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int code;
	private String message;
	
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

}
