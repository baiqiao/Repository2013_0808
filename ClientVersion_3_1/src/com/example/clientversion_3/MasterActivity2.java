package com.example.clientversion_3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
	OnItemClickListener, RTPullListView.OnRefreshListener  {
	
	static final int HEADER_TITLE_SET = 0;
	static final int LIST_DATA_SET = 1;
	
	private ImageButton leftBtn;
	private ImageButton rightBtn; 
	private TextView tvHeaderTitle;
	private boolean isLoadingMore = false;		//是否正在加载数据
	private final int maxAount = 2000;			//设置了最大数据值
	private int lastSize = 0;					//目前剩余可加载空间
	private final int originalNum = 8;			//初始条数
	private final int addMoreNum = 3;			//加载更多增加信息条数
	private RTPullListView pullListView;
	private List<ProjectInfo> proinfos = new ArrayList<ProjectInfo>();
	private LayoutInflater inflater;
	private LinearLayout master_loadingll;
	private Intent intent;
	public static Handler mHandler;
	private String titleText;
	
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
       
       master_loadingll = (LinearLayout)this.findViewById(R.id.master_loadingll);
       leftBtn = (ImageButton)this.findViewById(R.id.ivTitleBtnLeft);
       rightBtn = (ImageButton)this.findViewById(R.id.ivTitleBtnRigh);
       tvHeaderTitle = (TextView)this.findViewById(R.id.tvHeaderTitle);
       
       leftBtn.setOnClickListener(this);
       rightBtn.setOnClickListener(this);
       
       pullListView = (RTPullListView) this.findViewById(R.id.lvhome2);
       pullListView.setDividerHeight(0);
       pullListView.setMore(true);
       pullListView.setonRefreshListener(this);
       pullListView.setOnItemClickListener(this);
       
//       addLists(originalNum);
//       itemAdapter = new ItemAdapter(UilApplication.getImageLoader(), options, animateFirstListener, proinfos, inflater);
//       pullListView.setAdapter(itemAdapter);
       
       
       mHandler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				switch(msg.what){
				case HEADER_TITLE_SET:
					String newTitle = (String)msg.obj;
					titleText = tvHeaderTitle.getText().toString();
					
					if(!titleText.equals(newTitle)){
						tvHeaderTitle.setText(newTitle);
						master_loadingll.setVisibility(View.VISIBLE);
//						pullListView.setVisibility(View.INVISIBLE);
						
						/*清空列表显示*/
						proinfos.clear();
		            	addLists(0);
		            	pullListView.setMore(false);
		            	itemAdapter.notifyDataSetChanged();
						
						
						dataInit();
					}
					break;
				
					
				case LIST_DATA_SET:
					
					itemAdapter = new ItemAdapter(UilApplication.getImageLoader(), options, animateFirstListener, proinfos, inflater);
					pullListView.setAdapter(itemAdapter);
					master_loadingll.setVisibility(View.GONE);
					pullListView.setVisibility(View.VISIBLE);
					
					break;
					
				default:
					break;
				}
			}
       };
       
       dataInit();
       
   	}

   private void dataInit() {
	   mHandler.postDelayed(new Runnable() {  
           @Override  
           public void run() {  
        	   
        	   addLists(originalNum);
        	   
        	   Message msg = mHandler.obtainMessage();
        	   msg.what = LIST_DATA_SET;
        	   //msg.obj = options;
        	   msg.sendToTarget();
           }  
       }, 3000);  
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
                		Toast.makeText(getApplicationContext(), "End of list", Toast.LENGTH_SHORT).show();
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
	public void onBackPressed() {
		AnimateFirstDisplayListener.displayedImages.clear();
		UilApplication.getImageLoader().stop();
		super.onBackPressed();
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
		ActivityStartAnim.RightToLeft2(this);
		
	}

}
