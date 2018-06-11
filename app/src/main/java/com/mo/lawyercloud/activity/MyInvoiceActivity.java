package com.mo.lawyercloud.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
import com.mo.lawyercloud.beans.BaseEntity;
import com.mo.lawyercloud.beans.apiBeans.InvoiceListBean;
import com.mo.lawyercloud.network.BaseObserver;
import com.mo.lawyercloud.network.RetrofitFactory;
import com.mo.lawyercloud.utils.NToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;

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
    int pageNo = 1;
    int pageSize = 10;
    InvoiceDataAdapter invoiceDataAdapter;
    List<InvoiceListBean.ResultBean> dataList=new ArrayList<>();
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
                InvoiceListBean.ResultBean resultBean = dataList.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("id",resultBean.getOrderNo());//订单号
                bundle.putString("money",resultBean.getRealPrice()+"");//金额
                startActivity(ApplyInvoiceActivity.class);
            }
        });
    }

    @Override
    public void initData() {
        getInvoiceList();
    }

    @Override
    public void onEvent() {

    }


    public void getInvoiceList() {

        Observable<BaseEntity<InvoiceListBean>> observable = RetrofitFactory.getInstance().getInvoiceList(pageNo, pageSize);
        observable.compose(this.<BaseEntity<InvoiceListBean>>rxSchedulers()).subscribe(new BaseObserver<InvoiceListBean>() {
            @Override
            protected void onHandleSuccess(InvoiceListBean registerResult, String msg) {
                List<InvoiceListBean.ResultBean> result = registerResult.getResult();
                dataList.clear();
                if (result != null) {
                    dataList.addAll(result);
                }
                invoiceDataAdapter.notifyDataSetChanged();
            }

            @Override
            protected void onHandleError(int statusCode, String msg) {
                NToast.shortToast(mContext, msg);
            }
        });
    }
}
