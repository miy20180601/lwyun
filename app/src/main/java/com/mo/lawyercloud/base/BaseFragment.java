package com.mo.lawyercloud.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mo.lawyercloud.utils.CommonUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by mo on 2018/5/10.
 */

public abstract class BaseFragment extends Fragment {

    protected View mRootView;
    private Unbinder unbinder;
    protected Context mContext;

    public abstract int getLayoutId();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView =inflater.inflate(getLayoutId(),container,false);
        unbinder = ButterKnife.bind(this,mRootView);//绑定framgent
        mContext = getActivity();
        return mRootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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

}