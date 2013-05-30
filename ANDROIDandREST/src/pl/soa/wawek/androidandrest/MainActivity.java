package pl.soa.wawek.androidandrest;

import java.util.ArrayList;
import pl.soa.wawek.androidandrest.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends Activity {

	private ListView lw;
	private ArrayList<model.Conference> array = new ArrayList<model.Conference>();
	private MyArrayAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Bundle extras = getIntent().getExtras();
		ArrayList<model.ParcellableConference> pConfArray = extras.getParcelableArrayList("conferences");
		ArrayList<model.Conference> confArray = new ArrayList<model.Conference>();
		for(int i = 0; i < pConfArray.size(); i++){
			confArray.add(pConfArray.get(i).getConference());
		}
		for(int i = 0; i < confArray.size(); i++){
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
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent intent;
				switch(arg2){
				default:
					intent = new Intent(getApplicationContext(), MultiTabActivity.class);
					intent.putExtra("id",  adapter.getItem(arg2).getId());
					intent.putExtra("name",  adapter.getItem(arg2).getName());
					intent.putExtra("city",  adapter.getItem(arg2).getCity());
					intent.putExtra("date",  adapter.getItem(arg2).getDate());
					intent.putExtra("description",  adapter.getItem(arg2).getDescription());
					intent.putExtra("speaker",  adapter.getItem(arg2).getSpeaker());
					intent.putExtra("bio",  adapter.getItem(arg2).getBio());
					intent.putExtra("lat",  adapter.getItem(arg2).getLat());
					intent.putExtra("lon",  adapter.getItem(arg2).getLon());
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
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
		case R.id.menu_main:
			Toast t = new Toast(this);
			t.setDuration(Toast.LENGTH_LONG);
			t.setGravity(Gravity.CENTER, 0, 0);
			t.setText("Jestes w oknie glownym");
			t.show();
			return true;
		case R.id.menu_favourite:
			Intent i = new Intent(MainActivity.this, Favourites.class);
			//tu bedzie wolanie post servicem listy ulubionych
			startActivity(i);
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

}
