package com.example.clientversion_3;
/**
* ��Ҫ������ com.slidingmenu.lib�Լ�com.slidingmenu.lib.app����������Ķ�����
* res�����drawable�е�right_shadow�Լ�shadow�����߽��ӳ�����Ӱ
* ��Ҫ�Ŀ��Ʋ�����BaseActivity��
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
	private boolean isLoadingMore = false;//�Ƿ����ڼ�������
	private int maxAount = 50;//�������������ֵ
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
       /*LeftLayout��BaseActivity�������*/
       /*���RightLayout*/
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
       mPullDownView.setMore(true);//��������true��ʾ���и�����أ�����Ϊfalse�ײ�������ʾ����
       
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
    * ˢ�£������list������Ȼ�����¼��ظ�������
    */
	@Override
	public void onRefresh() {
		
		mHandler.postDelayed(new Runnable() {
			
			public void run() {
				proinfos.clear();
				addLists(20);
				mPullDownView.onRefreshComplete();//�����ʾˢ�´�����ɺ������ļ���ˢ�½�������
				mPullDownView.setMore(true);//��������true��ʾ���и�����أ�����Ϊfalse�ײ�������ʾ����
				masterAdapter.notifyDataSetChanged();	
				
			}
		}, 1500);
		
		
	}
	
	/**
	 * ���ظ��࣬��ԭ�������������������
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
						addLists(9);//ÿ�μ���9��������
					}
					mPullDownView.onLoadMoreComplete();//�����ʾ���ظ��ദ����ɺ������ļ��ظ�����棨���ػ��������������ࣩ
					if(lastSize > 0) {//�жϵ�ǰlist������ӵ������Ƿ�С�����ֵmaxAount������ô����ʾ���������ʾ
						mPullDownView.setMore(true);//��������true��ʾ���и�����أ�����Ϊfalse�ײ�������ʾ����
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
