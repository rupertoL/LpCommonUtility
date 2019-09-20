package com.lp.library.Banner.Holder;

import android.view.ViewGroup;

/**
 * @author Loren
 * Create_Time: 2019/9/19 13:40
 * description:
 */
public interface ItmeBuildImpi {


    FlexibleBannerHolder createViewHolder(ViewGroup parent, int viewType);

     int getItemViewType(Object t);

}
