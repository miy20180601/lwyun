package com.mo.lawyercloud.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.mo.lawyercloud.R;
import com.mo.lawyercloud.base.BaseFragment;
import com.mo.lawyercloud.beans.BaseEntity;
import com.mo.lawyercloud.beans.apiBeans.WebViewBean;
import com.mo.lawyercloud.network.BaseObserver;
import com.mo.lawyercloud.network.RetrofitFactory;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * Created by Mohaifeng on 18/5/23.
 * 新手指引页
 */
public class NoviceFragment extends BaseFragment {


    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.web_view)
    WebView mWebView;

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

        initWebView();
        loadData();
    }

    private void loadData() {
        Observable<BaseEntity<WebViewBean>> observable = RetrofitFactory
                .getInstance().noviceGuidelines();
        observable.compose(this.<BaseEntity<WebViewBean>>rxSchedulers()).subscribe(new BaseObserver<WebViewBean>() {

            @Override
            protected void onHandleSuccess(WebViewBean webViewBean, String msg) {
                mWebView.loadData(webViewBean.getContent(),"text/html","utf-8");
            }
        });
    }

    private void initWebView() {
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.setBackgroundColor(0);
    }

    @OnClick(R.id.bar_iv_back)
    public void onViewClicked() {
        // TODO: 18/5/23 返回首页

    }
}
