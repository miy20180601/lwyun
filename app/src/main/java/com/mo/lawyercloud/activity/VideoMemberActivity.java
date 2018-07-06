package com.mo.lawyercloud.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.mo.lawyercloud.R;
import com.mo.lawyercloud.base.BaseActivity;
import com.mo.lawyercloud.base.Constant;
import com.mo.lawyercloud.beans.BaseEntity;
import com.mo.lawyercloud.network.BaseObserver;
import com.mo.lawyercloud.network.RetrofitFactory;
import com.mo.lawyercloud.utils.MessageObservable;
import com.mo.lawyercloud.utils.NToast;
import com.mo.lawyercloud.utils.SPUtil;
import com.mo.lawyercloud.utils.StatusObservable;
import com.orhanobut.logger.Logger;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tencent.TIMMessage;
import com.tencent.TIMUserProfile;
import com.tencent.av.sdk.AVView;
import com.tencent.ilivesdk.ILiveCallBack;
import com.tencent.ilivesdk.ILiveConstants;
import com.tencent.ilivesdk.ILiveSDK;
import com.tencent.ilivesdk.core.ILiveLoginManager;
import com.tencent.ilivesdk.core.ILiveRoomManager;
import com.tencent.ilivesdk.view.AVRootView;
import com.tencent.livesdk.ILVCustomCmd;
import com.tencent.livesdk.ILVLiveConfig;
import com.tencent.livesdk.ILVLiveManager;
import com.tencent.livesdk.ILVLiveRoomOption;
import com.tencent.livesdk.ILVText;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Mohaifeng on 18/6/12.
 * 用户视频
 */
public class VideoMemberActivity extends BaseActivity implements ILVLiveConfig
        .ILVLiveMsgListener, ILiveLoginManager.TILVBStatusListener {

    @BindView(R.id.av_root_view)
    AVRootView avRootView;
    @BindView(R.id.timer)
    Chronometer mTimer;
    @BindView(R.id.iv_camera)
    ImageView ivCamera;

    private int roomId;
    private String hostID;
    private long callDurationc = 0;
    private boolean isCameraOn = true;

    @Override
    public int getLayoutId() {
        return R.layout.activity_member_video;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        initPermission();
        roomId = getIntent().getIntExtra("roomId", -1);
        hostID = getIntent().getStringExtra("hostID");
        //        avRootView = findViewById(R.id.av_root_view);
        ivCamera.setSelected(isCameraOn);
        setAvRoomView();

        MessageObservable.getInstance().addObserver(this);
        StatusObservable.getInstance().addObserver(this);


        joinRoom();
    }

    private void setAvRoomView() {
        avRootView.renderMySelf(true);
        String userName = (String) SPUtil.get(mContext, Constant.PHONE, "");
        //        avRootView.bindIdAndView(0, AVView.VIDEO_SRC_TYPE_SCREEN,hostID);
        avRootView.bindIdAndView(1, AVView.VIDEO_SRC_TYPE_CAMERA, userName);
        ILVLiveManager.getInstance().setAvVideoView(avRootView);
        avRootView.setLocalFullScreen(false);
        avRootView.setGravity(AVRootView.LAYOUT_GRAVITY_BOTTOM);
        avRootView.setSubMarginY(getResources().getDimensionPixelSize(R.dimen
                .small_area_margin_bottom));
        //        avRootView.setSubMarginX(getResources().getDimensionPixelSize(R.dimen
        // .small_area_marginright));
        //        avRootView.setSubPadding(getResources().getDimensionPixelSize(R.dimen
        // .small_area_marginbetween));
        avRootView.setSubWidth(getResources().getDimensionPixelSize(R.dimen.small_area_width));
        avRootView.setSubHeight(getResources().getDimensionPixelSize(R.dimen.small_area_height));
        //        avRootView.setSubCreatedListener(new AVRootView.onSubViewCreatedListener() {
        //            @Override
        //            public void onSubViewCreated() {
        //                avRootView.swapVideoView(0,1);
        //                avRootView.getViewByIndex(0).getIdentifier();
        //            }
        //        });
    }

    @SuppressLint("CheckResult")
    private void initPermission() {

        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.CAMERA
                , Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.WAKE_LOCK
                , Manifest.permission.MODIFY_AUDIO_SETTINGS, Manifest.permission.RECORD_AUDIO)
                .subscribe(new Consumer<Boolean>() {
                               @Override
                               public void accept(Boolean aBoolean) throws Exception {
                                   if (aBoolean) {
                                       // All requested permissions are granted
                                   } else {
                                       // At least one permission is denied
                                   }
                               }
                           }
                );
    }

    // 加入房间
    private void joinRoom() {

        ILVLiveRoomOption option = new ILVLiveRoomOption(ILiveLoginManager.getInstance()
                .getMyUserId())
                .controlRole(Constant.ROLE_LIVEGUEST)
                .videoMode(ILiveConstants.VIDEOMODE_NORMAL)
                .autoFocus(true);
        ILVLiveManager.getInstance().joinRoom(roomId, option, new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {
                videoOrderStart();
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                Logger.d("create failed:" + module + "|" + errCode + "|" + errMsg);
                finish();
                //                DlgMgr.showMsg(mContext, "create failed:" + module + "|" +
                // errCode + "|" + errMsg);
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void onEvent() {
    }

    private void showDialog() {
        new AlertDialog.Builder(mContext).setMessage("确定退出直播吗？")
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        quitLiveVideo();
                    }
                }).setPositiveButton("取消", null).show();
    }

    private void quitLiveVideo() {
        ILiveSDK.getInstance().getAvVideoCtrl().setLocalVideoPreProcessCallback(null);
        ILVLiveManager.getInstance().quitRoom(new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {
                mTimer.stop();
                videoOrderEnd();
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {

            }
        });
    }

    private void videoOrderStart() {
        Map<String, Object> params = new HashMap<>();
        params.put("id", roomId);
        Gson gson = new Gson();
        String strEntity = gson.toJson(params);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),
                strEntity);
        Observable<BaseEntity<Object>> observable = RetrofitFactory
                .getInstance().videoOrderStart(body);
        observable.compose(this.<BaseEntity<Object>>rxSchedulers()).subscribe(new BaseObserver<Object>() {
            @Override
            protected void onHandleSuccess(Object o, String msg) {
                mTimer.setBase(SystemClock.elapsedRealtime());//计时器清零
                int hour = (int) ((SystemClock.elapsedRealtime() - mTimer.getBase()) / 1000 / 60);
                mTimer.setFormat("0" + String.valueOf(hour) + ":%s");
                mTimer.start();
            }

            @Override
            protected void onHandleError(int statusCode, String msg) {
                super.onHandleError(statusCode, msg);
                NToast.shortToast(mContext, msg);
            }
        });
    }

    private void videoOrderEnd() {
        Map<String, Object> params = new HashMap<>();
        params.put("id", roomId);
        Gson gson = new Gson();
        String strEntity = gson.toJson(params);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),
                strEntity);
        Observable<BaseEntity<Object>> observable = RetrofitFactory
                .getInstance().videoOrderEnd(body);
        observable.compose(this.<BaseEntity<Object>>rxSchedulers()).subscribe(new BaseObserver<Object>() {
            @Override
            protected void onHandleSuccess(Object o, String msg) {
                finish();
            }

            @Override
            protected void onHandleError(int statusCode, String msg) {
                super.onHandleError(statusCode, msg);
                NToast.shortToast(mContext, msg);
                finish();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        ILVLiveManager.getInstance().onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ILVLiveManager.getInstance().onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTimer.stop();
        MessageObservable.getInstance().deleteObserver(this);
        StatusObservable.getInstance().deleteObserver(this);
        ILVLiveManager.getInstance().onDestory();
    }


    @Override
    public void onForceOffline(int error, String message) {
        finish();
    }

    @Override
    public void onNewTextMsg(ILVText text, String SenderId, TIMUserProfile userProfile) {

    }

    @Override
    public void onNewCustomMsg(ILVCustomCmd cmd, String id, TIMUserProfile userProfile) {

    }

    @Override
    public void onNewOtherMsg(TIMMessage message) {

    }



    @OnClick({R.id.iv_camera, R.id.iv_end_call})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_camera:
                isCameraOn = !isCameraOn;
                ILiveRoomManager.getInstance().enableCamera(ILiveRoomManager.getInstance().getCurCameraId(),
                        isCameraOn);
                ivCamera.setSelected(isCameraOn);
                break;
            case R.id.iv_end_call:
                showDialog();
                break;
        }
    }
}
