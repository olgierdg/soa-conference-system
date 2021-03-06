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

public class TableAllConferences extends TableConferences {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3164874918085020763L;
	
	public TableAllConferences() throws FaultMessageException, MessageDeliverException, ClassNotFoundException, RegistryException, IOException {
		super();
	}

	@Override
	protected List<Conference> loadConferences() {
		List<Conference> conferences;
		conferences = null;

		try {
			Message esbMessage = MessageFactory.getInstance().getMessage();
			ServiceInvoker si = new ServiceInvoker("ConferenceServices",
					"GetAllConferencesService");
			Message msg = si.deliverSync(esbMessage, 5000);

			Object obj = msg.getBody().get();

			if (obj instanceof List<?>) {
				conferences = (List<Conference>) obj;
			} else if (obj instanceof byte[]) {
				conferences = (List<Conference>) Utils
						.deserialize((byte[]) obj);
			}
		} catch (Exception e) {
		}
		return conferences;
	}
}
