package db;

import java.util.Iterator;
import java.util.List;

import model.Conference;
import model.User;

import org.hibernate.Session;

/**
 * This utility class contains common DB accessor methods.
 * DB mutator methods are in their respective Service Action classes.
 */
public class DBUtil {

	@SuppressWarnings("unchecked")
   	public static List<Integer> getFavsIdsList(int id){
   		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
   		session.beginTransaction();

   		List<Integer> list = session.createSQLQuery(
   				"SELECT elt FROM _user_idsconferences us " +
   				"WHERE us.idsconferences = " + id +";").list();

   		session.getTransaction().commit();
   		return list;
   	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
   	public static List<Conference> getFavsList(int id){
   		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
   		session.beginTransaction();

   		List list = session.createSQLQuery(
   				"SELECT c.id,c.name,c.city,c.date,c.description,c.speaker,c.bio,c.lat,c.lon " +
   				"FROM _user u " +
   				"JOIN _user_idsconferences uc ON(u.userid = uc.idsconferences) " +
   				"JOIN _conference c ON(uc.elt = c.id) " +
   				"WHERE userid = " + id +";").addEntity(Conference.class).list();

   		session.getTransaction().commit();
   		return list;
   	}
	
	@SuppressWarnings("rawtypes")
	public static User getUser(String nick){
    	User retUser = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List list = session.createSQLQuery("SELECT * FROM _user;").addEntity(User.class).list();
		Iterator itr = list.iterator();
		while(itr.hasNext()){
			User u = (User)itr.next();
			if (nick.trim().equals(u.getNick().trim())) {
				retUser = u;
				break;
			}
		}
		session.getTransaction().commit();
		return retUser;
	}
	
	@SuppressWarnings("rawtypes")
   	public static Conference checkConferenceInDB(String name){
    	Conference retCon = null;
   		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
   		session.beginTransaction();  		
   		List list = session.createSQLQuery("SELECT * FROM _conference;").addEntity(Conference.class).list();
   		Iterator itr = list.iterator();
   		while(itr.hasNext()){
   			Conference c = (Conference)itr.next();
   			if (name.trim().equals(c.getName().trim())) {
   				retCon = c;
   				break;
   			}
   		}		
   		session.getTransaction().commit();
   		return retCon;
   	}
	
	@SuppressWarnings("rawtypes")
   	public static User checkUserInDB(String nick){
       	User retUser = null;
   		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
   		session.beginTransaction();  		
   		List list = session.createSQLQuery("SELECT * FROM _user;").addEntity(User.class).list();
   		Iterator itr = list.iterator();
   		while(itr.hasNext()){
   			User u = (User)itr.next();
   			if (nick.trim().equals(u.getNick().trim())) {
   				retUser = u;
   				break;
   			}
   		}		
   		session.getTransaction().commit();
   		return retUser;	
   	}
	
	@SuppressWarnings("rawtypes")
   	public static boolean checkUserInDB(int id){
       	boolean resp = false;
   		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
   		session.beginTransaction();  		
   		List list = session.createSQLQuery("SELECT * FROM _user;").addEntity(User.class).list();
   		Iterator itr = list.iterator();
   		while(itr.hasNext()){
   			User u = (User)itr.next();
   			if (id == u.getId()) {
   				resp = true;
   				break;
   			}
   		}		
   		session.getTransaction().commit();
   		return resp;	
   	}
	
	@SuppressWarnings("rawtypes")
	public static boolean checkConferenceInDB(int id){
		boolean resp = false;
   		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
   		session.beginTransaction();  		
   		List list = session.createSQLQuery("SELECT * FROM _conference;").addEntity(Conference.class).list();
   		Iterator itr = list.iterator();
   		while(itr.hasNext()){
   			Conference c = (Conference)itr.next();
   			if (id == c.getId()) {
   				resp = true;
   				break;
   			}
   		}		
   		session.getTransaction().commit();
   		return resp;
   	}
}
