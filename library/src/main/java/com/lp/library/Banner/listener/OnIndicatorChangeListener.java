package com.lp.library.Banner.listener;


import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Sai on 2018/4/25.
 */

public class OnIndicatorChangeListener implements  OnPageChangeListener{

    //原点指示器
    private ArrayList<ImageView> pointViews;
    private int[] page_indicatorId;

    private OnPageChangeListener onPageChangeListener;

    public OnIndicatorChangeListener(ArrayList<ImageView> pointViews, int page_indicatorId[]) {
        this.pointViews = pointViews;
        this.page_indicatorId = page_indicatorId;
    }

    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        if (onPageChangeListener != null)
            onPageChangeListener.onScrollStateChanged(recyclerView, newState);
    }

    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (onPageChangeListener != null) onPageChangeListener.onScrolled(recyclerView, dx, dy);
    }

    public void onPageSelected(int index) {
        for (int i = 0; i < pointViews.size(); i++) {
            pointViews.get(index).setImageResource(page_indicatorId[1]);
            if (index != i) {
                pointViews.get(i).setImageResource(page_indicatorId[0]);
            }
        }
        if (onPageChangeListener != null) onPageChangeListener.onPageSelected(index);

    }

    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        this.onPageChangeListener = onPageChangeListener;
    }

}
