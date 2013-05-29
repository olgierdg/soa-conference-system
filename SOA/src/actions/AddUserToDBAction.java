package actions;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.client.ServiceInvoker;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.format.MessageFactory;

import util.Serializer;

import db.HibernateUtil;

import model.ConnectionResponse;
import model.User;

public class AddUserToDBAction extends AbstractActionLifecycle {
    protected ConfigTree  _config;

    public AddUserToDBAction(ConfigTree config) {
        _config = config;
    }

    @SuppressWarnings("unchecked")
	public Message process(Message message) throws Exception {

    	Object obj = message.getBody().get();
    	User request = null;
        
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
        
        System.out.println("[AddUserToDBAction] Incoming user nick : " + request.getNick());

        /*
         * Eager initialization
         */
        ConnectionResponse connResp = new ConnectionResponse();
        
        /*
         * Check for already taken nick
         */
        User respUser = invokeGetUserFromDBService(request);

        if(respUser.getId() == 0){
        	/*
        	 * Not taken
        	 */
        	addUser(request);
        	
        	/* 
        	 * Check for successful add and get new ID
        	 */
            //User respUser2 = invokeGetUserFromDBService(request);
        	//User respUser2 = invokeCheckUserInDBService(request);
        	User respUser2 = checkUserInDB(request);
        	
            if(respUser2 == null){
            	/*
            	 * Unsuccessful add
            	 */
            	connResp.setCode(-2);
            	connResp.setMessage("Registration unsuccessful");
            	
            }else{
            	/*
            	 * Successful add, we have a new ID
            	 */
            	connResp.setCode(respUser2.getId());
            	connResp.setMessage("Registration successful");
            }

        }else{ 	
        	/*
        	 * Taken
        	 */
        	connResp.setCode(-1);
        	connResp.setMessage("Registration unsuccessful - nick already taken");
        }

        message.getBody().add(Serializer.serialize(connResp));
        
        System.out.println("[AddUserToDBAction] Outgoing response code : " +connResp.getCode() + ", says : " + connResp.getMessage());
        
        return message;
    }
    
    private static void addUser(User u){		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(u);
		session.getTransaction().commit();
	}
    
    
    @SuppressWarnings("rawtypes")
	private static User checkUserInDB(User u){
    	User retUser = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List list = session.createSQLQuery("select * from _user;").addEntity(User.class).list();
		Iterator itr = list.iterator();
		while(itr.hasNext()){
			User us = (User)itr.next();
			if (u.getNick().trim().equals(us.getNick().trim())) {
				retUser = u;
				break;
			}
		}
		session.getTransaction().commit();
		return retUser;
    }
    
    /*
    private User invokeCheckUserInDBService(User u) throws Exception{
    	System.setProperty("javax.xml.registry.ConnectionFactoryClass",
				"org.apache.ws.scout.registry.ConnectionFactoryImpl");
        Message esbMessage = MessageFactory.getInstance().getMessage();
        esbMessage.getBody().add(AddUserToDBAction.serialize(u));
        
        Message response = new ServiceInvoker("UserServices",
				"ChecktUserInDBService").deliverSync(esbMessage, 10000);
        
        Object resp = response.getBody().get();
        
        User respUser = (User)AddUserToDBAction.deserialize((byte[]) resp);
        
        return respUser;
    }
    */
    
    private User invokeGetUserFromDBService(User u) throws Exception{
    	System.setProperty("javax.xml.registry.ConnectionFactoryClass",
				"org.apache.ws.scout.registry.ConnectionFactoryImpl");
        Message esbMessage = MessageFactory.getInstance().getMessage();
        esbMessage.getBody().add(Serializer.serialize(u));
        
        Message response = new ServiceInvoker("UserServices",
				"GetUserFromDBRegisterService").deliverSync(esbMessage, 10000);
        
        Object resp = response.getBody().get();
        
        User respUser = (User)Serializer.deserialize((byte[]) resp);
        
        return respUser;
    }
}
