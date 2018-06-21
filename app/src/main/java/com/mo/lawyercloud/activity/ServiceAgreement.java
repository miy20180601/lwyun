package com.mo.lawyercloud.activity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.mo.lawyercloud.R;
import com.mo.lawyercloud.base.BaseActivity;
import com.mo.lawyercloud.beans.BaseEntity;
import com.mo.lawyercloud.beans.apiBeans.WebViewBean;
import com.mo.lawyercloud.network.BaseObserver;
import com.mo.lawyercloud.network.RetrofitFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * Created by mo on 2018/6/21.
 */

public class ServiceAgreement extends BaseActivity {

    @BindView(R.id.bar_title)
    TextView mBarTitle;
    @BindView(R.id.web_view)
    WebView mWebView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_service_agreement;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mBarTitle.setText("服务协议");
        initWebView();
    }

    @Override
    public void initData() {
        Observable<BaseEntity<WebViewBean>> observable = RetrofitFactory.getInstance().serviceAgreement();
        observable.compose(this.<BaseEntity<WebViewBean>>rxSchedulers()).subscribe(new BaseObserver<WebViewBean>() {
            @Override
            protected void onHandleSuccess(WebViewBean webViewBean, String msg) {
                mWebView.loadData(webViewBean.getContent(),"text/html; charset=UTF-8", null);
            }
        });
    }

    @Override
    public void onEvent() {

    }

    private void initWebView() {
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDefaultTextEncodingName("UTF-8");//设置默认为utf-8
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.setBackgroundColor(0);
    }

    @OnClick(R.id.bar_iv_back)
    public void onViewClicked() {
        finish();
    }
}
