package edu.com.hzy.zhongyinews.activity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import edu.com.hzy.zhongyinews.R;
import edu.com.hzy.zhongyinews.entity.Newsbody;
import edu.com.hzy.zhongyinews.utils.CommonUrls;
import edu.com.hzy.zhongyinews.utils.NewsCollectHelper;

public class BrowserActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.webView1)
    WebView webView1;
    @BindView(R.id.img_left1)
    ImageView img_left1;
    @BindView(R.id.tv_title1)
    TextView tv_title1;

    Button mBtnSay;
    Button mBtnCollect;
    Button mBtnZan;
    @BindView(R.id.btn_right1)
    ImageButton mBtnRight1;
    PopupWindow popupWindow;
    Newsbody newsbody;
    String str;
    String title;
    String docid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        ButterKnife.bind(this);
        webView1.getSettings().setJavaScriptEnabled(true);
        webView1.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        title=getIntent().getStringExtra("title");
        tv_title1.setText(title);
        docid=getIntent().getStringExtra("docid");
        String url = CommonUrls.getCommonUrls().getFullUrl(docid);
        webView1.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                imgReset();
            }
        });




        x.http().get(new RequestParams(url), new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    str=new JSONObject(result).getString(docid);
                    newsbody=new Gson().fromJson(str,Newsbody.class);
                    String before="<img src=\"";
                    String after="\"/></img>";
                    for(Newsbody.Img img : newsbody.img){
                   newsbody.body=newsbody.body.replace(img.ref,before+img.src+after);
                    }
                    webView1.loadDataWithBaseURL(null, newsbody.body, "text/html", "utf-8", null);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }


    @Override
    // 设置回退
    // 覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView1.canGoBack()) {
            webView1.goBack(); // goBack()表示返回WebView的上一页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick(R.id.btn_right1)
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn_right1:
                showPopupWindow(view);
                break;
            case R.id.btn_collect:
                NewsCollectHelper helper=new NewsCollectHelper(this);
                SQLiteDatabase db=helper.getWritableDatabase();
                ContentValues initialValues = new ContentValues();
                initialValues.put("name",title);
                initialValues.put("url",docid);
                db.insert("CollTbl", null, initialValues);
                db.close();
                Toast.makeText(this, "收藏成功", Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
                break;
            case R.id.btn_say:
                Toast.makeText(this, "分享", Toast.LENGTH_SHORT).show();
                ShareSDK.initSDK(this);
                showShare();
                popupWindow.dismiss();
                break;
            case R.id.btn_zan:
                Toast.makeText(this, "举报", Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
                break;
        }
    }

    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(this);
    }

    private void showPopupWindow(View view) {

        // 一个自定义的布局，作为显示的内容
        View contentView = View.inflate(BrowserActivity.this, R.layout.layout_popwindow, null);
        // 设置按钮的点击事件
        mBtnCollect = (Button) contentView.findViewById(R.id.btn_collect);
        mBtnSay = (Button) contentView.findViewById(R.id.btn_say);
        mBtnZan = (Button) contentView.findViewById(R.id.btn_zan);
        mBtnZan.setOnClickListener(this);
        mBtnCollect.setOnClickListener(this);
        mBtnSay.setOnClickListener(this);
        popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, true);

        popupWindow.setTouchable(true);

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Log.i("mengdd", "onTouch : ");

                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        //如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        //我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // 设置好参数之后再show
        popupWindow.showAsDropDown(view);

    }
    private void imgReset() {
        webView1.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName('img'); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "var img = objs[i];   " +
                "    img.style.maxWidth = '100%';   " +
                "}" +
                "})()");
    }

}
