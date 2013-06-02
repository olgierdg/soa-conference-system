package actions;

import java.util.List;

import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

import util.Serializer;

import db.DBUtil;

import model.Conference;
import model.User;

public class GetUserFavsFromDBAction extends AbstractActionLifecycle {
    protected ConfigTree  _config;

    public GetUserFavsFromDBAction(ConfigTree config) {
        _config = config;
    }

	public Message process(Message message) throws Exception {

    	Object obj = message.getBody().get();
    	User request = Serializer.deserializeUser(obj);
    	
        System.out.println("[GetUserFavsFromDBAction] Incoming user nick : " + request.getNick());
           
        User retUser = DBUtil.getUser(request.getNick());
        
        List<Conference> retList = null;
        
        if(retUser != null){       
        	retList = DBUtil.getFavsList(retUser.getId());	
        }
        
        message.getBody().add(Serializer.serialize(retList));
        System.out.println("[GetUserFavsFromDBAction] Outgoing User id : "+retUser.getId());
        
        return message;
    }  
}
