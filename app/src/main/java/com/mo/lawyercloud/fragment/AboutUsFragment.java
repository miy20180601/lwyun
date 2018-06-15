package com.mo.lawyercloud.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mo.lawyercloud.R;
import com.mo.lawyercloud.base.BaseFragment;
import com.mo.lawyercloud.beans.BaseEntity;
import com.mo.lawyercloud.beans.apiBeans.WebViewBean;
import com.mo.lawyercloud.network.BaseObserver;
import com.mo.lawyercloud.network.RetrofitFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;

/**
 * Created by mo on 2018/5/25.
 */

public class AboutUsFragment extends BaseFragment {

    @BindView(R.id.web_view)
    WebView mWebView;

    private static AboutUsFragment instance = null;
    public static AboutUsFragment getInstance() {
        if (instance == null) {
            instance = new AboutUsFragment();
        }
        return instance;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fg_about_us;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initWebView();
        loadData();
    }

    private void loadData() {
        Observable<BaseEntity<WebViewBean>> observable = RetrofitFactory.getInstance().aboutus();
        observable.compose(this.<BaseEntity<WebViewBean>>rxSchedulers()).subscribe(new BaseObserver<WebViewBean>() {
            @Override
            protected void onHandleSuccess(WebViewBean webViewBean, String msg) {
                mWebView.loadData(webViewBean.getContent(),"text/html; charset=UTF-8", null);
            }
        });
    }

    private void initWebView() {
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDefaultTextEncodingName("UTF-8");//设置默认为utf-8
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.setBackgroundColor(0);
    }

}
