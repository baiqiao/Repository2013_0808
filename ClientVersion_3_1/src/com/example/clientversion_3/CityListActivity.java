package com.example.clientversion_3;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.clientversion_3.sqlite.MyDatabase;
import com.example.clientversion_3_1.R;


public class CityListActivity extends Activity {

	private String provincename;

	private String citys[][];

	private int cityCount = 0;

	private ListView lv;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_citylist);

		Intent intent = getIntent();
		provincename = intent.getStringExtra("provincename");
		String provinceid = intent.getStringExtra("provinceid");
		lv = (ListView) findViewById(R.id.listview_city);

		MyDatabase myDB = new MyDatabase(this);
		Cursor cCity = myDB.getCities(provinceid);
		cityCount = cCity.getCount();
		if (cityCount == 0) {
			if (ChangePersonalMaterialActivity.ADDRESS_FLOG == 0) {
				ChangePersonalMaterialActivity.change_btn_liveaddress
						.setText(provincename);
			} else if (ChangePersonalMaterialActivity.ADDRESS_FLOG == 1) {
				ChangePersonalMaterialActivity.change_btn_workaddress
						.setText(provincename);
			}
			CityListActivity.this.finish();

		}
		citys = new String[cityCount][2];

		for (int j = 0; j < cityCount; j++) {
			citys[j][0] = cCity.getString(0);
			citys[j][1] = cCity.getString(1);
			cCity.moveToNext();
		}

		lv.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_expandable_list_item_1, getData()));
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				if (ChangePersonalMaterialActivity.ADDRESS_FLOG == 0) {
					ChangePersonalMaterialActivity.change_btn_liveaddress
							.setText(provincename + "  " + citys[position][1]);
				} else if (ChangePersonalMaterialActivity.ADDRESS_FLOG == 1) {
					ChangePersonalMaterialActivity.change_btn_workaddress
							.setText(provincename + "  " + citys[position][1]);
				}

				CityListActivity.this.finish();
			}
		});
	}

	public List<String> getData() {

		List<String> ls = new ArrayList<String>();
		ls = asList(citys);
		return ls;
	}

	// 字符串数组转化list
	public List<String> asList(String s[][]) {
		List<String> l = new ArrayList<String>();
		for (int i = 0; i < cityCount; i++) {
			if (s[i][1] != null)
				l.add(s[i][1]);
		}
		return l;
	}
}
