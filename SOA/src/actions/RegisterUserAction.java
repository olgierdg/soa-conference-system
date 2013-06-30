package actions;

import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.client.ServiceInvoker;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.format.MessageFactory;

import util.Serializer;

import model.User;

public class RegisterUserAction extends AbstractActionLifecycle {
    protected ConfigTree  _config;

    public RegisterUserAction(ConfigTree config) {
        _config = config;
    }

	public Message process(Message message) throws Exception {

    	/*
    	 * Deserialization - for debugging
    	 */
		Object obj = message.getBody().get();
    	User request = Serializer.deserializeUser(obj);        
        System.out.println("[RegisterUserAction] Incoming user nick : " + request.getNick());
            
        System.setProperty("javax.xml.registry.ConnectionFactoryClass",
				"org.apache.ws.scout.registry.ConnectionFactoryImpl");
        Message esbMessage = MessageFactory.getInstance().getMessage();
        esbMessage.getBody().add(Serializer.serialize(request));
        
        Message response = new ServiceInvoker("UserServices",
				"AddUserToDBService").deliverSync(esbMessage, 20000);
        
        Object resp = response.getBody().get();
        
        User respUser = (User)Serializer.deserialize((byte[]) resp);
              
        System.out.println("[RegisterUserAction] Outgoing response code : " +respUser.getId());
        
        message.getBody().add(Serializer.serialize(respUser));
        
        return message;
    }
}
