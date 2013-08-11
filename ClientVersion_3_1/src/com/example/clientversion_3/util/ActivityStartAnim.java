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
	
}
