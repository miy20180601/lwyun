package com.mo.lawyercloud.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mo.lawyercloud.activity.MainActivity;
import com.mo.lawyercloud.beans.BaseEntity;
import com.mo.lawyercloud.beans.apiBeans.MemberBean;
import com.mo.lawyercloud.beans.apiBeans.RegisterResult;
import com.mo.lawyercloud.network.BaseObserver;
import com.mo.lawyercloud.network.RetrofitFactory;
import com.mo.lawyercloud.utils.ACache;
import com.mo.lawyercloud.utils.CommonUtils;
import com.mo.lawyercloud.utils.MD5util;
import com.mo.lawyercloud.utils.NToast;
import com.mo.lawyercloud.utils.SPUtil;
import com.orhanobut.logger.Logger;
import com.tencent.ilivesdk.ILiveCallBack;
import com.tencent.ilivesdk.core.ILiveLoginManager;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

/**
 * Created by mo on 2018/5/10.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public Context mContext;

    protected ACache mACache;//缓存类，类似于sharepreference

    /**
     * 设置布局方法
     */
    public abstract int getLayoutId();
    /**初始化方法*/
    public abstract void initViews(Bundle savedInstanceState);
    /**初始化数据*/
    public abstract void initData();

    public abstract void onEvent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        mContext = this;
        mACache = ACache.get(this);
        initViews(savedInstanceState);
        initData();
        onEvent();
    }

    /**
     * [页面跳转]
     *
     * @param clz
     */
    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    /**
     * [携带数据的页面跳转]
     *
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**rxjava线程调度*/
    public <T> ObservableTransformer<T,T> rxSchedulers() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> observable) {
                return observable
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                if (!CommonUtils.isNetworkConnected(mContext)) {
                                    Toast.makeText(mContext, "网络连接断开，请检查网络设置", Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    protected void login(final String phone, String pwd) {
        Map<String, String> params = new HashMap<>();
        params.put("username", phone);
        params.put("password", pwd);
        Gson gson=new Gson();
        String strEntity = gson.toJson(params);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),strEntity);
        Observable<BaseEntity<RegisterResult>> observable = RetrofitFactory.getInstance().login(body);
        observable.compose(this.<BaseEntity<RegisterResult>>rxSchedulers()).subscribe(new BaseObserver<RegisterResult>() {
            @Override
            protected void onHandleSuccess(RegisterResult registerResult, String msg) {
                getMemberInfo();
                txLogin(phone,registerResult.getTxSig());
            }

            @Override
            protected void onHandleError(int statusCode, String msg) {
                NToast.shortToast(mContext,msg);
            }
        });
    }

    /*腾讯视频登录*/
    private void txLogin(String phone, String txSig) {
        ILiveLoginManager.getInstance().iLiveLogin(phone, txSig, new ILiveCallBack() {
                    @Override
                    public void onSuccess(Object data) {
                        Logger.d("tx登录成功");
                    }

                    @Override
                    public void onError(String module, int errCode, String errMsg) {
                        Logger.d(module + "|login fail " + errCode + " " + errMsg, Toast.LENGTH_SHORT);
                    }
                });
    }

    protected void getMemberInfo() {
        Observable<BaseEntity<MemberBean>> observable = RetrofitFactory.getInstance()
                .getUserInfo();
        observable.compose(this.<BaseEntity<MemberBean>>rxSchedulers()).subscribe(new BaseObserver<MemberBean>() {

            @Override
            protected void onHandleSuccess(MemberBean memberBean, String msg) {
                mACache.put(Constant.MEMBER_INFO,memberBean);
            }
        });
    }


}
