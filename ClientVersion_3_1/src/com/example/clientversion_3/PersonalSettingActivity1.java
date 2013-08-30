package com.example.clientversion_3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.clientversion_3_1.R;

public class PersonalSettingActivity1 extends Activity implements OnClickListener{
	
	private LinearLayout personal_ll_gotosetting;
	private RelativeLayout personal_rl_changepwd;
	private Intent intent;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal_setting_1);
		
		context = PersonalSettingActivity1.this;
		
		initView();
	}
	
	private void initView(){
		
		personal_ll_gotosetting = (LinearLayout) this.findViewById(R.id.personal_ll_gotosetting);
		personal_rl_changepwd = (RelativeLayout) this.findViewById(R.id.personal_rl_changepwd);
		
		personal_ll_gotosetting.setOnClickListener(this);
		personal_rl_changepwd.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.personal_ll_gotosetting:
			intent = new Intent(context,DetailMaterialActivity.class);
			startActivity(intent);
			break;
			
		case R.id.personal_rl_changepwd:
			intent = new Intent(context,ChangePasswordActivity.class);
			startActivity(intent);
			
		default:
			break;
		}
	}
}
