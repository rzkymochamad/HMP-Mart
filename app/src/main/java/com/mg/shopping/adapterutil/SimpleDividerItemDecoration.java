package com.mg.shopping.adapterutil;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.mg.shopping.R;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;



public class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration {
    Context context;
    Drawable mDivider;

    public SimpleDividerItemDecoration(Context context) {
        this.context = context;
        mDivider = ContextCompat.getDrawable(context,R.drawable.line_divider);
    }

    public SimpleDividerItemDecoration(Context context,Drawable drawable) {
        this.context = context;
        mDivider = drawable;
    }



    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {

            if (i >= (childCount-1)){
                continue;
            }
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }
}