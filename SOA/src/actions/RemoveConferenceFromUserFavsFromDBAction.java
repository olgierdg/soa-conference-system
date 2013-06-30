package actions;

import java.util.ArrayList;
import java.util.List;

import model.Conference;

import org.hibernate.Session;
import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

import util.Serializer;

import db.DBUtil;
import db.HibernateUtil;

public class RemoveConferenceFromUserFavsFromDBAction extends AbstractActionLifecycle {
    protected ConfigTree  _config;

    public RemoveConferenceFromUserFavsFromDBAction(ConfigTree config) {
        _config = config;
    }

	public Message process(Message message) throws Exception {

		Object conferenceidobj = message.getBody().get("conferenceid");
    	Integer conferenceid = Serializer.deserializeInteger(conferenceidobj);
    	
    	Object useridobj = message.getBody().get("userid");
    	Integer userid = Serializer.deserializeInteger(useridobj);
        
        System.out.println("[RemoveConferenceFromUserFavsFromDBAction] Incoming conference id : " + conferenceid);
        System.out.println("[RemoveConferenceFromUserFavsFromDBAction] Incoming user id : " + userid);
        
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
        	 * Check User's favs contain this conference
        	 */
        	if(l.contains(conferenceid)){
        		/*
            	 * Remove
            	 */
        		l.remove(conferenceid);
            	removeFav(userid, l);	
        	}
        }
  
        /*
    	 * Get User's udpated favs
    	 */
    	retList = DBUtil.getFavsList(userid);
        
        if(retList == null)
    		retList = new ArrayList<Conference>();
        
        message.getBody().add(Serializer.serialize(retList));
        
        System.out.println("[RemoveConferenceFromUserFavsFromDBAction] Outgoing Favs list");
        
        return message;
    }
    
    private static void removeFav(int userid, List<Integer> l){		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.createSQLQuery("DELETE FROM _user_idsconferences WHERE " +
				"idsconferences = " + userid + ";").executeUpdate();
		for(int i = 0; i < l.size(); i++){
			session.createSQLQuery("INSERT INTO _user_idsconferences VALUES " +
					"(" + userid + ", " + l.get(i) + ", " + i + ")").executeUpdate();
		}	
		session.getTransaction().commit();
	}
}

