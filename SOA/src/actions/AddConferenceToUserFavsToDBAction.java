package actions;

import java.util.List;

import model.Conference;

import org.hibernate.Session;
import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

import util.Serializer;

import db.DBUtil;
import db.HibernateUtil;

public class AddConferenceToUserFavsToDBAction extends AbstractActionLifecycle {
    protected ConfigTree  _config;

    public AddConferenceToUserFavsToDBAction(ConfigTree config) {
        _config = config;
    }

	public Message process(Message message) throws Exception {

		Object conferenceidobj = message.getBody().get("conferenceid");
    	Integer conferenceid = Serializer.deserializeInteger(conferenceidobj);
    	
    	Object useridobj = message.getBody().get("userid");
    	Integer userid = Serializer.deserializeInteger(useridobj);
        
        System.out.println("[AddConferenceToUserFavsToDBAction] Incoming conference id : " + conferenceid);
        System.out.println("[AddConferenceToUserFavsToDBAction] Incoming user id : " + userid);
        
        List<Conference> retList = null;
        
        /*
         * Check if User and Conference are in DB
         */
        boolean isUserInDB = DBUtil.checkUserInDB(userid);
        boolean isConferenceInDB = DBUtil.checkConferenceInDB(conferenceid);
        
        if(isUserInDB & isConferenceInDB){
        	
        	/*
        	 * Get list of IDs of current User's favs
        	 */
        	List<Integer> l = DBUtil.getFavsIdsList(userid);
        	
        	/*
        	 * Check if this Conference is already in User's favs
        	 */
        	if(!l.contains(conferenceid)){
        		/*
            	 * Add  
            	 */
            	addFav(userid, conferenceid, l.size());
            	
            	/*
            	 * Get User's udpated favs
            	 */
            	retList = DBUtil.getFavsList(userid);
        	}
        }
  
        message.getBody().add(Serializer.serialize(retList));
        
        System.out.println("[AddConferenceToUserFavsToDBAction] Outgoing Favs list");
        
        return message;
    }
    
    private static void addFav(int userid, int conferenceid, int ids){		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.createSQLQuery("INSERT INTO _user_idsconferences VALUES " +
				"(" + userid + ", " + conferenceid + ", " + ids + ")").executeUpdate();
		session.getTransaction().commit();
	}
}

