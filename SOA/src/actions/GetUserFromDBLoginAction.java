package actions;

import java.util.ArrayList;
import java.util.List;

import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

import util.Serializer;

import db.DBUtil;

import model.User;

public class GetUserFromDBLoginAction extends AbstractActionLifecycle {
    protected ConfigTree  _config;

    public GetUserFromDBLoginAction(ConfigTree config) {
        _config = config;
    }

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Message process(Message message) throws Exception {

    	Object obj = message.getBody().get();
    	User request = Serializer.deserializeUser(obj);
    	
        System.out.println("[GetUserFromDBLoginAction] Incoming user nick : " + request.getNick());
           
        User retUser = DBUtil.getUser(request.getNick());
        
        if(retUser == null)
        	retUser = request;
        else{
        	List l = DBUtil.getFavsIdsList(retUser.getId());
        	if(l == null)
        		l = new ArrayList<Integer>();
        	retUser.setIdsConferences(l);
        }
        
        message.getBody().add(Serializer.serialize(retUser));
        System.out.println("[GetUserFromDBLoginAction] Outgoing User id : "+retUser.getId());
        
        return message;
    }

}
