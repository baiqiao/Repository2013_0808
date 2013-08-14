package com.example.clientversion_3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.clientversion_3_1.R;

public class RegisterActivity extends Activity implements OnClickListener{
	private ImageButton register_ibtn_back;
	private Button register_btn_login;
	private Button register_btn_register;
	private EditText register_et_username;
	private EditText register_et_email;
	private EditText register_et_password;
	
	private Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register);
		
		register_ibtn_back = (ImageButton)this.findViewById(R.id.register_ibtn_back); 
		register_btn_login = (Button)this.findViewById(R.id.register_btn_login);
		register_btn_register = (Button)this.findViewById(R.id.register_btn_register);
		register_et_username = (EditText)this.findViewById(R.id.register_et_username);
		register_et_email = (EditText)this.findViewById(R.id.register_et_email);
		register_et_password = (EditText)this.findViewById(R.id.register_et_password);
		
		register_ibtn_back.setOnClickListener(this);
		register_btn_login.setOnClickListener(this);
		register_btn_register.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.register_ibtn_back) {
			this.finish();
		}
		else if(v.getId() == R.id.register_btn_login) {
			
		}
		else if(v.getId() == R.id.register_btn_register) {
			
		}
		
	}
}
