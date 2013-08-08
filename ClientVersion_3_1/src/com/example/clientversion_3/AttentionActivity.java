package com.example.clientversion_3;

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

import com.example.clientversion_3.adapter.AttentionAdapter;
import com.example.clientversion_3.entity.ProjectInfo;
import com.example.clientversion_3_1.R;


public class AttentionActivity extends Activity implements OnItemClickListener, OnClickListener, OnTouchListener{

	private ListView attention_list;
	private ImageButton title_attention_ibtn_back;
	private LayoutInflater inflater;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_attention);
		inflater = this.getLayoutInflater();
		
		attention_list = (ListView)this.findViewById(R.id.attention_list);
		title_attention_ibtn_back = (ImageButton)this.findViewById(R.id.title_attention_ibtn_back);
		
		title_attention_ibtn_back.setOnClickListener(this);
		title_attention_ibtn_back.setOnTouchListener(this);
		
		attention_list.setOnItemClickListener(this);
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
		attention_list.setAdapter(new AttentionAdapter(proinfos, inflater));
		
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
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
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.title_attention_ibtn_back) {
			this.finish();
		}
		
	}
	
}
