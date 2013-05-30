package actions;

import java.util.Map;

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

    @SuppressWarnings("unchecked")
	public Message process(Message message) throws Exception {

    	Object obj = message.getBody().get();
    	Conference request = null;
        
        if (obj instanceof Conference) {
        	request = (Conference) obj;
        } else if (obj instanceof byte[]) {
        	request = (Conference)Serializer.deserialize((byte[]) obj);
        } else if (obj instanceof Map) {
        	Map<Conference, Object> rowData =(Map<Conference, Object>)obj;
        	for (Map.Entry<Conference, Object> curr : rowData.entrySet()) {
        		Object value = curr.getValue();
            	if (value instanceof String) {
            		request = (Conference) value;
            	}
  		    }
        }
        
        System.out.println("[AddConferenceAction] Incoming conference id : " + request.getId());
            
        System.setProperty("javax.xml.registry.ConnectionFactoryClass",
				"org.apache.ws.scout.registry.ConnectionFactoryImpl");
        Message esbMessage = MessageFactory.getInstance().getMessage();
        esbMessage.getBody().add(Serializer.serialize(request));
        
        Message response = new ServiceInvoker("ConferenceServices",
				"AddConferenceToDBService").deliverSync(esbMessage, 20000);
        
        Object resp = response.getBody().get();
        
        Conference respUser = (Conference)Serializer.deserialize((byte[]) resp);
              
        System.out.println("[AddConferenceAction] Outgoing response id : " +respUser.getId());
        
        message.getBody().add(Serializer.serialize(respUser));
        
        return message;
    }
}
