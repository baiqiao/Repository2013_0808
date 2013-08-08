package com.example.clientversion_3;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;

import com.example.clientversion_3_1.R;


public class ChangePersonalMaterialActivity extends Activity implements OnClickListener{
	
	private ImageButton title_perset_ibtn_back;
	
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_personal_material);
		
		init();
	}
	
	private void init(){
		
		title_perset_ibtn_back = (ImageButton)this.findViewById(R.id.title_perset_ibtn_back);
		title_perset_ibtn_back.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_perset_ibtn_back:
			intent = new Intent(ChangePersonalMaterialActivity.this,PersonalSettingActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}

}
