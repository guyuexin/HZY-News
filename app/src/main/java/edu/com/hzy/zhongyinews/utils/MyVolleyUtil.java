package edu.com.hzy.zhongyinews.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Administrator on 2016/8/27 0027.
 */
public class MyVolleyUtil {
    private Context context;
    private static MyVolleyUtil util;
    private RequestQueue queue;
    private BitmapCache bitmapCache;

  private MyVolleyUtil(Context context){
        this.context=context;
        queue= Volley.newRequestQueue(context);
        bitmapCache=new BitmapCache();
    }

    public static MyVolleyUtil getInstance(Context context){
        if (util==null){
            synchronized (context){
                util=new MyVolleyUtil(context);
            }
        }
        return util;
    }

    public RequestQueue getQueue(){
        return queue;
    }

    public ImageLoader getLoader(){
        return new ImageLoader(queue,bitmapCache);
    }

    public static class BitmapCache implements ImageLoader.ImageCache{
        private LruCache<String,Bitmap> lruCache=new LruCache<String,Bitmap>(5*1024*1024){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };

        @Override
        public Bitmap getBitmap(String s) {
            return lruCache.get(s);
        }

        @Override
        public void putBitmap(String s, Bitmap bitmap) {
             lruCache.put(s,bitmap);
        }
    }
}
