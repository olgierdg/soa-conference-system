package util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

import model.Conference;
import model.User;

public class Serializer {
	
	public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException{
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return is.readObject();
    }
    
    public static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(obj);
        return out.toByteArray();
    }
    
    /*
     * Research generics - seems like a good place for dem
     */
    @SuppressWarnings("unchecked")
	public static User deserializeUser(Object obj) throws ClassNotFoundException, IOException{
    	
    	User request = null;
    	if (obj instanceof User) {
        	request = (User) obj;
        } else if (obj instanceof byte[]) {
        	request = (User)deserialize((byte[]) obj);
        } else if (obj instanceof Map) {
        	Map<User, Object> rowData =(Map<User, Object>)obj;
        	for (Map.Entry<User, Object> curr : rowData.entrySet()) {
        		Object value = curr.getValue();
            	if (value instanceof String) {
            		request = (User) value;
            	}
  		    }
        }    	
    	return request;
    }
    
    @SuppressWarnings("unchecked")
	public static Conference deserializeConference(Object obj) throws ClassNotFoundException, IOException{
    	
    	Conference request = null;
    	if (obj instanceof Conference) {
        	request = (Conference) obj;
        } else if (obj instanceof byte[]) {
        	request = (Conference)deserialize((byte[]) obj);
        } else if (obj instanceof Map) {
        	Map<Conference, Object> rowData =(Map<Conference, Object>)obj;
        	for (Map.Entry<Conference, Object> curr : rowData.entrySet()) {
        		Object value = curr.getValue();
            	if (value instanceof String) {
            		request = (Conference) value;
            	}
  		    }
        }    	
    	return request;
    }
    
    @SuppressWarnings("unchecked")
	public static Integer deserializeInteger(Object obj) throws ClassNotFoundException, IOException{
    	
    	Integer request = null;
    	if (obj instanceof Integer) {
        	request = (Integer) obj;
        } else if (obj instanceof byte[]) {
        	request = (Integer)deserialize((byte[]) obj);
        } else if (obj instanceof Map) {
        	Map<Integer, Object> rowData =(Map<Integer, Object>)obj;
        	for (Map.Entry<Integer, Object> curr : rowData.entrySet()) {
        		Object value = curr.getValue();
            	if (value instanceof String) {
            		request = (Integer) value;
            	}
  		    }
        }    	
    	return request;
    }
    
}
