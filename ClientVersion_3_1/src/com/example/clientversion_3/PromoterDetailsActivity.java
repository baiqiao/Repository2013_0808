package com.example.clientversion_3;

/**发起人详细信息Activity*/
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
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
import android.widget.ScrollView;

import com.example.clientversion_3.adapter.PromoterInfoAdapter;
import com.example.clientversion_3.entity.ProjectInfo;
import com.example.clientversion_3.util.ListInScrollHelper;
import com.example.clientversion_3_1.R;

public class PromoterDetailsActivity extends Activity implements OnItemClickListener, OnClickListener, OnTouchListener{

	private ListView promoterinfo_list;
	private ImageButton title_pinfo_ibtn_back;
	private LayoutInflater inflater;
	private ScrollView promoterinfo_scr;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_promoter_information);
		inflater = this.getLayoutInflater();
		
		promoterinfo_list = (ListView)this.findViewById(R.id.promoterinfo_list);
		title_pinfo_ibtn_back = (ImageButton)this.findViewById(R.id.title_pinfo_ibtn_back);
		promoterinfo_scr = (ScrollView)this.findViewById(R.id.promoterinfo_scr);
		
		title_pinfo_ibtn_back.setOnClickListener(this);
		title_pinfo_ibtn_back.setOnTouchListener(this);
		promoterinfo_list.setOnItemClickListener(this);
		List<ProjectInfo> proinfos = new ArrayList<ProjectInfo>();
		for(int i=0; i<2; i++) {
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
		promoterinfo_list.setAdapter(new PromoterInfoAdapter(proinfos, inflater));
		ListInScrollHelper.setListViewHeight(promoterinfo_list);
		
		promoterinfo_scr.smoothScrollTo(0, 0);  

		
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.title_pinfo_ibtn_back) {
			this.finish();
		}
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
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
	
	
}
