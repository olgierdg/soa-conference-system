package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.os.Parcel;
import android.os.Parcelable;

public class ParcelableUser implements Parcelable{
	
	private User user;

	public ParcelableUser(){
		user = new User();
	}
	
	public ParcelableUser(User user){
		this.user = user;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@SuppressWarnings("unchecked")
	public ParcelableUser(Parcel in) throws ParseException {
		user.setId(in.readInt());
		user.setNick(in.readString());
		user.setPassword(in.readString());
		List<Integer> array = user.getIdsConferences();
		array = new ArrayList<Integer>();
	    in.readTypedList(array, null);
		user.setIdsConferences(array);
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel arg0, int arg1) {
		// TODO Auto-generated method stub
		arg0.writeInt(user.getId());
		arg0.writeString(user.getNick());
		arg0.writeString(user.getPassword());
		arg0.writeList(user.getIdsConferences());
	}
	
	public static final Parcelable.Creator<ParcelableUser> CREATOR = new Parcelable.Creator<ParcelableUser>() {
		public ParcelableUser createFromParcel(Parcel in) {
			try {
				return new ParcelableUser(in);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return null;
		}

		public ParcelableUser[] newArray(int size) {
			return new ParcelableUser[size];
		}
	};
}
