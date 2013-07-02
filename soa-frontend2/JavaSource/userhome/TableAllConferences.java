package userhome;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.Conference;

public class TableAllConferences implements Serializable {
	  
    private final static List<String> columns= Arrays.asList("name", "city", "date");  
    private List<Conference> conferences;
    
    public TableAllConferences() {
        conferences = new ArrayList<Conference>();  
        conferences.add(new Conference(1, "ala", "kotek", "piesek", "wiewiórka", "kaczka", "słoń", 5, 10));
        conferences.add(new Conference(1, "ala", "kotek", "wiewiórka", "piesek", "kaczka", "słoń", 5, 10));
        conferences.add(new Conference(1, "ala", "kotek", "piesek", "wiewiórka", "kaczka", "słoń", 5, 10));
        conferences.add(new Conference(1, "ala", "wiewiórka", "kaczka", "słoń", "kotek", "piesek", 5, 10));
        System.out.println("created conference");
    }  
      
    public List<Conference> getConferences() {  
        return conferences;  
    }  
      
    public List<String> getColumns() {  
        return columns;  
    }

	public void setColumns(java.util.List columns) {
		//this.columns = columns;
	}

	public void setConferences(java.util.List conferences) {
		this.conferences = conferences;
	}   
}
