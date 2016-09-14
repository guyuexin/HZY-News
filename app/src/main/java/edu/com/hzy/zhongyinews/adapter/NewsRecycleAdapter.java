package edu.com.hzy.zhongyinews.adapter;


import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.com.hzy.zhongyinews.R;
import edu.com.hzy.zhongyinews.entity.TypeList;
import edu.com.hzy.zhongyinews.utils.XImageUtil;

/**
 * Created by Administrator on 2016/9/2 0002.
 */

public class NewsRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<TypeList> list;
    private Handler handler;
    private Context context;
    public static final int RECYCLER_ITEM = 0;
    private OnItemClickListener onItemClickListener;

    public void setList(List<TypeList> list) {
        this.list = list;
    }

    public void addData(List<TypeList> l) {
        if (l == null) return;
        if (list == null) {
            setList(l);
            return;
        }
        list.addAll(l);
    }

    public List<TypeList> getList() {
        return list;
    }


    public NewsRecycleAdapter(List<TypeList> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public NewsRecycleAdapter(Context context) {
        this.context = context;
    }

    public static final int VIEW_TYPE_VP = 0;
    public static final int VIEW_TYPE_ONE_HEAD = 1;
    public static final int VIEW_TYPE_ONE_BIG = 2;
    public static final int VIEW_TYPE_THREE_SMALL = 3;
    public static final int VIEW_TYPE_DOWN_LOAD = 4;
    //上拉加载更过的三种状态
    public static final int PULLUP_LOAD_MORE = 0;
    //正在加载...
    public static final int ISLOADING = 1;
    public static final int NO_MORE_DATA = 2;// 没有更多数据了
    //上拉加载的显示状态，初始为0
    private int load_more_status = 0;
    //Adapter里设置的一个方法，方便Activity调用改变加载状态来显示不同的加载信息
    // （上拉加载更多，加载中…）：

    public  void changeMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_ONE_BIG:
                View v = View.inflate(context, R.layout.layout_item_one_img, null);
                return new OneImageHolder(v);
            case VIEW_TYPE_VP:
                v = View.inflate(context, R.layout.layout_item_vp, null);
                return new ViewPagerHolder(v);
            case VIEW_TYPE_THREE_SMALL:
                v = View.inflate(context, R.layout.layout_item_three_img, null);
                return new ThreeImageHolder(v);
            case VIEW_TYPE_ONE_HEAD:
                v = View.inflate(context, R.layout.layout_item_one_head, null);
                return new HeadPicHolder(v);
            case VIEW_TYPE_DOWN_LOAD:
                v = View.inflate(context, R.layout.layout_foot, null);
                return new FootHolder(v);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            if (list.get(position).ads == null) {
                return VIEW_TYPE_ONE_HEAD;
            } else {
                return VIEW_TYPE_VP;
            }
        } else if (position > 0 && position < list.size()) {
            if (list.get(position).imgextra == null) {
                return VIEW_TYPE_ONE_BIG;
            } else {
                return VIEW_TYPE_THREE_SMALL;
            }
        } else {
            return VIEW_TYPE_DOWN_LOAD;
        }

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //     TypeList east=list.get(position);

        if (holder instanceof OneImageHolder) {
            OneImageHolder h = (OneImageHolder) holder;

            XImageUtil.display(h.imgLeft, list.get(position).imgsrc);
            String title = list.get(position).ltitle == null ? list.get(position).ltitle : list.get(position).title;
            h.tvTitle.setText(title);
            h.tvFollow.setText(list.get(position).replyCount + "跟帖");

        } else if (holder instanceof HeadPicHolder) {
            HeadPicHolder h = (HeadPicHolder) holder;
            XImageUtil.display(h.imgHead, list.get(position).imgsrc);
            String title = list.get(position).ltitle == null ? list.get(position).title : list.get(position).ltitle;
            h.tvTitle.setText(title);


        } else if (holder instanceof ThreeImageHolder) {
            ThreeImageHolder h = (ThreeImageHolder) holder;

            XImageUtil.display(h.img1, list.get(position).imgsrc);
            XImageUtil.display(h.img2, list.get(position).imgextra.get(0).imgsrc);
            XImageUtil.display(h.img3, list.get(position).imgextra.get(1).imgsrc);
            String title = list.get(position).ltitle == null ? list.get(position).ltitle : list.get(position).title;
            h.tvTitle.setText(title);
            h.tvFollow.setText(list.get(position).replyCount + "跟帖");
        } else if (holder instanceof ViewPagerHolder) {
            initViewPagerHolder((ViewPagerHolder) holder, position);
        } else  {
            FootHolder footer = (FootHolder) holder;
            switch (load_more_status) {
                case PULLUP_LOAD_MORE:
                    footer.pb_foot.setVisibility(View.GONE);
                    footer.tv_foottext.setText("上拉加载更多");
                    break;
                case ISLOADING:
                    footer.pb_foot.setVisibility(View.VISIBLE);
                    footer.tv_foottext.setText("正在加载...");
                    break;
                case NO_MORE_DATA:
                    footer.pb_foot.setVisibility(View.GONE);
                    footer.tv_foottext.setText("没有更多加载数据了");
                    break;
            }
        }

        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    onItemClickListener.itemClick(RECYCLER_ITEM,position);
                }
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void itemClick(int viewId, int position);
    }

    public void initViewPagerHolder(ViewPagerHolder holder, int position) {
        final ViewPagerHolder h = holder;
        final List<TypeList.AD> ads = list.get(position).ads;
        AdAdapter adapter = new AdAdapter(ads);

        for (int i = 0; i < ads.size(); i++) {
            ImageView img = new ImageView(context);
            img.setImageResource(R.drawable.adware_style_default);
            h.ll_layout.addView(img);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) img.getLayoutParams();
            layoutParams.leftMargin = 5;
            layoutParams.rightMargin = 5;
        }

        ImageView childAt = (ImageView) h.ll_layout.getChildAt(0);
        childAt.setImageResource(R.drawable.adware_style_selected);
        h.vpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int pos = position % ads.size();
                for (int i = 0; i < ads.size(); i++) {
                    ImageView childAt = (ImageView) h.ll_layout.getChildAt(i);
                    childAt.setImageResource(R.drawable.adware_style_default);
                }
                ((ImageView) h.ll_layout.getChildAt(pos)).setImageResource(R.drawable.adware_style_selected);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        h.vpager.setAdapter(adapter);
        h.vpager.setCurrentItem(Integer.MAX_VALUE / 2 - ((Integer.MAX_VALUE / 2) % list.get(position).ads.size()));

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            int position = h.vpager.getCurrentItem();
                            h.vpager.setCurrentItem(position + 1);
                            handler.sendEmptyMessage(1);
                        }
                    }, 2000);
                }
            }
        };
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int position = h.vpager.getCurrentItem();
                h.vpager.setCurrentItem(position + 1);
                handler.sendEmptyMessage(1);
            }
        }, 2000);

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size() + 1;
    }

    //一张图，右边是文字的布局
    public static class OneImageHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_leftimgone)
        ImageView imgLeft;
        @BindView(R.id.tv_titleimgone)
        TextView tvTitle;
        @BindView(R.id.tv_followimgone)
        TextView tvFollow;

        public OneImageHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    //三张图并排，上边是标题下边时间的布局
    public static class ThreeImageHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_titleimgthree)
        TextView tvTitle;
        @BindView(R.id.img1)
        ImageView img1;
        @BindView(R.id.img2)
        ImageView img2;
        @BindView(R.id.img3)
        ImageView img3;
        @BindView(R.id.tv_followheadthree)
        TextView tvFollow;

        public ThreeImageHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    //第一行viewpager的布局
    public static class ViewPagerHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.vpager)
        ViewPager vpager;
        @BindView(R.id.ll_layout)
        LinearLayout ll_layout;

        public ViewPagerHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    //第一行有一张大图的布局
    public static class HeadPicHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_headone)
        ImageView imgHead;
        @BindView(R.id.tv_titleheadone)
        TextView tvTitle;

        public HeadPicHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    public static class FootHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_foottext)
        TextView tv_foottext;
        @BindView(R.id.pb_foot)
        ProgressBar pb_foot;

        public FootHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}