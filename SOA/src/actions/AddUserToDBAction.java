package actions;

import java.util.ArrayList;

import org.hibernate.Session;
import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

import util.Serializer;

import db.DBUtil;
import db.HibernateUtil;

import model.User;

public class AddUserToDBAction extends AbstractActionLifecycle {
    protected ConfigTree  _config;

    public AddUserToDBAction(ConfigTree config) {
        _config = config;
    }

	public Message process(Message message) throws Exception {

    	Object obj = message.getBody().get();
    	User request = Serializer.deserializeUser(obj);
        
        System.out.println("[AddUserToDBAction] Incoming user nick : " + request.getNick());
        
        /*
         * Check for already taken nick
         */
        User respUser = DBUtil.checkUserInDB(request.getNick());

        if(respUser == null){
        	/*
        	 * Not taken
        	 */
        	addUser(request);
        	
        	/* 
        	 * Check for successful add and get new ID
        	 */
        	User respUser2 = DBUtil.checkUserInDB(request.getNick());
        	
            if(respUser2 == null){
            	/*
            	 * Unsuccessful add
            	 */
            	respUser = request;
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
            	respUser.setIdsConferences(DBUtil.getFavsIdsList(respUser.getId()));
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
}
