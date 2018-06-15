package com.mo.lawyercloud.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mo.lawyercloud.R;
import com.mo.lawyercloud.activity.MyContactAcitity;
import com.mo.lawyercloud.base.BaseFragment;
import com.mo.lawyercloud.beans.BaseEntity;
import com.mo.lawyercloud.beans.apiBeans.BannerBean;
import com.mo.lawyercloud.beans.apiBeans.HomeBean;
import com.mo.lawyercloud.eventbus.HomeClickMessage;
import com.mo.lawyercloud.network.BaseObserver;
import com.mo.lawyercloud.network.RetrofitFactory;
import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.transformers.Transformer;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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


    @OnClick({R.id.tv_reception, R.id.tv_message, R.id.btn_open_live, R.id.ll_family_affairs, R
            .id.ll_contractual_dispute, R.id
            .ll_infringement_disputes, R.id.ll_merger, R.id.ll_intellectual_property, R.id
            .ll_labor_dispute, R.id.ll_securities, R.id.ll_criminal})
    public void onViewClicked(View view) {
        HomeClickMessage msg = new HomeClickMessage();
        switch (view.getId()) {
            case R.id.tv_reception:
                startActivity(MyContactAcitity.class);
                break;
            case R.id.tv_message:
                msg.type = 3;
                EventBus.getDefault().post(msg);
                break;
            case R.id.btn_open_live:
                msg.type = 1;
                EventBus.getDefault().post(msg);
                break;
            case R.id.ll_family_affairs:
                msg.type = 1;
                msg.channel = 1;
                EventBus.getDefault().post(msg);
                break;
            case R.id.ll_contractual_dispute:
                msg.type = 1;
                msg.channel = 2;
                EventBus.getDefault().post(msg);
                break;
            case R.id.ll_infringement_disputes:
                msg.type = 1;
                msg.channel = 3;
                EventBus.getDefault().post(msg);
                break;
            case R.id.ll_merger:
                msg.type = 1;
                msg.channel = 4;
                EventBus.getDefault().post(msg);
                break;
            case R.id.ll_intellectual_property:
                msg.type = 1;
                msg.channel = 5;
                EventBus.getDefault().post(msg);
                break;
            case R.id.ll_labor_dispute:
                msg.type = 1;
                msg.channel = 6;
                EventBus.getDefault().post(msg);
                break;
            case R.id.ll_securities:
                msg.type = 1;
                msg.channel = 7;
                EventBus.getDefault().post(msg);
                break;
            case R.id.ll_criminal:
                msg.type = 1;
                msg.channel = 8;
                EventBus.getDefault().post(msg);
                break;
        }
    }
}
