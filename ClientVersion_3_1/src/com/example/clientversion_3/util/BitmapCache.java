package com.example.clientversion_3.util;
/**
 * 
 * Java对象的引用类型

(1)强引用（StrongReference）如果一个对象具有强引用，那垃圾回收器绝不会回收它。当内存空间不足，Java虚拟机宁愿抛出OutOfMemoryError错误，使程序异常终止，也不会靠随意回收具有强引用的对象来解决内存不足的问题。

(2)软引用（SoftReference）如果一个对象只具有软引用，则内存空间足够，垃圾回收器就不会回收它；如果内存空间不足了，就会回收这些对象的内存。只要垃圾回收器没有回收它，该对象就可以被程序使用。

(3)弱引用（WeakReference）弱引用与软引用的区别在于：只具有弱引用的对象拥有更短暂的生命周期。在垃圾回收器线程扫描它所管辖的内存区域的过程中，一旦发现了只具有弱引用的对象，不管当前内存空间足够与否，都会回收它的内存。

(4)虚引用（PhantomReference）“虚引用”顾名思义，就是形同虚设，与其他几种引用都不同，虚引用并不会决定对象的生命周期。如果一个对象仅持有虚引用，那么它就和没有任何引用一样，在任何时候都可能被垃圾回收器回收。


 * 
 * 
 * 解决OOM的常用方案

个人总结的一些常用方法：

(1)缓存图像到内存，采用软引用缓存到内存，而不是在每次使用的时候都从新加载到内存；

(2)调整图像大小，手机屏幕尺寸有限，分配给图像的显示区域本身就更小，有时图像大小可以做适当调整；

(3)采用低内存占用量的编码方式，比如Bitmap.Config.ARGB_4444比Bitmap.Config.ARGB_8888更省内存；

(4)及时回收图像，如果引用了大量Bitmap对象，而应用又不需要同时显示所有图片，可以将暂时用不到的Bitmap对象及时回收掉；

(5)自定义堆内存分配大小，优化Dalvik虚拟机的堆内存分配；


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
    
    /** 用于Chche内容的存储 */  
    private Hashtable<Integer, MySoftRef> hashRefs;  
    
    /** 垃圾Reference的队列（所引用的对象已经被回收，则将该引用存入队列中） */  
    private ReferenceQueue<Bitmap> q;  
  
    /** 
     * 继承SoftReference，使得每一个实例都具有可识别的标识。 
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
     * 取得缓存器实例 =================================
     */  
    public static BitmapCache getInstance() {  
    	Log.d("MMM",111+"");
        if (cache == null) {  
            cache = new BitmapCache();  
        }  
        return cache;  
    }  
  
    /** 
     * 以软引用的方式对一个Bitmap对象的实例进行引用并保存该引用 
     */  
    private void addCacheBitmap(Bitmap bmp, Integer key) {  
    	
        cleanCache();// 清除垃圾引用   
        MySoftRef ref = new MySoftRef(bmp, q, key);  
        hashRefs.put(key, ref);  
    }  
  
    /** ==========================================
     * 依据所指定的drawable下的图片资源ID号（可以根据自己的需要从网络或本地path下获取），重新获取相应Bitmap对象的实例缩略图 
      
    public Bitmap getBitmap(int resId, Context context, int width, int height) {  
        Bitmap bmp = null;  
        // 缓存中是否有该Bitmap实例的软引用，如果有，从软引用中取得。   
        if (hashRefs.containsKey(resId)) {  
            MySoftRef ref = (MySoftRef) hashRefs.get(resId);  
            bmp = (Bitmap) ref.get();  
        }  
        // 如果没有软引用，或者从软引用中得到的实例是null，重新构建一个实例，   
        // 并保存对这个新建实例的软引用   
         if (bmp == null) {  
            // 传说decodeStream直接调用JNI>>nativeDecodeAsset()来完成decode，   
            // 无需再使用java层的createBitmap，从而节省了java层的空间。   
        	 
            GetThumbnail thumbmail = new GetThumbnail(context);
            bmp = thumbmail.getImageThumbnail(null, resId, width, height);
              
            this.addCacheBitmap(bmp, resId);  
        }  
        return bmp;  
    }  
    */ 
    
    /** ==========================================
     * 依据所指定的drawable下的图片资源ID号（可以根据自己的需要从网络或本地path下获取），重新获取相应Bitmap对象的实例 
     */  
    public Bitmap getBitmap(int resId, Context context) {  
        Bitmap bmp = null;  
        // 缓存中是否有该Bitmap实例的软引用，如果有，从软引用中取得。   
        if (hashRefs.containsKey(resId)) {  
            MySoftRef ref = (MySoftRef) hashRefs.get(resId);  
            bmp = (Bitmap) ref.get();  
        }  
        // 如果没有软引用，或者从软引用中得到的实例是null，重新构建一个实例，   
        // 并保存对这个新建实例的软引用   
         if (bmp == null) {  
            // 传说decodeStream直接调用JNI>>nativeDecodeAsset()来完成decode，   
            // 无需再使用java层的createBitmap，从而节省了java层的空间。   
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
     * 清除Cache内的全部内容 
     */  
    public void clearCache() {  
        cleanCache();  
        hashRefs.clear();  
        System.gc();  
        System.runFinalization();  
    }  
}  
