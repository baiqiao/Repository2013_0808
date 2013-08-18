package com.example.clientversion_3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.clientversion_3.adapter.AttentionAdapter;
import com.example.clientversion_3.entity.ProjectInfo;
import com.example.clientversion_3.util.Constants;
import com.example.clientversion_3_1.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;


public class AttentionActivity extends Activity implements OnItemClickListener, OnClickListener, OnTouchListener{

	private ListView attention_list;
	private ImageButton title_attention_ibtn_back;
	private LayoutInflater inflater;
	
	private UILApplication UilApplication; 
	/**图片加载相关因素*/
	private DisplayImageOptions options;
	private AttentionAdapter attentionAdapter;
	private ImageLoadingListener animateFirstListener;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_attention);
		
		UilApplication = (UILApplication)getApplication();  
		inflater = this.getLayoutInflater();
		options = new DisplayImageOptions.Builder()
			.showStubImage(R.drawable.ic_stub)
			.showImageForEmptyUri(R.drawable.ic_empty)
			.showImageOnFail(R.drawable.ic_error)
			.cacheInMemory(true)
			.cacheOnDisc(true)
			.build();
		animateFirstListener = new AnimateFirstDisplayListener();
		
		
		attention_list = (ListView)this.findViewById(R.id.attention_list);
		title_attention_ibtn_back = (ImageButton)this.findViewById(R.id.title_attention_ibtn_back);
		
		title_attention_ibtn_back.setOnClickListener(this);
		title_attention_ibtn_back.setOnTouchListener(this);
		
		attention_list.setOnItemClickListener(this);
		List<ProjectInfo> proinfos = new ArrayList<ProjectInfo>();
		for(int i=0; i<5; i++) {
			ProjectInfo proinfo = new ProjectInfo();
			proinfo.setProgressNum(76);
			proinfo.setReachNum(91);
			proinfo.setSupportNum(8426);
			proinfo.setRemainTime(16);
			proinfo.setAttentionNum(30);
			proinfo.setDiscussNum(15);
			proinfo.setSharedNum(675);
			
			proinfo.setImageUrl(Constants.IMAGES[i*3]);
			
			proinfos.add(proinfo);
		}
		
		attentionAdapter = new AttentionAdapter(UilApplication.getImageLoader(), options, animateFirstListener, proinfos, inflater);
		attention_list.setAdapter(attentionAdapter);
		
	}
	
	private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}
	
	@Override
	public void onBackPressed() {
		AnimateFirstDisplayListener.displayedImages.clear();
		super.onBackPressed();
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		if(event.getAction() == MotionEvent.ACTION_DOWN) {
			((ImageView)v).getDrawable().setAlpha(150);
			((ImageView)v).invalidate();
		}
		else {
			((ImageView)v).getDrawable().setAlpha(255);
			((ImageView)v).invalidate();
		}
		return false;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.title_attention_ibtn_back) {
			this.finish();
		}
		
	}
	
}
