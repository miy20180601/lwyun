package com.mo.lawyercloud.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.test.mock.MockContext;
import android.util.Log;
import android.view.DragAndDropPermissions;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.mo.lawyercloud.R;
import com.mo.lawyercloud.adapter.itemDecoration.GridSpacingItemDecoration;
import com.mo.lawyercloud.adapter.RegisterGoodAtQuickAdapter;
import com.mo.lawyercloud.base.BaseActivity;
import com.mo.lawyercloud.beans.BaseEntity;
import com.mo.lawyercloud.beans.SerializableMap;
import com.mo.lawyercloud.beans.apiBeans.ChannelBean;
import com.mo.lawyercloud.beans.apiBeans.RegisterResult;
import com.mo.lawyercloud.network.BaseObserver;
import com.mo.lawyercloud.network.RetrofitFactory;
import com.mo.lawyercloud.utils.MD5util;
import com.mo.lawyercloud.utils.NToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * Created by mo on 2018/5/14.
 *
 */

public class LowyerRegisterNextActivity extends BaseActivity {
    @BindView(R.id.bar_title)
    TextView mBarTitle;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.btn_register)
    Button mBtnRegister;

    private RegisterGoodAtQuickAdapter mQuickAdapter;
    private Map<String, String> mParams;
    private List<ChannelBean> selects = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_lowyer_register_next;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mBarTitle.setText("律师注册");
        Bundle bundle = getIntent().getExtras();
        SerializableMap serializableMap = (SerializableMap) bundle.get("data");
        mParams = serializableMap.getMap();
        
    }

    @Override
    public void initData() {
        Observable<BaseEntity<List<ChannelBean>>> observable = RetrofitFactory.getInstance().getChannels();
        observable.compose(this.<BaseEntity<List<ChannelBean>>>rxSchedulers()).subscribe(new BaseObserver<List<ChannelBean>>() {
            @Override
            protected void onHandleSuccess(List<ChannelBean> channelBeans, String msg) {
                initRecycle(channelBeans);
            }
        });
    }

    private void initRecycle(List<ChannelBean> channelBeans) {
        GridSpacingItemDecoration decoration = new GridSpacingItemDecoration(mContext, 2, 10, 10,
        false);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext,2));
        mRecyclerView.addItemDecoration(decoration);
        mQuickAdapter = new RegisterGoodAtQuickAdapter(channelBeans);
        mRecyclerView.setAdapter(mQuickAdapter);
        mQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                                 @Override
                                                 public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                                     mQuickAdapter.setIsSelected(position);
                                                     if (mQuickAdapter.isSelected.get(position)){
                                                         selects.add(mQuickAdapter.getData().get(position));
                                                     }else {
                                                         selects.remove(mQuickAdapter.getData().get(position));
                                                     }
                                                 }
                                             }
        );
    }

    @Override
    public void onEvent() {



    }

    @OnClick({R.id.bar_iv_back, R.id.btn_register, R.id.tv_protocol})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bar_iv_back:
                finish();
                break;
            case R.id.btn_register:
                if (selects.size()>0){
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < selects.size(); i++) {
                        if (i == 0){
                            sb.append(selects.get(i).getId());
                        }else {
                            sb.append(",").append(selects.get(i).getId());
                        }
                    }
                    String s = sb.toString();
                    mParams.put("channels",s);
                }
                register();

                break;
            case R.id.tv_protocol:
                // TODO: 2018/5/14 协议
                break;
        }
    }

    private void register() {
        Gson gson=new Gson();
        String strEntity = gson.toJson(mParams);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),strEntity);
        Observable<BaseEntity<RegisterResult>> observable = RetrofitFactory.getInstance().register(body);
        observable.compose(this.<BaseEntity<RegisterResult>>rxSchedulers()).subscribe(new BaseObserver<RegisterResult>() {
            @Override
            protected void onHandleSuccess(RegisterResult s, String msg) {
                finish();
            }

            @Override
            protected void onHandleError(int statusCode, String msg) {
                NToast.shortToast(mContext,msg );
            }
        });
    }
}
