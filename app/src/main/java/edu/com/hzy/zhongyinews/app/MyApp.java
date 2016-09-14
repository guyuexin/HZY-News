package edu.com.hzy.zhongyinews.app;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.xutils.x;

/**
 * Created by Administrator on 2016/8/31 0031.
 */
public class MyApp extends Application {
    private static RequestQueue queue;
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        queue= Volley.newRequestQueue(this);
    }

    public static RequestQueue getQueue(){
        return queue;
    }
}
