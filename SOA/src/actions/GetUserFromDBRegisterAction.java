package actions;

import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

import util.Serializer;

import db.DBUtil;

import model.User;

public class GetUserFromDBRegisterAction extends AbstractActionLifecycle {
    protected ConfigTree  _config;

    public GetUserFromDBRegisterAction(ConfigTree config) {
        _config = config;
    }

	public Message process(Message message) throws Exception {

    	Object obj = message.getBody().get();
    	User request = Serializer.deserializeUser(obj);
        
        System.out.println("[GetUserFromDBRegister] Incoming user");
              
        User retUser = DBUtil.getUser(request.getNick());
        
        if(retUser == null)
        	retUser = request;
        else{
        	retUser.setIdsConferences(DBUtil.getFavsIdsList(retUser.getId()));
        }
        
        message.getBody().add(Serializer.serialize(retUser));
        System.out.println("[GetUserFromDBRegister] Outgoing response");
        
        return message;
    }
}
