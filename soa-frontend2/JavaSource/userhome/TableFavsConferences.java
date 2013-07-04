package userhome;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

public class TableFavsConferences extends TableConferences {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4697233992885494990L;

	public TableFavsConferences() throws MessageDeliverException,
			FaultMessageException, RegistryException, ClassNotFoundException,
			IOException {
		super();
	}
	
	@Override
	protected List<Conference> loadConferences() {
		List<Conference> conferences;
		conferences = null;

		try {
			Message esbMessage = MessageFactory.getInstance().getMessage();
			ServiceInvoker si = new ServiceInvoker("ConferenceServices",
					"GetUserFavsService");
			
			UserManager userManager = (UserManager) FacesContext
					.getCurrentInstance().getExternalContext().getSessionMap()
					.get("userManager");
			esbMessage.getBody().add(userManager.getCurrent());
			
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
