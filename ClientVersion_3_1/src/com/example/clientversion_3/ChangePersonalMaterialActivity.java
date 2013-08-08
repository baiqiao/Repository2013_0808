package com.example.clientversion_3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.clientversion_3_1.R;

public class ChangePersonalMaterialActivity extends Activity implements
		OnClickListener {

	private Intent intent;

	private ImageButton title_perset_ibtn_back;

	public static Button change_btn_liveaddress, change_btn_workaddress;

	public static int addressFlag = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_personal_material);

		init();
	}

	private void init() {

		title_perset_ibtn_back = (ImageButton) this
				.findViewById(R.id.title_perset_ibtn_back);
		change_btn_liveaddress = (Button) this
				.findViewById(R.id.change_btn_liveaddress);
		change_btn_workaddress = (Button) this
				.findViewById(R.id.change_btn_workaddress);

		title_perset_ibtn_back.setOnClickListener(this);
		change_btn_liveaddress.setOnClickListener(this);
		change_btn_workaddress.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_perset_ibtn_back:
			ChangePersonalMaterialActivity.this.finish();
			break;

		case R.id.change_btn_liveaddress:

			intent = new Intent(ChangePersonalMaterialActivity.this,
					ProvinceListActivity.class);
			addressFlag = 0;
			startActivity(intent);
			break;

		case R.id.change_btn_workaddress:
			intent = new Intent(ChangePersonalMaterialActivity.this,
					ProvinceListActivity.class);
			addressFlag = 1;
			startActivity(intent);
			break;

		}
	}
}
