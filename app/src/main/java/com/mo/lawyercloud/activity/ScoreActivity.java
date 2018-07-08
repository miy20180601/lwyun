package com.mo.lawyercloud.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hedgehog.ratingbar.RatingBar;
import com.mo.lawyercloud.R;
import com.mo.lawyercloud.base.BaseActivity;
import com.mo.lawyercloud.beans.BaseEntity;
import com.mo.lawyercloud.network.BaseObserver;
import com.mo.lawyercloud.network.RetrofitFactory;
import com.mo.lawyercloud.utils.NToast;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

/**
 * Created by Mohaifeng on 18/7/8.
 * 律师评价
 */
public class ScoreActivity extends BaseActivity {

    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.rating_bar)
    RatingBar ratingBar;
    @BindView(R.id.tv_satisfaction)
    TextView tvSatisfaction;
    @BindView(R.id.tv_reply1)
    TextView tvReply1;
    @BindView(R.id.tv_reply2)
    TextView tvReply2;
    @BindView(R.id.tv_reply3)
    TextView tvReply3;
    @BindView(R.id.tv_reply4)
    TextView tvReply4;
    @BindView(R.id.et_other)
    EditText etOther;

    boolean isReply1, isReply2, isReply3, isReply4;
    private int score = 0;
    private String mQuickReply;
    private int mId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_score;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mId = getIntent().getIntExtra("id", 0);
        barTitle.setText("服务评价");
//        ratingBar.setStar(5);
//        tvSatisfaction.setText("非常满意");
    }

    @Override
    public void initData() {

    }

    @Override
    public void onEvent() {
        ratingBar.setOnRatingChangeListener(
                new RatingBar.OnRatingChangeListener() {
                    @Override
                    public void onRatingChange(float RatingCount) {
                        score = (int) RatingCount;
                        switch (score) {
                            case 1:
                                tvSatisfaction.setText("非常不满意");
                                break;
                            case 2:
                                tvSatisfaction.setText("不满意");
                                break;
                            case 3:
                                tvSatisfaction.setText("满意");
                                break;
                            case 4:
                                tvSatisfaction.setText("很满意");
                                break;
                            case 5:
                                tvSatisfaction.setText("非常满意");
                                break;
                        }
                    }
                }
        );

    }


    @OnClick({R.id.bar_iv_back, R.id.tv_reply1, R.id.tv_reply2, R.id.tv_reply3, R.id.tv_reply4, R
            .id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bar_iv_back:
                finish();
                break;
            case R.id.tv_reply1:
                isReply1 = !isReply1;
                tvReply1.setSelected(isReply1);
                break;
            case R.id.tv_reply2:
                isReply2 = !isReply2;
                tvReply2.setSelected(isReply2);
                break;
            case R.id.tv_reply3:
                isReply3 = !isReply3;
                tvReply3.setSelected(isReply3);
                break;
            case R.id.tv_reply4:
                isReply4 = !isReply4;
                tvReply4.setSelected(isReply4);
                break;
            case R.id.btn_submit:
                if (score == 0) {
                    NToast.shortToast(mContext, "请选择评分");
                    return;
                }
                StringBuilder sb = new StringBuilder();
                if (isReply1) {
                    sb.append(tvReply1.getText()).append(";");
                }
                if (isReply2) {
                    sb.append(tvReply2.getText()).append(";");
                }
                if (isReply3) {
                    sb.append(tvReply3.getText()).append(";");
                }
                if (isReply4) {
                    sb.append(tvReply4.getText());
                }
                mQuickReply = sb.toString();
                comment();
                break;
        }
    }

    private void comment() {
        Map<String, Object> params = new HashMap<>();
        params.put("id", mId); //订单号
        Map<String, Object> comment = new HashMap<>();
        comment.put("score", score);
        if (!TextUtils.isEmpty(mQuickReply)) {
            comment.put("quickReply",mQuickReply);
        }
        if (!TextUtils.isEmpty(etOther.getText().toString().trim())) {
            comment.put("content",etOther.getText().toString().trim());
        }
        params.put("comment", comment);
        Gson gson = new Gson();
        String strEntity = gson.toJson(params);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;" +
                "charset=UTF-8"), strEntity);
        Observable<BaseEntity<Object>> observable = RetrofitFactory.getInstance().orderComment
                (body);
        observable.compose(this.<BaseEntity<Object>>rxSchedulers()).subscribe(new BaseObserver<Object>
                () {
            @Override
            protected void onHandleSuccess(Object o, String msg) {
                NToast.shortToast(mContext,msg);
                finish();
            }

            @Override
            protected void onHandleError(int statusCode, String msg) {
                super.onHandleError(statusCode, msg);
                NToast.shortToast(mContext,msg);
            }
        });
    }
}
