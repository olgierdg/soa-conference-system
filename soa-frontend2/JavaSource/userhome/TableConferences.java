package userhome;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.jboss.soa.esb.client.ServiceInvoker;
import org.jboss.soa.esb.couriers.FaultMessageException;
import org.jboss.soa.esb.listeners.message.MessageDeliverException;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.format.MessageFactory;
import org.jboss.soa.esb.services.registry.RegistryException;

import authorization.UserManager;

import utils.Utils;

import model.Conference;
import model.User;

public abstract class TableConferences implements Serializable {
	  
    /**
	 * 
	 */
	private static final long serialVersionUID = -3164874918085020763L; 
    protected List<Conference> conferences;
    protected Conference selectedConference;
    private Boolean selectedInFavs;
    
    public TableConferences() throws MessageDeliverException, FaultMessageException, RegistryException, ClassNotFoundException, IOException {
		conferences = loadConferences();
		
		if (conferences == null) {
			FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error"));
			conferences = new ArrayList<Conference>();
		}
    }
    
    protected abstract List<Conference> loadConferences();
      
    public List<Conference> getConferences() {  
        return conferences;  
    }

	public void setConferences(List<Conference> conferences) {
		this.conferences = conferences;
	}

	public Conference getSelectedConference() {
		return selectedConference;
	}

	public void setSelectedConference(Conference selectedConference) {
		this.selectedConference = selectedConference;
		selectedInFavs = false;
		
		UserManager userManager = (UserManager) FacesContext
				.getCurrentInstance().getExternalContext().getSessionMap()
				.get("userManager");
		if(selectedConference == null) {
			return;
		}

		for(Conference conf : userManager.getFavs()) {
			if(conf.getId() == selectedConference.getId()) {
				selectedInFavs = true;
				break;
			}
		}
		
	}

	public Boolean getSelectedInFavs() {
		return selectedInFavs;
	}
	
	public void setSelectedInFavs(Boolean isInFavs) {
		selectedInFavs = isInFavs;
	}
	
	public void favsHandler() {
		String serviceName = null;
		List<Conference> favs = null;
		
		UserManager userManager = (UserManager) FacesContext
				.getCurrentInstance().getExternalContext().getSessionMap()
				.get("userManager");
		
		System.out.println(userManager);
		if(userManager == null)
			return;
		
		if(selectedInFavs) {
			serviceName = "AddConferenceToUserFavsService";
		}
		else {
			serviceName = "RemoveConferenceFromUserFavsService";
		}
		
		try {
			Message esbMessage = MessageFactory.getInstance().getMessage();
			ServiceInvoker si = new ServiceInvoker("ConferenceServices",
					serviceName);
			
			esbMessage.getBody().add("userid", userManager.getCurrent().getId());
			esbMessage.getBody().add("conferenceid", selectedConference.getId());
			
			Message msg = si.deliverSync(esbMessage, 5000);

			Object obj = msg.getBody().get();

			if (obj instanceof List<?>) {
				favs = (List<Conference>) obj;
			} else if (obj instanceof byte[]) {
				favs = (List<Conference>) Utils
						.deserialize((byte[]) obj);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		if(favs != null) {
			userManager.setFavs(favs);
		}
	}
}
