package com.mo.lawyercloud.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.mo.lawyercloud.R;
import com.mo.lawyercloud.base.BaseActivity;
import com.mo.lawyercloud.base.Constant;
import com.mo.lawyercloud.beans.BaseEntity;
import com.mo.lawyercloud.beans.ProvinceBean;
import com.mo.lawyercloud.beans.apiBeans.MemberBean;
import com.mo.lawyercloud.beans.apiBeans.UploadFileBean;
import com.mo.lawyercloud.network.BaseObserver;
import com.mo.lawyercloud.network.RetrofitFactory;
import com.mo.lawyercloud.utils.Base64Util;
import com.mo.lawyercloud.utils.GetJsonDataUtil;
import com.mo.lawyercloud.utils.NToast;
import com.mo.lawyercloud.utils.PhotoUtils;
import com.mo.lawyercloud.widget.ActionSheetDialog;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.json.JSONArray;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;

/**
 * @author CUI
 * @data 2018/06/08
 * @details 律师我的资料界面
 */
public class MineLwyerActivity extends BaseActivity {
    @BindView(R.id.bar_iv_back)
    ImageView barIvBack;
    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.bar_tv_right)
    TextView barTvRight;


    @BindView(R.id.civ_lwyer_avatar)
    CircleImageView civLwyerAvatar;
    @BindView(R.id.tv_lwyer_phone)
    TextView tvLwyerPhone;
    @BindView(R.id.tv_lwyer_nickname)
    TextView tvLwyerNickname;
    @BindView(R.id.tv_lwyer_sex)
    TextView tvLwyerSex;
    @BindView(R.id.tv_lwyer_corporation)
    TextView tvLwyerCorporation;
    @BindView(R.id.tv_lwyer_address)
    TextView tvLwyerAddress;
    @BindView(R.id.tv_lwyer_synopsis)
    TextView tv_lwyer_synopsis;

    private PhotoUtils photoUtils;
    private MemberBean mMember;
    private String mName;
    private String mCompany;
    private String mResume;

    private final static int MODIFY_NAME = 0X11;
    private final static int MODIFY_SEX_FEMALE= 0X22;
    private final static int MODIFY_SEX_MALE= 0X44;
    private final static int MODIFY_LOCATION = 0X33;
    private final static int MODIFY_COMPANY = 0X55;
    private final static int MODIFY_RESUME = 0X66;

    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private ArrayList<ProvinceBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private OptionsPickerView pvOptions;
    private String mLocation;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_lwyer;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        barTitle.setText("我的资料");
        setPortraitChangeListener();
        mMember = (MemberBean) mACache.getAsObject(Constant.MEMBER_INFO);
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
        if (mMember != null){

            intoImg(mMember.getAvatar(),civLwyerAvatar);
            tvLwyerPhone.setText(mMember.getMobile());
            tvLwyerNickname.setText(mMember.getNickname() == null? "":mMember.getNickname());
            tvLwyerSex.setText(mMember.getGender() == 1?"男":"女");
            tvLwyerAddress.setText(mMember.getLocation());
            tvLwyerCorporation.setText(mMember.getCompany());
            tv_lwyer_synopsis.setText(mMember.getResume());
        }

    }
    public void intoImg(String imgUrl,ImageView view){
        RequestOptions options = new RequestOptions();
        options.error(R.mipmap.data_button_avatar_n);
        Glide.with(mContext).load(imgUrl).apply(options).into(view);
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 子线程中解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;

                case MSG_LOAD_SUCCESS:
                    initOptionPicker();
                    break;

                case MSG_LOAD_FAILED:
                    Toast.makeText(mContext, "Parse Failed", Toast.LENGTH_SHORT).show();
                    break;
                case MODIFY_NAME:
                    tvLwyerNickname.setText(mName);
                    mMember.setNickname(mName);
                    mACache.put(Constant.MEMBER_INFO,mMember);
                    break;
                case MODIFY_COMPANY:
                    tvLwyerCorporation.setText(mCompany);
                    mMember.setCompany(mCompany);
                    mACache.put(Constant.MEMBER_INFO,mMember);
                    break;
                case MODIFY_RESUME:
                    tv_lwyer_synopsis.setText(mResume);
                    mMember.setResume(mResume);
                    mACache.put(Constant.MEMBER_INFO,mMember);
                    break;
                case MODIFY_SEX_MALE:
                    tvLwyerSex.setText("男");
                    mMember.setGender(1);
                    mACache.put(Constant.MEMBER_INFO,mMember);
                    break;
                case MODIFY_SEX_FEMALE:
                    mMember.setGender(2);
                    mACache.put(Constant.MEMBER_INFO,mMember);
                    break;
                case MODIFY_LOCATION:
                    tvLwyerAddress.setText(mLocation);
                    mMember.setLocation(mLocation);
                    mACache.put(Constant.MEMBER_INFO,mMember);
                    break;

            }
        }
    };
    @Override
    public void initData() {

    }

    @Override
    public void onEvent() {

    }
    private void initOptionPicker() {//条件选择器初始化
        /**
         * 注意 ：如果是三级联动的数据(省市区等)，请参照 JsonDataActivity 类里面的写法。
         */

        pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                /* + options3Items.get(options1).get(options2).get(options3).getPickerViewText()*/
                mLocation = options1Items.get(options1).getPickerViewText()
                        + options2Items.get(options1).get(options2);
                Map<String, String> params = new HashMap<>();
                params.put("location", mLocation);
                modifyInfo(MODIFY_LOCATION,params);
            }
        })
                .setTitleText("城市选择")
                .setContentTextSize(20)//设置滚轮文字大小
                .setDividerColor(Color.LTGRAY)//设置分割线的颜色
                .setSelectOptions(0, 1)//默认选中项
                .setBgColor(Color.WHITE)
                .setTitleBgColor(Color.WHITE)
                .setTitleColor(Color.LTGRAY)
                .setCancelColor(Color.parseColor("#0188ff"))
                .setSubmitColor(Color.parseColor("#0188ff"))
                .setTextColorCenter(Color.LTGRAY)
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                .setLabels("省", "市", "区")
//                .setBackgroundId(0x11000000) //设置外部遮罩颜色
//                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
//                    @Override
//                    public void onOptionsSelectChanged(int options1, int options2, int options3) {
//                        String str = "options1: " + options1 + "\noptions2: " + options2 +
// "\noptions3: " + options3;
//                        Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
//                    }
//                })
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器*/
        pvOptions.setPicker(options1Items, options2Items);//二级选择器
        /*pvOptions.setPicker(options1Items, options2Items,options3Items);//三级选择器*/
    }

    @OnClick({R.id.rl_lwyer_avatar, R.id.rl_lwyer_phone, R.id.rl_lwyer_nickname, R.id.rl_lwyer_sex, R.id.rl_lwyer_corporation, R.id.rl_lwyer_address, R.id.rl_lwyer_synopsis})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_lwyer_avatar:
                showPhotoDialog();

                break;
            case R.id.rl_lwyer_phone:

                break;
            case R.id.rl_lwyer_nickname:
                alertDialog("姓名",MODIFY_NAME,"姓名不能为空");

                break;
            case R.id.rl_lwyer_sex:
                chooseSex();

                break;
            case R.id.rl_lwyer_corporation:
                alertDialog("执业机构",MODIFY_COMPANY,"不能为空");

                break;
            case R.id.rl_lwyer_address:
                if (pvOptions!=null){
                    pvOptions.show();
                }
                break;
            case R.id.rl_lwyer_synopsis:
                alertDialog("个人简介",MODIFY_RESUME,"不能为空");

                break;

        }
    }
    /**
     * 弹出图片选择框
     *
     */
    @TargetApi(23)
    private void showPhotoDialog() {
        ActionSheetDialog dialog = new ActionSheetDialog(this);
        dialog.builder().setTitle("选择照片")
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .addSheetItem("相机", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {

                    @Override
                    public void onClick(int which) {
                        initPermission();
                        photoUtils.takePicture(MineLwyerActivity.this);
                    }

                })
                .addSheetItem("相册", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {

                            @Override
                            public void onClick(int which) {
                                initPermission();
                                photoUtils.selectPicture(MineLwyerActivity.this);
                            }
                        }

                );
        dialog.show();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PhotoUtils.INTENT_CROP:
            case PhotoUtils.INTENT_TAKE:
            case PhotoUtils.INTENT_SELECT:
                photoUtils.onActivityResult(MineLwyerActivity.this, requestCode, resultCode, data);
                break;

        }
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
    /**
     * 初始化图片选择工具及注册回调监听
     */
    private void setPortraitChangeListener() {
        photoUtils = new PhotoUtils(new PhotoUtils.OnPhotoResultListener() {
            @Override
            public void onPhotoResult(Uri uri) {
                if (uri != null && !TextUtils.isEmpty(uri.getPath())) {
                    compressWithRx(new File(uri.getPath()));
                }
            }

            @Override
            public void onPhotoCancel() {

            }
        });
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
                        updateImage(file);
                    }
                });
    }
    /**
     * 上传头像
     */
    private void updateImage(final File file) {
        String imageToBase64 = Base64Util.imageToBase64(file.getPath());
        Map<String, String> params = new HashMap<>();
        params.put("data", imageToBase64);
        Gson gson=new Gson();
        String strEntity = gson.toJson(params);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),strEntity);
        Observable<BaseEntity<UploadFileBean>> observable = RetrofitFactory.getInstance().uploadAvatar(body);
        observable.compose(this.<BaseEntity<UploadFileBean>>rxSchedulers()).subscribe(new BaseObserver<UploadFileBean>() {

            @Override
            protected void onHandleSuccess(UploadFileBean uploadFileBean, String msg) {
                mMember.setAvatar(uploadFileBean.getSrc());
                mACache.put(Constant.MEMBER_INFO,mMember);
                intoImg(file.getPath(),civLwyerAvatar);
            }
        });
    }
    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");
        //获取assets目录下的json文件数据

        ArrayList<ProvinceBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市
                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {
                    City_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }

        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }
    public ArrayList<ProvinceBean> parseData(String result) {//Gson 解析
        ArrayList<ProvinceBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                ProvinceBean entity = gson.fromJson(data.optJSONObject(i).toString(),
                        ProvinceBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }
    private void alertDialog(final String title,final int message,final String toast) {
        final AlertDialog dialog = new AlertDialog.Builder(mContext).create();
        dialog.show();
        Window window = dialog.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        window.setContentView(R.layout.dialog_edit_name);
        TextView tv_dialog_title=  window.findViewById(R.id.tv_dialog_title);
        tv_dialog_title.setText(title);
        final EditText editName = window.findViewById(R.id.edit_name);
        TextView tvCancel = window.findViewById(R.id.tv_cancel);
        TextView tvConfirm = window.findViewById(R.id.tv_confirm);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              String content   = editName.getText().toString().trim();

                if (TextUtils.isEmpty(content)) {
                    NToast.shortToast(mContext, toast);
                    dialog.dismiss();
                } else {
                    Map<String, String> params = new HashMap<>();
                    switch (title){
                        case "姓名":
                            mName=content;
                            params.put("realName", mName);
                            break;
                        case "执业机构":
                            mCompany=content;
                            params.put("company", mCompany);
                            break;
                        case "个人简介":
                            mResume=content;
                            params.put("resume", mResume);
                            break;
                    }
                    modifyInfo(message,params);
                }
                dialog.dismiss();

            }
        });

    }
    private void modifyInfo(final int message, Map<String, String> params) {
        Gson gson = new Gson();
        String strEntity = gson.toJson(params);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;" +
                "charset=UTF-8"), strEntity);
        Observable<BaseEntity<Object>> observable = RetrofitFactory.getInstance()
                .modifyInfo(body);
        observable.compose(this.<BaseEntity<Object>>rxSchedulers()).subscribe(new BaseObserver<Object>() {

            @Override
            protected void onHandleSuccess(Object s, String msg) {
                mHandler.sendEmptyMessage(message);
            }

            @Override
            protected void onHandleError(int statusCode, String msg) {
                super.onHandleError(statusCode, msg);
                NToast.shortToast(mContext, msg);
            }
        });
    }
    /**
     * 选择性别
     */
    private void chooseSex() {
        new ActionSheetDialog(this)
                .builder()
                .setTitle("选择性别")
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .addSheetItem("男", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog
                        .OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        Map<String, String> params = new HashMap<>();
                        params.put("gender", "1");
                        modifyInfo(MODIFY_SEX_MALE,params);
                    }
                })
                .addSheetItem("女", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog
                        .OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        Map<String, String> params = new HashMap<>();
                        params.put("gender", "2");
                        modifyInfo(MODIFY_SEX_FEMALE,params);
                    }
                })
                .show();

    }
}
