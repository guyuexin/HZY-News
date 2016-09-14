package edu.com.hzy.zhongyinews.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.com.hzy.zhongyinews.R;
import edu.com.hzy.zhongyinews.adapter.NewsRecycleAdapter;
import edu.com.hzy.zhongyinews.base.BaseFragment;
import edu.com.hzy.zhongyinews.entity.TypeList;
import edu.com.hzy.zhongyinews.view.RecycleViewDivider;

/**
 * Created by Administrator on 2016/8/31 0031.
 */
public class NewsListFragment extends BaseFragment {
    @BindView(R.id.recyclerView1)
    RecyclerView recyclerView1;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    @BindView(R.id.pb_loading)
    ProgressBar pb_loading;
    private boolean isPrepared;
    private boolean isVisible;
    private boolean isCompleted;
    NewsRecycleAdapter adapter;
    private LinearLayoutManager layoutManager;
    private String tid;


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    public void onVisible() {
        lazyLoad();
    }

    public void onInvisible() {

    }

    public void lazyLoad() {
        if (!isPrepared || !isVisible || isCompleted)
            return;

        Bundle bundle = getArguments();
        if (bundle != null) {
            String tid = bundle.getString("tid");
            getInitData(tid);
        }
    }

    private NewsRecycleAdapter.OnItemClickListener onItemClickListener = new NewsRecycleAdapter.OnItemClickListener() {
        @Override
        public void itemClick(int viewId, int position) {
            if (viewId == NewsRecycleAdapter.RECYCLER_ITEM) {
                String url = adapter.getList().get(position).url;
                String docid=adapter.getList().get(position).docid;
                String title=adapter.getList().get(position).title;
                if (url != null) {

                    Bundle bundle = new Bundle();
                    bundle.putString("docid",docid);
                    bundle.putString("url", url);
                    bundle.putString("title",title);
                    mListener.onFragmentInteraction(viewId, bundle);
                } else {
                    Toast.makeText(NewsListFragment.this.getContext(), "没有网址", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };


    @Override
    protected void initData() {
        isPrepared = true;
        lazyLoad();
        layoutManager = new LinearLayoutManager(getContext());
        Bundle bundle = getArguments();
        adapter = new NewsRecycleAdapter(getContext());
        adapter.setOnItemClickListener(onItemClickListener);

        recyclerView1.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.HORIZONTAL));

        //向上滑动，当滑到底的时候,有上滑的趋势，就加载更多
        recyclerView1.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!swipe.isRefreshing()) {
                    int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                    if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount()) {
                        //调用Adapter里的changeMoreStatus方法来改变加载脚View的显示状态为：正在加载...
                        adapter.changeMoreStatus(NewsRecycleAdapter.ISLOADING);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //                                 adapter.getList().addAll(adapter.getList());
                                //                                adapter.notifyDataSetChanged();
                                Toast.makeText(NewsListFragment.this.getContext(), "上拉加载", Toast.LENGTH_SHORT).show();
                                //当加载完数据后，再恢复加载脚View的显示状态为：上拉加载更多
                                adapter.changeMoreStatus(NewsRecycleAdapter.NO_MORE_DATA);
                            }
                        }, 3000);
                    }
                }
            }
        });
        //对下拉刷新进行监听，
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(NewsListFragment.this.getContext(), "刷新成功", Toast.LENGTH_SHORT).show();
                        swipe.setRefreshing(false);
                    }
                }, 3000);
            }
        });

    }


    public void getInitData(final String tid) {
        pb_loading.setVisibility(View.VISIBLE);
        swipe.setVisibility(View.GONE);
        String url = "http://c.m.163.com/nc/article/list/" + tid + "/0-20.html";
        x.http().get(new RequestParams(url), new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                List<TypeList> newslist = new ArrayList<TypeList>();
                try {
                    JSONObject json = new JSONObject(result);
                    JSONArray jsonarray = new JSONObject(result).getJSONArray(tid);
                    for (int i = 0; i < jsonarray.length(); i++) {
                        Gson gson = new Gson();
                        TypeList typeList = gson.fromJson(jsonarray.getString(i), TypeList.class);
                        newslist.add(typeList);


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                adapter.addData(newslist);
                recyclerView1.setLayoutManager(layoutManager);
                recyclerView1.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                pb_loading.setVisibility(View.GONE);
                swipe.setVisibility(View.VISIBLE);

                isCompleted = true;
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


    public static NewsListFragment getInstance(Bundle bundle) {
        NewsListFragment fragment = new NewsListFragment();
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_newlist;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
