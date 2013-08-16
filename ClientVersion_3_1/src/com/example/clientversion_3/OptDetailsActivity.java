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
		"   新华网北京８月９日电（记者陈炜伟、张晓松）审计署９日发布的２０１２年城镇保障性安居工程跟踪审计结果显示，３６０个项目或单位挪用保障性安居工程专项资金５７．９９亿元，用于归还贷款、对外投资、征地拆迁以及单位资金周转等非保障性安居工程项目支出。",
		"   审计署这份审计结果公告指出，从审计情况看，城镇保障性安居工程的资金管理、项目建设管理和分配管理等逐步规范，总体情况较好，但审计也发现一些项目和单位还存在违规或管理不规范等问题。",
		"   除了上述专项资金挪用的问题外，公告指出，１０．８４万户不符合保障条件的家庭，因提供不实资料、相关部门审核把关不严，违规享受保障性住房实物分配３．８９万套、领取租赁补贴１．５３亿元，另有１．１３万户家庭重复享受保障性住房实物分配２９７５套，重复领取租赁补贴２１３７．５５万元。",
		"   此外，３４个项目代建企业等单位违规出售保障性住房１．８３万套，另有５３３３套住房被有关单位、个人违规用于拆迁周转、转借出租等。４５个项目未办理建设用地规划许可等手续用地１４３３．１６亩，１２个项目将建设用地６０１．５３亩用于商业开发等其他用途。",
		"	公告指出，审计指出上述问题后，地方各级政府高度重视并督促相关单位积极整改，截至２０１３年６月１７日，有关单位已取消不符合条件保障对象资格５．２７万户，追回违规领取补贴４４３１．１５万元，收回或清理被违规分配使用的保障性住房１．９８万套，追回被挪用的资金４０．６３亿元，已补办１９个项目１２３５亩用地批准等手续，完善各类管理制度和规范５２９个。下一步，审计署将继续跟踪整改情况，适时公布整改结果。",
		"	此外，审计向相关部门移送违纪违规和经济犯罪案件线索２６起，涉案金额２４８７．１７万元，涉案人员５５人，相关部门正在依法查处。"
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
				//获取信息
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
