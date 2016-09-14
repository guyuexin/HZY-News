package edu.com.hzy.zhongyinews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.com.hzy.zhongyinews.R;
import edu.com.hzy.zhongyinews.entity.CollectNews;

/**
 * Created by Administrator on 2016/9/13 0013.
 */
public class CollectRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CollectNews> list;
    private Context context;

    public List<CollectNews> getList() {
        return list;
    }

    public void setList(List<CollectNews> list) {
        this.list = list;
    }

    public CollectRecycleAdapter(List<CollectNews> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.layout_item_favor, null);

        return new CollectHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CollectHolder h = (CollectHolder) holder;
        h.tv_colltitle.setText(list.get(position).title);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public static class CollectHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_colltitle)
        TextView tv_colltitle;
        public CollectHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
