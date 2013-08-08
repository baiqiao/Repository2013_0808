package com.example.clientversion_3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
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

import com.example.clientversion_3.adapter.RightAdapter;
import com.example.clientversion_3.util.ListInScrollHelper;
import com.example.clientversion_3.view.ImageTextButton;
import com.example.clientversion_3.view.SlidingMenu;
import com.example.clientversion_3_1.R;

@SuppressLint("ValidFragment")
public class RightFragment extends Fragment implements OnClickListener, OnTouchListener, OnItemClickListener{

	
	private Context context;

	private ListView right_list;
	private ImageTextButton right_btn_recommend;
	private ImageTextButton right_btn_hot;
	private ImageTextButton right_btn_new;
	private ImageTextButton right_btn_willtofinish;
	private RightAdapter radapter;
	
	private SlidingMenu slidingMenu;
	
	
	public RightFragment(Context context, SlidingMenu slidingMenu) {
		this.context = context;
		this.slidingMenu = slidingMenu;
	}
	
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
    	
   
        View view = inflater.inflate(R.layout.right_menu, null);
		view.setBackgroundColor(Color.WHITE);
		
		right_btn_recommend = (ImageTextButton)view.findViewById(R.id.right_btn_recommend);
		right_btn_hot = (ImageTextButton)view.findViewById(R.id.right_btn_hot);
		right_btn_new = (ImageTextButton)view.findViewById(R.id.right_btn_new);
		right_btn_willtofinish = (ImageTextButton)view.findViewById(R.id.right_btn_willtofinish);
		right_list = (ListView)view.findViewById(R.id.right_list);
		right_list.setDividerHeight(0);
		
		
		radapter = new RightAdapter(inflater);
		right_list.setAdapter(radapter);
		ListInScrollHelper.setListViewHeight(right_list);
		right_list.setOnItemClickListener(this);
		right_btn_recommend.setOnClickListener(this);
		right_btn_hot.setOnClickListener(this);
		right_btn_new.setOnClickListener(this);
		right_btn_willtofinish.setOnClickListener(this);
		
		
		return view;
		
    }

    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		//MasterActivity.tvHeaderTitle.setText(radapter.getItem(arg2).getItemname());
		MasterActivity2.tvHeaderTitle.setText(radapter.getItem(arg2).getItemname());
		slidingMenu.showContent();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
