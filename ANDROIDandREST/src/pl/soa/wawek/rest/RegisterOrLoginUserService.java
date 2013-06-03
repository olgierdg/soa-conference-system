package pl.soa.wawek.rest;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;

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
import android.os.ResultReceiver;
import android.widget.Toast;

import com.google.gson.Gson;

public class RegisterOrLoginUserService extends IntentService {

	private HttpClient httpClient;
	private HttpPost httpPost;
	private HttpResponse response;
	private HttpEntity entity;
	private String URL = "http://89.66.134.91:8080/RESTGateway/json/";
	private Handler h;
	private model.ParcelableUser pUser;
	private model.User user;
	private String urlclass;
	
	public RegisterOrLoginUserService() {
		super("PostRestService");
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		h = new Handler();
		pUser = new model.ParcelableUser();
		user = new model.User();
		urlclass = new String();
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		ResultReceiver rec = intent.getParcelableExtra("receiver");
		Bundle b = new Bundle();
		StringEntity s = null;
		Bundle extras = intent.getExtras();
		String jsonstring = extras.getString("jsonobj");
		urlclass = extras.getString("class");
		try {
			s = new StringEntity(jsonstring);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		httpClient = new DefaultHttpClient();
		httpPost = new HttpPost(URL + urlclass);
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
				String result = new String();
				instream = entity.getContent();
				result = GetAllConferencesService.convertStreamToString(instream);
				Gson gson = new Gson();
				user = gson.fromJson(result, model.User.class);
				pUser.setUser(user);
				instream.close();
				toastInfo();
			}
			b.putParcelable("user", pUser);
			rec.send(0, b); // wyslanie kodu na zewnatrz
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.stopSelf();
	}

	public void toastInfo(){
		if (urlclass.contains("login")) {
			if (user.getId() < 0) {
				h.post(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(getBaseContext(),
								"Z³y login lub has³o",
								Toast.LENGTH_LONG).show();
					}
				});
			}
		} else if (urlclass.contains("register")) {
			if (user.getId() < 0) {
				h.post(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(getBaseContext(),
								"Login zajêty", Toast.LENGTH_LONG)
								.show();
					}
				});
			}
		}

	}
}
