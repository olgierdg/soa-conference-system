package rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.jboss.soa.esb.client.ServiceInvoker;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.format.MessageFactory;

import com.google.gson.Gson;

import util.Serializer;

import model.Conference;
import model.User;
import model.UserAndConferenceIDs;

@Path("/json")
public class JSONService {


	@SuppressWarnings("unchecked")
	@POST
	@Path("/conference/addtouserfav")
	@Produces("application/json")
	public String addConferenceToUserFavs(String objString) throws Exception{

		Gson gson = new Gson();
		
		UserAndConferenceIDs c = gson.fromJson(objString, UserAndConferenceIDs.class);
		
		System.out.println("[REST Gateway POST] Add Conference To User Favs");
		
		System.setProperty("javax.xml.registry.ConnectionFactoryClass", "org.apache.ws.scout.registry.ConnectionFactoryImpl");
	
        Message esbMessage = MessageFactory.getInstance().getMessage();

        esbMessage.getBody().add("userid", c.getUserid());
        esbMessage.getBody().add("conferenceid", c.getConferenceid() );
    	
    	ServiceInvoker si = new ServiceInvoker("ConferenceServices",
				"AddConferenceToUserFavsService");
		Message msg = si.deliverSync(esbMessage, 5000);
    	
		Object obj = msg.getBody().get();
		List<Conference> response = null;
		
		if (obj instanceof List<?>) {
			response = (List<Conference>) obj;
		} else if (obj instanceof byte[]) {
			response = (List<Conference>) Serializer.deserialize((byte[]) obj);
		}

		System.out.println("[REST Gateway POST] Outgoing response");
		
		return gson.toJson(response);	
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/conference/removefromuserfav")
	@Produces("application/json")
	public String removeConferenceFromUserFavs(String objString) throws Exception{

		Gson gson = new Gson();
		
		UserAndConferenceIDs c = gson.fromJson(objString, UserAndConferenceIDs.class);
		
		System.out.println("[REST Gateway POST] Remove Conference From User Favs");
		
		System.setProperty("javax.xml.registry.ConnectionFactoryClass", "org.apache.ws.scout.registry.ConnectionFactoryImpl");
	
        Message esbMessage = MessageFactory.getInstance().getMessage();

        esbMessage.getBody().add("userid", c.getUserid());
        esbMessage.getBody().add("conferenceid", c.getConferenceid() );
    	
    	ServiceInvoker si = new ServiceInvoker("ConferenceServices",
				"RemoveConferenceFromUserFavsService");
		Message msg = si.deliverSync(esbMessage, 5000);
    	
		Object obj = msg.getBody().get();
		List<Conference> response = null;
		
		if (obj instanceof List<?>) {
			response = (List<Conference>) obj;
		} else if (obj instanceof byte[]) {
			response = (List<Conference>) Serializer.deserialize((byte[]) obj);
		}

		System.out.println("[REST Gateway POST] Outgoing response");
		System.out.println("-------------------------------------------");
		
		return gson.toJson(response);	
	}
	
	@POST
	@Path("/conference/add")
	@Produces("application/json")
	public String addConference(String objString) throws Exception{

		Gson gson = new Gson();
		
		Conference c = gson.fromJson(objString, Conference.class);
		
		System.out.println("[REST Gateway POST] Add Conference");
		
		System.setProperty("javax.xml.registry.ConnectionFactoryClass", "org.apache.ws.scout.registry.ConnectionFactoryImpl");
	
        Message esbMessage = MessageFactory.getInstance().getMessage();

        esbMessage.getBody().add(c);
    	
    	ServiceInvoker si = new ServiceInvoker("ConferenceServices",
				"AddConferenceService");
		Message msg = si.deliverSync(esbMessage, 5000);
    	
		Object obj = msg.getBody().get();
		Conference response = null;
		
		if (obj instanceof Conference) {
			response = (Conference) obj;
		} else if (obj instanceof byte[]) {
			response = (Conference) Serializer.deserialize((byte[]) obj);
		}

		System.out.println("[REST Gateway POST] Outgoing response");
		System.out.println("-------------------------------------------");
		
		return gson.toJson(response);	
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/conference/getuserfav")
	@Produces("application/json")
	public String getUserFavs(String objString) throws Exception{
		
		Gson gson = new Gson();

		User user = gson.fromJson(objString, User.class);
		
		System.out.println("[REST Gateway GET] Get User Favs");
		
		System.setProperty("javax.xml.registry.ConnectionFactoryClass", "org.apache.ws.scout.registry.ConnectionFactoryImpl");
		Message esbMessage = MessageFactory.getInstance().getMessage();
		
		esbMessage.getBody().add(user);
		ServiceInvoker si = new ServiceInvoker("ConferenceServices",
				"GetUserFavsService");
		Message msg = si.deliverSync(esbMessage, 5000);

		Object obj = msg.getBody().get();
		List<Conference> response = null;
		
		if (obj instanceof List<?>) {
			response = (List<Conference>) obj;
		} else if (obj instanceof byte[]) {
			response = (List<Conference>) Serializer.deserialize((byte[]) obj);
		}
		
		System.out.println("[REST Gateway GET] Outgoing response");
        System.out.println("-------------------------------------------");
        
        return gson.toJson(response);
		
	}
	
	@SuppressWarnings("unchecked")
	@GET
	@Path("/conference/getall")
	@Produces("application/json")
	public String getAllConferences() throws Exception{
		
		System.out.println("[REST Gateway GET] Get All Conferences");
		
		System.setProperty("javax.xml.registry.ConnectionFactoryClass", "org.apache.ws.scout.registry.ConnectionFactoryImpl");
		Message esbMessage = MessageFactory.getInstance().getMessage();
		
		ServiceInvoker si = new ServiceInvoker("ConferenceServices",
				"GetAllConferencesService");
		Message msg = si.deliverSync(esbMessage, 5000);

		Object obj = msg.getBody().get();
		List<Conference> response = null;
		
		if (obj instanceof List<?>) {
			response = (List<Conference>) obj;
		} else if (obj instanceof byte[]) {
			response = (List<Conference>) Serializer.deserialize((byte[]) obj);
		}
		
		System.out.println("[REST Gateway POST] Outgoing response");
        System.out.println("-------------------------------------------");
			
        Gson gson = new Gson();
        
        return gson.toJson(response);	
	}
	
	@POST
	@Path("/user/login")
	@Consumes("application/json")
	public String logInUser(String objString) throws Exception{
		
		Gson gson = new Gson();

		User user = gson.fromJson(objString, User.class);
		
		System.out.println("[REST Gateway POST] Log In User");
		
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
		
		System.out.println("[REST Gateway POST] Outgoing response");
		System.out.println("-------------------------------------------");
			
        return gson.toJson(response);  
	}
	
	@POST
	@Path("/user/register")
	@Consumes("application/json")
	public String registerUser(String objString) throws Exception{
		
		Gson gson = new Gson();
		
		User user = gson.fromJson(objString, User.class);

		System.out.println("[REST Gateway POST] Register User");
		
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
		
		System.out.println("[REST Gateway POST] Outgoing response");
		System.out.println("-------------------------------------------");
		
        return gson.toJson(response);
	}
}