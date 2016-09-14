package edu.com.hzy.zhongyinews.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import edu.com.hzy.zhongyinews.R;
import edu.com.hzy.zhongyinews.entity.TypeList;
import edu.com.hzy.zhongyinews.utils.XImageUtil;

/**
 * Created by Administrator on 2016/9/5 0005.
 */
    public class AdAdapter extends PagerAdapter {

    List<TypeList.AD> ads;

    public AdAdapter(List<TypeList.AD> ads) {
        this.ads = ads;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View v = View.inflate(container.getContext(), R.layout.layout_item_one_head, null);
        ImageView img = (ImageView) v.findViewById(R.id.img_headone);
        TextView tv = (TextView) v.findViewById(R.id.tv_titleheadone);
        XImageUtil.display(img, ads.get(position % ads.size()).imgsrc);
        tv.setText(ads.get(position % ads.size()).title);
        container.addView(v);
        return v;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);

    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
