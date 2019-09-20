package com.lp.library.Banner.loop;

import com.lp.library.Banner.Ui.FlexibleBanner;

/**
 * @author Loren
 * Create_Time: 2019/9/10 15:07
 * description:
 */
public class DefaultLoopTask extends BannerLoopTaskImpl {


    public DefaultLoopTask(FlexibleBanner convenientBanner) {
        super(convenientBanner);
    }

    @Override
    public void handle() {
        FlexibleBanner convenientBanner = reference.get();

        if (convenientBanner != null) {
            if (convenientBanner.getViewPager() != null && convenientBanner.isTurning()) {
                int page = convenientBanner.getCbLoopScaleHelper().getCurrentItem() + 1;
                convenientBanner.getCbLoopScaleHelper().setCurrentItem(page, true);
                convenientBanner.postDelayed(convenientBanner.getAdSwitchTask(), convenientBanner.getAutoTurningTime());
            }
        }
    }
}
