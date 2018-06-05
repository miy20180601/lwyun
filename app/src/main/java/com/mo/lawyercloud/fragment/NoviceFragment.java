package com.mo.lawyercloud.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.mo.lawyercloud.R;
import com.mo.lawyercloud.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Mohaifeng on 18/5/23.
 * 新手指引页
 */
public class NoviceFragment extends BaseFragment {


    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.web_view)
    WebView webView;

    private static NoviceFragment instance = null;

    public static NoviceFragment getInstance() {
        if (instance == null) {
            instance = new NoviceFragment();
        }
        return instance;
    }


    @Override
    public int getLayoutId() {
        return R.layout.fg_novice;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        barTitle.setText("新手指引");
        barTitle.setVisibility(View.VISIBLE);
    }



    @OnClick(R.id.bar_iv_back)
    public void onViewClicked() {
        // TODO: 18/5/23 返回首页
    }
}
