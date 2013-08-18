package com.example.clientversion_3.adapter;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.clientversion_3.entity.ProjectInfo;
import com.example.clientversion_3_1.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;


public class AttentionAdapter extends BaseAdapter {

	private List<ProjectInfo> proinfos;
	private LayoutInflater inflater;
	private DisplayImageOptions options;
	private ImageLoader imageLoader;
	private ImageLoadingListener animateFirstListener;
	int count = 5;
	
	public AttentionAdapter(ImageLoader imageLoader, DisplayImageOptions options, ImageLoadingListener animateFirstListener, List<ProjectInfo> proinfos, LayoutInflater inflater){
		this.imageLoader = imageLoader;
		this.options = options;
		this.animateFirstListener = animateFirstListener;
		this.proinfos = proinfos;
		this.inflater = inflater;
	}
	
	
	
	@Override
	public int getCount() {
		return proinfos.size();
	}

	@Override
	public Object getItem(int arg0) {
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		ProjectInfo proinfo = proinfos.get(position);
		
		if(convertView == null) {
			convertView = (LinearLayout) inflater.inflate(R.layout.item_attention, null);
			holder = new ViewHolder();
			
			holder.attention_item_iv_bg = (ImageView)convertView.findViewById(R.id.attention_item_iv_bg);
			holder.attentionpage_pbar = (ProgressBar)convertView.findViewById(R.id.attention_item_pbar);
			holder.attention_item_tv_reachnum = (TextView)convertView.findViewById(R.id.attention_item_tv_reachnum);
			holder.attention_item_tv_supportnum = (TextView)convertView.findViewById(R.id.attention_item_tv_supportnum);
			holder.attention_item_tv_remaintime = (TextView)convertView.findViewById(R.id.attention_item_tv_remaintime);
			holder.attention_item_tv_attentionnum = (TextView)convertView.findViewById(R.id.attention_item_tv_attentionnum);
			holder.attention_item_tv_discussnum = (TextView)convertView.findViewById(R.id.attention_item_tv_discussnum);
			holder.attention_item_tv_sharenum = (TextView)convertView.findViewById(R.id.attention_item_tv_sharenum);
			
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		
		holder.attentionpage_pbar.setProgress(proinfo.getProgressNum());
		holder.attention_item_tv_reachnum.setText(proinfo.getReachNum() + "%已达到");
		holder.attention_item_tv_supportnum.setText(proinfo.getSupportNum() + "已获支持");
		holder.attention_item_tv_remaintime.setText(proinfo.getRemainTime() + "天剩余");
		holder.attention_item_tv_attentionnum.setText(proinfo.getAttentionNum() + "");
		holder.attention_item_tv_discussnum.setText(proinfo.getDiscussNum() + "");
		holder.attention_item_tv_sharenum.setText(proinfo.getSharedNum() + "");
		
		imageLoader.displayImage(proinfo.getImageUrl(), holder.attention_item_iv_bg, options, animateFirstListener);

		
		return convertView;
	}
	
	private class ViewHolder {
		
		ImageView attention_item_iv_bg;
		ProgressBar attentionpage_pbar;
		TextView attention_item_tv_reachnum;
		TextView attention_item_tv_supportnum;
		TextView attention_item_tv_remaintime;
		TextView attention_item_tv_attentionnum;
		TextView attention_item_tv_discussnum;
		TextView attention_item_tv_sharenum;
	}

}
