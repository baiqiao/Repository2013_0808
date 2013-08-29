package com.example.clientversion_3;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.example.clientversion_3.view.SlidingMenu;
import com.example.clientversion_3.view.lib.SlidingFragmentActivity;
import com.example.clientversion_3_1.R;

public class MasterBaseActivity extends SlidingFragmentActivity {

    protected Fragment    mFrag;
    protected SlidingMenu slidingMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setBehindContentView(R.layout.left_frame);
        FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
        mFrag = new LeftFragment2();
        t.replace(R.id.menu_frame, mFrag);
        t.commit();

//        setBehindContentView(R.layout.left_menu2);
        
        slidingMenu = getSlidingMenu(); 
        //设置是左滑还是右滑，还是左右都可以滑 
        slidingMenu.setMode(SlidingMenu.LEFT_RIGHT); 
        //设置阴影宽度 
        slidingMenu.setShadowWidth(getWindowManager().getDefaultDisplay().getWidth() / 50); 
        //设置左菜单阴影图片 
        slidingMenu.setShadowDrawable(R.drawable.shadow); 
        //设置右菜单阴影图片 
        slidingMenu.setSecondaryShadowDrawable(R.drawable.right_shadow); 
	  	//设置菜单占屏幕的比例 
        slidingMenu.setBehindOffset(getWindowManager().getDefaultDisplay().getWidth() / 7); 
        //设置滑动时菜单的是否淡入淡出 
        slidingMenu.setFadeEnabled(true); 
        //设置淡入淡出的比例 
        slidingMenu.setFadeDegree(0.4f); 
        //设置滑动时拖拽效果 
        slidingMenu.setBehindScrollScale(0); 
        //设置要使菜单滑动，触碰屏幕的范围 
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

    }

}
