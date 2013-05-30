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

@Path("/json")
public class JSONService {

	@GET
	@Path("/conference/add")
	@Produces("application/json")
	public String addConference() throws Exception{

	System.setProperty("javax.xml.registry.ConnectionFactoryClass", "org.apache.ws.scout.registry.ConnectionFactoryImpl");
		
        Message esbMessage = MessageFactory.getInstance().getMessage();

        Conference c8 = new Conference(8, "Miłość w kamienicach", "Wrocław", "9/6/2014","Poznaj życie w kamienicy, gdzie nie grozi Ci wybuch piecyka gazowego, ponieważ go nie masz. Dowiesz się jak założyć rodzinę w kamienicy do rozbiórki, dostając jedynie zasiłek dla bezrobotnych.", "Jan Ostowski", "Wiekowa już postać, która żyła w kamienicy przez ponad pół swojego życia. Obecnie milioner z kontem w Szwajcarii.", 51.1, 17.0);
        
        esbMessage.getBody().add(c8);
    	
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
		
		Gson gson = new Gson();
        
        return gson.toJson(response);
		
	}
	
	@SuppressWarnings("unchecked")
	@GET
	@Path("/conference/getall")
	@Produces("application/json")
	public String getAllConferences() throws Exception{
		
		System.out.println("[REST Gateway GET] GET all conferences request");
		
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
		
		System.out.println("[REST Gateway POST] Outgoing response says : ");
        System.out.println("-------------------------------------------");
			
        Gson gson = new Gson();
        
        return gson.toJson(response);
		
	}
	
	@POST
	@Path("/user/login")
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
	@Path("/user/register")
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