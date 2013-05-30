package pl.soa.wawek.androidandrest;

import pl.soa.wawek.androidandrest.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;


@SuppressLint("NewApi")
public class MapActivity extends Activity{

	private GoogleMap map;
	private LatLng CITY;


	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maplayout);
        
        map = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
        
        Bundle extras = getIntent().getExtras();
        CITY = new LatLng(extras.getDouble("lat"), extras.getDouble("lon"));
        
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(CITY, 15));
        map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
    }


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	
	
}
