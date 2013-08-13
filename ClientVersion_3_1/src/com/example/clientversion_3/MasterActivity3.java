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
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.example.clientversion_3.adapter.ItemAdapter;
import com.example.clientversion_3.entity.ProjectInfo;
import com.example.clientversion_3.util.ActivityStartAnim;
import com.example.clientversion_3.util.Constants;
import com.example.clientversion_3.view.RefreshListView;
import com.example.clientversion_3_1.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class MasterActivity3 extends MasterBaseActivity implements OnClickListener, OnTouchListener, 
	OnItemClickListener, RefreshListView.RefreshListener{
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
	private RefreshListView refreshView;
	private List<ProjectInfo> proinfos = new ArrayList<ProjectInfo>();
	private LayoutInflater inflater;
	private Intent intent;
	private LinearLayout mLoadLayout;  			//容器，用于添加自定义listview
	
	/**图片加载相关因素*/
	private DisplayImageOptions options;
	private ItemAdapter itemAdapter;
	private String[] imageUrls = Constants.IMAGES;
	private ImageLoadingListener animateFirstListener;
	
	
	
	private final LayoutParams mTipContentLayoutParams = new LinearLayout.LayoutParams(  
            LinearLayout.LayoutParams.FILL_PARENT,  
            LinearLayout.LayoutParams.WRAP_CONTENT);

   @Override
   protected void onCreate(Bundle savedInstanceState) {
	   
	   super.onCreate(savedInstanceState);
       requestWindowFeature(Window.FEATURE_NO_TITLE);
       setContentView(R.layout.master_frame3);
       
       /*添加RightLayout,LeftLayout在BaseActivity中添加了*/
       FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
       RightFragment rightFrag = new RightFragment(this, super.slidingMenu);
       slidingMenu.setSecondaryMenu(R.layout.right_frame);
       transaction.replace(R.id.right_frame, rightFrag);
       transaction.commit();
       
       inflater = this.getLayoutInflater();
       options = new DisplayImageOptions.Builder()
		.showStubImage(R.drawable.ic_stub)
		.showImageForEmptyUri(R.drawable.ic_empty)
		.showImageOnFail(R.drawable.ic_error)
		.cacheInMemory(true)
		.cacheOnDisc(true)
		.displayer(new RoundedBitmapDisplayer(20))
		.build();
       animateFirstListener = new AnimateFirstDisplayListener();
       
       
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
       this.addLists(originalNum);
       refreshView.setMore(true);
       itemAdapter = new ItemAdapter(imageLoader, options, animateFirstListener, proinfos, inflater);
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
            	addLists(8);
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
                	if(lastSize < addMoreNum && lastSize >= 0) {
                		addLists(lastSize);
					}
					else {
						addLists(addMoreNum);
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
		if(v.getId() == R.id.ivTitleBtnLeft){
			super.slidingMenu.showMenu();
		}
		else if(v.getId() == R.id.ivTitleBtnRigh) {
			super.slidingMenu.showSecondaryMenu();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		intent = new Intent(MasterActivity3.this, OptDetailsActivity.class);
		startActivity(intent);
		ActivityStartAnim.RightToLeft(this);
		
	}

}