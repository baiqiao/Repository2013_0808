package com.example.clientversion_3;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.example.clientversion_3_1.R;

public class MailCheckActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mailcheck);
	}
	
}
