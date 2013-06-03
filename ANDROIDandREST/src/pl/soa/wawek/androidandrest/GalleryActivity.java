package pl.soa.wawek.androidandrest;

import pl.soa.wawek.androidandrest.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;

public class GalleryActivity extends Activity{

	private Gallery gallery;
	private ImageAdapter imageAdapter;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gallery);
		gallery = (Gallery) findViewById(R.id.gallery);
		Display mDisplay= getWindowManager().getDefaultDisplay();
		int width= mDisplay.getWidth();
		int height= mDisplay.getHeight();
		//Bundle b = getIntent().getExtras();
		//int [] ids = b.getIntArray("ids");
		//imageAdapter = new ImageAdapter(this, width, height, ids);
		imageAdapter = new ImageAdapter(this, width, height);
		gallery.setAdapter(imageAdapter);
		gallery.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
			}
			
		});
	}

	
}
