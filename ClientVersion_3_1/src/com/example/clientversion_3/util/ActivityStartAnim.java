package com.example.clientversion_3.util;

import android.app.Activity;

import com.example.clientversion_3_1.R;

public class ActivityStartAnim {

	public static void DownToUp(Activity activity) {
		int version =  Integer.valueOf(android.os.Build.VERSION.SDK);       
		if(version > 5 ){         
			activity.overridePendingTransition(R.anim.zoomin, R.anim.zoomout);       
		} 
	}
	
	public static void RightToLeft(Activity activity) {
		int version =  Integer.valueOf(android.os.Build.VERSION.SDK);       
		if(version > 5 ){         
			activity.overridePendingTransition(R.anim.in, R.anim.out);       
		} 
	}
	
	public static void LeftToRight(Activity activity) {
		int version =  Integer.valueOf(android.os.Build.VERSION.SDK);       
		if(version > 5 ){         
			activity.overridePendingTransition(R.anim.in_animation, R.anim.out_animation);       
		} 
	}
	
	public static void RightToLeft2(Activity activity) {
		int version =  Integer.valueOf(android.os.Build.VERSION.SDK);       
		if(version > 5 ){         
			activity.overridePendingTransition(R.anim.push_in, R.anim.push_out);       
		} 
	}
	
	public static void Alpha(Activity activity) {
		int version =  Integer.valueOf(android.os.Build.VERSION.SDK);       
		if(version > 5 ){         
			activity.overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);       
		} 
	}
	
	public static void BottomToTop(Activity activity) {
		int version =  Integer.valueOf(android.os.Build.VERSION.SDK);       
		if(version > 5 ){         
			activity.overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);       
		} 
	}
	
	public static void TopToBottom(Activity activity) {
		int version =  Integer.valueOf(android.os.Build.VERSION.SDK);       
		if(version > 5 ){         
			activity.overridePendingTransition(R.anim.push_down_in, R.anim.push_down_in);       
		} 
	}
	
	
	
}
