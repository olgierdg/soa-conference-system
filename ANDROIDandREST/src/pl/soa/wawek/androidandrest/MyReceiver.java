package pl.soa.wawek.androidandrest;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

public class MyReceiver extends ResultReceiver{

	private Receiver receiver;
	
	public MyReceiver(Handler handler) {
        super(handler);
        // TODO Auto-generated constructor stub
    }
 
    public interface Receiver {
        public void onReceiveResult(int resultCode, Bundle resultData);
 
    }
 
    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }
 
    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
 
        if (this.receiver != null) {
            this.receiver.onReceiveResult(resultCode, resultData);
        }
    }

}
