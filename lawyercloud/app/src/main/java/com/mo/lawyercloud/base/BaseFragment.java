package com.mo.lawyercloud.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by mo on 2018/5/10.
 */

public abstract class BaseFragment extends Fragment {

    protected View mRootView;
    private Unbinder unbinder;
    protected Context mContext;

    public abstract int getLayoutId();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView =inflater.inflate(getLayoutId(),container,false);
        unbinder = ButterKnife.bind(this,mRootView);//绑定framgent
        mContext = getActivity();
        return mRootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
