package edu.com.hzy.zhongyinews.fragments;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.com.hzy.zhongyinews.R;
import edu.com.hzy.zhongyinews.adapter.CollectRecycleAdapter;
import edu.com.hzy.zhongyinews.base.BaseFragment;
import edu.com.hzy.zhongyinews.entity.CollectNews;
import edu.com.hzy.zhongyinews.utils.NewsCollectHelper;

/**
 * Created by Administrator on 2016/8/31 0031.
 */
public class FavorFragment extends BaseFragment {
    List<CollectNews> list;
    CollectRecycleAdapter adapter;
    private LinearLayoutManager layoutManager;
    @BindView(R.id.recy_favor)
    RecyclerView recy_Favor;

    @Override
    protected void initData() {
        NewsCollectHelper helper = new NewsCollectHelper(getContext());
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select * from CollTbl";
        list=new ArrayList<>();
        Cursor c = db.rawQuery(sql, null);

        while (c.moveToNext()) {
            String str = c.getString(c.getColumnIndex("url"));
            String title = c.getString(c.getColumnIndex("name"));
            list.add(new CollectNews(title));
        }
        c.close();
        db.close();
        adapter=new CollectRecycleAdapter(list,getContext());
        layoutManager=new LinearLayoutManager(getContext());
        recy_Favor.setLayoutManager(layoutManager);
        recy_Favor.setAdapter(adapter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_favor;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
