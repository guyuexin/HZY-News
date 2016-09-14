package edu.com.hzy.zhongyinews.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import edu.com.hzy.zhongyinews.entity.NetEaseType;
import edu.com.hzy.zhongyinews.fragments.NewsListFragment;

/**
 * Created by Administrator on 2016/8/31 0031.
 */

public class NewsTypeAdapter extends FragmentPagerAdapter {

    private List<NetEaseType.TList> titleList;

    public NewsTypeAdapter(FragmentManager fm, List<NetEaseType.TList> titleList) {
        super(fm);
        this.titleList = titleList;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList==null?"未命名":titleList.get(position).getTname();
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("tid", titleList.get(position).getTid());
        bundle.putString("tname", titleList.get(position).getTname());
        return NewsListFragment.getInstance(bundle);

    }

    @Override
    public int getCount() {
        return titleList==null?0:titleList.size();
    }
}
