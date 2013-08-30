package com.example.clientversion_3;

import android.app.Activity;
import android.app.SearchManager.OnCancelListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.clientversion_3.util.ActivityStartAnim;
import com.example.clientversion_3_1.R;

public class MyDiscussActivity extends Activity implements OnClickListener {

	private ListView my_discuss_list;
	private MyDiscussAdapter myDiscussAdapter;
	private LayoutInflater inflater;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mydiscuss);
		inflater = getLayoutInflater();
		
		this.findViewById(R.id.mydiscuss_ibtn_back).setOnClickListener(this);
		
		my_discuss_list = (ListView)this.findViewById(R.id.my_discuss_list);
		myDiscussAdapter = new MyDiscussAdapter();
		my_discuss_list.setDividerHeight(0);
		//my_discuss_list.setEnabled(false);
		my_discuss_list.setAdapter(myDiscussAdapter);
	}
	
	private class MyDiscussAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return 10;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public boolean isEnabled(int position) {
			//return super.isEnabled(position);
			return false;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final ViewHolder holder;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_discuss_discussproject, parent, false);
				holder = new ViewHolder();
				
				holder.discuss_pro_tv_user_name = (TextView)convertView.findViewById(R.id.discuss_pro_tv_user_name);
				holder.discuss_pro_tv_type = (TextView)convertView.findViewById(R.id.discuss_pro_tv_type);
				holder.discuss_pro_tv_discusstext = (TextView)convertView.findViewById(R.id.discuss_pro_tv_discusstext);
				holder.discuss_tv_discuss_time = (TextView)convertView.findViewById(R.id.discuss_tv_discuss_time);
				holder.discuss_tv_delete = (TextView)convertView.findViewById(R.id.discuss_tv_delete);
				holder.discuss_pro_tv_projectname = (TextView)convertView.findViewById(R.id.discuss_pro_tv_projectname);
				holder.discuss_pro_tv_getsupportnum = (TextView)convertView.findViewById(R.id.discuss_pro_tv_getsupportnum);
				holder.discuss_pro_tv_getmoneynum = (TextView)convertView.findViewById(R.id.discuss_pro_tv_getmoneynum);
				holder.discuss_pro_iv_projectpic = (ImageView)convertView.findViewById(R.id.discuss_pro_iv_projectpic);
				
				convertView.setTag(holder);
			} 
			else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			return convertView;
		}
		
		private class ViewHolder {
			TextView discuss_pro_tv_user_name;
			TextView discuss_pro_tv_type;
			TextView discuss_pro_tv_discusstext;
			TextView discuss_tv_discuss_time;
			TextView discuss_tv_delete;
			TextView discuss_pro_tv_projectname;
			TextView discuss_pro_tv_getsupportnum;
			TextView discuss_pro_tv_getmoneynum;
			ImageView discuss_pro_iv_projectpic;
		}
		
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.mydiscuss_ibtn_back) {
			this.finish();
			//ActivityStartAnim.TopToBottom(this);
		}
		
	}

}
