package com.mo.lawyercloud.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.mo.lawyercloud.R;
import com.mo.lawyercloud.activity.LawyerDetailsActivity;
import com.mo.lawyercloud.activity.MyContactAcitity;
import com.mo.lawyercloud.adapter.LawyerProfileQuickAdapter;
import com.mo.lawyercloud.base.BaseFragment;
import com.mo.lawyercloud.beans.BaseEntity;
import com.mo.lawyercloud.beans.ProvinceBean;
import com.mo.lawyercloud.beans.apiBeans.BaseListEntity;
import com.mo.lawyercloud.beans.apiBeans.ChannelBean;
import com.mo.lawyercloud.beans.apiBeans.SolicitorDetailBean;
import com.mo.lawyercloud.eventbus.AdvisoryMessage;
import com.mo.lawyercloud.eventbus.HomeClickMessage;
import com.mo.lawyercloud.network.BaseObserver;
import com.mo.lawyercloud.network.RetrofitFactory;
import com.mo.lawyercloud.utils.CommonUtils;
import com.mo.lawyercloud.utils.GetJsonDataUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * Created by Mohaifeng on 18/5/23.
 * 咨询页面
 */
public class AdvisoryFragment extends BaseFragment implements View.OnClickListener{

    private static AdvisoryFragment instance = null;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    EditText editSearch;
    LinearLayout mLlFamilyAffairs;
    LinearLayout mLlContractualDispute;
    LinearLayout mLlInfringementDisputes;
    LinearLayout mLlMerger;
    LinearLayout mLlIntellectualProperty;
    LinearLayout mLlLaborDispute;
    LinearLayout mLlSecurities;
    LinearLayout mLlCriminal;
    TextView mTvLocation;
    TextView mTvChannel;



    private List<SolicitorDetailBean> mDatas;
    private LawyerProfileQuickAdapter mQuickAdapter;
    private int pageNo = 1;
    private int pageSize = 10;
    private Integer mChannel = null;
    private String solicitorName, mLocation;

    private ArrayList<ChannelBean> mChannelBeans = new ArrayList<>();
    private OptionsPickerView pvChannels;
    private ArrayList<ProvinceBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private OptionsPickerView pvOptions;


    public static AdvisoryFragment getInstance() {
        if (instance == null) {
            instance = new AdvisoryFragment();
        }
        return instance;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fg_advisory;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);//地区列表加载
        getChannels(); //channel 加载

        initRecycleView();
        getSolicitorList();
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(AdvisoryMessage msg) {
        if (msg.type == 1) {
            solicitorName = null;
            mChannel = msg.channel;
            pageNo = 1;
            setSelectedStatus();
        } else if (msg.type == 2) {
            solicitorName = msg.name;
            mChannel = null;
            pageNo = 1;
            setSelectedStatus();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public void getChannels() {
        Observable<BaseEntity<List<ChannelBean>>> observable = RetrofitFactory.getInstance()
                .getChannels();
        observable.compose(this.<BaseEntity<List<ChannelBean>>>rxSchedulers()).subscribe(new BaseObserver<List<ChannelBean>>() {

            @Override
            protected void onHandleSuccess(List<ChannelBean> channelBeans, String msg) {
                mChannelBeans.addAll(channelBeans);
                ChannelBean channelBean = new ChannelBean();
                channelBean.setName("全部");
                mChannelBeans.add(0, channelBean);
                initChannelPv();

            }
        });
    }

    private void initChannelPv() {
        pvChannels = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                /* + options3Items.get(options1).get(options2).get(options3).getPickerViewText()*/
                mChannel = mChannelBeans.get(options1).getId() == 0 ? null : mChannelBeans.get
                        (options1).getId();
                pageNo = 1;
                solicitorName = null;
                mLocation = null;
                mTvLocation.setText("地区");
                mTvChannel.setText(mChannelBeans.get(options1).getPickerViewText());
                setSelectedStatus();
            }
        })
                .setTitleText("选择领域")
                .setContentTextSize(20)//设置滚轮文字大小
                .setDividerColor(Color.LTGRAY)//设置分割线的颜色
                .setSelectOptions(0, 1)//默认选中项
                .setBgColor(Color.WHITE)
                .setTitleBgColor(Color.parseColor("#eaecec"))
                .setTitleColor(Color.GRAY)
                .setCancelColor(Color.parseColor("#0188ff"))
                .setSubmitColor(Color.parseColor("#0188ff"))
                .setTextColorCenter(Color.GRAY)
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .build();

        pvChannels.setPicker(mChannelBeans);
    }

    /*获取律师列表*/
    private void getSolicitorList() {
        Observable<BaseEntity<BaseListEntity<SolicitorDetailBean>>> observable = RetrofitFactory
                .getInstance().solicitorList(solicitorName, mLocation, mChannel, pageNo, pageSize);
        observable.compose(this.<BaseEntity<BaseListEntity<SolicitorDetailBean>>>rxSchedulers())
                .subscribe
                        (new BaseObserver<BaseListEntity<SolicitorDetailBean>>() {
                            @Override
                            protected void onHandleSuccess(BaseListEntity<SolicitorDetailBean> dataList, String msg) {
                                if (pageNo == 1) {//第一次加载或者是下拉加载
                                    mQuickAdapter.setNewData(dataList.getResult());
                                    if (mQuickAdapter.getData().size() >= dataList.getTotalCount()) {
                                        mQuickAdapter.loadMoreEnd();
                                    }
                                } else {
                                    mQuickAdapter.addData(dataList.getResult());
                                    if (mQuickAdapter.getData().size() >= dataList.getTotalCount()) {
                                        mQuickAdapter.loadMoreEnd();
                                    } else {
                                        mQuickAdapter.loadMoreComplete();
                                    }
                                }
                            }
                            @Override
                            protected void onHandleError(int statusCode, String msg) {
                                if (pageNo>1){
                                    mQuickAdapter.loadMoreFail();
                                }
                            }
                        });
    }

    private void initRecycleView() {
        mDatas = new ArrayList<>();
        String[] channelArray = getResources().getStringArray(R.array.channel);
        mQuickAdapter = new LawyerProfileQuickAdapter(mDatas, channelArray);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(mQuickAdapter);
        mQuickAdapter.setEmptyView(R.layout.empty_lowyer_list, recyclerView);
        mQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(mContext, LawyerDetailsActivity.class).putExtra("id",
                        mQuickAdapter.getData().get(position).getId()));
            }
        });
        mQuickAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                pageNo++;
                getSolicitorList();
            }
        },recyclerView);
        mQuickAdapter.addHeaderView(getHeaderView());
    }


    private View getHeaderView() {
        View headerView = getLayoutInflater().inflate(R.layout.header_advisory_fg_view, (ViewGroup)
                recyclerView.getParent(), false);
        editSearch = headerView.findViewById(R.id.edit_search);
        editSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (TextUtils.isEmpty(editSearch.getText().toString().trim())) {
                        return false;
                    }
                    CommonUtils.hideKeyboard(getActivity());
                    mChannel = null;
                    pageNo = 1;
                    solicitorName = editSearch.getText().toString().trim();
                    setSelectedStatus();
                    return true;
                }
                return false;
            }
        });
        headerView.findViewById(R.id.tv_reception).setOnClickListener(this); //客服
        headerView.findViewById(R.id.tv_message).setOnClickListener(this);  //消息
        mLlFamilyAffairs = headerView.findViewById(R.id.ll_family_affairs);//婚姻家事
        mLlFamilyAffairs.setOnClickListener(this);
        mLlContractualDispute = headerView.findViewById(R.id.ll_contractual_dispute);//合同纠纷
        mLlContractualDispute.setOnClickListener(this);
        mLlInfringementDisputes = headerView.findViewById(R.id.ll_infringement_disputes);//侵权纠纷
        mLlInfringementDisputes.setOnClickListener(this);
        mLlMerger = headerView.findViewById(R.id.ll_merger);//公司并购
        mLlMerger.setOnClickListener(this);
        mLlIntellectualProperty = headerView.findViewById(R.id.ll_intellectual_property);//知识产权
        mLlIntellectualProperty.setOnClickListener(this);
        mLlLaborDispute = headerView.findViewById(R.id.ll_labor_dispute);//劳动争议
        mLlLaborDispute.setOnClickListener(this);
        mLlSecurities = headerView.findViewById(R.id.ll_securities);//证券保险
        mLlSecurities.setOnClickListener(this);
        mLlCriminal = headerView.findViewById(R.id.ll_criminal);//刑事及其他
        mLlCriminal.setOnClickListener(this);
        mTvLocation = headerView.findViewById(R.id.tv_location); //地区
        mTvChannel = headerView.findViewById(R.id.tv_channel);//频道筛选
        headerView.findViewById(R.id.fl_area).setOnClickListener(this); //地区点击
        headerView.findViewById(R.id.fl_good_at).setOnClickListener(this);  //channel点击

        return headerView;
    }

    private void clearSelectedStatus() {
        mLlFamilyAffairs.setSelected(false);
        mLlContractualDispute.setSelected(false);
        mLlInfringementDisputes.setSelected(false);
        mLlMerger.setSelected(false);
        mLlIntellectualProperty.setSelected(false);
        mLlLaborDispute.setSelected(false);
        mLlSecurities.setSelected(false);
        mLlCriminal.setSelected(false);
        mTvChannel.setText("擅长");
    }

    private void setSelectedStatus() {
        clearSelectedStatus();
        if (mChannel != null) {
            if (mChannel == 1) {
                mLlFamilyAffairs.setSelected(true);
                mTvChannel.setText("婚姻家事");
            } else if (mChannel == 2) {
                mLlContractualDispute.setSelected(true);
                mTvChannel.setText("合同纠纷");
            } else if (mChannel == 3) {
                mLlInfringementDisputes.setSelected(true);
                mTvChannel.setText("侵权纠纷");
            } else if (mChannel == 4) {
                mLlMerger.setSelected(true);
                mTvChannel.setText("公司并购");
            } else if (mChannel == 5) {
                mLlIntellectualProperty.setSelected(true);
                mTvChannel.setText("知识产权");
            } else if (mChannel == 6) {
                mLlLaborDispute.setSelected(true);
                mTvChannel.setText("劳动争议");
            } else if (mChannel == 7) {
                mLlSecurities.setSelected(true);
                mTvChannel.setText("证券保险");
            } else if (mChannel == 8) {
                mLlCriminal.setSelected(true);
                mTvChannel.setText("刑事及其他");
            }
        }
        getSolicitorList();
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
            }
        }
    };

    private void initOptionPicker() {//条件选择器初始化
        /**
         * 注意 ：如果是三级联动的数据(省市区等)，请参照 JsonDataActivity 类里面的写法。
         */

        pvOptions = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                /* + options3Items.get(options1).get(options2).get(options3).getPickerViewText()*/
                mLocation = options2Items.get(options1).get(options2);
                pageNo = 1;
                mChannel = null;
                mTvChannel.setText("擅长");
                mTvLocation.setText(mLocation);
                setSelectedStatus();
            }
        })
                .setTitleText("城市选择")
                .setContentTextSize(20)//设置滚轮文字大小
                .setDividerColor(Color.LTGRAY)//设置分割线的颜色
                .setSelectOptions(0, 1)//默认选中项
                .setBgColor(Color.WHITE)
                .setTitleBgColor(Color.parseColor("#eaecec"))
                .setTitleColor(Color.GRAY)
                .setCancelColor(Color.parseColor("#0188ff"))
                .setSubmitColor(Color.parseColor("#0188ff"))
                .setTextColorCenter(Color.GRAY)
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器*/
        pvOptions.setPicker(options1Items, options2Items);//二级选择器
        /*pvOptions.setPicker(options1Items, options2Items,options3Items);//三级选择器*/
    }

    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(mContext, "province.json");
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_reception:
                startActivity(MyContactAcitity.class);
                break;
            case R.id.tv_message:
                HomeClickMessage msg = new HomeClickMessage();
                msg.type = 3;
                EventBus.getDefault().post(msg);
                break;
            case R.id.ll_family_affairs:
                if (!mLlFamilyAffairs.isSelected()) {
                    solicitorName = null;
                    pageNo=1;
                    mChannel = 1;
                    setSelectedStatus();
                }
                break;
            case R.id.ll_contractual_dispute:
                if (!mLlContractualDispute.isSelected()) {
                    solicitorName = null;
                    mChannel = 2;
                    setSelectedStatus();
                }
                break;
            case R.id.ll_infringement_disputes:
                if (!mLlInfringementDisputes.isSelected()) {
                    solicitorName = null;
                    pageNo=1;
                    mChannel = 3;
                    setSelectedStatus();
                }
                break;
            case R.id.ll_merger:
                if (!mLlMerger.isSelected()) {
                    solicitorName = null;
                    pageNo=1;
                    mChannel = 4;
                    setSelectedStatus();
                }
                break;
            case R.id.ll_intellectual_property:
                if (!mLlIntellectualProperty.isSelected()) {
                    solicitorName = null;
                    pageNo=1;
                    mChannel = 5;
                    setSelectedStatus();
                }
                break;
            case R.id.ll_labor_dispute:
                if (!mLlLaborDispute.isSelected()) {
                    solicitorName = null;
                    pageNo=1;
                    mChannel = 6;
                    setSelectedStatus();
                }
                break;
            case R.id.ll_securities:
                if (!mLlSecurities.isSelected()) {
                    solicitorName = null;
                    pageNo=1;
                    mChannel = 7;
                    setSelectedStatus();
                }
                break;
            case R.id.ll_criminal:
                if (!mLlCriminal.isSelected()) {
                    solicitorName = null;
                    pageNo=1;
                    mChannel = 8;
                    setSelectedStatus();
                }
                break;
            case R.id.fl_area:
                pvOptions.show();
                break;
            case R.id.fl_good_at:
                pvChannels.show();
                break;
        }
    }
}
