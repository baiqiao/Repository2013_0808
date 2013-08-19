package com.example.clientversion_3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.example.clientversion_3_1.R;

/** 开场欢迎动画 */
public class WelcomeA extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_strat);
		//延迟两秒后执行run方法中的页面跳转ת
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				Intent intent = new Intent(WelcomeA.this, MasterActivity3.class);
				startActivity(intent);
				WelcomeA.this.finish();
			}
		}, 2000);
	}
}