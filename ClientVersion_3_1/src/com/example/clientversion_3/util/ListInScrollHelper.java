package com.example.clientversion_3.util;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ListInScrollHelper {
	/**  
     * ���¼���ListView�ĸ߶ȣ����ScrollView��ListView����View���й�����Ч������Ƕ��ʹ��ʱ���ͻ������  
     * @param listView  
     */  
    public static void setListViewHeight(ListView listView) {    
            
        // ��ȡListView��Ӧ��Adapter    
        ListAdapter listAdapter = listView.getAdapter();    
        
        if (listAdapter == null) {    
            return;    
        }    
        int totalHeight = 0;    
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()�������������Ŀ    
            View listItem = listAdapter.getView(i, null, listView);    
            listItem.measure(0, 0); // ��������View �Ŀ��    
            totalHeight += listItem.getMeasuredHeight(); // ͳ������������ܸ߶�    
        }    
        
        ViewGroup.LayoutParams params = listView.getLayoutParams();    
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));    
        listView.setLayoutParams(params);    
    }    
}
