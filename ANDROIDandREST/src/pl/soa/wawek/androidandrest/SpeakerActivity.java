package pl.soa.wawek.androidandrest;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class SpeakerActivity extends Activity{

	TextView name;
	TextView bio;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.speaker_layout);
		
		name = (TextView) findViewById(R.id.tvName);
		bio = (TextView) findViewById(R.id.tvBio);
		bio.setMovementMethod(new ScrollingMovementMethod());
		Bundle extras = getIntent().getExtras();
		name.setText(extras.getString("speaker"));
		bio.setText(extras.getString("bio"));
	}
}
