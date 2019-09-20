package com.lp.commonutility;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.lp.library.Banner.Holder.FlexibleBannerHolder;

/**
 * @author Loren
 * Create_Time: 2019/9/10 14:09
 * description:
 */
public class TestLocalImageHolderView1 extends FlexibleBannerHolder {

    private String id;

    public TestLocalImageHolderView1(View itemView) {
        super(itemView);

    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    protected void initView(View itemView) {
        mItemView = itemView;
        imageView = itemView.findViewById(R.id.iv_banner);


    }

    @Override
    public void updateUI(Object data) {
        Banner banner = (Banner) data;
        Glide.with(mItemView.getContext())
                .load(banner.getUrl())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                .into(imageView);
    }


    private ImageView imageView;
    private View mItemView;


}


