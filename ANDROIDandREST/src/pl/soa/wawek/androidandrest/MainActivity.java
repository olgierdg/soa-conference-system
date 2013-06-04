package pl.soa.wawek.androidandrest;

import java.util.ArrayList;

import pl.soa.wawek.androidandrest.MyReceiver.Receiver;
import pl.soa.wawek.rest.GetUserFavsService;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

public class MainActivity extends Activity implements Receiver {

	private ListView lw;
	private ArrayList<model.Conference> array = new ArrayList<model.Conference>();
	private MyArrayAdapter adapter;
	private MyReceiver mReceiver;
	private ArrayList<model.ParcellableConference> conf;
	private model.User user;
	private model.ParcelableUser puser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mReceiver = new MyReceiver(new Handler());
		mReceiver.setReceiver(this);
		conf = new ArrayList<model.ParcellableConference>();
		puser = new model.ParcelableUser();
		Bundle extras = getIntent().getExtras();
		puser = (model.ParcelableUser) extras.getParcelable("puser");

		user = new model.User(puser.getUser().getId(), puser.getUser().getNick(), puser.getUser().getPassword(), (ArrayList<Integer>)puser.getUser().getIdsConferences());
		
		ArrayList<model.ParcellableConference> pConfArray = extras
				.getParcelableArrayList("conferences");
		ArrayList<model.Conference> confArray = new ArrayList<model.Conference>();
		for (int i = 0; i < pConfArray.size(); i++) {
			model.Conference c = pConfArray.get(i).getConference();
			confArray.add(c);
		}
		for (int i = 0; i < confArray.size(); i++) {
			try {
				model.Conference conference = new model.Conference();
				conference = confArray.get(i);
				array.add(conference);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

		lw = (ListView) findViewById(R.id.listView);
		lw.setItemsCanFocus(false);
		adapter = new MyArrayAdapter(MainActivity.this, R.layout.row, array);

		lw.setAdapter(adapter);
		lw.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent;
				switch (arg2) {
				default:
					intent = new Intent(getApplicationContext(),
							MultiTabActivity.class);
					intent.putExtra("id", adapter.getItem(arg2).getId());
					intent.putExtra("name", adapter.getItem(arg2).getName());
					intent.putExtra("city", adapter.getItem(arg2).getCity());
					intent.putExtra("date", adapter.getItem(arg2).getDate());
					intent.putExtra("description", adapter.getItem(arg2)
							.getDescription());
					intent.putExtra("speaker", adapter.getItem(arg2)
							.getSpeaker());
					intent.putExtra("bio", adapter.getItem(arg2).getBio());
					intent.putExtra("lat", adapter.getItem(arg2).getLat());
					intent.putExtra("lon", adapter.getItem(arg2).getLon());
					intent.putExtra("puser", puser);
					startActivity(intent);
					break;
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.layout.menu_to_view, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_main:
			Toast.makeText(MainActivity.this, "Jesteœ w oknie g³ównym", Toast.LENGTH_LONG).show();
			return true;
		case R.id.menu_favourite:
			Intent postService = new Intent(MainActivity.this,
					GetUserFavsService.class);
			postService.putExtra("Post2ViewFavReceiver", mReceiver);
			Gson gson = new Gson();
			String json = gson.toJson(user);
			postService.putExtra("jsonobj", json.toString());
			startService(postService);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public ArrayList<model.Conference> getArray() {
		return array;
	}

	public void setArray(ArrayList<model.Conference> array) {
		this.array = array;
	}

	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
		if (resultCode == 2) {
			Intent i = new Intent(MainActivity.this, Favourites.class);
			conf = resultData.getParcelableArrayList("conferences");
			i.putExtra("puser", puser);
			if (conf.isEmpty()) {
				Toast.makeText(MainActivity.this, "Lista ulubionych jest pusta", Toast.LENGTH_LONG).show();
			} else {
				i.putParcelableArrayListExtra("conferences", conf);
				startActivity(i);
			}
		}
	}

}
