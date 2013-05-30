package pl.soa.wawek.androidandrest;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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
			ConferenceWrapper.city = (TextView) item.findViewById(R.id.tvPlace);
			ConferenceWrapper.id = (TextView) item.findViewById(R.id.tvi);
			ConferenceWrapper.description = (TextView) item.findViewById(R.id.tvd);
			ConferenceWrapper.speaker = (TextView) item.findViewById(R.id.tvs);
			ConferenceWrapper.bio = (TextView) item.findViewById(R.id.tvb);
			ConferenceWrapper.lat = (TextView) item.findViewById(R.id.tvla);
			ConferenceWrapper.lon = (TextView) item.findViewById(R.id.tvlo);
			item.setTag(ConferenceWrapper);
		} else {
			ConferenceWrapper = (ConferenceWrapper) item.getTag();
		}

		model.Conference conference = conferences.get(position);
		ConferenceWrapper.name.setText(conference.getName());
		ConferenceWrapper.date.setText(conference.getDate());
		ConferenceWrapper.city.setText(conference.getCity());
		ConferenceWrapper.id.setText(Integer.toString(conference.getId()));
		ConferenceWrapper.description.setText(conference.getDescription());
		ConferenceWrapper.speaker.setText(conference.getSpeaker());
		ConferenceWrapper.bio.setText(conference.getBio());
		ConferenceWrapper.lat.setText(Double.toString(conference.getLat()));
		ConferenceWrapper.lon.setText(Double.toString(conference.getLon()));
		return item;

	}

	static class ConferenceWrapper {
		TextView name;
		TextView date;
		TextView city;
		TextView id;
		TextView description;
		TextView speaker;
		TextView bio;
		TextView lat;
		TextView lon;
	}

}
