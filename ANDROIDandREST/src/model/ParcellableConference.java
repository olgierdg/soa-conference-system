package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

@SuppressLint("SimpleDateFormat")
public class ParcellableConference implements Parcelable {

	private Conference conference;

	public ParcellableConference() {
		conference = new Conference();
	}

	public ParcellableConference(Conference conference) {
		this.conference = conference; // tu zglupialem
	}

	public Conference getConference() {
		return conference;
	}

	public void setConference(Conference conference) {
		this.conference = conference;
	}

	public ParcellableConference(Parcel in) throws ParseException {
		this.conference = new model.Conference();
		conference.setId(in.readInt());
		conference.setName(in.readString());
		conference.setCity(in.readString());
		conference.setDate(in.readString());
		conference.setDescription(in.readString());
		conference.setSpeaker(in.readString());
		conference.setBio(in.readString());
		conference.setLat(in.readDouble());
		conference.setLon(in.readDouble());
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public static final Parcelable.Creator<ParcellableConference> CREATOR = new Parcelable.Creator<ParcellableConference>() {
		public ParcellableConference createFromParcel(Parcel in) {
			try {
				return new ParcellableConference(in);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return null;
		}

		public ParcellableConference[] newArray(int size) {
			return new ParcellableConference[size];
		}
	};

	@Override
	public void writeToParcel(Parcel arg0, int arg1) {
		arg0.writeInt(conference.getId());
		arg0.writeString(conference.getName());
		arg0.writeString(conference.getCity());
		arg0.writeString(conference.getDate());
		arg0.writeString(conference.getDescription());
		arg0.writeString(conference.getSpeaker());
		arg0.writeString(conference.getBio());
		arg0.writeDouble(conference.getLat());
		arg0.writeDouble(conference.getLon());
	}
}
