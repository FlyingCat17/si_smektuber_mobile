package com.nekoid.smektuber.helpers.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecycleItemDecoration extends RecyclerView.ItemDecoration {

    private int paddingRight;

    public RecycleItemDecoration(int paddingRight) {
        this.paddingRight = paddingRight;
    }

    public void setPaddingRight(int padding) {
        this.paddingRight = padding;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int itemCount = parent.getAdapter().getItemCount();
        int itemPosition = parent.getChildAdapterPosition(view);

        if (itemCount > 0 && itemPosition == itemCount - 1) {
            outRect.right = paddingRight;
        }
    }


}
