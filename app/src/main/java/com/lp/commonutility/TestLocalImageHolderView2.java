package com.lp.commonutility;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.lp.library.Banner.Holder.FlexibleBannerHolder;

/**
 * @author Loren
 * Create_Time: 2019/9/10 14:09
 * description:
 */
public class TestLocalImageHolderView2 extends FlexibleBannerHolder {

    private String id;

    public TestLocalImageHolderView2(View itemView) {
        super(itemView);

    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    protected void initView(View itemView) {
        mItemView = itemView;
        image1 = itemView.findViewById(R.id.image1);
        text1 = itemView.findViewById(R.id.text1);
        ll_root1 = itemView.findViewById(R.id.ll_root1);

        image2 = itemView.findViewById(R.id.image2);
        text2 = itemView.findViewById(R.id.text2);
        ll_root2 = itemView.findViewById(R.id.ll_root2);

        image3 = itemView.findViewById(R.id.image3);
        text3 = itemView.findViewById(R.id.text3);
        ll_root3 = itemView.findViewById(R.id.ll_root3);


    }

    @Override
    public void updateUI(Object data) {
        Banner banner = (Banner) data;
        if (banner.getGoods() != null) {

            if (banner.getGoods().size() == 1) {
                Glide.with(mItemView.getContext())
                        .load(banner.getGoods().get(0).getUrl())
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                        .into(image1);
                text1.setText(banner.getGoods().get(0).getName());
                ll_root1.setVisibility(View.VISIBLE);
                ll_root2.setVisibility(View.INVISIBLE);
                ll_root3.setVisibility(View.INVISIBLE);
            } else if (banner.getGoods().size() == 2) {
                Glide.with(mItemView.getContext())
                        .load(banner.getGoods().get(0).getUrl())
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                        .into(image1);
                text1.setText(banner.getGoods().get(0).getName());

                Glide.with(mItemView.getContext())
                        .load(banner.getGoods().get(1).getUrl())
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                        .into(image2);
                text2.setText(banner.getGoods().get(1).getName());
                ll_root1.setVisibility(View.VISIBLE);
                ll_root2.setVisibility(View.VISIBLE);
                ll_root3.setVisibility(View.INVISIBLE);
            } else if (banner.getGoods().size() >= 3) {
                Glide.with(mItemView.getContext())
                        .load(banner.getGoods().get(0).getUrl())
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                        .into(image1);
                text1.setText(banner.getGoods().get(0).getName());

                Glide.with(mItemView.getContext())
                        .load(banner.getGoods().get(1).getUrl())
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                        .into(image2);
                text3.setText(banner.getGoods().get(1).getName());


                Glide.with(mItemView.getContext())
                        .load(banner.getGoods().get(2).getUrl())
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                        .into(image3);
                text3.setText(banner.getGoods().get(2).getName());
                ll_root1.setVisibility(View.VISIBLE);
                ll_root2.setVisibility(View.VISIBLE);
                ll_root3.setVisibility(View.VISIBLE);
            }

        }

    }


    private ImageView image1, image2, image3;
    private LinearLayout ll_root1, ll_root2, ll_root3;
    private TextView text1, text2, text3;

    private View mItemView;


}


