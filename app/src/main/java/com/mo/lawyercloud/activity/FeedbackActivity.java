package com.mo.lawyercloud.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mo.lawyercloud.R;
import com.mo.lawyercloud.base.BaseActivity;
import com.mo.lawyercloud.beans.BaseEntity;
import com.mo.lawyercloud.network.BaseObserver;
import com.mo.lawyercloud.network.RetrofitFactory;
import com.mo.lawyercloud.utils.NToast;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * 问题反馈
 */
public class FeedbackActivity extends BaseActivity {
    @BindView(R.id.bar_iv_back)
    ImageView barIvBack;
    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.bar_tv_right)
    TextView barTvRight;
    @BindView(R.id.et_feeback_content)
    EditText etFeebackContent;
    @BindView(R.id.bt_feeback_feedback)
    Button btFeebackFeedback;

    @Override
    public int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        barTitle.setText("问题反馈");
    }

    @Override
    public void initData() {

    }

    @Override
    public void onEvent() {

    }


    @OnClick({R.id.bar_iv_back, R.id.bt_feeback_feedback})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bar_iv_back:

                break;
            case R.id.bt_feeback_feedback:
                chack();
                break;
        }
    }

    public void chack() {
        String content = etFeebackContent.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            NToast.shortToast(mContext, "反馈问题内容不能为空");
            return;

        }
        submitFeedback();
    }

    public void submitFeedback() {
        String content = etFeebackContent.getText().toString().trim();
        Map<String, String> params = new HashMap<>();
        params.put("content", content);
        Gson gson = new Gson();
        String strEntity = gson.toJson(params);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), strEntity);
        Observable<BaseEntity<Object>> observable = RetrofitFactory.getInstance().submitFeedback(body);
        observable.compose(this.<BaseEntity<Object>>rxSchedulers()).subscribe(new BaseObserver<Object>() {
            @Override
            protected void onHandleSuccess(Object registerResult, String msg) {
                NToast.shortToast(mContext, "反馈问题成功");
            }
            @Override
            protected void onHandleError(int statusCode, String msg) {
                NToast.shortToast(mContext, msg);
            }
        });
    }
}
