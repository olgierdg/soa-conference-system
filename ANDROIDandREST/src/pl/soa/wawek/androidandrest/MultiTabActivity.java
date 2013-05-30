package pl.soa.wawek.androidandrest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class MultiTabActivity extends TabActivity{

	private TabHost tabHost;
	private TabSpec spec1;
	private TabSpec spec2;
	private TabSpec spec3;
	private TabSpec spec4;
	
	model.Conference conf; //moze trzeba bedzie zmienic na model.Conference
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.multitab);
		
		//uzyskanie danych na podstawie klikniecia pozycji z listy w MainAcitivty
		try {
			getExtrasAboutConference();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		tabHost = getTabHost();
		
		View v1 = buildTabView(this, "Opis");
		View v2 = buildTabView(this, "Galeria");
		View v3 = buildTabView(this, "Prelegenci");
		View v4 = buildTabView(this, "Mapa");

		spec1=tabHost.newTabSpec("Opis");
		spec1.setIndicator(v1);
		Intent descIntent = new Intent(this, DescriptionActivity.class);
		descIntent.putExtra("name", conf.getName());
		descIntent.putExtra("description", conf.getDescription());
		spec1.setContent(descIntent);

		spec2=tabHost.newTabSpec("Galeria");
		spec2.setIndicator(v2);
		Intent galleryIntent = new Intent(this, GalleryActivity.class);
		spec2.setContent(galleryIntent);

		spec3=tabHost.newTabSpec("Prelegenci");
		spec3.setIndicator(v3);
		Intent speakerIntent = new Intent(this, SpeakerActivity.class);
		speakerIntent.putExtra("speaker", conf.getSpeaker());
		speakerIntent.putExtra("bio", conf.getBio());
		spec3.setContent(speakerIntent);
		
		spec4=tabHost.newTabSpec("Mapa");
		spec4.setIndicator(v4);
		Intent mapIntent = new Intent(this, MapActivity.class);
		mapIntent.putExtra("lat", conf.getLat());
		mapIntent.putExtra("lon", conf.getLon());
		spec4.setContent(mapIntent);

		tabHost.addTab(spec1);
		tabHost.addTab(spec2);
		tabHost.addTab(spec3);
		tabHost.addTab(spec4);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.layout.menu_to_add, menu);
		return true;
	}
	
	public void getExtrasAboutConference() throws ParseException{
		Bundle extras = getIntent().getExtras();
		conf = new model.Conference();
		conf.setId(extras.getInt("id"));
		conf.setName(extras.getString("name"));
		conf.setCity(extras.getString("city"));
		conf.setDate(extras.getString("date"));
		conf.setDescription(extras.getString("description"));
		conf.setSpeaker(extras.getString("speaker"));
		conf.setBio(extras.getString("bio"));
		conf.setLat(extras.getDouble("lat"));
		conf.setLon(extras.getDouble("lon"));
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
		case R.id.menu_add_fav:
			
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	public static LinearLayout buildTabView(Context context, String label){

        LayoutInflater li = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final LinearLayout ll = (LinearLayout)li.inflate(R.layout.helpful_to_tabs, null);

        LinearLayout.LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, label.length() + 1);
        ll.setLayoutParams(layoutParams);       
        final TextView tv = (TextView)ll.findViewById(R.id.tab_tv);

        tv.setOnTouchListener(new OnTouchListener() {

        	@Override
            public boolean onTouch(View v, MotionEvent event) {
                ll.onTouchEvent(event);
                return false;
            }
        });

        tv.setText(label);

        return ll;
    }
}
