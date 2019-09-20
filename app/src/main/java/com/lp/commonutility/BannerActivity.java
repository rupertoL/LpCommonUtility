package com.lp.commonutility;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lp.library.Banner.Holder.FlexibleBannerHolder;
import com.lp.library.Banner.Holder.ItmeBuildImpi;
import com.lp.library.Banner.Ui.FlexibleBanner;
import com.lp.library.Banner.congif.IndicatorConfig;

import java.util.ArrayList;
import java.util.List;

public class BannerActivity extends AppCompatActivity {

    private FlexibleBanner mFlexibleBanner, mFlexibleBanner2;

    private List<String> mBannerUrlLists = new ArrayList<>();
    private List<Banner> mBanner2UrlLists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);

        mFlexibleBanner = findViewById(R.id.fl_root);
        mFlexibleBanner2 = findViewById(R.id.fl_root2);
        initBanner();
        setBannerData();
    }

    /**
     * Banner
     */
    private void initBanner() {

        mFlexibleBanner.setPages(new ItmeBuildImpi() {

            @Override
            public FlexibleBannerHolder createViewHolder(ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(getBaseContext()).inflate(R.layout.banner_item, parent, false);

                return new TestLocalImageHolderView(itemView);
            }

            @Override
            public int getItemViewType(Object t) {
                return 0;
            }
        }, mBannerUrlLists);
        mFlexibleBanner
                .setPageIndicatorAlign(IndicatorConfig.INDICATOR_orientation_BOTTOM_CENTER)
                .setPageIndicatorPadding(0, 0, 10, 10);


        mFlexibleBanner2.setPages(new ItmeBuildImpi() {

            @Override
            public FlexibleBannerHolder createViewHolder(ViewGroup parent, int viewType) {

                if (viewType == 1) {
                    View itemView = LayoutInflater.from(getBaseContext()).inflate(R.layout.banner_item, parent, false);

                    return new TestLocalImageHolderView1(itemView);
                } else {
                    View itemView = LayoutInflater.from(getBaseContext()).inflate(R.layout.banner2_item, parent, false);

                    return new TestLocalImageHolderView2(itemView);
                }

            }

            @Override
            public int getItemViewType(Object t) {
                Banner banner = (Banner) t;
                return banner.getType();
            }
        }, mBanner2UrlLists);
        mFlexibleBanner2
                .setPageIndicatorAlign(IndicatorConfig.INDICATOR_orientation_BOTTOM_CENTER)
                .setPageIndicatorPadding(0, 0, 10, 10);


    }

    public void setBannerData() {


        mBannerUrlLists.add("http://goodspics.oss-cn-beijing.aliyuncs.com/1553655100989.jpg");
        mBannerUrlLists.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1671621993,3744599115&fm=26&gp=0.jpg");
        mBannerUrlLists.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1568981588793&di=20317894445829f09d9df6d01977cb1f&imgtype=0&src=http%3A%2F%2Fimg1.xcarimg.com%2Fmotonews%2F24455%2F26792%2F26852%2F640_480_20171107233115689311064468772.jpg");
        mBannerUrlLists.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1568981588789&di=ea322207ae329e80d5a5f99eca4f0369&imgtype=0&src=http%3A%2F%2Fimg.newmotor.com.cn%2FUploadFiles%2F2015-02%2Flq%2F2015020417520760960.jpg");
        mFlexibleBanner.update(mBannerUrlLists);
        if (mBannerUrlLists.size() < 2) {
            mFlexibleBanner.stopTurning();
        } else {
            mFlexibleBanner.startTurning(5000);
        }


        Banner banner = new Banner();
        banner.setType(1);
        banner.setUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1568982681130&di=e1fcc02ba2eab53e8ba591f5bc054e2e&imgtype=0&src=http%3A%2F%2Fimg.pconline.com.cn%2Fimages%2Fphotoblog%2F7%2F1%2F3%2F1%2F7131554%2F20095%2F22%2F1242970205386_mthumb.jpg");

        mBanner2UrlLists.add(banner);


        Banner banner2 = new Banner();
        banner2.setType(2);
        List<Banner.Goods> goods2 = new ArrayList<>();

        goods2.add(new Banner.Goods("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1569578559&di=61e3fd3bb62d084d5ec15a6d87fca2ff&imgtype=jpg&er=1&src=http%3A%2F%2Fm.360buyimg.com%2Fpop%2Fjfs%2Ft22885%2F315%2F2060265358%2F20803%2Ff549b2c7%2F5b727da5N74ec59cb.jpg","123"));
        goods2.add(new Banner.Goods("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1569578597&di=cf7536e44d6dfc00cfde9bcf805e7902&imgtype=jpg&er=1&src=http%3A%2F%2Fwww.qnong.com.cn%2Fuploadfile%2F2014%2F0222%2F20140222094954105.jpg","456"));
        goods2.add(new Banner.Goods("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1568983878048&di=57691e331ed7e5ce27b54bca351ee4ba&imgtype=0&src=http%3A%2F%2Fbpic.ooopic.com%2F13%2F49%2F46%2F35b2OOOPIC4d.jpg","789"));
        banner2.setGoods(goods2);
        mBanner2UrlLists.add(banner2);

        Banner banner3 = new Banner();
        banner3.setType(2);
        List<Banner.Goods> goods3 = new ArrayList<>();
        goods3.add(new Banner.Goods("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1568983957953&di=458cb53a55b05d8d0b41bb9f48c9f8d9&imgtype=0&src=http%3A%2F%2Fimg.daimg.com%2Fuploads%2Fallimg%2F120517%2F1-12051H34951924.jpg","963"));
        goods3.add(new Banner.Goods("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1568983957954&di=36867c312502a74f77b5620a27de31ca&imgtype=0&src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20171115%2Fc316641e4ce644bbb853ce7f472badd9.jpeg","852"));
        banner3.setGoods(goods3);
        mBanner2UrlLists.add(banner3);


        Banner banner4 = new Banner();
        banner4.setType(1);
        banner4.setUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1568982681130&di=d3f3061f3feb49c7a908d11c02e33270&imgtype=0&src=http%3A%2F%2Fwww.firstep.cn%2Fwp-content%2Fuploads%2F2013%2F10%2Fquanjing-yiheyuan1.jpg");
        mBanner2UrlLists.add(banner4);
        mFlexibleBanner2.update(mBanner2UrlLists);
        if (mBanner2UrlLists.size() < 2) {
            mFlexibleBanner2.stopTurning();
        } else {
            mFlexibleBanner2.startTurning(5000);
        }

    }

}
