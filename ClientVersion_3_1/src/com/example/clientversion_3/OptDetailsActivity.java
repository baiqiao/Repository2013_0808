package com.example.clientversion_3;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.clientversion_3.entity.ProjectInfo;
import com.example.clientversion_3_1.R;
import com.example.clientversion_3_1.R.color;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class OptDetailsActivity extends Activity implements OnClickListener, OnTouchListener{

	private ImageButton optdetails_ibtn_back;
	private ImageView optdetails_iv_promoter_head;
	private ImageView optdetails_iv_bg;
	private LinearLayout details_texts;
	private ProjectInfo projectinfo;
	private Intent intent;
	private String key;
	private UILApplication UilApplication;
	private DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener;
	
	private String[] strs = {
		"   �»����������£��յ磨���߳��ΰ�������ɣ�������շ����ģ���������������԰��ӹ��̸�����ƽ����ʾ������������Ŀ��λŲ�ñ����԰��ӹ���ר���ʽ𣵣���������Ԫ�����ڹ黹�������Ͷ�ʡ����ز�Ǩ�Լ���λ�ʽ���ת�ȷǱ����԰��ӹ�����Ŀ֧����",
		"   ����������ƽ������ָ�����������������������԰��ӹ��̵��ʽ������Ŀ�������ͷ��������𲽹淶����������Ϻã������Ҳ����һЩ��Ŀ�͵�λ������Υ�������淶�����⡣",
		"   ��������ר���ʽ�Ų�õ������⣬����ָ���������������򻧲����ϱ��������ļ�ͥ�����ṩ��ʵ���ϡ���ز�����˰ѹز��ϣ�Υ�����ܱ�����ס��ʵ����䣳���������ס���ȡ���޲�������������Ԫ�����У��������򻧼�ͥ�ظ����ܱ�����ס��ʵ����䣲�������ף��ظ���ȡ���޲�������������������Ԫ��",
		"   ���⣬��������Ŀ������ҵ�ȵ�λΥ����۱�����ס�������������ף����У���������ס�����йص�λ������Υ�����ڲ�Ǩ��ת��ת�����ȡ���������Ŀδ�������õع滮��ɵ������õأ�������������Ķ����������Ŀ�������õأ�����������Ķ������ҵ������������;��",
		"	����ָ�������ָ����������󣬵ط����������߶����Ӳ�������ص�λ�������ģ��������������ꣶ�£����գ��йص�λ��ȡ���������������϶����ʸ񣵣������򻧣�׷��Υ����ȡ��������������������Ԫ���ջػ�����Υ�����ʹ�õı�����ס�������������ף�׷�ر�Ų�õ��ʽ𣴣���������Ԫ���Ѳ��죱������Ŀ��������Ķ�õ���׼�����������Ƹ�������ƶȺ͹淶������������һ��������𽫼������������������ʱ�������Ľ����",
		"	���⣬�������ز�������Υ��Υ��;��÷��ﰸ�������������永����������������Ԫ���永��Ա�����ˣ���ز������������鴦��"
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_optdetails);
		
		key = getIntent().getStringExtra("KEY");
		UilApplication = (UILApplication)getApplication();  
		projectinfo = (ProjectInfo)UilApplication.getHashmap().get(key);
		
		options = new DisplayImageOptions.Builder()
		.showStubImage(R.drawable.ic_stub)
		.showImageForEmptyUri(R.drawable.ic_empty)
		.showImageOnFail(R.drawable.ic_error)
		.cacheInMemory(true)
		.cacheOnDisc(true)
		.build();
		animateFirstListener = new AnimateFirstDisplayListener();
		
		
		details_texts = (LinearLayout)this.findViewById(R.id.details_texts);
		optdetails_iv_promoter_head = (ImageView)this.findViewById(R.id.optdetails_iv_promoter_head);
		optdetails_ibtn_back = (ImageButton)this.findViewById(R.id.optdetails_ibtn_back);
		optdetails_iv_bg = (ImageView)this.findViewById(R.id.optdetails_iv_bg);
		
		optdetails_ibtn_back.setOnClickListener(this);
		optdetails_ibtn_back.setOnTouchListener(this);
		optdetails_iv_promoter_head.setOnClickListener(this);
		optdetails_iv_promoter_head.setOnTouchListener(this);
		
		
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				//��ȡ��Ϣ
				//````
				Message msg=new Message();
				msg.obj = strs;
				mHandler.sendMessage(msg);
			}
		}).run();
		
		
	}
	
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			
			BaseActivity.imageLoader.displayImage(projectinfo.getImageUrl(), optdetails_iv_bg, options, animateFirstListener);
			
			String[] s =(String[]) msg.obj;
			for(int i = 0; i<s.length; i++){
				Log.d("HANDLER", i +"");
				TextView t = new TextView(getBaseContext());
				details_texts.addView(t);
				t.setText(s[i]);
				t.setTextSize(17);
				t.setTextColor(color.med_gray);
				t.setLineSpacing((float) 1.2, (float) 1.2);
			}
		}
	};
	
	
	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.optdetails_iv_promoter_head) {
			intent = new Intent(OptDetailsActivity.this, PromoterDetailsActivity.class);
			startActivity(intent);
			
		}
		else if(v.getId() == R.id.optdetails_ibtn_back) {
			this.finish();
		}
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
	public void onBackPressed() {
		AnimateFirstDisplayListener.displayedImages.clear();
		UilApplication.getHashmap().clear();
		super.onBackPressed();
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

}
