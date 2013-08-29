package com.example.clientversion_3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
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
import android.widget.Toast;
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

public class MasterActivity3 extends MasterBaseActivity implements OnClickListener, OnTouchListener, 
	OnItemClickListener, RefreshListView.RefreshListener{
	
	static final int HEADER_TITLE_SET = 0;
	static final int LIST_DATA_SET = 1;
	
	private ImageButton leftBtn;
	private ImageButton rightBtn; 
	private TextView tvHeaderTitle;
	private Dialog dialog;
	private boolean isLoadingMore = false;		//�Ƿ����ڼ�������
	private final int maxAount = 2000;			//�������������ֵ
	private int lastSize = 0;					//Ŀǰʣ��ɼ��ؿռ�
	private final int originalNum = 8;			//��ʼ����
	private final int addMoreNum = 3;			//���ظ���������Ϣ����
	private RefreshListView refreshView;
	private List<ProjectInfo> proinfos = new ArrayList<ProjectInfo>();
	private LayoutInflater inflater;
	private Intent intent;
	private LinearLayout mLoadLayout;  			//��������������Զ���listview
	public static Handler mHandler = new Handler();
	private String titleText;
	private LinearLayout master_loadingll;
	private UILApplication UilApplication; 
	
	/**ͼƬ�����������*/
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
       
       /*���RightLayout,LeftLayout��BaseActivity�������*/
       FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
       RightFragment rightFrag = new RightFragment();
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
       
       
       refreshView = new RefreshListView(this, null);
       refreshView.setDividerHeight(0);
       refreshView.setOnRefreshListener(this);
       refreshView.setOnItemClickListener(this);
       
       mLoadLayout = (LinearLayout)findViewById(R.id.linearlayout_list);
       mLoadLayout.setGravity(Gravity.CENTER); 
//       this.addLists(originalNum);
       refreshView.setMore(true);
//       itemAdapter = new ItemAdapter(UilApplication.getImageLoader(), options, animateFirstListener, proinfos, inflater);
//       refreshView.setAdapter(itemAdapter);
//       refreshView.setSelection(1);
       mLoadLayout.addView(refreshView, mTipContentLayoutParams);
       
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
						
						/*����б���ʾ*/
						proinfos.clear();
		            	addLists(0);
		            	refreshView.setMore(false);
		            	itemAdapter.notifyDataSetChanged();
						
						
						dataInit();
					}
					break;
				
					
				case LIST_DATA_SET:
					
					itemAdapter = new ItemAdapter(UilApplication.getImageLoader(), options, animateFirstListener, proinfos, inflater);
					refreshView.setAdapter(itemAdapter);
					refreshView.setSelection(1);
					master_loadingll.setVisibility(View.GONE);
					refreshView.setVisibility(View.VISIBLE);
					
					break;
					
				default:
					break;
				}
			}
       	};
      
       	dataInit();
       
   	}
   
//   private void DialogView() {
//		
//		LinearLayout li=(LinearLayout)inflater.inflate(R.layout.dialog_loading,null);
//	   	
//	   	dialog = new Dialog(this);
//	   	dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//	   	Window win = dialog.getWindow();
//	   	WindowManager.LayoutParams lp = win.getAttributes();
//	   	
//	   	lp.alpha = 1.0f;
//	   	lp.dimAmount = 0.9f;
//	   	lp.x = 0;
//	   	lp.y = 0;
//	   	lp.width = 500;
//	   	lp.height = 300;
//	   	
//	   	win.setAttributes(lp);
//	   	win.setGravity(Gravity.CENTER);
//	   	li.setBackgroundColor(Color.WHITE);
//	   	LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(500, 300);
//	   	dialog.setContentView(li, ll);
//			
//	}
   
   private void dataInit() {
	   mHandler.postDelayed(new Runnable() {  
           @Override  
           public void run() {  
        	   
        	   addLists(originalNum);
        	   refreshView.setMore(true);
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
			getSlidingMenu().showMenu();
		}
		else if(v.getId() == R.id.ivTitleBtnRigh) {
			getSlidingMenu().showSecondaryMenu();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
		/*���������ݷ��뵽 Application�����У��ﵽ����Ŀ��*/
		HashMap<String, Object> projectInfo = new HashMap<String, Object>();
		projectInfo.put("projectinfo", itemAdapter.getItem(arg2));
		UilApplication.setHashmap(projectInfo);
		
		intent = new Intent(MasterActivity3.this, OptDetailsActivity.class);
		intent.putExtra("KEY", "projectinfo");
		startActivity(intent);
		ActivityStartAnim.RightToLeft(this);
		
	}

	
	
}