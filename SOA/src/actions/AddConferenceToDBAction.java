package actions;

import model.Conference;

import org.hibernate.Session;
import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

import util.Serializer;

import db.DBUtil;
import db.HibernateUtil;

public class AddConferenceToDBAction extends AbstractActionLifecycle {
    protected ConfigTree  _config;

    public AddConferenceToDBAction(ConfigTree config) {
        _config = config;
    }

	public Message process(Message message) throws Exception {

    	Object obj = message.getBody().get();
    	Conference request = Serializer.deserializeConference(obj);
        
        System.out.println("[AddConferenceToDBAction] Incoming Conference name : " + request.getName());
        
        /*
         * Check for taken Conference name
         */
        Conference respCon = DBUtil.checkConferenceInDB(request.getName());

        if(respCon == null){
        	/*
        	 * Not taken
        	 */
        	addConference(request);
        	
        	/* 
        	 * Check for successful add and get new ID
        	 */
        	Conference respCon2 = DBUtil.checkConferenceInDB(request.getName());
        	
            if(respCon2 == null){
            	/*
            	 * Unsuccessful add
            	 */
            	respCon = request;
            	respCon.setId(-2);
            	
            }else{
            	/*
            	 * Successful add, we have a new ID
            	 */
            	respCon = respCon2;
            }
        }else{ 	
        	/*
        	 * Taken
        	 */
        	respCon = request;
        	respCon.setId(-1);
        }
     
        message.getBody().add(Serializer.serialize(respCon));
        
        System.out.println("[AddConferenceToDBAction] Outgoing Conference id : " +respCon.getId());
        
        return message;
    }
    
    private static void addConference(Conference u){		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(u);
		session.getTransaction().commit();
	} 
}
