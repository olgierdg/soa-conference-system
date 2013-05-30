package actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
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
        	User respUser2 = checkUserInDB(request);
        	
            if(respUser2 == null){
            	/*
            	 * Unsuccessful add
            	 */
            	respUser.setId(-2);
            	respUser.setIdsConferences(new ArrayList<Integer>());
            	
            }else{
            	/*
            	 * Successful add, we have a new ID
            	 */
            	respUser = respUser2;
            	/*
            	 * Get Conferences list for new User
            	 */
            	respUser.setIdsConferences(getList(respUser));
            }

        }else{ 	
        	/*
        	 * Taken
        	 */
        	respUser.setId(-1);
        	respUser.setIdsConferences(new ArrayList<Integer>());
        }

        message.getBody().add(Serializer.serialize(respUser));
        
        System.out.println("[AddUserToDBAction] Outgoing response code : " +respUser.getId());
        
        return message;
    }
    
    private static void addUser(User u){		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(u);
		session.getTransaction().commit();
	}
  
    @SuppressWarnings("rawtypes")
   	public static User checkUserInDB(User user){
       	User retUser = null;
   		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
   		session.beginTransaction();  		
   		List list = session.createSQLQuery("select * from _user;").addEntity(User.class).list();
   		Iterator itr = list.iterator();
   		while(itr.hasNext()){
   			User u = (User)itr.next();
   			if (user.getNick().trim().equals(u.getNick().trim())) {
   				retUser = u;
   				break;
   			}
   		}		
   		session.getTransaction().commit();
   		return retUser;
   		
   	}
       
    @SuppressWarnings("unchecked")
   	public static List<Integer> getList(User user){
   		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
   		session.beginTransaction();

   		List<Integer> list = session.createSQLQuery(
   				"select elt from _user_idsconferences us " +
   				"where us.idsconferences = " + user.getId() +";").list();

   		session.getTransaction().commit();

   		return list;
   	}
    
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
