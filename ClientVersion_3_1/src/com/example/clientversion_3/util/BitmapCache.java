package com.example.clientversion_3.util;
/**
 * 
 * Java�������������

(1)ǿ���ã�StrongReference�����һ���������ǿ���ã�����������������������������ڴ�ռ䲻�㣬Java�������Ը�׳�OutOfMemoryError����ʹ�����쳣��ֹ��Ҳ���῿������վ���ǿ���õĶ���������ڴ治������⡣

(2)�����ã�SoftReference�����һ������ֻ���������ã����ڴ�ռ��㹻�������������Ͳ��������������ڴ�ռ䲻���ˣ��ͻ������Щ������ڴ档ֻҪ����������û�л��������ö���Ϳ��Ա�����ʹ�á�

(3)�����ã�WeakReference���������������õ��������ڣ�ֻ���������õĶ���ӵ�и����ݵ��������ڡ��������������߳�ɨ��������Ͻ���ڴ�����Ĺ����У�һ��������ֻ���������õĶ��󣬲��ܵ�ǰ�ڴ�ռ��㹻��񣬶�����������ڴ档

(4)�����ã�PhantomReference���������á�����˼�壬������ͬ���裬�������������ö���ͬ�������ò��������������������ڡ����һ����������������ã���ô���ͺ�û���κ�����һ�������κ�ʱ�򶼿��ܱ��������������ա�


 * 
 * 
 * ���OOM�ĳ��÷���

�����ܽ��һЩ���÷�����

(1)����ͼ���ڴ棬���������û��浽�ڴ棬��������ÿ��ʹ�õ�ʱ�򶼴��¼��ص��ڴ棻

(2)����ͼ���С���ֻ���Ļ�ߴ����ޣ������ͼ�����ʾ������͸�С����ʱͼ���С�������ʵ�������

(3)���õ��ڴ�ռ�����ı��뷽ʽ������Bitmap.Config.ARGB_4444��Bitmap.Config.ARGB_8888��ʡ�ڴ棻

(4)��ʱ����ͼ����������˴���Bitmap���󣬶�Ӧ���ֲ���Ҫͬʱ��ʾ����ͼƬ�����Խ���ʱ�ò�����Bitmap����ʱ���յ���

(5)�Զ�����ڴ�����С���Ż�Dalvik������Ķ��ڴ���䣻


 * 
 */
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Hashtable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class BitmapCache {  
	
    static private BitmapCache cache;  
    
    /** ����Chche���ݵĴ洢 */  
    private Hashtable<Integer, MySoftRef> hashRefs;  
    
    /** ����Reference�Ķ��У������õĶ����Ѿ������գ��򽫸����ô�������У� */  
    private ReferenceQueue<Bitmap> q;  
  
    /** 
     * �̳�SoftReference��ʹ��ÿһ��ʵ�������п�ʶ��ı�ʶ�� 
     */  
    private class MySoftRef extends SoftReference<Bitmap> {  
    	
        private Integer _key = 0;  
  
        public MySoftRef(Bitmap bmp, ReferenceQueue<Bitmap> q, int key) {  
        	
        	super(bmp, q);  
            _key = key;  
            Log.d("MMM",333+"");
        }  
    }  
    
    
    private BitmapCache() { 
    	Log.d("MMM",222+"");
        hashRefs = new Hashtable<Integer, MySoftRef>();  
        q = new ReferenceQueue<Bitmap>();  
    }  
  
    /** 
     * ȡ�û�����ʵ�� =================================
     */  
    public static BitmapCache getInstance() {  
    	Log.d("MMM",111+"");
        if (cache == null) {  
            cache = new BitmapCache();  
        }  
        return cache;  
    }  
  
    /** 
     * �������õķ�ʽ��һ��Bitmap�����ʵ���������ò���������� 
     */  
    private void addCacheBitmap(Bitmap bmp, Integer key) {  
    	
        cleanCache();// �����������   
        MySoftRef ref = new MySoftRef(bmp, q, key);  
        hashRefs.put(key, ref);  
    }  
  
    /** ==========================================
     * ������ָ����drawable�µ�ͼƬ��ԴID�ţ����Ը����Լ�����Ҫ������򱾵�path�»�ȡ�������»�ȡ��ӦBitmap�����ʵ������ͼ 
      
    public Bitmap getBitmap(int resId, Context context, int width, int height) {  
        Bitmap bmp = null;  
        // �������Ƿ��и�Bitmapʵ���������ã�����У�����������ȡ�á�   
        if (hashRefs.containsKey(resId)) {  
            MySoftRef ref = (MySoftRef) hashRefs.get(resId);  
            bmp = (Bitmap) ref.get();  
        }  
        // ���û�������ã����ߴ��������еõ���ʵ����null�����¹���һ��ʵ����   
        // �����������½�ʵ����������   
         if (bmp == null) {  
            // ��˵decodeStreamֱ�ӵ���JNI>>nativeDecodeAsset()�����decode��   
            // ������ʹ��java���createBitmap���Ӷ���ʡ��java��Ŀռ䡣   
        	 
            GetThumbnail thumbmail = new GetThumbnail(context);
            bmp = thumbmail.getImageThumbnail(null, resId, width, height);
              
            this.addCacheBitmap(bmp, resId);  
        }  
        return bmp;  
    }  
    */ 
    
    /** ==========================================
     * ������ָ����drawable�µ�ͼƬ��ԴID�ţ����Ը����Լ�����Ҫ������򱾵�path�»�ȡ�������»�ȡ��ӦBitmap�����ʵ�� 
     */  
    public Bitmap getBitmap(int resId, Context context) {  
        Bitmap bmp = null;  
        // �������Ƿ��и�Bitmapʵ���������ã�����У�����������ȡ�á�   
        if (hashRefs.containsKey(resId)) {  
            MySoftRef ref = (MySoftRef) hashRefs.get(resId);  
            bmp = (Bitmap) ref.get();  
        }  
        // ���û�������ã����ߴ��������еõ���ʵ����null�����¹���һ��ʵ����   
        // �����������½�ʵ����������   
         if (bmp == null) {  
            // ��˵decodeStreamֱ�ӵ���JNI>>nativeDecodeAsset()�����decode��   
            // ������ʹ��java���createBitmap���Ӷ���ʡ��java��Ŀռ䡣   
            bmp = BitmapFactory.decodeStream(context.getResources().openRawResource(resId)); 
              
            this.addCacheBitmap(bmp, resId);  
        }  
        return bmp;  
    }  
  
    private void cleanCache() {  
        MySoftRef ref = null;  
        while ((ref = (MySoftRef) q.poll()) != null) {  
            hashRefs.remove(ref._key);  
        }  
    }  
  
    /** 
     * ==========================================
     * ���Cache�ڵ�ȫ������ 
     */  
    public void clearCache() {  
        cleanCache();  
        hashRefs.clear();  
        System.gc();  
        System.runFinalization();  
    }  
}  
