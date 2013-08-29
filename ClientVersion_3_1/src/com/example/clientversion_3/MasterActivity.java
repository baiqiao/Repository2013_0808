package com.example.clientversion_3;
/**
* 主要部分是 com.slidingmenu.lib以及com.slidingmenu.lib.app两个包里面的东西，
* res里面的drawable中的right_shadow以及shadow是两边交接出的阴影
* 主要的控制部分在BaseActivity中
*/
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
import android.widget.ListView;
import android.widget.TextView;

import com.example.clientversion_3.adapter.MasterAdapter;
import com.example.clientversion_3.entity.ProjectInfo;
import com.example.clientversion_3.util.ActivityStartAnim;
import com.example.clientversion_3.view.PullDownListView;
import com.example.clientversion_3_1.R;

public class MasterActivity extends MasterBaseActivity implements OnClickListener, OnTouchListener, 
	OnItemClickListener, PullDownListView.OnRefreshListioner{
	private ImageButton leftBtn;
	private ImageButton rightBtn; 
	private ListView HomelistView;
	public static TextView tvHeaderTitle;
	public Dialog dialog;
	private PullDownListView mPullDownView;
	private Handler mHandler = new Handler();
	private boolean isLoadingMore = false;//是否正在加载数据
	private int maxAount = 50;//设置了最大数据值
	private List<ProjectInfo> proinfos = new ArrayList<ProjectInfo>();
	private LayoutInflater inflater;
	private MasterAdapter masterAdapter;
	private Intent intent;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
	   
	   super.onCreate(savedInstanceState);
       requestWindowFeature(Window.FEATURE_NO_TITLE);
       setContentView(R.layout.master_frame);
       inflater = this.getLayoutInflater();
       /*LeftLayout在BaseActivity中添加了*/
       /*添加RightLayout*/
       FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
       RightFragment rightFrag = new RightFragment();
       slidingMenu.setSecondaryMenu(R.layout.right_frame);
       transaction.replace(R.id.right_frame, rightFrag);
       transaction.commit();
       
       
       
       leftBtn = (ImageButton)this.findViewById(R.id.ivTitleBtnLeft);
       rightBtn = (ImageButton)this.findViewById(R.id.ivTitleBtnRigh);
       tvHeaderTitle = (TextView)this.findViewById(R.id.tvHeaderTitle);
       
       leftBtn.setOnClickListener(this);
       rightBtn.setOnClickListener(this);
       
       
       
       mPullDownView = (PullDownListView) findViewById(R.id.sreach_list);
       mPullDownView.setRefreshListioner(this);
       mPullDownView.setAutoLoadMore(true);
       HomelistView = mPullDownView.mListView;
       HomelistView.setDividerHeight(0);
       HomelistView.setOnItemClickListener(this);
       addLists(20);
       masterAdapter = new MasterAdapter(proinfos, this, inflater);
       mPullDownView.setMore(true);//这里设置true表示还有更多加载，设置为false底部将不显示更多
       
       HomelistView.setAdapter(masterAdapter);
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
   
   /**
    * 刷新，先清空list中数据然后重新加载更新内容
    */
	@Override
	public void onRefresh() {
		
		mHandler.postDelayed(new Runnable() {
			
			public void run() {
				proinfos.clear();
				addLists(20);
				mPullDownView.onRefreshComplete();//这里表示刷新处理完成后把上面的加载刷新界面隐藏
				mPullDownView.setMore(true);//这里设置true表示还有更多加载，设置为false底部将不显示更多
				masterAdapter.notifyDataSetChanged();	
				
			}
		}, 1500);
		
		
	}
	
	/**
	 * 加载更多，在原来基础上在添加新内容
	 */
	@Override
	public void onLoadMore() {
		
		
		if(!isLoadingMore) {
			isLoadingMore = true;
			mHandler.postDelayed(new Runnable() {
				
				public void run() {
					int lastSize = maxAount - proinfos.size();
					if(lastSize < 9 && lastSize >= 0) {
						addLists(lastSize);
					}
					else {
						addLists(9);//每次加载9项新内容
					}
					mPullDownView.onLoadMoreComplete();//这里表示加载更多处理完成后把下面的加载更多界面（隐藏或者设置字样更多）
					if(lastSize > 0) {//判断当前list中已添加的数据是否小于最大值maxAount，是那么久显示更多否则不显示
						mPullDownView.setMore(true);//这里设置true表示还有更多加载，设置为false底部将不显示更多
						masterAdapter.notifyDataSetChanged();	
						isLoadingMore = false;
					}
					else{
						mPullDownView.setMore(false);
						isLoadingMore = false;
					}
				}
			}, 1500);
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
		intent = new Intent(MasterActivity.this, OptDetailsActivity.class);
		startActivity(intent);
		ActivityStartAnim.DownToUp(this);
		
	}

}
