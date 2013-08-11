package com.example.clientversion_3;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Intent;
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

import com.example.clientversion_3.adapter.MasterAdapter;
import com.example.clientversion_3.entity.ProjectInfo;
import com.example.clientversion_3.util.ActivityStartAnim;
import com.example.clientversion_3.view.RefreshListView;
import com.example.clientversion_3_1.R;

public class MasterActivity3 extends BaseActivity implements OnClickListener, OnTouchListener, 
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
	private MasterAdapter masterAdapter;
	private Intent intent;
	private LinearLayout mLoadLayout;  
	
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
       masterAdapter = new MasterAdapter(proinfos, this, inflater);
       refreshView.setAdapter(masterAdapter);
       
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
					masterAdapter.notifyDataSetChanged();
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
                		masterAdapter.notifyDataSetChanged();
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
		// TODO Auto-generated method stub
		intent = new Intent(MasterActivity3.this, OptDetailsActivity.class);
		startActivity(intent);
		ActivityStartAnim.RightToLeft(this);
		
	}

	

}
