package rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.jboss.soa.esb.client.ServiceInvoker;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.format.MessageFactory;

import util.Serializer;

import model.User;
import model.ConnectionResponse;

@Path("/json/user")
public class JSONService {


	@POST
	@Path("/post")
	@Consumes("application/json")
	public Response logInUser(User user) throws Exception{
		
		System.out.println("[REST Gateway POST] LogIn User, id : " + user.getId());
		
		System.setProperty("javax.xml.registry.ConnectionFactoryClass", "org.apache.ws.scout.registry.ConnectionFactoryImpl");
		Message esbMessage = MessageFactory.getInstance().getMessage();
		
		esbMessage.getBody().add(user);
		ServiceInvoker si = new ServiceInvoker("UserServices",
				"LogInUserService");
		Message msg = si.deliverSync(esbMessage, 5000);

		Object obj = msg.getBody().get();
		ConnectionResponse response = null;
		
		if (obj instanceof ConnectionResponse) {
			response = (ConnectionResponse) obj;
		} else if (obj instanceof byte[]) {
			response = (ConnectionResponse) Serializer.deserialize((byte[]) obj);
		}
		
		System.out.println("[REST Gateway POST] Outgoing response says : " + response.getMessage());
        System.out.println("-------------------------------------------");
		
		return Response.status(201).entity(response).build();
		
	}
	
	@POST
	@Path("/register")
	@Consumes("application/json")
	public Response registerUser(User user) throws Exception{
		
		System.out.println("[REST Gateway POST] Register User, id : " + user.getId());
		
		System.setProperty("javax.xml.registry.ConnectionFactoryClass", "org.apache.ws.scout.registry.ConnectionFactoryImpl");
		Message esbMessage = MessageFactory.getInstance().getMessage();
		
		esbMessage.getBody().add(user);
		ServiceInvoker si = new ServiceInvoker("UserServices",
				"RegisterUserService");
		Message msg = si.deliverSync(esbMessage, 5000);

		Object obj = msg.getBody().get();
		ConnectionResponse response = null;
		
		if (obj instanceof ConnectionResponse) {
			response = (ConnectionResponse) obj;
		} else if (obj instanceof byte[]) {
			response = (ConnectionResponse) Serializer.deserialize((byte[]) obj);
		}
		
		System.out.println("[REST Gateway POST] Outgoing response says : " + response.getMessage());
        System.out.println("-------------------------------------------");
		
		return Response.status(201).entity(response).build();
	}
}