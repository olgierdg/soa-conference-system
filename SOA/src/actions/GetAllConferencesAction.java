package actions;

import java.util.List;

import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.client.ServiceInvoker;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.format.MessageFactory;

import util.Serializer;

import model.Conference;

public class GetAllConferencesAction extends AbstractActionLifecycle {
    protected ConfigTree  _config;

    public GetAllConferencesAction(ConfigTree config) {
        _config = config;
    }

    @SuppressWarnings("unchecked")
	public Message process(Message message) throws Exception {

        System.out.println("[GetAllConferencesAction] Get all conferences request");
        
        System.setProperty("javax.xml.registry.ConnectionFactoryClass",
				"org.apache.ws.scout.registry.ConnectionFactoryImpl");
        Message esbMessage = MessageFactory.getInstance().getMessage();
              
        /*
         * Get all Conferences from DB
         */
        Message response = new ServiceInvoker("ConferenceServices",
				"GetAllConferencesFromDBService").deliverSync(esbMessage, 5000);
        
        Object resp = response.getBody().get();
        
        List<Conference> respUser = (List<Conference>)Serializer.deserialize((byte[]) resp);
        
        System.out.println("[GetAllConferencesAction] Get all conferences response");
        
        message.getBody().add(Serializer.serialize(respUser));
        
        return message;   
    }
}
