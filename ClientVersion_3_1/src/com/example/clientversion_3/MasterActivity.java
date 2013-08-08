package com.example.clientversion_3;
/**
* 主要部分是 com.slidingmenu.lib以及com.slidingmenu.lib.app两个包里面的东西，
* res里面的drawable中的right_shadow以及shadow是两边交接出的阴影
* 主要的控制部分在BaseActivity中
*/
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
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
import android.widget.ListView;
import android.widget.TextView;

import com.example.clientversion_3.adapter.MasterAdapter;
import com.example.clientversion_3.entity.ProjectInfo;
import com.example.clientversion_3.util.ActivityStartAnim;
import com.example.clientversion_3_1.R;

public class MasterActivity extends BaseActivity implements OnClickListener, OnTouchListener, OnItemClickListener{
	private ImageButton leftBtn;
	private ImageButton rightBtn; 
	private ListView HomelistView;
	public static TextView tvHeaderTitle;
	
	private LayoutInflater inflater;
	
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
       	RightFragment rightFrag = new RightFragment(this, super.slidingMenu);
       	slidingMenu.setSecondaryMenu(R.layout.right_frame);
       	transaction.replace(R.id.right_frame, rightFrag);
       	transaction.commit();
       	
       
       	leftBtn = (ImageButton)this.findViewById(R.id.ivTitleBtnLeft);
		rightBtn = (ImageButton)this.findViewById(R.id.ivTitleBtnRigh);
		HomelistView = (ListView)this.findViewById(R.id.lvhome);
		HomelistView.setDividerHeight(0);
		tvHeaderTitle = (TextView)this.findViewById(R.id.tvHeaderTitle);
       
		leftBtn.setOnClickListener(this);
		rightBtn.setOnClickListener(this);
		HomelistView.setOnItemClickListener(this);
		List<ProjectInfo> proinfos = new ArrayList<ProjectInfo>();
		for(int i=0; i<5; i++) {
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
		HomelistView.setAdapter(new MasterAdapter(proinfos, inflater));
     
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
