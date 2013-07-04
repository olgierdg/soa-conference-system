package conference;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import model.Conference;

import org.jboss.soa.esb.client.ServiceInvoker;
import org.jboss.soa.esb.couriers.FaultMessageException;
import org.jboss.soa.esb.listeners.message.MessageDeliverException;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.format.MessageFactory;
import org.jboss.soa.esb.services.registry.RegistryException;

import utils.Utils;

public class ConferenceCreator {
	String name = null;
	String city = null;
	String date = null;
	String description = null;
	String speaker = null;
	String bio = null;
	double lat = 0;
	double lon = 0; 

	public String create() throws ClassNotFoundException, IOException,
			MessageDeliverException, FaultMessageException, RegistryException {
		
		Message esbMessage = MessageFactory.getInstance().getMessage();

		Conference conf = new Conference();
		conf.setName(name);
		conf.setCity(city);
		conf.setDate(date);
		conf.setDescription(description);
		conf.setSpeaker(speaker);
		conf.setBio(bio);
		conf.setLat(lat);
		conf.setLon(lon);

		esbMessage.getBody().add(conf);
		ServiceInvoker si = new ServiceInvoker("ConferenceServices",
				"AddConferenceService");
		Message msg = si.deliverSync(esbMessage, 5000);

		Object obj = msg.getBody().get();
		Conference cr = null;

		if (obj instanceof Conference) {
			cr = (Conference) obj;
		} else if (obj instanceof byte[]) {
			cr = (Conference) Utils.deserialize((byte[]) obj);
		}

		if (cr != null && cr.getId() > -1) {
			conf.setId(cr.getId());
			FacesContext.getCurrentInstance().getExternalContext().getFlash()
					.setKeepMessages(true);
			FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(
									"Conference has been created."));
			name = city = date = description = speaker = bio = null;
			lat = lon = 0.0;
			return "/pages/userhome/userhome?faces-redirect=true";
		} else if (cr.getId() == -1) {
			FacesContext.getCurrentInstance()
					.addMessage(
							"create-conference-form:id-name",
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Name already taken",
									"Name already taken"));
		} else {
			FacesContext.getCurrentInstance()
			.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Failure",
							"Failure"));
		}
		
		return null;
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

	public void setDescription(String descritpion) {
		this.description = descritpion;
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
}
