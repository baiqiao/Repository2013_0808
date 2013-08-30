package com.example.clientversion_3;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.example.clientversion_3_1.R;

public class DetailMaterialActivity extends Activity implements OnClickListener{
	
	private ImageButton detail_ibtn_title_right;
	private Intent intent;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_material);
		
		context = DetailMaterialActivity.this;
		
		initView();
	}
	
	private void initView(){
		detail_ibtn_title_right = (ImageButton)this.findViewById(R.id.detail_ibtn_title_right);
		
		detail_ibtn_title_right.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.detail_ibtn_title_right:
			intent = new Intent(context,EditMaterialActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}
}
