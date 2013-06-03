package model;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

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
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ParcelableUser(Parcel in) throws ParseException {
		this();
		user.setId(in.readInt());
		user.setNick(in.readString());
		user.setPassword(in.readString());
		List<Integer> array = new ArrayList<Integer>();
		in.readList(array, Integer.class.getClassLoader());
		user.setIdsConferences(array);
		//Integer[] b = Arrays.copyOf(in.readArray(null), in.readArray(null).length, Integer[].class);
		//user.setIdsConferences(Arrays.asList(b));
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
		//arg0.writeTypedList(user.getIdsConferences());
		//Integer[] a = new Integer[user.getIdsConferences().size()];
		//user.getIdsConferences().toArray(a);
		//arg0.writeArray(a);
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
