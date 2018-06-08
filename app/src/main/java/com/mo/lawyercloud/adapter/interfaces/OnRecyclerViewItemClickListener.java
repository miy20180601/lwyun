package com.mo.lawyercloud.adapter.interfaces;


import android.view.View;

/**
 * @author CUI
 * @data 2018/06/08
 * @details recycler item 监听事件
 */
public interface OnRecyclerViewItemClickListener {
    void onItemClick(View view, int position);
    void onItemLongClick(View view , int position);
}
