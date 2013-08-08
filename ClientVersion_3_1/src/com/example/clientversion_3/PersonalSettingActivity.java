package com.example.clientversion_3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;

import com.example.clientversion_3_1.R;

public class PersonalSettingActivity extends Activity implements OnClickListener{
	
	//修改个人资料
	private ImageButton perset_ibtn_changeper_mat;
	
	//修改密码
	private ImageButton perset_ibtn_changepassword;
	
	private ImageButton title_perset_ibtn_back;
	
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_personal_setting);
		init();
	}
	
	private void init(){
		
		perset_ibtn_changeper_mat = (ImageButton)this.findViewById(R.id.perset_ibtn_changeper_mat);
		perset_ibtn_changepassword = (ImageButton)this.findViewById(R.id.perset_ibtn_changepassword);
		title_perset_ibtn_back = (ImageButton)this.findViewById(R.id.title_perset_ibtn_back);
		
		perset_ibtn_changeper_mat.setOnClickListener(this);
		perset_ibtn_changepassword.setOnClickListener(this);
		title_perset_ibtn_back.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.perset_ibtn_changeper_mat:
			intent = new Intent(PersonalSettingActivity.this,ChangePersonalMaterialActivity.class);
			startActivity(intent);
			break;
			
		case R.id.perset_ibtn_changepassword:
			intent = new Intent(PersonalSettingActivity.this,ChangePasswordActivity.class);
			startActivity(intent);
			break;
			
		case R.id.title_perset_ibtn_back:
			this.finish();
		default:
			break;
		}
	}
}
