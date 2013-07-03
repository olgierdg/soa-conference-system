package actions;

import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.client.ServiceInvoker;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.format.MessageFactory;

public class GetUserFavsAction extends AbstractActionLifecycle {
    protected ConfigTree  _config;

    public GetUserFavsAction(ConfigTree config) {
        _config = config;
    }

	public Message process(Message message) throws Exception {

    	Object inc = message.getBody().get();
    	
    	System.out.println("[GetUserFavs] Incoming user");
        
        System.setProperty("javax.xml.registry.ConnectionFactoryClass",
				"org.apache.ws.scout.registry.ConnectionFactoryImpl");
        Message esbMessage = MessageFactory.getInstance().getMessage();

        esbMessage.getBody().add(inc);
              
        Message response = new ServiceInvoker("ConferenceServices",
				"GetUserFavsFromDBService").deliverSync(esbMessage, 5000);
        
        Object resp = response.getBody().get();
        
        System.out.println("[GetUserFavs] Outgoing response");
        System.out.println("-------------------------------------------");
        
        message.getBody().add(resp);
        
        return message;   
    }
}
