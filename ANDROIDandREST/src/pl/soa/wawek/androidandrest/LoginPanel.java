package pl.soa.wawek.androidandrest;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;

import model.Conference;
import model.User;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import pl.soa.wawek.androidandrest.MyReceiver.Receiver;
import pl.soa.wawek.rest.GetRest;
import pl.soa.wawek.rest.PostRest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginPanel extends Activity implements Receiver {

	TextView header;
	EditText etUserLogin;
	EditText etPasswordLogin;
	EditText etUserRegister;
	EditText etPasswordRegister;
	Button bLogin;
	Button bRegister;
	private model.User user;
	private MyReceiver mReceiver;
	private ArrayList<model.ParcellableConference> conf;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_panel);
		mReceiver = new MyReceiver(new Handler());
		mReceiver.setReceiver(this);
		conf = new ArrayList<model.ParcellableConference>();
		header = (TextView) findViewById(R.id.tvUser);
		etUserLogin = (EditText) findViewById(R.id.etUserLogin);
		etPasswordLogin = (EditText) findViewById(R.id.etPasswordLogin);
		etUserRegister = (EditText) findViewById(R.id.etUserRegister);
		etPasswordRegister = (EditText) findViewById(R.id.etPasswordRegister);
		bLogin = (Button) findViewById(R.id.bLogin);
		bRegister = (Button) findViewById(R.id.bRegister);

		bRegister.setText("Zarejestruj");
		bLogin.setText("Zaloguj");

		bLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// checkPassword(etPasswordLogin.getText().toString());
				try {
					user = new model.User(0, etUserLogin.getText().toString(),
							User.hashPasswordWithMD5(etPasswordLogin.getText().toString()), new ArrayList<Integer>());
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
				Gson gson = new Gson();
				String jsonString = gson.toJson(user);
				Intent serviceIntent = new Intent(LoginPanel.this,
						PostRest.class);
				serviceIntent.putExtra("receiver", mReceiver);
				serviceIntent.putExtra("jsonobj", jsonString);
				serviceIntent.putExtra("class", "user/login"); 
				startService(serviceIntent);
			}

		});

		bRegister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// checkPassword(etPasswordLogin.getText().toString());
				try {
					user = new model.User(0, etUserRegister.getText().toString(),
							User.hashPasswordWithMD5(etPasswordRegister.getText().toString()), new ArrayList<Integer>());
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
				Gson gson = new Gson();
				String jsonString = gson.toJson(user);
				Intent serviceIntent = new Intent(LoginPanel.this,
						PostRest.class);
				serviceIntent.putExtra("receiver", mReceiver);
				serviceIntent.putExtra("jsonobj", jsonString);
				serviceIntent.putExtra("class", "user/register"); 
				startService(serviceIntent);

			}

		});
	}

	public boolean checkPassword(String password) {
		if (password.length() < 6) {
			Toast t = new Toast(LoginPanel.this);
			t.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
			t.setDuration(Toast.LENGTH_SHORT);
			t.setText("Haslo musi zawierac przynajmniej 6 znakow");
			t.show();
			return false;
		} else if (password.contains(" ")) {
			Toast t = new Toast(LoginPanel.this);
			t.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
			t.setDuration(Toast.LENGTH_SHORT);
			t.setText("Haslo nie moze zawierac spacji");
			t.show();
			return false;
		}
		return true;
	}

	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
		// tu sie ma odbywac sprawdzanie czy sie udalo zalogowac badz
		// zarejestrowac
		if(resultCode == 0){ // POSTREST
			model.ParcelableUser pUser = new model.ParcelableUser();
			pUser = resultData.getParcelable("user");
			user.setId(pUser.getUser().getId());
			user.setNick(pUser.getUser().getNick());
			user.setPassword(pUser.getUser().getPassword());
			user.setIdsConferences(pUser.getUser().getIdsConferences());
			if (user.getId() > 0) {
				Intent getService = new Intent(LoginPanel.this, GetRest.class);
				getService.putExtra("GetReceiver", mReceiver);
				startService(getService);
			}
		}
		if(resultCode == 1){
			conf = resultData.getParcelableArrayList("conferences");
			Intent i = new Intent(LoginPanel.this, MainActivity.class);
			i.putParcelableArrayListExtra("conferences", conf);
			startActivity(i);
		}
	}
}
