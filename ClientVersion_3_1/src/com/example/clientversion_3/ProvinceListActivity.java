package com.example.clientversion_3;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
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

public class ProvinceListActivity extends Activity {

	private ListView lv;

	private Context mcontext;

	String provinces[][];// 存放省数据 0_id 1_name
	int provinceCount = 0;// 读取数据库中省的数量

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_provincelist);

		mcontext = this;
		lv = (ListView) findViewById(R.id.listview_province);

		// 开始
		MyDatabase myDB = new MyDatabase(this);
		Cursor cProvinces = myDB.getProvinces();
		provinceCount = cProvinces.getCount();
		provinces = new String[provinceCount][2];

		for (int j = 0; j < provinceCount; j++) {
			provinces[j][0] = cProvinces.getString(0);
			provinces[j][1] = cProvinces.getString(1);
			cProvinces.moveToNext();
		}

		lv.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_expandable_list_item_1, getData()));
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent intent = new Intent(mcontext, CityListActivity.class);
				intent.putExtra("provinceid", provinces[position][0]);
				intent.putExtra("provincename", provinces[position][1]);
				startActivity(intent);
				int version = Integer.valueOf(android.os.Build.VERSION.SDK);
				if (version >= 5) {
					overridePendingTransition(android.R.anim.slide_in_left,
							android.R.anim.fade_out);
				}
				ProvinceListActivity.this.finish();
			}
		});
	}

	public List<String> getData() {

		List<String> ls = new ArrayList<String>();
		ls = asList(provinces);
		return ls;
	}

	// 字符串数组转化list
	public List<String> asList(String s[][]) {
		List<String> l = new ArrayList<String>();
		for (int i = 0; i < provinceCount; i++) {
			if (s[i][1] != null)
				l.add(s[i][1]);
		}
		return l;
	}
}
