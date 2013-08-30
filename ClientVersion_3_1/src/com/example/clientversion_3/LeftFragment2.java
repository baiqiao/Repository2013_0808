package com.example.clientversion_3;

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

import com.example.clientversion_3_1.R;

public class LeftFragment2 extends Fragment implements OnTouchListener,
		OnClickListener {

	private Context context;
	private Intent intent;
	
	private static String EXTRA_TITLE;
	private static String EXTRA_MESSAGE;
	private int title;
	private String message;
	
	public static final LeftFragment2 newInstance(int title, String message)
	{
		LeftFragment2 f = new LeftFragment2();
	    Bundle bdl = new Bundle(2);
	    bdl.putInt(EXTRA_TITLE, title);
	    bdl.putString(EXTRA_MESSAGE, message);
	    f.setArguments(bdl);
	    return f;
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.left_menu2, null);
		view.setBackgroundColor(Color.WHITE);
		context = getActivity().getApplicationContext();

		
		view.findViewById(R.id.left_btn_i_discuss).setOnClickListener(this);
		view.findViewById(R.id.left_btn_discuss_i).setOnClickListener(this);
		view.findViewById(R.id.left_tv_personalsetting).setOnClickListener(this);
		
		return view;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	    //title = getArguments().getInt(EXTRA_TITLE);
	    //message = getArguments().getString(EXTRA_MESSAGE);
	    //...
	    //etc
	    //...
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.left_btn_i_discuss) {
			intent = new Intent(context, MyDiscussActivity.class);
			startActivity(intent);
		}
		
		if(v.getId() == R.id.left_btn_discuss_i) {
			intent = new Intent(context, DiscussForMeActivity.class);
			startActivity(intent);
		}
		if(v.getId() == R.id.left_tv_personalsetting) {
			intent = new Intent(context, PersonalSettingActivity1.class);
			startActivity(intent);
		}
		
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			((ImageView) v).getDrawable().setAlpha(150);
			((ImageView) v).invalidate();
		} else {
			((ImageView) v).getDrawable().setAlpha(255);
			((ImageView) v).invalidate();
		}
		return false;
	}
	
}
