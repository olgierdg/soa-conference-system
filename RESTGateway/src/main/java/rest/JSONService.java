package rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.jboss.soa.esb.client.ServiceInvoker;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.format.MessageFactory;

import com.google.gson.Gson;

import util.Serializer;

import model.User;

@Path("/json/user")
public class JSONService {

	@POST
	@Path("/login")
	@Consumes("application/json")
	public String logInUser(String objString) throws Exception{
		
		Gson gson = new Gson();
		
		User user = gson.fromJson(objString, model.User.class);
		
		System.out.println("[REST Gateway POST] LogIn User, id : " + user.getId());
		
		System.setProperty("javax.xml.registry.ConnectionFactoryClass", "org.apache.ws.scout.registry.ConnectionFactoryImpl");
		Message esbMessage = MessageFactory.getInstance().getMessage();
		
		esbMessage.getBody().add(user);
		ServiceInvoker si = new ServiceInvoker("UserServices",
				"LogInUserService");
		Message msg = si.deliverSync(esbMessage, 5000);

		Object obj = msg.getBody().get();
		User response = null;
		
		if (obj instanceof User) {
			response = (User) obj;
		} else if (obj instanceof byte[]) {
			response = (User) Serializer.deserialize((byte[]) obj);
		}
		
		System.out.println("[REST Gateway POST] Outgoing response says : " + response.getId());
        System.out.println("-------------------------------------------");
			
        return gson.toJson(response);
        
	}
	
	@POST
	@Path("/register")
	@Consumes("application/json")
	public String registerUser(String objString) throws Exception{
		
		Gson gson = new Gson();
		
		User user = gson.fromJson(objString, model.User.class);
		
		if(user.getIdsConferences() == null)
			System.out.println("Conference list is NULL!");
		else
			System.out.println("Conference list is NOT NULL! HOORAY!");
		
		System.out.println("[REST Gateway POST] Register User, id : " + user.getId());
		
		System.setProperty("javax.xml.registry.ConnectionFactoryClass", "org.apache.ws.scout.registry.ConnectionFactoryImpl");
		Message esbMessage = MessageFactory.getInstance().getMessage();
		
		esbMessage.getBody().add(user);
		ServiceInvoker si = new ServiceInvoker("UserServices",
				"RegisterUserService");
		Message msg = si.deliverSync(esbMessage, 5000);

		Object obj = msg.getBody().get();
		User response = null;
		
		if (obj instanceof User) {
			response = (User) obj;
		} else if (obj instanceof byte[]) {
			response = (User) Serializer.deserialize((byte[]) obj);
		}
		
		System.out.println("[REST Gateway POST] Outgoing response says : " + response.getId());
        System.out.println("-------------------------------------------");
		
        return gson.toJson(response);
	}
}