package actions;

import java.util.Map;

import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.client.ServiceInvoker;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.format.MessageFactory;

import util.Serializer;

import model.ConnectionResponse;
import model.User;

public class LogInUserAction extends AbstractActionLifecycle {
    protected ConfigTree  _config;

    public LogInUserAction(ConfigTree config) {
        _config = config;
    }

    @SuppressWarnings("unchecked")
	public Message process(Message message) throws Exception {

    	Object obj = message.getBody().get();
    	User request = null;
        
    	/*
    	 * Deserialization, in practice this utilises that static method,
    	 * but this code is nice
    	 */
        if (obj instanceof User) {
        	request = (User) obj;
        } else if (obj instanceof byte[]) {
        	request = (User)Serializer.deserialize((byte[]) obj);
        } else if (obj instanceof Map) {
        	Map<User, Object> rowData =(Map<User, Object>)obj;
        	for (Map.Entry<User, Object> curr : rowData.entrySet()) {
        		Object value = curr.getValue();
            	if (value instanceof String) {
            		request = (User) value;
            	}
  		    }
        }
        
        System.out.println("[LogInUserAction] Incoming user nick : " + request.getNick());
        
        System.setProperty("javax.xml.registry.ConnectionFactoryClass",
				"org.apache.ws.scout.registry.ConnectionFactoryImpl");
        Message esbMessage = MessageFactory.getInstance().getMessage();
        esbMessage.getBody().add(Serializer.serialize(request));
              
        /*
         * Get User from DB
         */
        Message response = new ServiceInvoker("UserServices",
				"GetUserFromDBLoginService").deliverSync(esbMessage, 5000);
        
        Object resp = response.getBody().get();
        
        User respUser = (User)Serializer.deserialize((byte[]) resp);

        ConnectionResponse connResp = new ConnectionResponse();
        
        if(respUser.getId() == 0){
        	connResp.setCode(-1);
        	connResp.setMessage("No such nick in DB");
        }else{
        	if(respUser.getPassword().trim().equals(request.getPassword().trim())){
        		connResp.setCode(respUser.getId());
            	connResp.setMessage("Login successful");
        	}else{
        		connResp.setCode(-2);
            	connResp.setMessage("Wrong password");
        	}
        }
        
        System.out.println("[LogInUserAction] Outgoing response code : " +connResp.getCode() + ", says : " + connResp.getMessage());
        
        message.getBody().add(Serializer.serialize(connResp));
        
        return message;   
    }
}
