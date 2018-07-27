package com.mo.lawyercloud.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mo.lawyercloud.R;
import com.mo.lawyercloud.base.BaseActivity;
import com.mo.lawyercloud.beans.BaseEntity;
import com.mo.lawyercloud.beans.apiBeans.PromotionImgBean;
import com.mo.lawyercloud.network.BaseObserver;
import com.mo.lawyercloud.network.RetrofitFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * Created by Mohaifeng on 18/6/13.
 */
public class PromotionImgActivity extends BaseActivity {

    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.iv_img)
    ImageView ivImg;

    @Override
    public int getLayoutId() {
        return R.layout.activity_promotion_img;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

        barTitle.setText("推广图");


    }

    @Override
    public void initData() {
        Observable<BaseEntity<PromotionImgBean>> observable = RetrofitFactory.getInstance().getPromotionImg(2);
        observable.compose(this.<BaseEntity<PromotionImgBean>>rxSchedulers()).subscribe(new BaseObserver<PromotionImgBean>() {
            @Override
            protected void onHandleSuccess(PromotionImgBean promotionImgBean, String msg) {
                Glide.with(mContext).load(promotionImgBean.getImage()).into(ivImg);
            }
        });
    }

    @Override
    public void onEvent() {

    }


    @OnClick(R.id.bar_iv_back)
    public void onViewClicked() {
        finish();
    }
}
