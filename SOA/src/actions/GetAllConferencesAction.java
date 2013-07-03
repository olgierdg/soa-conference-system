package actions;

import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.client.ServiceInvoker;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.format.MessageFactory;

public class GetAllConferencesAction extends AbstractActionLifecycle {
    protected ConfigTree  _config;

    public GetAllConferencesAction(ConfigTree config) {
        _config = config;
    }

	public Message process(Message message) throws Exception {

        System.out.println("[GetAllConferences] Incoming request");
        
        System.setProperty("javax.xml.registry.ConnectionFactoryClass",
				"org.apache.ws.scout.registry.ConnectionFactoryImpl");
        Message esbMessage = MessageFactory.getInstance().getMessage();
              
        /*
         * Get all Conferences from DB
         */
        Message response = new ServiceInvoker("ConferenceServices",
				"GetAllConferencesFromDBService").deliverSync(esbMessage, 5000);
        
        Object resp = response.getBody().get();
        
        System.out.println("[GetAllConferences] Outgoing response");
        System.out.println("-------------------------------------------");
        
        message.getBody().add(resp);
        
        return message;   
    }
}
