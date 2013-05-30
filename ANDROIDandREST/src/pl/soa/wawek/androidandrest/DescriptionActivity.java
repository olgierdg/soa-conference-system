package pl.soa.wawek.androidandrest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class DescriptionActivity extends Activity{

	TextView title;
	TextView desc;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.description_layout);
		
		title = (TextView) findViewById(R.id.tvTitle);
		desc = (TextView) findViewById(R.id.tvDesc);
		Bundle extras = getIntent().getExtras();
		title.setText(extras.getString("name"));
		desc.setText(extras.getString("description"));
	}
}
