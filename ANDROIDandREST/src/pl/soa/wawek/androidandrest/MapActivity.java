package pl.soa.wawek.androidandrest;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


@SuppressLint("NewApi")
public class MapActivity extends Activity{

	private GoogleMap map;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maplayout);
        
        map = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
        
        Bundle extras = getIntent().getExtras();
        double lat, lon;
        lat = extras.getDouble("lat");
        lon = extras.getDouble("lon");
        final LatLng CITY = new LatLng(lat,lon);
        
        map.addMarker(new MarkerOptions().position(CITY).title("Tutaj"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(CITY, 25));
        map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
    }


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	
	
}
