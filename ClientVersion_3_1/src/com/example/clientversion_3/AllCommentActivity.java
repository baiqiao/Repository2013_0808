package com.example.clientversion_3;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.example.clientversion_3_1.R;

public class AllCommentActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_allcomment);
	}
	

}
