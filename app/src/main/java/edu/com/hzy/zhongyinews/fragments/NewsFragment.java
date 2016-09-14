package edu.com.hzy.zhongyinews.fragments;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;

import butterknife.BindView;
import edu.com.hzy.zhongyinews.R;
import edu.com.hzy.zhongyinews.adapter.NewsTypeAdapter;
import edu.com.hzy.zhongyinews.base.BaseFragment;
import edu.com.hzy.zhongyinews.entity.NetEaseType;

/**
 * Created by Administrator on 2016/8/31 0031.
 */

public class NewsFragment extends BaseFragment {


    @BindView(R.id.indicator)
    TabPageIndicator indicator;
    @BindView(R.id.pager)
    ViewPager pager;
    NewsTypeAdapter adapter;
    @BindView(R.id.img_left)
    ImageView img_left;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.img_right)
    ImageView img_right;

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            ArrayList<NetEaseType.TList> list = (ArrayList<NetEaseType.TList>) bundle.getSerializable("list");
            adapter = new NewsTypeAdapter(getFragmentManager(), list);
            pager.setAdapter(adapter);
            indicator.setViewPager(pager);
            indicator.setVisibility(View.VISIBLE);
        }

    }

    public static NewsFragment getInstance(Bundle bundle) {
        NewsFragment nf = new NewsFragment();
        if (bundle != null) {
            nf.setArguments(bundle);
        }
        return nf;
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_news;
    }


}
