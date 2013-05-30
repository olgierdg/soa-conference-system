package pl.soa.wawek.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.widget.Toast;

public class GetRest extends IntentService {

	private HttpClient httpClient;
	private HttpGet httpGet;
	private HttpResponse response;
	private HttpEntity entity;
	private String URL = "http://89.66.134.91:8080/RESTGateway/json/";
	private JSONObject json;
	private Handler h;

	private ArrayList<model.ParcellableConference> conferences;

	public GetRest() {
		super("GetRestService");
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		h = new Handler();
		conferences = new ArrayList<model.ParcellableConference>();
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		ResultReceiver rec = intent.getParcelableExtra("GetReceiver");
		Bundle b = new Bundle();
		Bundle extras = intent.getExtras();
		String urlclass = extras.getString("class");
		String result = null;
		httpClient = new DefaultHttpClient();
		httpGet = new HttpGet(URL + urlclass + "/get");
		httpGet.addHeader("accept", "application/json");
		try {
			InputStream instream = null;
			response = httpClient.execute(httpGet);
			entity = response.getEntity();
			instream = entity.getContent();
			result = convertStreamToString(instream);
			json = new JSONObject(result);
			instream.close();
		} catch (Exception e) {
			e.getStackTrace();
		}

		try {
			JSONArray array = json.getJSONArray("conferences");
			for (int i = 0; i < array.length(); i++) {
				JSONObject object = array.getJSONObject(i);
				model.Conference conference = new model.Conference();
				conference.setId(object.getInt("id"));
				conference.setName(object.getString("name"));
				conference.setCity(object.getString("city"));
				conference.setDescription(object.getString("description"));
				conference.setSpeaker(object.getString("speaker"));
				conference.setDate(new SimpleDateFormat("MMMM d, yyyy",
						Locale.ENGLISH).parse(object.getString("date")));
				conference.setBio(object.getString("bio"));
				conference.setLat(object.getDouble("lat"));
				conference.setLon(object.getDouble("lon"));
				
				model.ParcellableConference pConf = new model.ParcellableConference(conference);
				conferences.add(pConf);
			}
			b.putParcelableArrayList("conferences", conferences);
			rec.send(1, b);
		} catch (Exception e) {
			e.printStackTrace();
		}

		/*h.post(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(getBaseContext(), user.toString(),
						Toast.LENGTH_LONG).show();
			}
		});*/
		this.stopSelf();
	}

	public static String convertStreamToString(InputStream is) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

}
