package com.example.clientversion_3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.clientversion_3_1.R;

public class RLoginActivity extends Activity implements OnClickListener{

	private Button login_btn_register;
	private Button login_btn_tencent;
	private Button login_btn_sina;
	private Button login_btn_login;
	private TextView login_tv_losepwd;
	private ImageButton login_ibtn_back;
	private EditText login_et_account;
	private EditText login_et_password;
	private Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login2);
		
		
		login_btn_register = (Button)this.findViewById(R.id.login_btn_register);
		login_btn_tencent = (Button)this.findViewById(R.id.login_btn_tencent);
		login_btn_sina = (Button)this.findViewById(R.id.login_btn_sina);
		login_btn_login = (Button)this.findViewById(R.id.login_btn_login);
		login_tv_losepwd = (TextView)this.findViewById(R.id.login_tv_losepwd);
		login_ibtn_back = (ImageButton)this.findViewById(R.id.login_ibtn_back);
		login_et_account = (EditText)this.findViewById(R.id.login_et_account);
		login_et_password = (EditText)this.findViewById(R.id.login_et_password);
		
		login_btn_register.setOnClickListener(this);
		login_tv_losepwd.setOnClickListener(this);
		login_ibtn_back.setOnClickListener(this);
		login_btn_tencent.setOnClickListener(this);
		login_btn_sina.setOnClickListener(this);
		login_btn_login.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.login_btn_register) {
			intent = new Intent(RLoginActivity.this, RegisterActivity.class);
			startActivity(intent);
		}
		else if(v.getId() == R.id.login_tv_losepwd) {
			intent = new Intent(RLoginActivity.this, FindPasswordActivity.class);
			startActivity(intent);
		}
		else if(v.getId() == R.id.login_btn_tencent) {
			
		}
		else if(v.getId() == R.id.login_btn_sina) {
			
		}
		else if(v.getId() == R.id.login_btn_login) {
			
		}
		else if(v.getId() == R.id.login_ibtn_back) {
			this.finish();
		}
	}
	
}
