package actions;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

import util.Serializer;

import db.HibernateUtil;

import model.Conference;

public class GetAllConferencesFromDBAction extends AbstractActionLifecycle {
    protected ConfigTree  _config;

    public GetAllConferencesFromDBAction(ConfigTree config) {
        _config = config;
    }

	public Message process(Message message) throws Exception {

        System.out.println("[GetAllConferencesFromDB] Incoming request");
           
        List<Conference> list = getConferences();
        if(list == null)
        	list = new ArrayList<Conference>();
        
        message.getBody().add(Serializer.serialize(list));
        System.out.println("[GetAllConferencesFromDB] Outgoing response");
        
        return message;
    }
  
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<Conference> getConferences(){
    	
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List list = session.createSQLQuery("select * from _conference;").addEntity(Conference.class).list();
		
		session.getTransaction().commit();
		return list;
	}   
}
