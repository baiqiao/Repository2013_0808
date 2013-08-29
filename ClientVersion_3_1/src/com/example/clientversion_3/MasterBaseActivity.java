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
        //�������󻬻����һ����������Ҷ����Ի� 
        slidingMenu.setMode(SlidingMenu.LEFT_RIGHT); 
        //������Ӱ��� 
        slidingMenu.setShadowWidth(getWindowManager().getDefaultDisplay().getWidth() / 50); 
        //������˵���ӰͼƬ 
        slidingMenu.setShadowDrawable(R.drawable.shadow); 
        //�����Ҳ˵���ӰͼƬ 
        slidingMenu.setSecondaryShadowDrawable(R.drawable.right_shadow); 
	  	//���ò˵�ռ��Ļ�ı��� 
        slidingMenu.setBehindOffset(getWindowManager().getDefaultDisplay().getWidth() / 7); 
        //���û���ʱ�˵����Ƿ��뵭�� 
        slidingMenu.setFadeEnabled(true); 
        //���õ��뵭���ı��� 
        slidingMenu.setFadeDegree(0.4f); 
        //���û���ʱ��קЧ�� 
        slidingMenu.setBehindScrollScale(0); 
        //����Ҫʹ�˵�������������Ļ�ķ�Χ 
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

    }

}
