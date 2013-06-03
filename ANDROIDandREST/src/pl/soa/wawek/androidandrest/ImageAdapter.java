package pl.soa.wawek.androidandrest;

import pl.soa.wawek.androidandrest.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class ImageAdapter extends BaseAdapter{

	private Context context = null;
	private int width;
	private int height;
	private int[] imageIds = {R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher};
	
	public ImageAdapter(Context context, int w, int h){
		this.context = context;
		this.width = w;
		this.height = h;
		//this.imageIds = ids;
	}
	
	@Override
	public int getCount() {
		return imageIds.length;
	}

	@Override
	public Object getItem(int arg0) {
		return imageIds[arg0];
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ImageView iv = new ImageView(context);
		iv.setImageResource(imageIds[arg0]);
		iv.setScaleType(ScaleType.FIT_XY);
		iv.setLayoutParams(new Gallery.LayoutParams(width, height));
		return iv;
	}

}
