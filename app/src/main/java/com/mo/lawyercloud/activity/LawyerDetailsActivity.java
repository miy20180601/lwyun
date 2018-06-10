package com.mo.lawyercloud.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mo.lawyercloud.R;
import com.mo.lawyercloud.base.BaseActivity;
import com.mo.lawyercloud.beans.BaseEntity;
import com.mo.lawyercloud.beans.apiBeans.SolicitorDetailBean;
import com.mo.lawyercloud.fragment.ProfessionQuickAdapter;
import com.mo.lawyercloud.network.BaseObserver;
import com.mo.lawyercloud.network.RetrofitFactory;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observable;

/**
 * @author CUI
 * @data 2018/06/07
 * @details 律师详情界面
 */
public class LawyerDetailsActivity extends BaseActivity {


    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.iv_avatar)
    CircleImageView ivAvatar;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_resume)
    TextView tvResume;


    private int mSolicitorId;
    private List<String> mChannels;
    private String[] mChannelArray;
    private ProfessionQuickAdapter mAdapter;


    @Override
    public int getLayoutId() {
        return R.layout.activity_lawyer_details;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        barTitle.setText("详细资料");
        mSolicitorId = getIntent().getIntExtra("id", 0);
        mChannelArray = getResources().getStringArray(R.array.channel);
        mChannels = new ArrayList<>();
        mAdapter = new ProfessionQuickAdapter(mChannels);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initData() {
        Observable<BaseEntity<SolicitorDetailBean>> observable = RetrofitFactory
                .getInstance().solicitorInfo(mSolicitorId);
        observable.compose(this.<BaseEntity<SolicitorDetailBean>>rxSchedulers()).subscribe(new BaseObserver<SolicitorDetailBean>() {
            @Override
            protected void onHandleSuccess(SolicitorDetailBean solicitorDetailBean, String msg) {
                RequestOptions options = new RequestOptions();
                options.error(R.mipmap.data_button_avatar_n);
                Glide.with(mContext).load(solicitorDetailBean.getAvatar()).apply(options).into(ivAvatar);
                tvNickname.setText(solicitorDetailBean.getRealName());
                tvLocation.setText(solicitorDetailBean.getLocation());
                tvResume.setText("简介："+solicitorDetailBean.getResume());
                String[] split = solicitorDetailBean.getChannels().split(",");
                for (String s : split) {
                    mChannels.add(mChannelArray[Integer.parseInt(s)]);
                }
                mAdapter.setNewData(mChannels);
            }
        });

    }

    @Override
    public void onEvent() {

    }

    @OnClick({R.id.bar_iv_back, R.id.tv_reserve})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bar_iv_back:
                finish();
                break;
            case R.id.tv_reserve:
                startActivity(new Intent(mContext,VideoAppointmentAcitivty.class).putExtra("id", mSolicitorId));
                break;
        }
    }
}
