package com.mo.lawyercloud.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mo.lawyercloud.R;
import com.mo.lawyercloud.adapter.RecruitmentQuickAdapter;
import com.mo.lawyercloud.base.BaseFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by mo on 2018/5/25.
 * 人才招聘
 */

public class RecruitmentFragment extends BaseFragment {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;


    @Override
    public int getLayoutId() {
        return R.layout.fg_recruitment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    private void initViews() {
        ArrayList<String> datas = new ArrayList<>();
        datas.add(" ");
        RecruitmentQuickAdapter quickAdapter = new RecruitmentQuickAdapter(datas);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(quickAdapter);
    }

}
