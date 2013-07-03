package userhome;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.Conference;

public class TableAllConferences implements Serializable {
	  
    /**
	 * 
	 */
	private static final long serialVersionUID = -3164874918085020763L;
	private List<String> columns = Arrays.asList("name", "city", "date");  
    private List<Conference> conferences;
    
    public TableAllConferences() {
    	columns = new ArrayList<String>();
    	columns.add("name");
    	columns.add("city");
    	columns.add("date");
        conferences = new ArrayList<Conference>();  
        conferences.add(new Conference(1, "ala", "kotek", "piesek", "wiewiórka", "kaczka", "słoń", 5, 10));
        conferences.add(new Conference(2, "ala", "kotek", "wiewiórka", "piesek", "kaczka", "słoń", 5, 10));
        conferences.add(new Conference(3, "ala", "kotek", "piesek", "wiewiórka", "kaczka", "słoń", 5, 10));
        conferences.add(new Conference(4, "ala", "wiewiórka", "kaczka", "słoń", "kotek", "piesek", 5, 10));
        System.out.println("size " + conferences.size());
    }
      
    public List<Conference> getConferences() {  
        return conferences;  
    }  
      
    public List<String> getColumns() {  
        return columns;  
    }

	public void setColumns(List<String> columns) {
		this.columns = columns;
	}

	public void setConferences(List<Conference> conferences) {
		this.conferences = conferences;
	}   
}
