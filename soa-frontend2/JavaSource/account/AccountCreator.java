package account;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import model.User;

import org.jboss.soa.esb.client.ServiceInvoker;
import org.jboss.soa.esb.couriers.FaultMessageException;
import org.jboss.soa.esb.listeners.message.MessageDeliverException;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.format.MessageFactory;
import org.jboss.soa.esb.services.registry.RegistryException;

import utils.Utils;

public class AccountCreator {
	String username = null;
	String password = null;
	String name = null;
	String surname = null;
	String email = null;
	String phoneNumber = null;

	public String create() throws ClassNotFoundException, IOException,
			MessageDeliverException, FaultMessageException, RegistryException {
		Message esbMessage = MessageFactory.getInstance().getMessage();

		User user = new User();
		user.setNick(username);
		user.setPassword(password);

		esbMessage.getBody().add(user);
		ServiceInvoker si = new ServiceInvoker("UserServices",
				"RegisterUserService");
		Message msg = si.deliverSync(esbMessage, 5000);

		Object obj = msg.getBody().get();
		User cr = null;

		if (obj instanceof User) {
			cr = (User) obj;
		} else if (obj instanceof byte[]) {
			cr = (User) Utils.deserialize((byte[]) obj);
		}

		if (cr != null && cr.getId() != -1) {
			user.setId(cr.getId());
			FacesContext.getCurrentInstance().getExternalContext().getFlash()
					.setKeepMessages(true);
			FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(
									"Registration successful. You can login now."));
			username = password = name = surname = email = phoneNumber = null;
			return "/pages/authorization/login?faces-redirect=true";
		} else if (cr.getId() == -1) {
			FacesContext.getCurrentInstance()
					.addMessage(
							"create-account-form:id-username",
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Username already taken",
									"Username already taken"));
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
}
