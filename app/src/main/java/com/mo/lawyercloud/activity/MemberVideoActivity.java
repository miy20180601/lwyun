package com.mo.lawyercloud.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;

import com.mo.lawyercloud.R;
import com.mo.lawyercloud.base.BaseActivity;
import com.mo.lawyercloud.base.Constant;
import com.mo.lawyercloud.beans.apiBeans.MemberBean;
import com.mo.lawyercloud.utils.DlgMgr;
import com.mo.lawyercloud.utils.MessageObservable;
import com.mo.lawyercloud.utils.SPUtil;
import com.mo.lawyercloud.utils.StatusObservable;
import com.orhanobut.logger.Logger;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tencent.TIMMessage;
import com.tencent.TIMUserProfile;
import com.tencent.av.sdk.AVView;
import com.tencent.ilivesdk.ILiveCallBack;
import com.tencent.ilivesdk.ILiveConstants;
import com.tencent.ilivesdk.core.ILiveLoginManager;
import com.tencent.ilivesdk.view.AVRootView;
import com.tencent.livesdk.ILVCustomCmd;
import com.tencent.livesdk.ILVLiveConfig;
import com.tencent.livesdk.ILVLiveManager;
import com.tencent.livesdk.ILVLiveRoomOption;
import com.tencent.livesdk.ILVText;

import io.reactivex.functions.Consumer;

/**
 * Created by Mohaifeng on 18/6/12.
 */
public class MemberVideoActivity extends BaseActivity implements ILVLiveConfig.ILVLiveMsgListener, ILiveLoginManager.TILVBStatusListener{

    AVRootView avRootView;
    private int roomId;
    private String hostID;

    @Override
    public int getLayoutId() {
        return R.layout.activity_member_video;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        initPermission();
        roomId = getIntent().getIntExtra("roomId", -1);
        hostID = getIntent().getStringExtra("hostID");
        avRootView = findViewById(R.id.av_root_view);
        avRootView.renderMySelf(true);
        String userName  = (String) SPUtil.get(mContext, Constant.PHONE, "");
        avRootView.bindIdAndView(1, AVView.VIDEO_SRC_TYPE_SCREEN,hostID);
        avRootView.bindIdAndView(3, AVView.VIDEO_SRC_TYPE_CAMERA,userName);
        ILVLiveManager.getInstance().setAvVideoView(avRootView);
        avRootView.setLocalFullScreen(false);
        avRootView.setGravity(AVRootView.LAYOUT_GRAVITY_BOTTOM);
//        avRootView.setSubMarginY(getResources().getDimensionPixelSize(R.dimen.small_area_margin_top));
//        avRootView.setSubMarginX(getResources().getDimensionPixelSize(R.dimen.small_area_marginright));
//        avRootView.setSubPadding(getResources().getDimensionPixelSize(R.dimen.small_area_marginbetween));
//        avRootView.setSubWidth(getResources().getDimensionPixelSize(R.dimen.small_area_width));
//        avRootView.setSubHeight(getResources().getDimensionPixelSize(R.dimen.small_area_height));
//        avRootView.setSubCreatedListener(new AVRootView.onSubViewCreatedListener() {
//            @Override
//            public void onSubViewCreated() {
//                avRootView.swapVideoView(0,1);
//                avRootView.getViewByIndex(0).getIdentifier();
//            }
//        });

        MessageObservable.getInstance().addObserver(this);
        StatusObservable.getInstance().addObserver(this);


        joinRoom();
    }

    @SuppressLint("CheckResult")
    private void initPermission() {

        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.CAMERA
                , Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.WAKE_LOCK
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
    private void joinRoom(){

        ILVLiveRoomOption option = new ILVLiveRoomOption(ILiveLoginManager.getInstance().getMyUserId())
                .controlRole(Constant.ROLE_LIVEGUEST)
                .videoMode(ILiveConstants.VIDEOMODE_NORMAL)
                .autoFocus(true);
        ILVLiveManager.getInstance().joinRoom(roomId, option, new ILiveCallBack() {
                    @Override
                    public void onSuccess(Object data) {

                    }

                    @Override
                    public void onError(String module, int errCode, String errMsg) {
                        DlgMgr.showMsg(mContext, "create failed:"+module+"|"+errCode+"|"+errMsg);
                    }
                });
    }

    @Override
    public void initData() {

    }

    @Override
    public void onEvent() {

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

}
