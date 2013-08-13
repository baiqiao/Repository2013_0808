package com.example.clientversion_3.adapter;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.clientversion_3.entity.ProjectInfo;
import com.example.clientversion_3_1.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

public class ItemAdapter extends BaseAdapter {
	
	private LayoutInflater inflater;
	private DisplayImageOptions options;
	private ImageLoader imageLoader;
	private List<ProjectInfo> proinfos;
	private ImageLoadingListener animateFirstListener;
	
	public ItemAdapter(ImageLoader imageLoader, DisplayImageOptions options, ImageLoadingListener animateFirstListener, List<ProjectInfo> proinfos, LayoutInflater inflater){
		this.imageLoader = imageLoader;
		this.options = options;
		this.animateFirstListener = animateFirstListener;
		this.proinfos = proinfos;
		this.inflater = inflater;
	}

	private class ViewHolder {
		
		ImageView master_item_iv_bg;
		ProgressBar masterpage_pbar;
		TextView master_item_tv_reachnum;
		TextView master_item_tv_supportnum;
		TextView master_item_tv_remaintime;
		TextView master_item_tv_attentionnum;
		TextView master_item_tv_discussnum;
		TextView master_item_tv_sharenum;
	}

	@Override
	public int getCount() {
		return proinfos.size();
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
		ProjectInfo proinfo = proinfos.get(position);
		
		if (convertView == null) {
			view = inflater.inflate(R.layout.item_master_page, parent, false);
			holder = new ViewHolder();
			
			holder.master_item_iv_bg = (ImageView)view.findViewById(R.id.master_item_iv_bg);
			holder.masterpage_pbar = (ProgressBar)view.findViewById(R.id.master_item_pbar);
			holder.master_item_tv_reachnum = (TextView)view.findViewById(R.id.master_item_tv_reachnum);
			holder.master_item_tv_supportnum = (TextView)view.findViewById(R.id.master_item_tv_supportnum);
			holder.master_item_tv_remaintime = (TextView)view.findViewById(R.id.master_item_tv_remaintime);
			holder.master_item_tv_attentionnum = (TextView)view.findViewById(R.id.master_item_tv_attentionnum);
			holder.master_item_tv_discussnum = (TextView)view.findViewById(R.id.master_item_tv_discussnum);
			holder.master_item_tv_sharenum = (TextView)view.findViewById(R.id.master_item_tv_sharenum);
			
			holder.masterpage_pbar.setProgress(proinfo.getProgressNum());
			holder.master_item_tv_reachnum.setText(proinfo.getReachNum() + "%达到");
			holder.master_item_tv_supportnum.setText(proinfo.getSupportNum() + "已获支持");
			holder.master_item_tv_remaintime.setText(proinfo.getRemainTime() + "天剩余时间");
			holder.master_item_tv_attentionnum.setText(proinfo.getAttentionNum() + "");
			holder.master_item_tv_discussnum.setText(proinfo.getDiscussNum() + "");
			holder.master_item_tv_sharenum.setText(proinfo.getSharedNum() + "");
			
			view.setTag(holder);
		} 
		else {
			holder = (ViewHolder) view.getTag();
		}

		imageLoader.displayImage(proinfo.getImageUrl(), holder.master_item_iv_bg, options, animateFirstListener);

		return view;
	}

}
