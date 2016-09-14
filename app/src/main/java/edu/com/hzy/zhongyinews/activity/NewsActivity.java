package edu.com.hzy.zhongyinews.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.com.hzy.zhongyinews.R;
import edu.com.hzy.zhongyinews.adapter.NewsRecycleAdapter;
import edu.com.hzy.zhongyinews.base.BaseFragment;
import edu.com.hzy.zhongyinews.fragments.FavorFragment;
import edu.com.hzy.zhongyinews.fragments.HotFragment;
import edu.com.hzy.zhongyinews.fragments.LoginFragment;
import edu.com.hzy.zhongyinews.fragments.NewsFragment;

public class NewsActivity extends AppCompatActivity implements BaseFragment.onFragmentInteractionListener,RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.news_content)
    FrameLayout newsContent;
    @BindView(R.id.radiogroup1)
    RadioGroup radiogroup1;
    NewsFragment nf;
    FavorFragment ff;
    HotFragment hf;
    LoginFragment lf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);

        ff=new FavorFragment();
        hf=new HotFragment();
        lf=new LoginFragment();
        nf=NewsFragment.getInstance(getIntent().getExtras());
        addFragment(nf);
        radiogroup1.setOnCheckedChangeListener(this);
    }

    public void addFragment(Fragment f){
        getSupportFragmentManager().beginTransaction().add(R.id.news_content, f, f.getClass().getSimpleName()).commit();

    }



    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i){
            case R.id.radiobutton1:
               showFragment(nf);
                break;
            case R.id.radiobutton2:
                showFragment(hf);
                break;
            case R.id.radiobutton3:
                showFragment(ff);
                break;
            case R.id.radiobutton4:
                showFragment(lf);
                break;
        }
    }

    public void showFragment(Fragment f) {
        Fragment[] fs = {nf, ff, lf, hf};
        FragmentManager fm = getSupportFragmentManager();
        if (f != fm.findFragmentByTag(f.getClass().getSimpleName())) {
            addFragment(f);
        }
        FragmentTransaction tr = fm.beginTransaction();
        for (Fragment tf : fs) {
            tr.hide(tf);
        }
        tr.show(f).commit();
    }

    @Override
    public void onFragmentInteraction(int viewId, Bundle bundle) {
        switch (viewId) {
            case NewsRecycleAdapter.RECYCLER_ITEM:
                //跳转activity，传值过去
                Intent intent = new Intent(NewsActivity.this, BrowserActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);

                break;
        }
    }
}
