package com.lp.library.Banner.Holder;

/**
 * Created by Sai on 15/12/14.
 *
 * @param <T> 任何你指定的对象
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class FlexibleBannerHolder extends RecyclerView.ViewHolder {
    public FlexibleBannerHolder(View itemView) {
        super(itemView);
        initView(itemView);
    }

    protected abstract void initView(View itemView);

    public abstract void updateUI(Object data);
}
