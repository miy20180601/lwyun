package com.mo.lawyercloud.activity;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DrawableUtils;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mo.lawyercloud.R;
import com.mo.lawyercloud.adapter.InvoiceDataAdapter;
import com.mo.lawyercloud.base.BaseActivity;
import com.mo.lawyercloud.beans.apiBeans.InvoiceDataBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的发票
 */
public class MyInvoiceActivity extends BaseActivity {
    @BindView(R.id.bar_iv_back)
    ImageView barIvBack;
    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.bar_tv_right)
    TextView barTvRight;
    @BindView(R.id.rv_invoic_data)
    RecyclerView rvInvoicData;

    InvoiceDataAdapter invoiceDataAdapter;
    List<InvoiceDataBean> dataList=new ArrayList<>();
    @Override
    public int getLayoutId() {
        return R.layout.activity_my_invoice;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        barTitle.setText("我的发票");
        barTvRight.setText("已申请");
        Drawable drawable =getResources().getDrawable(R.mipmap.my_application_icon_applied);
        barTvRight.setTextColor(getResources().getColor(R.color.tv_high_light));
        barTvRight.setVisibility(View.VISIBLE);
        barTvRight.setCompoundDrawablesWithIntrinsicBounds(drawable,null,null,null);
        LinearLayoutManager  linearLayoutManager = new LinearLayoutManager(mContext);
        rvInvoicData.setLayoutManager(linearLayoutManager);
        invoiceDataAdapter=new InvoiceDataAdapter(dataList);
        rvInvoicData.setAdapter(invoiceDataAdapter);
        invoiceDataAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Log.i("onItemChildClick","position="+position);
                startActivity(ApplyInvoiceActivity.class);
            }
        });
    }

    @Override
    public void initData() {
        dataList.add(new InvoiceDataBean("1","110101010101","交易成功","合同纠纷","14:00-16:00","200"));
        invoiceDataAdapter.notifyDataSetChanged();
    }

    @Override
    public void onEvent() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
