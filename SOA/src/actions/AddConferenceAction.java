package actions;

import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.client.ServiceInvoker;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.format.MessageFactory;

import util.Serializer;

import model.Conference;

public class AddConferenceAction extends AbstractActionLifecycle {
    protected ConfigTree  _config;

    public AddConferenceAction(ConfigTree config) {
        _config = config;
    }

	public Message process(Message message) throws Exception {

		/*
		 * Deserialization - for debugging
		 */
    	Object obj = message.getBody().get();
    	Conference request = Serializer.deserializeConference(obj);
        
        System.out.println("[AddConferenceAction] Incoming conference name : " + request.getName());
            
        System.setProperty("javax.xml.registry.ConnectionFactoryClass",
				"org.apache.ws.scout.registry.ConnectionFactoryImpl");
        Message esbMessage = MessageFactory.getInstance().getMessage();
        esbMessage.getBody().add(Serializer.serialize(request));
        
        Message response = new ServiceInvoker("ConferenceServices",
				"AddConferenceToDBService").deliverSync(esbMessage, 20000);
        
        Object resp = response.getBody().get();
        
        Conference respUser = (Conference)Serializer.deserialize((byte[]) resp);
              
        System.out.println("[AddConferenceAction] Outgoing conference id : " +respUser.getId());
        
        message.getBody().add(Serializer.serialize(respUser));
        
        return message;
    }
}
