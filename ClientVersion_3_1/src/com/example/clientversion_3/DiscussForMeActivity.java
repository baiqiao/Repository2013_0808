package com.example.clientversion_3;

import android.app.Activity;
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

public class DiscussForMeActivity extends Activity implements OnClickListener{

	private ListView discussforme_list;
	private DiscussFormeAdapter discussFormeAdapter;
	private LayoutInflater inflater;
	
	private static final String TYPE_REPLY = "reply";
	private static final String TYPE_DISCUSS = "discuss";
	
	private String[] data = {"reply","discuss","reply","reply","discuss","reply","reply","reply","discuss"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_discussforme);
		inflater = getLayoutInflater();
		
		this.findViewById(R.id.discussforme_ibtn_back).setOnClickListener(this);
		
		discussforme_list = (ListView)this.findViewById(R.id.discussforme_list);
		discussFormeAdapter = new DiscussFormeAdapter();
		discussforme_list.setDividerHeight(0);
		discussforme_list.setAdapter(discussFormeAdapter);
	}

	private class DiscussFormeAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return data.length;
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
			
			if(position == 1){
				
				if (convertView == null) {
					convertView = inflater.inflate(R.layout.item_discuss_replymydiscuss, parent, false);
					holder = new ViewHolder();
					
					holder.tv_user_name = (TextView)convertView.findViewById(R.id.discuss_tv_user_name);
					holder.tv_type = (TextView)convertView.findViewById(R.id.discuss_tv_type);
					holder.tv_discusstext = (TextView)convertView.findViewById(R.id.discuss_tv_reply_text);
					holder.tv_discuss_time = (TextView)convertView.findViewById(R.id.discuss_tv_reply_time);
					holder.tv_my_discuss = (TextView)convertView.findViewById(R.id.discuss_tv_my_discuss);
					
					convertView.setTag(holder);
				} 
				else {
					holder = (ViewHolder) convertView.getTag();
				}
				
				holder.tv_user_name.setText("Ð¡Ç¿"+position);
			}
			
			else {
				if (convertView == null) {
					convertView = inflater.inflate(R.layout.item_discuss_discussproject, parent, false);
					holder = new ViewHolder();
					
					holder.tv_user_name = (TextView)convertView.findViewById(R.id.discuss_pro_tv_user_name);
					holder.tv_type = (TextView)convertView.findViewById(R.id.discuss_pro_tv_type);
					holder.tv_discusstext = (TextView)convertView.findViewById(R.id.discuss_pro_tv_discusstext);
					holder.tv_discuss_time = (TextView)convertView.findViewById(R.id.discuss_tv_discuss_time);
					holder.tv_delete = (TextView)convertView.findViewById(R.id.discuss_tv_delete);
					holder.tv_projectname = (TextView)convertView.findViewById(R.id.discuss_pro_tv_projectname);
					holder.tv_getsupportnum = (TextView)convertView.findViewById(R.id.discuss_pro_tv_getsupportnum);
					holder.tv_getmoneynum = (TextView)convertView.findViewById(R.id.discuss_pro_tv_getmoneynum);
					holder.iv_projectpic = (ImageView)convertView.findViewById(R.id.discuss_pro_iv_projectpic);
					
					convertView.setTag(holder);
				} 
				else {
					holder = (ViewHolder) convertView.getTag();
				}
				
				holder.tv_user_name.setText("ÎûÎû¹þ¹þ"+ position);
			}
			
			return convertView;
		}
		
//		private class ReplyViewHolder {
//			TextView discuss_pro_tv_user_name;
//			TextView discuss_pro_tv_type;
//			TextView discuss_pro_tv_discusstext;
//			TextView discuss_tv_discuss_time;
//			TextView discuss_tv_my_discuss;
//		}
//		
//		private class DiscussViewHolder {
//			TextView discuss_pro_tv_user_name;
//			TextView discuss_pro_tv_type;
//			TextView discuss_pro_tv_discusstext;
//			TextView discuss_tv_discuss_time;
//			TextView discuss_tv_delete;
//			TextView discuss_tv_my_discuss;
//			TextView discuss_pro_tv_projectname;
//			TextView discuss_pro_tv_getsupportnum;
//			TextView discuss_pro_tv_getmoneynum;
//			ImageView discuss_pro_iv_projectpic;
//		}
		
		private class ViewHolder {
			TextView tv_user_name;
			TextView tv_type;
			TextView tv_discusstext;
			TextView tv_discuss_time;
			TextView tv_delete;
			TextView tv_my_discuss;
			TextView tv_projectname;
			TextView tv_getsupportnum;
			TextView tv_getmoneynum;
			ImageView iv_projectpic;
		}
		
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.discussforme_ibtn_back) {
			this.finish();
			//ActivityStartAnim.TopToBottom(this);
		}
		
	}
	
}
