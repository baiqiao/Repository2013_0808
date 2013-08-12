package com.example.clientversion_3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.clientversion_3.adapter.MasterAdapter;
import com.example.clientversion_3.entity.ProjectInfo;
import com.example.clientversion_3.util.ActivityStartAnim;
import com.example.clientversion_3.util.Constants;
import com.example.clientversion_3.view.RefreshListView;
import com.example.clientversion_3_1.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class Test extends BaseActivity implements OnClickListener, OnTouchListener, 
	OnItemClickListener, RefreshListView.RefreshListener{
	private ImageButton leftBtn;
	private ImageButton rightBtn; 
	public static TextView tvHeaderTitle;
	public Dialog dialog;
	private Handler mHandler = new Handler();
	private boolean isLoadingMore = false;//是否正在加载数据
	private int maxAount = 50;//设置了最大数据值
	private int lastSize = 0;
	private RefreshListView refreshView;
	private List<ProjectInfo> proinfos = new ArrayList<ProjectInfo>();
	private LayoutInflater inflater;
	private Intent intent;
	private LinearLayout mLoadLayout;  
	
	
	private ImageLoader imageLoader = ImageLoader.getInstance();
	private DisplayImageOptions options;
	private ItemAdapter itemAdapter;
	private String[] imageUrls = Constants.IMAGES;
	
	
	
	private final LayoutParams mTipContentLayoutParams = new LinearLayout.LayoutParams(  
            LinearLayout.LayoutParams.FILL_PARENT,  
            LinearLayout.LayoutParams.WRAP_CONTENT);

   @Override
   protected void onCreate(Bundle savedInstanceState) {
	   
	   super.onCreate(savedInstanceState);
       requestWindowFeature(Window.FEATURE_NO_TITLE);
       setContentView(R.layout.master_frame3);
       inflater = this.getLayoutInflater();
       
       /*LeftLayout在BaseActivity中添加了*/
       /*添加RightLayout*/
       FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
       RightFragment rightFrag = new RightFragment(this, super.slidingMenu);
       slidingMenu.setSecondaryMenu(R.layout.right_frame);
       transaction.replace(R.id.right_frame, rightFrag);
       transaction.commit();
       
       options = new DisplayImageOptions.Builder()
		.showStubImage(R.drawable.ic_stub)
		.showImageForEmptyUri(R.drawable.ic_empty)
		.showImageOnFail(R.drawable.ic_error)
		.cacheInMemory(true)
		.cacheOnDisc(true)
		.displayer(new RoundedBitmapDisplayer(20))
		.build();
       
       leftBtn = (ImageButton)this.findViewById(R.id.ivTitleBtnLeft);
       rightBtn = (ImageButton)this.findViewById(R.id.ivTitleBtnRigh);
       tvHeaderTitle = (TextView)this.findViewById(R.id.tvHeaderTitle);
       leftBtn.setOnClickListener(this);
       rightBtn.setOnClickListener(this);
       
       
       refreshView = new RefreshListView(this, null);
       refreshView.setDividerHeight(0);
       refreshView.setOnRefreshListener(this);
       refreshView.setOnItemClickListener(this);
       
       mLoadLayout = (LinearLayout)findViewById(R.id.linearlayout_list);
       mLoadLayout.setGravity(Gravity.CENTER); 
       
       addLists(20);
       refreshView.setMore(true);
       //masterAdapter = new MasterAdapter(proinfos, this, inflater);
       //refreshView.setAdapter(masterAdapter);
       
       
       itemAdapter = new ItemAdapter();
       refreshView.setAdapter(itemAdapter);
       
       
       refreshView.setSelection(1);
         
       mLoadLayout.addView(refreshView, mTipContentLayoutParams);
       
       
       DialogView();
   	}
   
   private void DialogView() {
		
		LinearLayout li=(LinearLayout)inflater.inflate(R.layout.dialog_loading,null);
	   	
	   	dialog = new Dialog(this);
	   	dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	   	Window win = dialog.getWindow();
	   	WindowManager.LayoutParams lp = win.getAttributes();
	   	
	   	lp.alpha = 1.0f;
	   	lp.dimAmount = 0.9f;
	   	lp.x = 0;
	   	lp.y = 0;
	   	lp.width = 500;
	   	lp.height = 300;
	   	
	   	win.setAttributes(lp);
	   	win.setGravity(Gravity.CENTER);
	   	li.setBackgroundColor(Color.WHITE);
	   	LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(500, 300);
	   	dialog.setContentView(li, ll);
			
	}

	private void addLists(int n){
   	 	n += proinfos.size();
	   	for(int i=proinfos.size();i<n;i++) {
	 	   ProjectInfo proinfo = new ProjectInfo();
	 	   proinfo.setProgressNum(76);
	 	   proinfo.setReachNum(91);
	 	   proinfo.setSupportNum(8426);
	 	   proinfo.setRemainTime(16);
	 	   proinfo.setAttentionNum(30);
	 	   proinfo.setDiscussNum(15);
	 	   proinfo.setSharedNum(675);
	 	   proinfo.setImageUrl(imageUrls[i]);
				
	 	   proinfos.add(proinfo);
	    }
	}
	
	@Override
	public Object refreshing() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void refreshed(Object obj) {
		mHandler.postDelayed(new Runnable() {  
            @Override  
            public void run() {  
            	proinfos.clear();
            	addLists(15);
            	itemAdapter.notifyDataSetChanged();
				refreshView.setSelection(1);
				refreshView.setMore(true);
            }  
        }, 50);  
		
		
	}

	@Override
	public void more() {

		if(!isLoadingMore) {
			isLoadingMore = true;
			
			mHandler.postDelayed(new Runnable() {  
                @Override  
                public void run() {  
                	lastSize = maxAount - proinfos.size();
                	
                	if(lastSize < 9 && lastSize >= 0) {
                		addLists(lastSize);
					}
					else {
						addLists(9);
					}
                	
                	if(lastSize > 0) {
                		itemAdapter.notifyDataSetChanged();
						isLoadingMore = false;
                	}
                	else{
                		refreshView.setMore(false);
                		isLoadingMore = false;
                	}
                }  
            }, 2000);  
		}
	}
	
	
	class ItemAdapter extends BaseAdapter {

		private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

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
				view = getLayoutInflater().inflate(R.layout.item_master_page, parent, false);
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
	
	
	
	
	/*class ItemAdapter extends BaseAdapter {

		private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

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
				view = getLayoutInflater().inflate(R.layout.item_list_image, parent, false);
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
	}*/

	private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}
	
	
	
   	@Override
   	public boolean onCreateOptionsMenu(Menu menu)
   	{
   		getMenuInflater().inflate(R.menu.main, menu);
   		return true;
   	}
   	
   	@Override
	public void onBackPressed() {
		AnimateFirstDisplayListener.displayedImages.clear();
		super.onBackPressed();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
	
		if(event.getAction() == MotionEvent.ACTION_DOWN) {
			((ImageView)v).getDrawable().setAlpha(150);
			((ImageView)v).invalidate();
		}
		else {
			((ImageView)v).getDrawable().setAlpha(255);
			((ImageView)v).invalidate();
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.ivTitleBtnLeft){
			super.slidingMenu.showMenu();
			
		}
		else if(v.getId() == R.id.ivTitleBtnRigh) {
			super.slidingMenu.showSecondaryMenu();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		intent = new Intent(Test.this, OptDetailsActivity.class);
		startActivity(intent);
		ActivityStartAnim.RightToLeft(this);
		
	}

	

}