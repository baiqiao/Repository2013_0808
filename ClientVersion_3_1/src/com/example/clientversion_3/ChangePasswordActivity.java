package com.example.clientversion_3;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;

import com.example.clientversion_3_1.R;

public class ChangePasswordActivity extends Activity implements OnClickListener{
	
	private ImageButton title_perset_ibtn_back;
	
	private ImageButton title_perset_ibtn_confirm;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_password);
		
		init();
	}

	private void init(){
		title_perset_ibtn_back = (ImageButton)this.findViewById(R.id.title_perset_ibtn_back);
		title_perset_ibtn_confirm = (ImageButton)this.findViewById(R.id.title_changepwd_ibtn_confirm);
		
		title_perset_ibtn_back.setOnClickListener(this);
		title_perset_ibtn_confirm.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_perset_ibtn_back:
			ChangePasswordActivity.this.finish();
			break;
			
		case R.id.title_changepwd_ibtn_confirm:
			ChangePasswordActivity.this.finish();
			break;
		}
	}

}
