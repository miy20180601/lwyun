package com.mo.lawyercloud.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mo.lawyercloud.R;
import com.mo.lawyercloud.base.BaseFragment;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by mo on 2018/5/25.
 * 法规常识
 */

public class RegulationFragment extends BaseFragment {
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Override
    public int getLayoutId() {
        return R.layout.fg_regulation;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void initViews() {
        ArrayList<String> datas = new ArrayList<>();
        datas.add(" ");
        RegulationQuickAdapter quickAdapter = new RegulationQuickAdapter(datas);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(quickAdapter);
    }
}
