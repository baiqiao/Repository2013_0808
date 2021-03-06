package com.example.clientversion_3;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ScrollView;

import com.example.clientversion_3.adapter.RightAdapter;
import com.example.clientversion_3.util.ListInScrollHelper;
import com.example.clientversion_3.view.ImageTextButton;
import com.example.clientversion_3_1.R;
import com.example.clientversion_3_1.R.color;
import com.example.clientversion_3_1.R.drawable;

public class RightFragment extends Fragment implements OnClickListener, OnTouchListener, OnItemClickListener{

	
	private Context context;
	private ScrollView right_scroll;
	private ListView right_list;
	private ImageTextButton right_btn_recommend;			//currentSelect = R.id.right_btn_recommend			
	private ImageTextButton right_btn_hot;					//currentSelect = R.id.right_btn_hot				
	private ImageTextButton right_btn_new;					//currentSelect = R.id.right_btn_new				
	private ImageTextButton right_btn_willtofinish;			//currentSelect = R.id.right_btn_willtofinish		
	
	private int currentSelect = R.id.right_btn_recommend;
	private int lastSelect = R.id.right_btn_recommend;
	private RightAdapter radapter;
	
	private String titleText;
	
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
    	
   
    	context = getActivity().getApplicationContext();
        View view = inflater.inflate(R.layout.right_menu, null);
		view.setBackgroundColor(Color.WHITE);
		
		right_btn_recommend = (ImageTextButton)view.findViewById(R.id.right_btn_recommend);
		right_btn_hot = (ImageTextButton)view.findViewById(R.id.right_btn_hot);
		right_btn_new = (ImageTextButton)view.findViewById(R.id.right_btn_new);
		right_btn_willtofinish = (ImageTextButton)view.findViewById(R.id.right_btn_willtofinish);
		right_list = (ListView)view.findViewById(R.id.right_list);
		right_list.setDividerHeight(0);
		right_scroll = (ScrollView)view.findViewById(R.id.right_scroll);
		
		
		radapter = new RightAdapter(inflater);
		right_list.setAdapter(radapter);
		ListInScrollHelper.setListViewHeight(right_list);
		right_scroll.smoothScrollTo(0, 0); 
		
		right_list.setOnItemClickListener(this);
		right_btn_recommend.setOnClickListener(this);
		right_btn_hot.setOnClickListener(this);
		right_btn_new.setOnClickListener(this);
		right_btn_willtofinish.setOnClickListener(this);
		
		right_btn_recommend.setOnTouchListener(this);
		right_btn_hot.setOnTouchListener(this);
		right_btn_new.setOnTouchListener(this);
		right_btn_willtofinish.setOnTouchListener(this);
		
		
		return view;
		
    }

    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		if(v.getId() != currentSelect) {
			if(event.getAction() == MotionEvent.ACTION_DOWN) {
				((ImageTextButton)v).setTextColor(getResources().getColor(color.color_zuiqianhui));
				switch(v.getId()){
				case R.id.right_btn_recommend:
					
					break;
				case R.id.right_btn_hot :	
					((ImageTextButton)v).setIcon(drawable.arrow_up);
					break;
				case R.id.right_btn_new:
					break;
				case R.id.right_btn_willtofinish:
					break;
				default:
					break;	
				}
				((ImageTextButton)v).invalidate();
			}
			else {
				((ImageTextButton)v).setTextColor(getResources().getColor(color.black));
				switch(v.getId()){
				case R.id.right_btn_recommend:
					
					break;
				case R.id.right_btn_hot :
					((ImageTextButton)v).setIcon(drawable.right_iv_hot_uncheck);
					break;
				case R.id.right_btn_new:
					break;
				case R.id.right_btn_willtofinish:
					break;
				default:
					break;	
				}
				((ImageTextButton)v).invalidate();
			}
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		
		currentSelect = v.getId();
		/*		
		if(v.getId() == R.id.right_btn_recommend){
			clickHelper(currentSelect, lastSelect);
		}
		if(v.getId() == R.id.right_btn_hot){
			clickHelper(currentSelect, lastSelect);
		}
		if(v.getId() == R.id.right_btn_new){
			clickHelper(currentSelect, lastSelect);
		}
		if(v.getId() == R.id.right_btn_willtofinish){
			clickHelper(currentSelect, lastSelect);
		}
		*/
		clickHelper(currentSelect, lastSelect);
		
		titleText = ((ImageTextButton)v).getText().toString();
		RightToContentHelper(titleText);
		
		lastSelect = currentSelect;
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		//MasterActivity.tvHeaderTitle.setText(radapter.getItem(arg2).getItemname());
		
		titleText = radapter.getItem(arg2).getItemname();
		RightToContentHelper(titleText);
		//right_scroll.smoothScrollTo(0, 0); 
	}
	
	private void clickHelper(int currentSelect, int lastSelect) {
		if(currentSelect != lastSelect) {
			switch(currentSelect){
			case R.id.right_btn_recommend:
				right_btn_recommend.setTextColor(getResources().getColor(color.red));
				break;
			case R.id.right_btn_hot :
				right_btn_hot.setTextColor(getResources().getColor(color.red));	
				break;
			case R.id.right_btn_new:
				right_btn_new.setTextColor(getResources().getColor(color.red));
				break;
			case R.id.right_btn_willtofinish:
				right_btn_willtofinish.setTextColor(getResources().getColor(color.red));
				break;
			default:
				break;	
			}
			
			switch(lastSelect) {
			case R.id.right_btn_recommend:
				right_btn_recommend.setTextColor(getResources().getColor(color.black));
				break;
			case R.id.right_btn_hot :
				right_btn_hot.setTextColor(getResources().getColor(color.black));	
				break;
			case R.id.right_btn_new:
				right_btn_new.setTextColor(getResources().getColor(color.black));
				break;
			case R.id.right_btn_willtofinish:
				right_btn_willtofinish.setTextColor(getResources().getColor(color.black));
				break;
			default:
				break;	
			}
		}
	}
	
	
	private void RightToContentHelper(String title) {
		
		Message msg = MasterActivity2.mHandler.obtainMessage();
		msg.what = 0;
		msg.obj = title;
		msg.sendToTarget();
		
		((MasterActivity2)getActivity()).getSlidingMenu().showContent();
	}
	
}
