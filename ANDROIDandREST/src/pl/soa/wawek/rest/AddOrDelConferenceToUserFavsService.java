package pl.soa.wawek.rest;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
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
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class AddOrDelConferenceToUserFavsService extends IntentService {

	private HttpClient httpClient;
	private HttpPost httpPost;
	private HttpResponse response;
	private HttpEntity entity;
	private String URL = "http://89.66.134.91:8080/RESTGateway/json/conference/";
	private Handler h;

	public AddOrDelConferenceToUserFavsService() {
		super("PostFavRestService");
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		h = new Handler();
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		StringEntity s = null;
		String result = null;
		Bundle extras = intent.getExtras();
		String jsonstring = extras.getString("jsonobj");
		String option = extras.getString("option");
		model.ParcellableConference pConference = extras.getParcelable("conf");
		String size = extras.getString("size");
		try {
			s = new StringEntity(jsonstring);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		httpClient = new DefaultHttpClient();
		httpPost = new HttpPost(URL + option);
		s.setContentEncoding("UTF-8");
		s.setContentType("application/json");
		httpPost.setEntity(s);
		httpPost.addHeader("accept", "application/json");

		try {
			response = httpClient.execute(httpPost);
			if (response.getStatusLine().getStatusCode() >= 300) {
				h.post(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(getBaseContext(),
								"Error at server side", Toast.LENGTH_LONG)
								.show();
					}
				});
			} else {
				entity = response.getEntity();
				InputStream instream = null;
				result = new String();
				instream = entity.getContent();
				result = GetAllConferencesService
						.convertStreamToString(instream);
				instream.close();
			}
			Gson gson = new Gson();
			java.lang.reflect.Type listType = new TypeToken<List<model.Conference>>() {
			}.getType();
			List<model.Conference> listConferences = gson.fromJson(result,
					listType);
			//toastInfo(listConferences, option, pConference.getConference(), Integer.parseInt(size));
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.stopSelf();
	}

	public void toastInfo(List<model.Conference> list, String op, model.Conference c, int s) {
		if (op.equalsIgnoreCase("addtouserfav") && list.contains(c) && (Math.abs(s-list.size()) == 1)) {
			h.post(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(getBaseContext(), "Dodano z powodzeniem",
							Toast.LENGTH_LONG).show();
				}

			});
		}
		if (op.equalsIgnoreCase("removefromuserfav") && (!list.contains(c)) && (Math.abs(s-list.size()) == 1)) {
			h.post(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(getBaseContext(), "Usuniêto z powodzeniem",
							Toast.LENGTH_LONG).show();
				}

			});
		}
	}

}
