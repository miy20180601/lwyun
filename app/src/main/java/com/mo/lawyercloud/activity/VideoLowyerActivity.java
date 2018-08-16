package com.mo.lawyercloud.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mo.lawyercloud.R;
import com.mo.lawyercloud.base.BaseActivity;
import com.mo.lawyercloud.base.Constant;
import com.mo.lawyercloud.beans.BaseEntity;
import com.mo.lawyercloud.beans.apiBeans.OrderTimeoutBean;
import com.mo.lawyercloud.network.BaseObserver;
import com.mo.lawyercloud.network.RetrofitFactory;
import com.mo.lawyercloud.utils.DlgMgr;
import com.mo.lawyercloud.utils.MessageObservable;
import com.mo.lawyercloud.utils.NToast;
import com.mo.lawyercloud.utils.SPUtil;
import com.mo.lawyercloud.utils.StatusObservable;
import com.mo.lawyercloud.utils.TimeUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tencent.TIMMessage;
import com.tencent.TIMUserProfile;
import com.tencent.av.sdk.AVRoomMulti;
import com.tencent.av.sdk.AVView;
import com.tencent.ilivesdk.ILiveCallBack;
import com.tencent.ilivesdk.ILiveConstants;
import com.tencent.ilivesdk.ILiveSDK;
import com.tencent.ilivesdk.adapter.CommonConstants;
import com.tencent.ilivesdk.core.ILiveLoginManager;
import com.tencent.ilivesdk.core.ILiveRoomManager;
import com.tencent.ilivesdk.view.AVRootView;
import com.tencent.livesdk.ILVCustomCmd;
import com.tencent.livesdk.ILVLiveConfig;
import com.tencent.livesdk.ILVLiveManager;
import com.tencent.livesdk.ILVLiveRoomOption;
import com.tencent.livesdk.ILVText;

import java.util.HashMap;
import java.util.List;
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
 * 律师视频
 */
public class VideoLowyerActivity extends BaseActivity implements ILVLiveConfig
        .ILVLiveMsgListener, ILiveLoginManager.TILVBStatusListener {

    @BindView(R.id.av_root_view)
    AVRootView avRootView;
    @BindView(R.id.iv_end_call)
    ImageView ivEndCall;
    @BindView(R.id.timer)
    Chronometer mTimer;

    @BindView(R.id.iv_camera)
    ImageView ivCamera;


    private int roomId;
    private String mUserName;
    private boolean isCameraOn = true;


    @Override
    public int getLayoutId() {
        return R.layout.activity_member_video;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        initPermission();
        roomId = getIntent().getIntExtra("roomId", -1);
        mUserName = getIntent().getStringExtra("userName");
        ivCamera.setSelected(isCameraOn);
        setAvRoomView();

        MessageObservable.getInstance().addObserver(this);
        StatusObservable.getInstance().addObserver(this);


        createRoom();
    }

    private void setAvRoomView() {
        avRootView.renderMySelf(true);
        String userName = (String) SPUtil.get(mContext, Constant.PHONE, "");
//        avRootView.bindIdAndView(0, AVView.VIDEO_SRC_TYPE_SCREEN,userName);
        avRootView.bindIdAndView(1, AVView.VIDEO_SRC_TYPE_CAMERA, userName);
        ILVLiveManager.getInstance().setAvVideoView(avRootView);
        avRootView.setLocalFullScreen(false);
        avRootView.setGravity(AVRootView.LAYOUT_GRAVITY_BOTTOM);
        avRootView.setSubMarginY(getResources().getDimensionPixelSize(R.dimen
                .small_area_margin_bottom));

        avRootView.setSubWidth(getResources().getDimensionPixelSize(R.dimen.small_area_width));
        avRootView.setSubHeight(getResources().getDimensionPixelSize(R.dimen.small_area_height));
        avRootView.setAutoOrientation(false);
        // 打开摄像头预览
//        ILiveRoomManager.getInstance().enableCamera(ILiveConstants.FRONT_CAMERA, true);
//        avRootView.setSubCreatedListener(new AVRootView.onSubViewCreatedListener() {
//            @Override
//            public void onSubViewCreated() {
//                avRootView.renderVideoView(true, ILiveLoginManager.getInstance().getMyUserId(), CommonConstants.Const_VideoType_Camera, true);
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

    private void pushMessage() {
        Map<String, Object> params = new HashMap<>();
        params.put("alias", mUserName);
        params.put("alert", "律师邀请视频");
        params.put("content", "你的预约时间开始了，律师正在发起视频");
        Gson gson = new Gson();
        String strEntity = gson.toJson(params);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;" +
                "charset=UTF-8"), strEntity);
        Observable<BaseEntity<Object>> observable = RetrofitFactory.getInstance()
                .pushMessageToOne(body);
        observable.compose(this.<BaseEntity<Object>>rxSchedulers()).subscribe(new BaseObserver<Object>() {

            @Override
            protected void onHandleSuccess(Object o, String msg) {

            }
        });
    }


    CountDownTimer mDownTimer = new CountDownTimer(60 * 1000 * 60 * 24, 1000 * 60) {
        @Override
        public void onTick(long millisUntilFinished) {
            videoOrderTimeout();
        }


        @Override
        public void onFinish() {

        }
    };

    private void videoOrderTimeout() {
        Observable<BaseEntity<List<OrderTimeoutBean>>> observable = RetrofitFactory
                .getInstance().orderTimeout();
        observable.compose(this.<BaseEntity<List<OrderTimeoutBean>>>rxSchedulers()).subscribe(
                new BaseObserver<List<OrderTimeoutBean>>() {

                    @Override
                    protected void onHandleSuccess(List<OrderTimeoutBean> datas, String msg) {
                        if (datas != null && datas.size() > 0) {
                            for (OrderTimeoutBean data : datas) {
                                if (data.getId() == roomId) {
                                    videoOrderEnd();
                                }
                            }

                        }
                    }
                });
    }

    // 创建房间
    private void createRoom() {

        ILVLiveRoomOption option = new ILVLiveRoomOption(ILiveLoginManager.getInstance().getMyUserId())
                .autoCamera(true)
//                .videoRecvMode(AVRoomMulti.VIDEO_RECV_MODE_SEMI_AUTO_RECV_CAMERA_VIDEO) //是否开始半自动接收
                .videoMode(ILiveConstants.VIDEOMODE_NORMAL)
                .controlRole(Constant.ROLE_MASTER)
                .autoMic(true)
                .autoFocus(true);
        ILVLiveManager.getInstance().createRoom(roomId, option, new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {

                videoOrderStart();
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                DlgMgr.showMsg(mContext, "create failed:" + module + "|" + errCode + "|" + errMsg);
                finish();

            }
        });
    }

    private void showChoiceDlg() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("房间已存在，是否加入房间？")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        joinRoom();
                        dialogInterface.dismiss();
                    }
                });
        DlgMgr.showAlertDlg(this, builder);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onEvent() {
        ivEndCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
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
//                mDownTimer.cancel();
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
//                        mDownTimer.start();
                pushMessage();
            }

            @Override
            protected void onHandleError(int statusCode, String msg) {
                super.onHandleError(statusCode, msg);
                NToast.shortToast(mContext, msg);
                finish();
            }
        });
    }

    private void videoOrderEnd() {
        Map<String, Object> params = new HashMap<>();
        params.put("id", roomId);
        Gson gson = new Gson();
        String strEntity = gson.toJson(params);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), strEntity);
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
//        mDownTimer.cancel();
        MessageObservable.getInstance().deleteObserver(this);
        StatusObservable.getInstance().deleteObserver(this);
        ILVLiveManager.getInstance().onDestory();
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

}
