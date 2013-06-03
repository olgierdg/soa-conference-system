package pl.soa.wawek.rest;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GetUserFavsService extends IntentService {

	private HttpClient httpClient;
	private HttpPost httpPost;
	private HttpResponse response;
	private HttpEntity entity;
	private String URL = "http://89.66.134.91:8080/RESTGateway/json/conference/getuserfav";

	public GetUserFavsService() {
		super("Post2ViewFavService");
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
		ResultReceiver rec = intent.getParcelableExtra("Post2ViewFavReceiver");
		Bundle b = new Bundle();
		String result = null;
		StringEntity s = null;
		Bundle extras = intent.getExtras();
		String jsonstring = extras.getString("jsonobj");
		
		httpClient = new DefaultHttpClient();
		httpPost = new HttpPost(URL);

		try {
			s = new StringEntity(jsonstring);
			s.setContentEncoding("UTF-8");
			s.setContentType("application/json");
			httpPost.setEntity(s);
			httpPost.addHeader("accept", "application/json");
			InputStream instream = null;
			response = httpClient.execute(httpPost);
			entity = response.getEntity();
			instream = entity.getContent();
			result = GetAllConferencesService.convertStreamToString(instream);
			instream.close();
		} catch (Exception e) {
			e.getStackTrace();
		}
		Log.i("res", result);
		Gson gson = new Gson();
		java.lang.reflect.Type listType = new TypeToken<List<model.Conference>>(){}.getType();
		List<model.Conference> listConferences  = gson.fromJson(result, listType);
		
		ArrayList<model.ParcellableConference> pConf = new ArrayList<model.ParcellableConference>();
		for(int i = 0; i < listConferences.size(); i++){
			model.ParcellableConference pc = new model.ParcellableConference(listConferences.get(i));
			pConf.add(pc);
		}
		b.putParcelableArrayList("conferences", pConf);
		rec.send(2, b);
		this.stopSelf();
	}
}
