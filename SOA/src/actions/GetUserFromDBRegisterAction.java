package actions;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

import util.Serializer;

import db.HibernateUtil;

import model.User;

public class GetUserFromDBRegisterAction extends AbstractActionLifecycle {
    protected ConfigTree  _config;

    public GetUserFromDBRegisterAction(ConfigTree config) {
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
        
        System.out.println("[GetUserFromDBRegisterAction] Incoming user nick : " + request.getNick());
              
        User retUser = getUser(request);
        
        if(retUser == null)
        	retUser = request;
        
        message.getBody().add(Serializer.serialize(retUser));
        System.out.println("[GetUserFromDBRegisterAction] Outgoing User id : "+retUser.getId());
        
        return message;
    }
     
    @SuppressWarnings("rawtypes")
	public static User getUser(User user){
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
}
