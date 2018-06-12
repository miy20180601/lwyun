package com.mo.lawyercloud.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.mo.lawyercloud.R;
import com.mo.lawyercloud.base.BaseActivity;
import com.mo.lawyercloud.base.Constant;
import com.mo.lawyercloud.beans.BaseEntity;
import com.mo.lawyercloud.beans.ProvinceBean;
import com.mo.lawyercloud.beans.SerializableMap;
import com.mo.lawyercloud.beans.apiBeans.RegisterResult;
import com.mo.lawyercloud.beans.apiBeans.UploadFileBean;
import com.mo.lawyercloud.network.BaseObserver;
import com.mo.lawyercloud.network.RetrofitFactory;
import com.mo.lawyercloud.utils.Base64Util;
import com.mo.lawyercloud.utils.GetJsonDataUtil;
import com.mo.lawyercloud.utils.MD5util;
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
import butterknife.OnClick;
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
 * Created by mo on 2018/5/11.
 */

public class LowyerRegisterActivity extends BaseActivity {

    @BindView(R.id.bar_title)
    TextView mBarTitle;
    @BindView(R.id.edit_phone)
    EditText mEditPhone;
    @BindView(R.id.edit_password)
    EditText mEditPassword;
    @BindView(R.id.edit_code)
    EditText mEditCode;
    @BindView(R.id.tv_get_code)
    TextView mTvGetCode;
    @BindView(R.id.edit_name)
    EditText mEditName;
    @BindView(R.id.edit_organization)
    EditText mEditOrganization;
    @BindView(R.id.tv_location)
    TextView mTvLocation;
    @BindView(R.id.edit_professional)
    EditText mEditProfessional;
    @BindView(R.id.edit_resume)
    EditText mEditResume;
    @BindView(R.id.iv_paperwork)
    ImageView mIvPaperwork;
    @BindView(R.id.tv_protocol)
    TextView mTvProtocol;
    @BindView(R.id.cb_pwd_eye)
    CheckBox mCbEye;

    private String mPhone, mPwd, mCode, mName, mOrganization, mProfession, mResume;

    private ArrayList<ProvinceBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private OptionsPickerView pvOptions;

    private PhotoUtils photoUtils;
    private String mPaperworkName;


    @Override
    public int getLayoutId() {
        return R.layout.activity_lowyer_register;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mBarTitle.setText("律师注册");
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
        setPortraitChangeListener();
    }

    @Override
    public void initData() {

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
        Observable<BaseEntity<UploadFileBean>> observable = RetrofitFactory.getInstance().uploadImage(body);
        observable.compose(this.<BaseEntity<UploadFileBean>>rxSchedulers()).subscribe(new BaseObserver<UploadFileBean>() {
            @Override
            protected void onHandleSuccess(UploadFileBean uploadFileBean, String msg) {
                mPaperworkName = uploadFileBean.getName();
                Glide.with(mContext).load(file).into(mIvPaperwork);
            }
        });
    }


    @Override
    public void onEvent() {
        mEditPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPhone = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mEditPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPwd = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mEditCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCode = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mEditName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mName = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mEditOrganization.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mOrganization = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mEditProfessional.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mProfession = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mEditResume.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mResume = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mCbEye.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //选中显示密码
                    mEditPassword.setTransformationMethod(HideReturnsTransformationMethod
                            .getInstance());
                } else {
                    //隐藏密码
                    mEditPassword.setTransformationMethod(PasswordTransformationMethod
                            .getInstance());
                }
                //把光标设置在文字结尾
                mEditPassword.setSelection(mEditPassword.getText().length());
            }
        });
    }

    private CountDownTimer timer = new CountDownTimer(60000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            mTvGetCode.setText((millisUntilFinished / 1000) + "s");
        }

        @Override
        public void onFinish() {
            mTvGetCode.setEnabled(true);
            mTvGetCode.setText("重新获取");
        }
    };

    @OnClick({R.id.bar_iv_back, R.id.tv_get_code, R.id.fl_location, R.id.btn_register,R.id.iv_paperwork})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bar_iv_back:
                finish();
                break;
            case R.id.tv_get_code:
                if (TextUtils.isEmpty(mPhone)) {


                    NToast.shortToast(mContext, "请输入手机号");
                    return;
                }
                timer.start();
                mTvGetCode.setEnabled(false);
                getMsmCode();
                break;
            case R.id.fl_location:
                if (pvOptions!=null){
                    pvOptions.show();
                }
                break;
            case R.id.btn_register:
                if (TextUtils.isEmpty(mPhone)) {
                    NToast.shortToast(mContext, "请输入手机号");
                    return;
                }
                if (TextUtils.isEmpty(mPwd) || mPwd.length() < 6) {
                    NToast.shortToast(mContext, "密码不能少于6位");
                    return;
                }
                if (TextUtils.isEmpty(mCode)) {
                    NToast.shortToast(mContext, "请输入短信验证码");
                    return;
                }
                if (TextUtils.isEmpty(mName)) {
                    NToast.shortToast(mContext, "请输入您的真实姓名");
                    return;
                }

                Map<String, String> params = new HashMap<>();
                params.put("username", mPhone);
                params.put("password", MD5util.getMd5Value(mPwd));
                params.put("realName", mName);
                params.put("type", "2"); //2为律师
                params.put("mobileCode", mCode);
                if (!TextUtils.isEmpty(mOrganization)) {
                    params.put("company", mOrganization);
                }
                if (!TextUtils.isEmpty(mTvLocation.getText().toString())) {
                    params.put("location", mTvLocation.getText().toString());
                }
                if (!TextUtils.isEmpty(mProfession)) {
                    params.put("profession", mProfession);
                }
                if (!TextUtils.isEmpty(mResume)) {
                    params.put("resume", mResume);
                }
                if (!TextUtils.isEmpty(mPaperworkName)){
                    params.put("paperwork", mPaperworkName);
                }
                SerializableMap tmpmap = new SerializableMap();
                tmpmap.setMap(params);
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", tmpmap);
                startActivity(new Intent(mContext, LowyerRegisterNextActivity.class).putExtras
                        (bundle));
                break;
            case R.id.iv_paperwork:
                showPhotoDialog();
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
                        photoUtils.takePicture(LowyerRegisterActivity.this);
                    }

                })
                .addSheetItem("相册", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {

                            @Override
                            public void onClick(int which) {
                                initPermission();
                                photoUtils.selectPicture(LowyerRegisterActivity.this);
                            }
                        }

                );
        dialog.show();

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PhotoUtils.INTENT_CROP:
            case PhotoUtils.INTENT_TAKE:
            case PhotoUtils.INTENT_SELECT:
                photoUtils.onActivityResult(LowyerRegisterActivity.this, requestCode, resultCode, data);
                break;

        }
    }


    private void initOptionPicker() {//条件选择器初始化

        /**
         * 注意 ：如果是三级联动的数据(省市区等)，请参照 JsonDataActivity 类里面的写法。
         */

        pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
//                String tx = options1Items.get(options1).getPickerViewText()
//                        + options2Items.get(options1).get(options2)
                /* + options3Items.get(options1).get(options2).get(options3).getPickerViewText()*/
//                ;
                String tx = options2Items.get(options1).get(options2);
                mTvLocation.setText(tx);
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

//        pvOptions.setSelectOptions(1,1);
        /*pvOptions.setPicker(options1Items);//一级选择器*/
        pvOptions.setPicker(options1Items, options2Items);//二级选择器
        /*pvOptions.setPicker(options1Items, options2Items,options3Items);//三级选择器*/
    }



    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
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
            }
        }
    };

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


    /*获取验证码*/
    private void getMsmCode() {
        Observable<BaseEntity<RegisterResult>> observable = RetrofitFactory.getInstance().getMsmCode
                (mPhone);
        observable.compose(this.<BaseEntity<RegisterResult>>rxSchedulers()).subscribe(new BaseObserver<RegisterResult>() {
            @Override
            protected void onHandleSuccess(RegisterResult s, String msg) {

            }
        });
    }

}
