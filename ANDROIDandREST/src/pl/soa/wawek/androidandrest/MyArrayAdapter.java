package pl.soa.wawek.androidandrest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

@SuppressLint("SimpleDateFormat")
public class MyArrayAdapter extends ArrayAdapter<model.Conference>{

	Context context;
	int layoutResourceId;
	ArrayList<model.Conference> conferences = new ArrayList<model.Conference>();

	public MyArrayAdapter(Context context, int layoutResourceId,
			ArrayList<model.Conference> conf) {
		super(context, layoutResourceId, conf);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.conferences = conf;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View item = convertView;
		ConferenceWrapper ConferenceWrapper = null;

		if (item == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			item = inflater.inflate(layoutResourceId, parent, false);
			ConferenceWrapper = new ConferenceWrapper();
			ConferenceWrapper.name = (TextView) item.findViewById(R.id.tvName);
			ConferenceWrapper.date = (TextView) item.findViewById(R.id.tvDate);
			ConferenceWrapper.place = (TextView) item.findViewById(R.id.tvPlace);
			item.setTag(ConferenceWrapper);
		} else {
			ConferenceWrapper = (ConferenceWrapper) item.getTag();
		}

		model.Conference conference = conferences.get(position);
		ConferenceWrapper.name.setText(conference.getName());
		ConferenceWrapper.date.setText(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(conference.getDate()));
		ConferenceWrapper.place.setText(conference.getCity());


		return item;

	}

	static class ConferenceWrapper {
		TextView name;
		TextView date;
		TextView place;
	}

}
