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

import utils.Utils;

import model.Conference;
import model.User;

public class TableAllConferences implements Serializable {
	  
    /**
	 * 
	 */
	private static final long serialVersionUID = -3164874918085020763L; 
    private List<Conference> conferences;
    
    public TableAllConferences() throws MessageDeliverException, FaultMessageException, RegistryException, ClassNotFoundException, IOException {
    	conferences = null;

        Message esbMessage = MessageFactory.getInstance().getMessage();
		ServiceInvoker si = new ServiceInvoker("ConferenceServices",
				"GetAllConferencesService");
		Message msg = si.deliverSync(esbMessage, 5000);

		Object obj = msg.getBody().get();
		conferences = null;
		
		if (obj instanceof List<?>) {
			conferences = (List<Conference>) obj;
		} else if (obj instanceof byte[]) {
			conferences = (List<Conference>) Utils.deserialize((byte[]) obj);
		}
		
		if (conferences == null) {
			FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wrong login or password, try again", "Wrong login or password, try again"));
		} else {
			conferences = new ArrayList<Conference>();  
		}
    }
      
    public List<Conference> getConferences() {  
        return conferences;  
    }

	public void setConferences(List<Conference> conferences) {
		this.conferences = conferences;
	}   
}
