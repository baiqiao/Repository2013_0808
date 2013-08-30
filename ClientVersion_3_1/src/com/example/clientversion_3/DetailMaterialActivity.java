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
	
	private ImageButton detailmat_ibtn_topbar_right,detailmat_ibtn_topbar_left;
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
		detailmat_ibtn_topbar_right = (ImageButton)this.findViewById(R.id.detailmat_ibtn_topbar_right);
		detailmat_ibtn_topbar_left = (ImageButton)this.findViewById(R.id.detailmat_ibtn_topbar_left);
		
		detailmat_ibtn_topbar_right.setOnClickListener(this);
		detailmat_ibtn_topbar_left.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.detailmat_ibtn_topbar_right:
			intent = new Intent(context,EditMaterialActivity.class);
			startActivity(intent);
			break;
			
		case R.id.detailmat_ibtn_topbar_left:
			this.finish();
			break;

		default:
			break;
		}
	}
}
