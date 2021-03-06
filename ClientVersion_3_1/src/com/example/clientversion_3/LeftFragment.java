package com.example.clientversion_3;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.galhttprequest.GalHttpRequest;
import com.galhttprequest.GalHttpRequest.GalHttpLoadImageCallBack;

public class LeftFragment extends Fragment implements OnTouchListener,
		OnClickListener {

	private final int SINAREQUESTCODE = 1;
	private final int QQREQUESTCODE = 2;

	public static ImageView iv_userhead;
	private Button left_btn_attention;
	private TextView left_tv_personalsetting;
	private TextView tv_username;
	private Context context;
	private Intent intent;
	private Button left_btn_discuss;
	
	
	private static String EXTRA_TITLE;
	private static String EXTRA_MESSAGE;
	private int title;
	private String message;
	


	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		context = getActivity().getApplicationContext();
		View view = inflater.inflate(R.layout.left_menu, null);
		view.setBackgroundColor(Color.WHITE);

		iv_userhead = (ImageView) view.findViewById(R.id.left_iv_userhead);
		left_tv_personalsetting = (TextView) view
				.findViewById(R.id.left_tv_personalsetting);
		left_btn_attention = (Button) view
				.findViewById(R.id.left_btn_attention);
		left_btn_discuss = (Button) view.findViewById(R.id.left_btn_discuss);
		tv_username = (TextView) view.findViewById(R.id.left_tv_username);

		iv_userhead.setOnClickListener(this);
		iv_userhead.setOnTouchListener(this);
		left_tv_personalsetting.setOnClickListener(this);
		left_btn_attention.setOnClickListener(this);
		left_btn_discuss.setOnClickListener(this);

		return view;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.left_iv_userhead) {
//			intent = new Intent(context, LoginActivity.class);
//			startActivityForResult(intent, QQREQUESTCODE);
//			ActivityStartAnim.DownToUp(activity);
			
			intent = new Intent(context, RLoginActivity.class);
			startActivity(intent);
			ActivityStartAnim.DownToUp(getActivity());
		} 
		else if (v.getId() == R.id.left_btn_attention) {
			intent = new Intent(context, AttentionActivity.class);
			startActivity(intent);
			ActivityStartAnim.DownToUp(getActivity());
		} 
		else if (v.getId() == R.id.left_tv_personalsetting) {
			intent = new Intent(context, PersonalSettingActivity.class);
			startActivity(intent);
			ActivityStartAnim.DownToUp(getActivity());
		} 
		else if (v.getId() == R.id.left_btn_discuss) {
			intent = new Intent(context, AllCommentActivity.class);
			startActivity(intent);
			ActivityStartAnim.DownToUp(getActivity());
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
		GalHttpRequest request;

		switch (resultCode) {
		case QQREQUESTCODE:
			String nickname = data.getStringExtra("nickname");
			String headGraphUrl = data.getStringExtra("headGraphUrl");
			request = GalHttpRequest.requestWithURL(context, headGraphUrl);
			request.startAsynRequestBitmap(new GalHttpLoadImageCallBack() {
				@Override
				public void imageLoaded(Bitmap bitmap) {
					iv_userhead.setImageBitmap(bitmap);
				}
			});
			tv_username.setText(nickname);
			break;

		default:
			break;
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
	
	
	
	public static final LeftFragment newInstance(int title, String message)
	{
		LeftFragment f = new LeftFragment();
	    Bundle bdl = new Bundle(2);
	    bdl.putInt(EXTRA_TITLE, title);
	    bdl.putString(EXTRA_MESSAGE, message);
	    f.setArguments(bdl);
	    return f;
	}
	/*
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
	    title = getArguments().getInt(EXTRA_TITLE);
	    message = getArguments().getString(EXTRA_MESSAGE);
	    //...
	    //etc
	    //...
	}*/
	
}
