package actions;

import java.util.List;

import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.client.ServiceInvoker;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.format.MessageFactory;

import util.Serializer;

import model.Conference;

public class RemoveConferenceFromUserFavsAction extends AbstractActionLifecycle {
    protected ConfigTree  _config;

    public RemoveConferenceFromUserFavsAction(ConfigTree config) {
        _config = config;
    }

	@SuppressWarnings("unchecked")
	public Message process(Message message) throws Exception {

		Object conferenceidobj = message.getBody().get("conferenceid");
    	Integer conferenceid = Serializer.deserializeInteger(conferenceidobj);
    	
    	Object useridobj = message.getBody().get("userid");
    	Integer userid = Serializer.deserializeInteger(useridobj);
        
        System.out.println("[RemoveConferenceFromUserFavsAction] Incoming conference id : " + conferenceid);
        System.out.println("[RemoveConferenceFromUserFavsAction] Incoming user id : " + userid);
            
        System.setProperty("javax.xml.registry.ConnectionFactoryClass",
				"org.apache.ws.scout.registry.ConnectionFactoryImpl");
        Message esbMessage = MessageFactory.getInstance().getMessage();
        esbMessage.getBody().add("conferenceid", Serializer.serialize(conferenceid));
        esbMessage.getBody().add("userid", Serializer.serialize(userid));
        
        Message response = new ServiceInvoker("ConferenceServices",
				"RemoveConferenceFromUserFavsFromDBService").deliverSync(esbMessage, 20000);
             
        Object resp = response.getBody().get();
        
        List<Conference> respList = (List<Conference>)Serializer.deserialize((byte[]) resp);
        
        System.out.println("[RemoveConferenceFromUserFavsAction] AddConferenceToUserFavsAction response");
        
        message.getBody().add(Serializer.serialize(respList));
        
        return message;  
    }
}

