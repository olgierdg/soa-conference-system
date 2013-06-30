package actions;

import java.util.List;

import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.client.ServiceInvoker;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.format.MessageFactory;

import util.Serializer;

import model.Conference;
import model.User;

public class GetUserFavsAction extends AbstractActionLifecycle {
    protected ConfigTree  _config;

    public GetUserFavsAction(ConfigTree config) {
        _config = config;
    }

    @SuppressWarnings("unchecked")
	public Message process(Message message) throws Exception {

    	Object obj = message.getBody().get();
    	
    	User request = Serializer.deserializeUser(obj);
        
        System.out.println("[GetUserFavsAction] Incoming user nick : " + request.getNick());
        
        System.setProperty("javax.xml.registry.ConnectionFactoryClass",
				"org.apache.ws.scout.registry.ConnectionFactoryImpl");
        Message esbMessage = MessageFactory.getInstance().getMessage();
        esbMessage.getBody().add(Serializer.serialize(request));
              
        Message response = new ServiceInvoker("ConferenceServices",
				"GetUserFavsFromDBService").deliverSync(esbMessage, 5000);
        
        Object resp = response.getBody().get();
        
        List<Conference> respList = (List<Conference>)Serializer.deserialize((byte[]) resp);
        
        System.out.println("[GetUserFavsAction] Get UserFavourite conferences response");
        
        message.getBody().add(Serializer.serialize(respList));
        
        return message;   
    }
}
