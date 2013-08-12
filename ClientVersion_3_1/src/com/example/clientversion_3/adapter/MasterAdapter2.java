package com.example.clientversion_3.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.clientversion_3_1.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;


public class MasterAdapter2 extends BaseAdapter {
	
	private LayoutInflater inflater;
	private String[] imageUrls;
	private DisplayImageOptions options;
	private ImageLoader imageLoader;
	private ImageLoadingListener animateFirstListener;
	
	
	
	

	private class ViewHolder {
		public TextView text;
		public ImageView image;
	}

	@Override
	public int getCount() {
		return imageUrls.length;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = convertView;
		final ViewHolder holder;
		if (convertView == null) {
			view = inflater.inflate(R.layout.item_list_image, parent, false);
			holder = new ViewHolder();
			holder.text = (TextView) view.findViewById(R.id.text);
			holder.image = (ImageView) view.findViewById(R.id.image);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		holder.text.setText("Item " + (position + 1));

		imageLoader.displayImage(imageUrls[position], holder.image, options, animateFirstListener);

		return view;
	}

}
