package model;

import java.io.Serializable;

public class UserAndConferenceIDs implements Serializable {

	private static final long serialVersionUID = 1L;
	private int userid;
	private int conferenceid;

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public int getConferenceid() {
		return conferenceid;
	}

	public void setConferenceid(int conferenceid) {
		this.conferenceid = conferenceid;
	}
}