package model;

import java.io.Serializable;

public class Conference implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String name;
	private String city;
	private String date;
	private String description;
	private String speaker;
	private String bio;
	private double lat;
	private double lon;

	public Conference() {
		id = 0;
		name = new String();
		city = new String();
		date = new String();
		description = new String();
		speaker = new String();
		bio = new String();
		lat = 0;
		lon = 0;
	}

	
	
	public Conference(int id, String name, String city, String date,
			String description, String speaker, String bio, double lat,
			double lon) {
		this.id = id;
		this.name = name;
		this.city = city;
		this.date = date;
		this.description = description;
		this.speaker = speaker;
		this.bio = bio;
		this.lat = lat;
		this.lon = lon;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSpeaker() {
		return speaker;
	}

	public void setSpeaker(String speaker) {
		this.speaker = speaker;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public String toString() {
		return id + " " + name + " " + city + " " + date + " "
				+ description + " " + speaker + " " + bio + " " + lat + " "
				+ lon;
	}

	
}
