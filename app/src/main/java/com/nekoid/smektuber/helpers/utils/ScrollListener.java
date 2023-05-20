package com.nekoid.smektuber.helpers.utils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class ScrollListener extends RecyclerView.OnScrollListener {
    RecyclerView recyclerView;
    private int horizontal;
    private int vertical;
    private int newState;

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        this.recyclerView = recyclerView;
        this.horizontal = dx;
        this.vertical = dy;
        onScroll(recyclerView, dx, dy, this.newState);
        super.onScrolled(recyclerView, dx, dy);
    }

    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        this.recyclerView = recyclerView;
        this.newState = newState;
        super.onScrollStateChanged(recyclerView, newState);
    }

    public abstract void onScroll(@NonNull RecyclerView recyclerView, int horizontal, int vertical, int newState);
}
