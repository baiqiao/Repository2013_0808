package com.example.clientversion_3;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.clientversion_3.util.ActivityStartAnim;
import com.example.clientversion_3_1.R;

@SuppressLint("ValidFragment")
public class LeftFragment extends Fragment implements OnTouchListener, OnClickListener{
	
	private ImageView iv_userhead;
	private Button left_btn_attention;
	private TextView left_tv_personalsetting;
	private Context context;
	private Activity activity;
	private Intent intent;
	
	public LeftFragment(Context context, Activity activity) {
		this.context = context;
		this.activity = activity;
	}
	
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        
        View view = inflater.inflate(R.layout.left_menu, null);
		view.setBackgroundColor(Color.WHITE);
		
		iv_userhead = (ImageView)view.findViewById(R.id.left_iv_userhead);
		left_tv_personalsetting = (TextView)view.findViewById(R.id.left_tv_personalsetting);
		left_btn_attention = (Button)view.findViewById(R.id.left_btn_attention);
		
		iv_userhead.setOnClickListener(this);
		iv_userhead.setOnTouchListener(this);
		left_tv_personalsetting.setOnClickListener(this);
		left_btn_attention.setOnClickListener(this);
		
		return view;
    }

    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

    }
    
    @Override
	public void onClick(View v) {

		if(v.getId() == R.id.left_iv_userhead) {
			intent = new Intent(context, LoginActivity.class);
			startActivity(intent);
			ActivityStartAnim.DownToUp(activity);
		}
		else if(v.getId() == R.id.left_btn_attention) {
			intent = new Intent(context, AttentionActivity.class);
			startActivity(intent);
			ActivityStartAnim.DownToUp(activity);
		}
		else if(v.getId() == R.id.left_tv_personalsetting){
			intent = new Intent(context, PersonalSettingActivity.class);
			startActivity(intent);
			ActivityStartAnim.DownToUp(activity);
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
