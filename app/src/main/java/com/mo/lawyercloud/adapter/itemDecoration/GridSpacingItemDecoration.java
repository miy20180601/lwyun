package com.mo.lawyercloud.adapter.itemDecoration;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.mo.lawyercloud.utils.CommonUtils;

/**
 * Created by Mohaifeng on 2017/9/11.
 */

public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int spanCount;
    private int topSpacing;
    private int leftSpacing;
    private boolean includeEdge;

    public GridSpacingItemDecoration(Context context,int spanCount, int leftSpacing, int topSpacing, boolean includeEdge) {
        this.spanCount = spanCount;
        this.leftSpacing = CommonUtils.dp2px(context,leftSpacing);
        this.topSpacing = CommonUtils.dp2px(context,topSpacing);
        this.includeEdge = includeEdge;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // item position
        int column = position % spanCount; // item column

        if (includeEdge) {
            outRect.left = leftSpacing - column * leftSpacing / spanCount;
            outRect.right = (column + 1) * leftSpacing / spanCount;
            if (position < spanCount) { // top edge
                outRect.top = topSpacing;
            }
            outRect.bottom = leftSpacing; // item bottom
        } else {
            outRect.left = column * leftSpacing / spanCount;
            outRect.right = leftSpacing - (column + 1) * leftSpacing / spanCount;
            if (position >= spanCount) {
                outRect.top = topSpacing; // item top
            }
        }
    }
}
