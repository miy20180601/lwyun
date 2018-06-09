package com.mo.lawyercloud.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mo.lawyercloud.R;
import com.mo.lawyercloud.base.BaseFragment;
import com.mo.lawyercloud.beans.BaseEntity;
import com.mo.lawyercloud.beans.apiBeans.BannerBean;
import com.mo.lawyercloud.beans.apiBeans.HomeBean;
import com.mo.lawyercloud.network.BaseObserver;
import com.mo.lawyercloud.network.RetrofitFactory;
import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.transformers.Transformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;

/**
 * Created by mo on 2018/5/15.
 */

public class HomeFragment extends BaseFragment {


    @BindView(R.id.xbanner)
    XBanner mXbanner;
    @BindView(R.id.edit_search)
    EditText mEditSearch;

    //轮播图
    private List<String> imageList;

    private static HomeFragment instance = null;
    public static HomeFragment getInstance() {
        if (instance == null) {
            instance = new HomeFragment();
        }
        return instance;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fg_home;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadData();

    }

    private void loadData() {
        Observable<BaseEntity<HomeBean>> observable = RetrofitFactory.getInstance()
                .homeIndex();
        observable.compose(this.<BaseEntity<HomeBean>>rxSchedulers()).subscribe(new BaseObserver<HomeBean>() {
            @Override
            protected void onHandleSuccess(HomeBean homeBean, String msg) {
                List<BannerBean> banners = homeBean.getBanners();
                initXbanner(banners);
            }
        });
    }

    private void initXbanner(final List<BannerBean> banners) {

        mXbanner.setData(banners, null);
        mXbanner.setmAdapter(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                //Log.e("imageList", Constant.SERVER_API + imageList.get(position));
                Glide.with(mContext).load(banners.get(position).getImage())
                        .into((ImageView) view);
            }
        });
        mXbanner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, int position) {
                // TODO: 2018/5/21 点击事件
            }
        });
        // 设置XBanner的页面切换特效
        mXbanner.setPageTransformer(Transformer.Default);
        // 设置XBanner页面切换的时间，即动画时长
        mXbanner.setPageChangeDuration(500);
    }


    @Override
    public void onResume() {
        super.onResume();
        mXbanner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        mXbanner.stopAutoPlay();
    }

}
