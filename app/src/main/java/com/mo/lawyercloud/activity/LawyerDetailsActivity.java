package com.mo.lawyercloud.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mo.lawyercloud.R;
import com.mo.lawyercloud.base.BaseActivity;
import com.mo.lawyercloud.fragment.ProfessionQuickAdapter;
import com.mo.lawyercloud.utils.NToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author CUI
 * @data 2018/06/07
 * @details 律师详情界面
 */
public class LawyerDetailsActivity extends BaseActivity {
    @BindView(R.id.bar_iv_back)
    ImageView barIvBack;
    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.bar_tv_right)
    TextView barTvRight;
    @BindView(R.id.iv_detail_img)
    CircleImageView ivDetailImg;
    @BindView(R.id.tv_details_region)
    TextView tvDetailsRegion;
    @BindView(R.id.rv_details_trait)
    RecyclerView rv_details_trait;
    @BindView(R.id.tv_details_content)
    TextView tvDetailsContent;
    @BindView(R.id.tv_datails_subscribe)
    Button tvDatailsSubscribe;

    @Override
    public int getLayoutId() {
        return R.layout.activity_lawyer_details;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        barTitle.setText("详细资料");
        tvDetailsRegion.setText("山西省 太原市");
    }

    @Override
    public void initData() {
        List<String> datas = new ArrayList<>();
        datas.add("家庭纠纷");
        datas.add("公司并购");
        ProfessionQuickAdapter adapter = new ProfessionQuickAdapter(datas);
        rv_details_trait.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager
                .HORIZONTAL, false));
        rv_details_trait.setAdapter(adapter);
    }

    @Override
    public void onEvent() {

    }

    @OnClick(R.id.tv_datails_subscribe)
    public void onViewClicked() {
        startActivity(VideoAppointmentAcitivty.class);
    }
}
