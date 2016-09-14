package edu.com.hzy.zhongyinews.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/31 0031.
 */
public abstract class BaseFragment extends Fragment {
    protected onFragmentInteractionListener mListener;
     protected View rootView;

    public BaseFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView==null){
            rootView=inflater.inflate(getLayoutId(),null);
        }
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();

            parent.removeView(rootView);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        initData();
    }

    protected abstract void initData();

    public abstract int getLayoutId();

    public void onButtonPressed(int viewId, Bundle bundle) {
        if (mListener != null) {
            mListener.onFragmentInteraction(viewId, bundle);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof onFragmentInteractionListener){
           mListener= (onFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener=null;
    }

    public interface onFragmentInteractionListener{
        void onFragmentInteraction(int viewId, Bundle bundle);
    }

}