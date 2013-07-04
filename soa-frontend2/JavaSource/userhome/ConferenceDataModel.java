package userhome;
import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import model.Conference;

public class ConferenceDataModel  extends ListDataModel<Conference> implements SelectableDataModel<Conference>, Serializable {
	 public ConferenceDataModel() {  
	    }  
	  
	    public ConferenceDataModel(List<Conference> data) {  
	        super(data);  
	    }  
	      
	    @Override  
	    public Conference getRowData(String rowKey) {  
	          
	        List<Conference> conferences = (List<Conference>) getWrappedData();  
	          
	        for(Conference conference : conferences) {  
	            if(rowKey.equals(Integer.toString(conference.getId()))) 
	                return conference;  
	        }  
	          
	        return null;  
	    }  
	  
	    @Override  
	    public Object getRowKey(Conference car) {  
	        return car.getId();  
	    }  
	}  