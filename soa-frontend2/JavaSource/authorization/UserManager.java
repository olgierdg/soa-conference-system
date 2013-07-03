package authorization;

import java.io.IOException;

import javax.enterprise.context.SessionScoped;
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
//import javax.context.SessionScoped;

//@ManagedBean(name="user")
@SessionScoped
public class UserManager {

	private String username;
	private String password;
	private User current;

	public String login() throws MessageDeliverException,
			FaultMessageException, RegistryException, ClassNotFoundException, IOException {
		current = null;
		
		System.out.println("Trying to log in");
		
		//System.setProperty("javax.xml.registry.ConnectionFactoryClass", "org.apache.ws.scout.registry.ConnectionFactoryImpl");
		Message esbMessage = MessageFactory.getInstance().getMessage();

		User user = new User();
		user.setNick(username);
		user.setPassword(password);
		user.setId(-1);

		esbMessage.getBody().add(user);
		ServiceInvoker si = new ServiceInvoker("UserServices",
				"LogInUserService");
		Message msg = si.deliverSync(esbMessage, 5000);

		Object obj = msg.getBody().get();
		User cr = null;
		
		if (obj instanceof User) {
			cr = (User) obj;
		} else if (obj instanceof byte[]) {
			cr = (User) Utils.deserialize((byte[]) obj);
		}

		if (cr != null && cr.getId() >= 0) {
			user.setId(cr.getId());
			current = user;
		}
		
		if (current == null) {
			FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wrong login or password, try again", "Wrong login or password, try again"));
			return (username = password = null);
		} else {
			return "/pages/userhome/userhome?faces-redirect=true";
		}
	}

	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		//((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false)).invalidate();
		//current = null;
		return "/pages/authorization/login?faces-redirect=true";
	}

	public boolean isLoggedIn() {
		return current != null;
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
}