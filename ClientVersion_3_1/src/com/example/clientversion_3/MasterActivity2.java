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
import android.widget.TextView;

import com.example.clientversion_3.adapter.MasterAdapter;
import com.example.clientversion_3.entity.ProjectInfo;
import com.example.clientversion_3.util.ActivityStartAnim;
import com.example.clientversion_3.view.RTPullListView;
import com.example.clientversion_3_1.R;

public class MasterActivity2 extends BaseActivity implements OnClickListener, OnTouchListener, 
	OnItemClickListener,RTPullListView.OnRefreshListener  {
	private ImageButton leftBtn;
	private ImageButton rightBtn; 
	public static TextView tvHeaderTitle;
	public Dialog dialog;
	private Handler mHandler = new Handler();
	private boolean isLoadingMore = false;//�Ƿ����ڼ�������
	private int maxAount = 50;//�������������ֵ
	private int lastSize = 0;
	private RTPullListView pullListView;
	private List<ProjectInfo> proinfos = new ArrayList<ProjectInfo>();
	private LayoutInflater inflater;
	private MasterAdapter masterAdapter;
	private Intent intent;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
	   
	   super.onCreate(savedInstanceState);
       requestWindowFeature(Window.FEATURE_NO_TITLE);
       setContentView(R.layout.master_frame2);
       inflater = this.getLayoutInflater();
       
       /*LeftLayout��BaseActivity�������*/
       /*���RightLayout*/
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
       
       
       pullListView = (RTPullListView) this.findViewById(R.id.lvhome2);
       pullListView.setDividerHeight(0);
       pullListView.setMore(true);
       addLists(20);
       masterAdapter = new MasterAdapter(proinfos, inflater);
       pullListView.setAdapter(masterAdapter);
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
				
	 	   proinfos.add(proinfo);
	    }
	}
   
	@Override
	public void Refresh() {
		mHandler.postDelayed(new Runnable() {  
            @Override  
            public void run() {  
            	proinfos.clear();
            	addLists(15);
					masterAdapter.notifyDataSetChanged();
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
                	
                	if(lastSize < 9 && lastSize >= 0) {
                		addLists(lastSize);
					}
					else {
						addLists(9);
					}
                	
                	if(lastSize > 0) {
                		masterAdapter.notifyDataSetChanged();
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
		intent = new Intent(MasterActivity2.this, OptDetailsActivity.class);
		startActivity(intent);
		ActivityStartAnim.DownToUp(this);
		
	}

}
