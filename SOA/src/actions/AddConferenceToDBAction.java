package actions;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import model.Conference;

import org.hibernate.Session;
import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

import util.Serializer;

import db.HibernateUtil;

public class AddConferenceToDBAction extends AbstractActionLifecycle {
    protected ConfigTree  _config;

    public AddConferenceToDBAction(ConfigTree config) {
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
        
        System.out.println("[AddConferenceToDBAction] Incoming Conference id : " + request.getId());
        
        /*
         * Check for already taken nick
         */
        //User respUser = invokeGetUserFromDBService(request);

        //if(respUser.getId() == 0){
        	/*
        	 * Not taken
        	 */
        	addConference(request);
        	
        	/* 
        	 * Check for successful add and get new ID
        	 */
        	Conference respUser = checkConferenceInDB(request);
        	
            if(respUser == null){
            	/*
            	 * Unsuccessful add
            	 */
            	respUser = request;
            	respUser.setId(-2);
            	//respUser.setIdsConferences(new ArrayList<Integer>());
            	
            }else{
            	/*
            	 * Successful add, we have a new ID
            	 */
            	//respUser = respUser2;
            	/*
            	 * Get Conferences list for new User
            	 */
            	//respUser.setIdsConferences(getList(respUser));
            }

       // }else{ 	
        	/*
        	 * Taken
        	 */
        //	respUser.setId(-1);
     //   	respUser.setIdsConferences(new ArrayList<Integer>());
     //   }

        message.getBody().add(Serializer.serialize(respUser));
        
        System.out.println("[AddConferenceToDBAction] Outgoing Conference id : " +respUser.getId());
        
        return message;
    }
    
    private static void addConference(Conference u){		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(u);
		session.getTransaction().commit();
	}
  
    @SuppressWarnings("rawtypes")
   	public static Conference checkConferenceInDB(Conference user){
    	Conference retUser = null;
   		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
   		session.beginTransaction();  		
   		List list = session.createSQLQuery("select * from _conference;").addEntity(Conference.class).list();
   		Iterator itr = list.iterator();
   		while(itr.hasNext()){
   			Conference u = (Conference)itr.next();
   			if (user.getName().trim().equals(u.getName().trim())) {
   				retUser = u;
   				break;
   			}
   		}		
   		session.getTransaction().commit();
   		return retUser;
   		
   	}
       
    /*
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
    */
}
