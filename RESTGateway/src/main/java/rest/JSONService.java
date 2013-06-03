package rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.jboss.soa.esb.client.ServiceInvoker;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.format.MessageFactory;

import com.google.gson.Gson;

import util.Serializer;

import model.Conference;
import model.User;
import model.UserAndConferenceIDs;

@Path("/json")
public class JSONService {

	/*
	 * Moja testowa metoda - przez wpisanie w przeglądarke
	 * uruchamialem rozne rzeczy
	 */
	@GET
	@Path("/testget")
	@Produces("application/json")
	public String testGet() throws Exception{
		
		/*
		List<Integer> l = new ArrayList<Integer>();
		l.add(1);
		l.add(2);
		
		User u = new User(2, "wawek", "pass", new ArrayList<Integer>());
		
		UserAndConferenceIDs uci = new UserAndConferenceIDs();
		uci.setUserid(2);
		uci.setConferenceid(3);
		*/
		
		
		Conference c1 = new Conference(1, "Google I/O", "Kraków", "1/1/2014","Opis konferencji odbywającej się w Krakowie. Wszystkich zapraszamy.", "Karol Adamski", "Urodzony w 1940r. nasz najlepszy programista, można o nim mówić w samych superlatywach", 50.3, 19.9);
		Conference c2 = new Conference(2, "AGH Bajki o sztucznej inteligencji", "Kraków", "3/1/2014","Bajki i baśnie w wykonaniu najlepszych. Wszystkich zapraszamy.", "Zbigniew Krawczyk", "Bajkopisarz, zna się na rzeczy, lubi marzyć, że roboty podbiją świat", 50.3, 19.9);
		Conference c3 = new Conference(3, "Nad morze i z powrotem", "Gdańsk", "10/5/2014","Wyruszmy na najlepszą przygodę po oceanach. Konferencja o tym jak żeglować aby wrócić", "Paweł Gaduła", "Marynarz z marines, przeżył wszystko co można przeżyć. Stracił dłoń, w zamian nosi hak stąd też pseudonim artystyczny 'cpt hook' ", 54.3, 18.6);
		Conference c4 = new Conference(4, "O malarstwie słów kilka", "Warszawa", "2/4/2014","Maluj jak najlepsi, Picasso, Pollock, maluj cokolwiek, a będziesz artystą", "Wiesław von Artist", "Uzdolniony malarz XXI wieku, nie ma sobie równych, maluje wszystkim co ma pod ręką", 52.2, 21.0);
		Conference c5 = new Conference(5, "Wychowanie dzieci - zabawa czy koszmar", "Poznań", "13/10/2014","Poznasz tajniki, które pozwolą Ci w końcu założyć zakupioną koszulkę z nadrukiem 'Best Dad' lub 'Best Mum' ", "Grażyna Witkowska", "Niania, występowała w wielu tv show. Zna się na wszystkim co do tyczy dzieci, włącznie z tym jak je ubrać aby wyglądały modnie", 52.4, 16.9);
		Conference c6 = new Conference(6, "Najnowsze technologie w edukacji", "Łódź", "23/7/2014","Czyli jak pomóc uczniowi w nauce. Dowiesz się o tym co zrobić aby młodzi ludzie nie mogli odłączyć się od komputera kończąc na terapii leczącej uzależnienia od internetu", "Mariusz Pasta", "Doktor w dziedzinie wszelakich uzależnień. Wie jak wyleczyć każdego ze wszystkiego", 51.7, 19.5);
		Conference c7 = new Conference(7, "Przyroda w okół nas", "Zakopane", "19/11/2014","Szanuj zieleń, to hasło każdej osoby przywiązującej się do drzewa. Dowiedz się jak z tymi osobami walczyć tak aby samemu się nie wykończyć", "Wiesława Lambert", "Przyrodniczka urodzona w 1980r. Odpowie na każde pytanie dotyczące walki z greenpeace, ponieważ sama kiedyś zakuła się w kajdanki przy sośnie na cały tydzień.", 49.3, 20.0);
		Conference c8 = new Conference(8, "Miłość w kamienicach", "Wrocław", "9/6/2014","Poznaj życie w kamienicy, gdzie nie grozi Ci wybuch piecyka gazowego, ponieważ go nie masz. Dowiesz się jak założyć rodzinę w kamienicy do rozbiórki, dostając jedynie zasiłek dla bezrobotnych.", "Jan Ostowski", "Wiekowa już postać, która żyła w kamienicy przez ponad pół swojego życia. Obecnie milioner z kontem w Szwajcarii.", 51.1, 17.0);
		
		
		//Conference c8 = new Conference(8, "Miłość w kamienicach", "Wrocław", "9/6/2014","Poznaj życie w kamienicy, gdzie nie grozi Ci wybuch piecyka gazowego, ponieważ go nie masz. Dowiesz się jak założyć rodzinę w kamienicy do rozbiórki, dostając jedynie zasiłek dla bezrobotnych.", "Jan Ostowski", "Wiekowa już postać, która żyła w kamienicy przez ponad pół swojego życia. Obecnie milioner z kontem w Szwajcarii.", 51.1, 17.0);
		/*
        Conference c = new Conference(0, "c1", "LA", "1.1.2014",
				"conference", "Wawek", "a taki", 12.34,
				56.78);
		
		Conference c = new Conference(0, "c2", "LAX", "1.2.2014",
				"conference2", "Olo", "a taki", 12.34,
				56.78);
				*/
		Gson gson = new Gson();
		addConference(gson.toJson(c1));
		addConference(gson.toJson(c2));
		addConference(gson.toJson(c3));
		addConference(gson.toJson(c4));
		addConference(gson.toJson(c5));
		addConference(gson.toJson(c6));
		addConference(gson.toJson(c7));
		String s = addConference(gson.toJson(c8));
		//String s = getUserFavs(gson.toJson(u));
		//String s = registerUser(gson.toJson(u));
		
		return s;
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/conference/addtouserfav")
	@Produces("application/json")
	public String addConferenceToUserFavs(String objString) throws Exception{

		Gson gson = new Gson();
		
		UserAndConferenceIDs c = gson.fromJson(objString, UserAndConferenceIDs.class);
		
		System.out.println("[REST Gateway POST] addConferenceToUserFavs, userid : " + c.getUserid() + ", conferenceid : "+c.getConferenceid());
		
		System.setProperty("javax.xml.registry.ConnectionFactoryClass", "org.apache.ws.scout.registry.ConnectionFactoryImpl");
	
        Message esbMessage = MessageFactory.getInstance().getMessage();

        esbMessage.getBody().add("userid", c.getUserid());
        esbMessage.getBody().add("conferenceid", c.getConferenceid() );
    	
    	ServiceInvoker si = new ServiceInvoker("ConferenceServices",
				"AddConferenceToUserFavsService");
		Message msg = si.deliverSync(esbMessage, 5000);
    	
		Object obj = msg.getBody().get();
		List<Conference> response = null;
		
		if (obj instanceof List<?>) {
			response = (List<Conference>) obj;
		} else if (obj instanceof byte[]) {
			response = (List<Conference>) Serializer.deserialize((byte[]) obj);
		}

		System.out.println("[REST Gateway POST] Outgoing response");
		
		return gson.toJson(response);	
	}
	
	@POST
	@Path("/conference/add")
	@Produces("application/json")
	public String addConference(String objString) throws Exception{

		Gson gson = new Gson();
		
		Conference c = gson.fromJson(objString, Conference.class);
		
		System.out.println("[REST Gateway POST] addConference, name : " + c.getName());
		
		System.setProperty("javax.xml.registry.ConnectionFactoryClass", "org.apache.ws.scout.registry.ConnectionFactoryImpl");
	
        Message esbMessage = MessageFactory.getInstance().getMessage();

        esbMessage.getBody().add(c);
    	
    	ServiceInvoker si = new ServiceInvoker("ConferenceServices",
				"AddConferenceService");
		Message msg = si.deliverSync(esbMessage, 5000);
    	
		Object obj = msg.getBody().get();
		Conference response = null;
		
		if (obj instanceof Conference) {
			response = (Conference) obj;
		} else if (obj instanceof byte[]) {
			response = (Conference) Serializer.deserialize((byte[]) obj);
		}

		System.out.println("[REST Gateway POST] Outgoing response, id : " + c.getId());
		System.out.println("-------------------------------------------");
		
		return gson.toJson(response);	
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/conference/getuserfav")
	@Produces("application/json")
	public String getUserFavs(String objString) throws Exception{
		
		Gson gson = new Gson();

		User user = gson.fromJson(objString, User.class);
		
		System.out.println("[REST Gateway GET] GET User Favs request");
		
		System.setProperty("javax.xml.registry.ConnectionFactoryClass", "org.apache.ws.scout.registry.ConnectionFactoryImpl");
		Message esbMessage = MessageFactory.getInstance().getMessage();
		
		esbMessage.getBody().add(user);
		ServiceInvoker si = new ServiceInvoker("ConferenceServices",
				"GetUserFavsService");
		Message msg = si.deliverSync(esbMessage, 5000);

		Object obj = msg.getBody().get();
		List<Conference> response = null;
		
		if (obj instanceof List<?>) {
			response = (List<Conference>) obj;
		} else if (obj instanceof byte[]) {
			response = (List<Conference>) Serializer.deserialize((byte[]) obj);
		}
		
		System.out.println("[REST Gateway GET] Outgoing response says : ");
        System.out.println("-------------------------------------------");
        
        return gson.toJson(response);
		
	}
	
	@SuppressWarnings("unchecked")
	@GET
	@Path("/conference/getall")
	@Produces("application/json")
	public String getAllConferences() throws Exception{
		
		System.out.println("[REST Gateway GET] GET all conferences request");
		
		System.setProperty("javax.xml.registry.ConnectionFactoryClass", "org.apache.ws.scout.registry.ConnectionFactoryImpl");
		Message esbMessage = MessageFactory.getInstance().getMessage();
		
		ServiceInvoker si = new ServiceInvoker("ConferenceServices",
				"GetAllConferencesService");
		Message msg = si.deliverSync(esbMessage, 5000);

		Object obj = msg.getBody().get();
		List<Conference> response = null;
		
		if (obj instanceof List<?>) {
			response = (List<Conference>) obj;
		} else if (obj instanceof byte[]) {
			response = (List<Conference>) Serializer.deserialize((byte[]) obj);
		}
		
		System.out.println("[REST Gateway POST] Outgoing response says : ");
        System.out.println("-------------------------------------------");
			
        Gson gson = new Gson();
        
        return gson.toJson(response);	
	}
	
	@POST
	@Path("/user/login")
	@Consumes("application/json")
	public String logInUser(String objString) throws Exception{
		
		Gson gson = new Gson();

		User user = gson.fromJson(objString, User.class);
		
		System.out.println("[REST Gateway POST] LogIn User, id : " + user.getId());
		
		System.setProperty("javax.xml.registry.ConnectionFactoryClass", "org.apache.ws.scout.registry.ConnectionFactoryImpl");
		Message esbMessage = MessageFactory.getInstance().getMessage();
		
		esbMessage.getBody().add(user);
		ServiceInvoker si = new ServiceInvoker("UserServices",
				"LogInUserService");
		Message msg = si.deliverSync(esbMessage, 5000);

		Object obj = msg.getBody().get();
		User response = null;
		
		if (obj instanceof User) {
			response = (User) obj;
		} else if (obj instanceof byte[]) {
			response = (User) Serializer.deserialize((byte[]) obj);
		}
		
		System.out.println("[REST Gateway POST] Outgoing response says : " + response.getId());
		if(response.getIdsConferences() == null)
			System.out.println("[REST Gateway POST] LIST IS NULL");
		if(response.getIdsConferences().size() == 0)
			System.out.println("[REST Gateway POST] LIST IS EMPTY");
		System.out.println("-------------------------------------------");
			
        return gson.toJson(response);  
	}
	
	@POST
	@Path("/user/register")
	@Consumes("application/json")
	public String registerUser(String objString) throws Exception{
		
		Gson gson = new Gson();
		
		User user = gson.fromJson(objString, User.class);

		System.out.println("[REST Gateway POST] Register User, id : " + user.getId());
		
		System.setProperty("javax.xml.registry.ConnectionFactoryClass", "org.apache.ws.scout.registry.ConnectionFactoryImpl");
		Message esbMessage = MessageFactory.getInstance().getMessage();
		
		esbMessage.getBody().add(user);
		ServiceInvoker si = new ServiceInvoker("UserServices",
				"RegisterUserService");
		Message msg = si.deliverSync(esbMessage, 5000);

		Object obj = msg.getBody().get();
		User response = null;
		
		if (obj instanceof User) {
			response = (User) obj;
		} else if (obj instanceof byte[]) {
			response = (User) Serializer.deserialize((byte[]) obj);
		}
		
		System.out.println("[REST Gateway POST] Outgoing response says : " + response.getId());
		if(response.getIdsConferences() == null)
			System.out.println("[REST Gateway POST] LIST IS NULL");
		System.out.println("-------------------------------------------");
		
        return gson.toJson(response);
	}
}