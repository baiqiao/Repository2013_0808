package com.example.clientversion_3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.clientversion_3_1.R;

public class OptDetailsActivity extends Activity implements OnClickListener, OnTouchListener{

	private ImageButton optdetails_ibtn_back;
	private ImageView optdetails_iv_promoter_head;
	private Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_optdetails);
		
		optdetails_iv_promoter_head = (ImageView)this.findViewById(R.id.optdetails_iv_promoter_head);
		optdetails_ibtn_back = (ImageButton)this.findViewById(R.id.optdetails_ibtn_back);
		
		optdetails_ibtn_back.setOnClickListener(this);
		optdetails_ibtn_back.setOnTouchListener(this);
		optdetails_iv_promoter_head.setOnClickListener(this);
		optdetails_iv_promoter_head.setOnTouchListener(this);
	}
	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.optdetails_iv_promoter_head) {
			intent = new Intent(OptDetailsActivity.this, PromoterDetailsActivity.class);
			startActivity(intent);
			
		}
		else if(v.getId() == R.id.optdetails_ibtn_back) {
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
