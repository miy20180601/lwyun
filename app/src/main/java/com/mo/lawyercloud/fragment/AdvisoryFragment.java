package com.mo.lawyercloud.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mo.lawyercloud.R;
import com.mo.lawyercloud.activity.LawyerDetailsActivity;
import com.mo.lawyercloud.adapter.LawyerProfileQuickAdapter;
import com.mo.lawyercloud.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Mohaifeng on 18/5/23.
 */
public class AdvisoryFragment extends BaseFragment {


    private static AdvisoryFragment instance = null;
    @BindView(R.id.edit_search)
    EditText editSearch;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private List<String> mDatas;
    private LawyerProfileQuickAdapter mQuickAdapter;


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
        mDatas = new ArrayList<>();
        mDatas.add(" ");
        mDatas.add(" ");
        mDatas.add(" ");
        mQuickAdapter = new LawyerProfileQuickAdapter(mDatas);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(mQuickAdapter);
        mQuickAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Log.i("itemClickLinstener","position="+position);
                startActivity(LawyerDetailsActivity.class);
            }
        });

    }

    @OnClick({R.id.ll_family_affairs, R.id.ll_contractual_dispute, R.id.ll_infringement_disputes,
            R.id.ll_merger, R.id.ll_intellectual_property, R.id.ll_labor_dispute, R.id
            .ll_securities, R.id.ll_criminal,R.id.fl_area, R.id.fl_good_at})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_family_affairs:

                break;
            case R.id.ll_contractual_dispute:

                break;
            case R.id.ll_infringement_disputes:

                break;
            case R.id.ll_merger:

                break;
            case R.id.ll_intellectual_property:

                break;
            case R.id.ll_labor_dispute:

                break;
            case R.id.ll_securities:

                break;
            case R.id.ll_criminal:

                break;
            case R.id.fl_area:
                break;
            case R.id.fl_good_at:
                break;
        }
    }


}
