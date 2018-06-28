package com.mo.lawyercloud.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mo.lawyercloud.R;
import com.mo.lawyercloud.adapter.ImageQuickAdapter;
import com.mo.lawyercloud.adapter.ReserveTimeQuickAdapter;
import com.mo.lawyercloud.adapter.itemDecoration.GridSpacingItemDecoration;
import com.mo.lawyercloud.adapter.ReserveChannelQuickAdapter;
import com.mo.lawyercloud.adapter.itemDecoration.UniversalItemDecoration;
import com.mo.lawyercloud.base.BaseActivity;
import com.mo.lawyercloud.base.Constant;
import com.mo.lawyercloud.beans.BaseEntity;
import com.mo.lawyercloud.beans.apiBeans.BaseListEntity;
import com.mo.lawyercloud.beans.apiBeans.ChannelBean;
import com.mo.lawyercloud.beans.apiBeans.FeeDescriptionlBean;
import com.mo.lawyercloud.beans.apiBeans.ReserveTimeBean;
import com.mo.lawyercloud.beans.apiBeans.UploadFileBean;
import com.mo.lawyercloud.network.BaseObserver;
import com.mo.lawyercloud.network.RetrofitFactory;
import com.mo.lawyercloud.utils.Base64Util;
import com.mo.lawyercloud.utils.NToast;
import com.mo.lawyercloud.utils.PhotoUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.iwf.photopicker.PhotoPicker;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;

/**
 * @author CUI
 * @data 2018/06/08
 * @details 视频预约界面
 */
public class VideoAppointmentAcitivty extends BaseActivity {
    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.bar_tv_right)
    TextView barTvRight;
    @BindView(R.id.tv_fee_descriptionl)
    TextView mTvFeeDescriptionl;
    @BindView(R.id.et_appointment_conetnt)
    EditText etAppointmentConetnt;
    @BindView(R.id.iv_appointment_accessory)
    ImageView ivAppointmentAccessory;
    @BindView(R.id.rv_appointment_time)
    RecyclerView rvAppointmentTime;
    @BindView(R.id.bt_appointment_submit)
    Button btAppointmentSubmit;
    @BindView(R.id.rl_appointment_succeed)
    RelativeLayout rl_appointment_succeed;
    @BindView(R.id.recycler_channel)
    RecyclerView mRecyclerChannel;
    @BindView(R.id.recycler_img)
    RecyclerView mRecyclerImg;


    private int mSolicitorId;
    private ReserveChannelQuickAdapter mChannelQuickAdapter;
    private int mSelectChannel = -1;
    private List<UploadFileBean> mUploadFileBeans = new ArrayList<>();
    private ImageQuickAdapter mImageQuickAdapter;
    private ReserveTimeQuickAdapter mTimeQuickAdapter;
    private ReserveTimeBean mSelectTimeBean;

    private static final int REQUEST_FILE = 0x11;

    @Override
    public int getLayoutId() {
        return R.layout.activity_video_appointment_acitivty;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        barTitle.setText("视频预约");
        rl_appointment_succeed.setVisibility(View.GONE);
        mSolicitorId = getIntent().getIntExtra("id", 0);
        Observable<BaseEntity<FeeDescriptionlBean>> observable = RetrofitFactory
                .getInstance().feeDescription();
        observable.compose(this.<BaseEntity<FeeDescriptionlBean>>rxSchedulers()).subscribe(new BaseObserver<FeeDescriptionlBean>() {

            @Override
            protected void onHandleSuccess(FeeDescriptionlBean feeDescriptionlBean, String msg) {
                mTvFeeDescriptionl.setText(feeDescriptionlBean.getContent());
            }
        });
        initRecycleView();
    }


    private void initRecycleView() {
        List<ChannelBean> channels = new ArrayList<>();
        mChannelQuickAdapter = new ReserveChannelQuickAdapter(channels);
        mRecyclerChannel.addItemDecoration(new UniversalItemDecoration() {
            @Override
            public Decoration getItemOffsets(int position) {
                ColorDecoration decoration = new ColorDecoration();
                //你的逻辑设置分割线
                decoration.bottom = 1;
                decoration.right = 1;
                decoration.left = 1;
                decoration.top = 1;
                decoration.decorationColor = Color.parseColor("#e6e6e6");
                return decoration;
            }
        });
        mRecyclerChannel.setLayoutManager(new GridLayoutManager(mContext, 4));
        mRecyclerChannel.setAdapter(mChannelQuickAdapter);

        //附件
        mImageQuickAdapter = new ImageQuickAdapter(mUploadFileBeans);
        mRecyclerImg.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager
                .HORIZONTAL, false));
        mRecyclerImg.setAdapter(mImageQuickAdapter);

        //时间
        List<ReserveTimeBean> timeList = new ArrayList<>();
        mTimeQuickAdapter = new ReserveTimeQuickAdapter(timeList);
        rvAppointmentTime.addItemDecoration(new UniversalItemDecoration() {
            @Override
            public Decoration getItemOffsets(int position) {
                ColorDecoration decoration = new ColorDecoration();
                //你的逻辑设置分割线
                decoration.bottom = 1;
                decoration.right = 1;
                decoration.left = 1;
                decoration.top = 1;
                decoration.decorationColor = Color.parseColor("#e6e6e6");
                return decoration;
            }
        });
        rvAppointmentTime.setLayoutManager(new GridLayoutManager(mContext, 3));
        rvAppointmentTime.setAdapter(mTimeQuickAdapter);

    }

    @Override
    public void initData() {
        getChannels();
        reserveTimeLIst();
    }

    private void getChannels() {
        Observable<BaseEntity<List<ChannelBean>>> observable = RetrofitFactory.getInstance()
                .getChannels();
        observable.compose(this.<BaseEntity<List<ChannelBean>>>rxSchedulers()).subscribe(new BaseObserver<List<ChannelBean>>() {
            @Override
            protected void onHandleSuccess(List<ChannelBean> channelBeans, String msg) {
                mChannelQuickAdapter.setNewData(channelBeans);
            }
        });
    }

    private void reserveTimeLIst() {
        Observable<BaseEntity<BaseListEntity<ReserveTimeBean>>> observable =
                RetrofitFactory.getInstance().reserveTimeList(mSolicitorId, null, null);
        observable.compose(this.<BaseEntity<BaseListEntity<ReserveTimeBean>>>rxSchedulers())
                .subscribe(new BaseObserver<BaseListEntity<ReserveTimeBean>>() {
                    @Override
                    protected void onHandleSuccess(BaseListEntity<ReserveTimeBean> dataList, String msg) {
                        mTimeQuickAdapter.setNewData(dataList.getResult());
                    }
                });
    }


    @Override
    public void onEvent() {
        mChannelQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ChannelBean channelBean = mChannelQuickAdapter.getData().get(position);
                mSelectChannel = channelBean.getId();
                mChannelQuickAdapter.setSelectId(channelBean.getId());
            }
        });
        mImageQuickAdapter.setOnItemChildClickListener(new BaseQuickAdapter
                .OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_delete:
                        mUploadFileBeans.remove(position);
                        mImageQuickAdapter.setNewData(mUploadFileBeans);
                        break;
                }
            }
        });
        mTimeQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mSelectTimeBean = mTimeQuickAdapter.getData().get(position);
                mTimeQuickAdapter.setSelectId(mSelectTimeBean.getId());
            }
        });

    }


    @OnClick({R.id.bar_iv_back, R.id.iv_appointment_accessory, R.id.bt_appointment_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bar_iv_back:
                finish();
                break;
            case R.id.iv_appointment_accessory:
                initPermission();
//                startActivityForResult(new Intent(mContext,FolderActivity.class),REQUEST_FILE);
                PhotoPicker.builder()
                        .setPhotoCount(3)
                        .setShowCamera(false)
                        .setShowGif(false)
                        .setPreviewEnabled(true)
                        .start(this, PhotoPicker.REQUEST_CODE);
                break;
            case R.id.bt_appointment_submit:
                if (mSelectChannel == -1) {
                    NToast.shortToast(mContext, "请选择预定的类型！");
                    return;
                }
                if (mSelectTimeBean == null) {
                    NToast.shortToast(mContext, "请选择预约时间！");
                    return;
                }
                reserveSubmit();
                break;
        }
    }

    private void reserveSubmit() {
        Map<String, Object> params = new HashMap<>();
        Gson gson = new Gson();
        String content = etAppointmentConetnt.getText().toString().trim();
        if (!TextUtils.isEmpty(content))
            params.put("problem", content);
        //预约类型
        Map<String, String> channel = new HashMap<>();
        channel.put("id",mSelectChannel+"");
        params.put("channel",channel);
        //预约时间
        Map<String, String> timeMap = new HashMap<>();
        timeMap.put("id",mSelectTimeBean.getId()+"");
        params.put("timeMsg",timeMap);
        //律师id
        Map<String, String> solicitorMap = new HashMap<>();
        solicitorMap.put("id", mSolicitorId+ "");
        params.put("solicitor", solicitorMap);

        //附件
        if (mUploadFileBeans!=null&&mUploadFileBeans.size()>0){
            ArrayList<Map<String, String>> arrayList = new ArrayList<>();
            for (UploadFileBean uploadFileBean : mUploadFileBeans) {
                HashMap<String, String> map = new HashMap<>();
                map.put("image",uploadFileBean.getName());
                arrayList.add(map);
            }
            params.put("attachments", arrayList);
        }

        String strEntity = gson.toJson(params);

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;" +
                "charset=UTF-8"), strEntity);

        Observable<BaseEntity<Object>> observable = RetrofitFactory.getInstance()
                .reserveSubmit(body);
        observable.compose(this.<BaseEntity<Object>>rxSchedulers()).subscribe(new BaseObserver<Object>() {

            @Override
            protected void onHandleSuccess(Object o, String msg) {
                NToast.shortToast(mContext, msg);
                startActivity(MainActivity.class);
            }

            @Override
            protected void onHandleError(int statusCode, String msg) {
                super.onHandleError(statusCode, msg);
                NToast.shortToast(mContext,msg);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                ArrayList<String> photos = data.getStringArrayListExtra(PhotoPicker
                        .KEY_SELECTED_PHOTOS);
                for (String photo : photos) {
                    compressWithRx(new File(photo));
                }

            }
        }
    }

    /**
     * 压缩图片
     */
    @SuppressLint("CheckResult")
    private void compressWithRx(File file) {
        Flowable.just(file)
                .observeOn(Schedulers.io())
                .map(new Function<File, File>() {
                    @Override
                    public File apply(@NonNull File file) throws Exception {
                        return Luban.with(mContext).load(file).get();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<File>() {
                    @Override
                    public void accept(@NonNull File file) throws Exception {
                        updateImage(file.getPath());
                    }
                });
    }

    /**
     * 上传图片
     */
    private void updateImage(final String path) {
        String imageToBase64 = Base64Util.imageToBase64(path);
        Map<String, String> params = new HashMap<>();
        params.put("data", imageToBase64);
        Gson gson = new Gson();
        String strEntity = gson.toJson(params);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;" +
                "charset=UTF-8"), strEntity);
        Observable<BaseEntity<UploadFileBean>> observable = RetrofitFactory.getInstance()
                .uploadImage(body);
        observable.compose(this.<BaseEntity<UploadFileBean>>rxSchedulers()).subscribe(new BaseObserver<UploadFileBean>() {

            @Override
            protected void onHandleSuccess(UploadFileBean uploadFileBean, String msg) {
                mUploadFileBeans.add(uploadFileBean);
                mImageQuickAdapter.setNewData(mUploadFileBeans);
            }
        });
    }

    @SuppressLint("CheckResult")
    private void initPermission() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.CAMERA
                , Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.READ_EXTERNAL_STORAGE)
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
}
