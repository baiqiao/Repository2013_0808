package com.example.clientversion_3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.clientversion_3.adapter.ItemAdapter;
import com.example.clientversion_3.entity.ProjectInfo;
import com.example.clientversion_3.util.ActivityStartAnim;
import com.example.clientversion_3.util.Constants;
import com.example.clientversion_3.view.RTPullListView;
import com.example.clientversion_3_1.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class MasterActivity2 extends MasterBaseActivity implements OnClickListener, OnTouchListener, 
	OnItemClickListener,RTPullListView.OnRefreshListener  {
	private ImageButton leftBtn;
	private ImageButton rightBtn; 
	public static TextView tvHeaderTitle;
	public Dialog dialog;
	private Handler mHandler = new Handler();
	private boolean isLoadingMore = false;		//是否正在加载数据
	private final int maxAount = 2000;			//设置了最大数据值
	private int lastSize = 0;					//目前剩余可加载空间
	private final int originalNum = 8;			//初始条数
	private final int addMoreNum = 3;			//加载更多增加信息条数
	private RTPullListView pullListView;
	private List<ProjectInfo> proinfos = new ArrayList<ProjectInfo>();
	private LayoutInflater inflater;
	private Intent intent;
	
	private UILApplication UilApplication; 
	
	/**图片加载相关因素*/
	//private ImageLoader imageLoader = ImageLoader.getInstance();
	private DisplayImageOptions options;
	private ItemAdapter itemAdapter;
	private String[] imageUrls = Constants.IMAGES;
	private ImageLoadingListener animateFirstListener;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
	   
	   super.onCreate(savedInstanceState);
       requestWindowFeature(Window.FEATURE_NO_TITLE);
       setContentView(R.layout.master_frame2);
       
       /*LeftLayout在BaseActivity中添加了*/
       /*添加RightLayout*/
       FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
       RightFragment rightFrag = new RightFragment(this, super.slidingMenu);
       slidingMenu.setSecondaryMenu(R.layout.right_frame);
       transaction.replace(R.id.right_frame, rightFrag);
       transaction.commit();
       
       UilApplication = (UILApplication)getApplication();  
       inflater = this.getLayoutInflater();
       options = new DisplayImageOptions.Builder()
		.showStubImage(R.drawable.ic_stub)
		.showImageForEmptyUri(R.drawable.ic_empty)
		.showImageOnFail(R.drawable.ic_error)
		.cacheInMemory(true)
		.cacheOnDisc(true)
		.build();
       animateFirstListener = new AnimateFirstDisplayListener();
       
       leftBtn = (ImageButton)this.findViewById(R.id.ivTitleBtnLeft);
       rightBtn = (ImageButton)this.findViewById(R.id.ivTitleBtnRigh);
       tvHeaderTitle = (TextView)this.findViewById(R.id.tvHeaderTitle);
       
       leftBtn.setOnClickListener(this);
       rightBtn.setOnClickListener(this);
       
       pullListView = (RTPullListView) this.findViewById(R.id.lvhome2);
       pullListView.setDividerHeight(0);
       pullListView.setMore(true);
       this.addLists(originalNum);
       itemAdapter = new ItemAdapter(imageLoader, options, animateFirstListener, proinfos, inflater);
       pullListView.setAdapter(itemAdapter);
       pullListView.setonRefreshListener(this);
       pullListView.setOnItemClickListener(this);
       
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
	public void Refresh() {
		mHandler.postDelayed(new Runnable() {  
            @Override  
            public void run() {  
            	proinfos.clear();
            	addLists(originalNum);
            	itemAdapter.notifyDataSetChanged();
				pullListView.onRefreshComplete();
				pullListView.setMore(true);
            }  
        }, 2000);  
		
	}

	@Override
	public void loadMore() {
		
		if(!isLoadingMore) {
			isLoadingMore = true;
			
			mHandler.postDelayed(new Runnable() {  
                @Override  
                public void run() {  
                	lastSize = maxAount - proinfos.size();
                	
                	if(lastSize < addMoreNum && lastSize >= 0) {
                		addLists(lastSize);
					}
					else {
						addLists(addMoreNum);
					}
                	
                	if(lastSize > 0) {
                		itemAdapter.notifyDataSetChanged();
						pullListView.setSelectionfoot();
						isLoadingMore = false;
                	}
                	else{
                		pullListView.setMore(false);
                		isLoadingMore = false;
                	}
                }  
            }, 2000);  
		}
		
	}
	
	/*
	private class ItemAdapter extends BaseAdapter {

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
				
				view.setTag(holder);
			} 
			else {
				holder = (ViewHolder) view.getTag();
			}
			
			holder.masterpage_pbar.setProgress(proinfo.getProgressNum());
			holder.master_item_tv_reachnum.setText(proinfo.getReachNum() + "%达到");
			holder.master_item_tv_supportnum.setText(proinfo.getSupportNum() + "已获支持");
			holder.master_item_tv_remaintime.setText(proinfo.getRemainTime() + "天剩余");
			holder.master_item_tv_attentionnum.setText(proinfo.getAttentionNum() + "");
			holder.master_item_tv_discussnum.setText(proinfo.getDiscussNum() + "");
			holder.master_item_tv_sharenum.setText(proinfo.getSharedNum() + "");

			imageLoader.displayImage(proinfo.getImageUrl(), holder.master_item_iv_bg, options, animateFirstListener);

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
		
		/*将复杂数据放入到 Application对象中，达到共享目的*/
		HashMap<String, Object> projectInfo = new HashMap<String, Object>();
		projectInfo.put("projectinfo", itemAdapter.getItem(arg2));
		UilApplication.setHashmap(projectInfo);
		
		intent = new Intent(MasterActivity2.this, OptDetailsActivity.class);
		intent.putExtra("KEY", "projectinfo");
		startActivity(intent);
		ActivityStartAnim.RightToLeft(this);
		
	}

}
