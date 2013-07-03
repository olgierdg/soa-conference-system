package actions;

import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.client.ServiceInvoker;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.format.MessageFactory;

public class AddConferenceToUserFavsAction extends AbstractActionLifecycle {
    protected ConfigTree  _config;

    public AddConferenceToUserFavsAction(ConfigTree config) {
        _config = config;
    }

	public Message process(Message message) throws Exception {

		Object conferenceidobj = message.getBody().get("conferenceid");   	
    	Object useridobj = message.getBody().get("userid");
        
        System.out.println("[AddConferenceToUserFavs] Incoming conference");
        System.out.println("[AddConferenceToUserFavs] Incoming user");
            
        System.setProperty("javax.xml.registry.ConnectionFactoryClass",
				"org.apache.ws.scout.registry.ConnectionFactoryImpl");
        Message esbMessage = MessageFactory.getInstance().getMessage();
        esbMessage.getBody().add("conferenceid", conferenceidobj);
        esbMessage.getBody().add("userid", useridobj);
        
        Message response = new ServiceInvoker("ConferenceServices",
				"AddConferenceToUserFavsToDBService").deliverSync(esbMessage, 20000);
             
        Object resp = response.getBody().get();
        
        System.out.println("[AddConferenceToUserFavs] Outgoing response");
        System.out.println("-------------------------------------------");
        
        message.getBody().add(resp);
        
        return message;  
    }
}

