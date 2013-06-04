package pl.soa.wawek.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ResultReceiver;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GetAllConferencesService extends IntentService {

	private HttpClient httpClient;
	private HttpGet httpGet;
	private HttpResponse response;
	private HttpEntity entity;
	private String URL = "http://89.66.134.91:8080/RESTGateway/json/conference/getall";

	public GetAllConferencesService() {
		super("GetRestService");
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		ResultReceiver rec = intent.getParcelableExtra("GetReceiver");
		Bundle b = new Bundle();
		String result = null;
		httpClient = new DefaultHttpClient();
		httpGet = new HttpGet(URL);
		httpGet.addHeader("accept", "application/json");
		try {
			InputStream instream = null;
			response = httpClient.execute(httpGet);
			entity = response.getEntity();
			instream = entity.getContent();
			result = convertStreamToString(instream);
			instream.close();
		} catch (Exception e) {
			e.getStackTrace();
		}
		Gson gson = new Gson();
		java.lang.reflect.Type listType = new TypeToken<List<model.Conference>>(){}.getType();
		List<model.Conference> listConferences  = gson.fromJson(result, listType);
		
		ArrayList<model.ParcellableConference> pConf = new ArrayList<model.ParcellableConference>();
		for(int i = 0; i < listConferences.size(); i++){
			model.ParcellableConference pc = new model.ParcellableConference(listConferences.get(i));
			pConf.add(pc);
		}
		b.putParcelableArrayList("conferences", pConf);
		rec.send(1, b);
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
