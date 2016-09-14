package edu.com.hzy.zhongyinews.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import edu.com.hzy.zhongyinews.R;
import edu.com.hzy.zhongyinews.entity.NetEaseType;

public class MainActivity extends AppCompatActivity{
    long time;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent=new Intent(MainActivity.this,NewsActivity.class);

        //        Handler handler=new Handler();
        //        handler.postDelayed(new Runnable() {
        //            @Override
        //            public void run() {
        //                Intent intent=new Intent(MainActivity.this,NewsActivity.class);
        //                startActivity(intent);
        //            }
        //        },1000);
        getList();

    }

    private void getList() {
       time=System.currentTimeMillis();
        String url = "http://c.m.163.com/nc/topicset/android/subscribe/manage/listspecial.html";
        RequestParams entity = new RequestParams(url);
        x.http().get(entity, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                NetEaseType netEaseType= gson.fromJson(result, NetEaseType.class);


                intent.putExtra("list",netEaseType.gettList());

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
//                Toast.makeText(MainActivity.this, "网络错误", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                long endTime=System.currentTimeMillis();
                if ((endTime-time)<1500){
                    try {
                        Thread.sleep(1500-(endTime-time));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                startActivity(intent);
                finish();
            }
        });
    }

}
