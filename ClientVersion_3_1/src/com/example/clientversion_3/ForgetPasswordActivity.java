package com.example.clientversion_3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.clientversion_3_1.R;

public class ForgetPasswordActivity extends Activity implements OnClickListener{
	
	private ImageButton findpwd_ibtn_back;
	private Button findpwd_btn_resetpwd;
	private Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_forgetpassword);
		
		findpwd_ibtn_back = (ImageButton)this.findViewById(R.id.findpwd_ibtn_back);
		findpwd_btn_resetpwd = (Button)this.findViewById(R.id.findpwd_btn_resetpwd);
		
		findpwd_ibtn_back.setOnClickListener(this);
		findpwd_btn_resetpwd.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.findpwd_ibtn_back) {
			this.finish();
		}
		else if(v.getId() == R.id.findpwd_btn_resetpwd) {
			intent = new Intent(ForgetPasswordActivity.this, MailCheckActivity.class);
			startActivity(intent);
		}
		
	}
}
