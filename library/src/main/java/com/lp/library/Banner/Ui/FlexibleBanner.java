package com.lp.library.Banner.Ui;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lp.library.Banner.Adapter.FlexilbeBasePageAdapter;
import com.lp.library.Banner.Holder.ItmeBuildImpi;
import com.lp.library.Banner.congif.IndicatorConfig;
import com.lp.library.Banner.helper.CBLoopScaleHelper;
import com.lp.library.Banner.listener.BannerItemListener;
import com.lp.library.Banner.listener.OnIndicatorChangeListener;
import com.lp.library.Banner.listener.OnPageChangeListener;
import com.lp.library.Banner.loop.BannerLoopTaskImpl;
import com.lp.library.Banner.loop.DefaultLoopTask;
import com.lp.library.R;
import com.lp.library.utlis.DisplayHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Loren
 * Create_Time 2019/9/19 14:11
 * description:
 */
public class FlexibleBanner<T, L extends BannerLoopTaskImpl> extends RelativeLayout implements LifecycleObserver {
    private List<T> mDatas;
    private int[] page_indicatorId = new int[]{R.drawable.other_point_no_bg, R.drawable.other_point_ok_bg};
    private ArrayList<ImageView> mPointViews = new ArrayList<ImageView>();
    private FlexilbeBasePageAdapter<T> pageAdapter;
    private RecyclerView viewPager;
    private LinearLayout loPageTurningPoint;
    //布局比例
    private float proportion = -1;

    private long autoSwitchTime = 5000;
    private boolean isAutoSwitch = false;

    private boolean isAloneAutoSwitch = false;

    private boolean Turning = false;

    private CBLoopScaleHelper cbLoopScaleHelper;

    private OnPageChangeListener onPageChangeListener;
    protected BannerLoopTaskImpl adSwitchTask;
    private boolean isVertical = false;
    private OnIndicatorChangeListener onIndicatorChangeListener;

    public CBLoopScaleHelper getCbLoopScaleHelper() {
        return cbLoopScaleHelper;
    }

    public RecyclerView getViewPager() {
        return viewPager;
    }

    public long getAutoTurningTime() {
        return autoSwitchTime;
    }


    public BannerLoopTaskImpl getAdSwitchTask() {
        return adSwitchTask;
    }

    public List<T> getDatas() {
        return mDatas;
    }

    public FlexibleBanner(Context context) {
        super(context);
        init(context);
    }


    public FlexibleBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FlexibleBanner);
        isAutoSwitch = a.getBoolean(R.styleable.FlexibleBanner_auto_switch, true);
        isAloneAutoSwitch = a.getBoolean(R.styleable.FlexibleBanner_alone_auto_switch, true);
        proportion = a.getFloat(R.styleable.FlexibleBanner_aspect_ratio, -1);
        autoSwitchTime = a.getInteger(R.styleable.FlexibleBanner_distance_time, -1);
        a.recycle();
        init(context);
    }

    private void init(Context context) {
        View hView = LayoutInflater.from(context).inflate(
                R.layout.include_viewpager, this, true);
        viewPager = (RecyclerView) hView.findViewById(R.id.recyclerView);
        loPageTurningPoint = hView
                .findViewById(R.id.loPageTurningPoint);

        viewPager.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        cbLoopScaleHelper = new CBLoopScaleHelper();


    }

    public FlexibleBanner setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        viewPager.setLayoutManager(layoutManager);
        return this;
    }

    public FlexibleBanner setPages(ItmeBuildImpi holderCreator, List<T> datas) {
        this.mDatas = datas;
        pageAdapter = new FlexilbeBasePageAdapter<>(holderCreator, mDatas);
        viewPager.setAdapter(pageAdapter);

        if (page_indicatorId != null)
            setPageIndicator(page_indicatorId);

        cbLoopScaleHelper.setFirstItemPos(isAutoSwitch ? mDatas.size() : 0);
        cbLoopScaleHelper.attachToRecyclerView(viewPager);
        if (datas != null && datas.size() > 1) {
            isShowIndicator(true);
        } else {
            isShowIndicator(false);
        }
        return this;
    }

    public void update(List<T> datas) {
        this.mDatas = datas;

        pageAdapter.update(datas);


        cbLoopScaleHelper.setFirstItemPos(isAutoSwitch ? mDatas.size() : 0);
        //更新banner是判断是否只有一个对象一个对象不显示导航
        if (datas.size() > 1) {
            isShowIndicator(true);
        } else {
            isShowIndicator(false);
        }
        //更新banner是判断是否只有一个对象并且判断一个对象时是否允许轮播
        if (datas.size() < 2 && !isAloneAutoSwitch) {
            pageAdapter.setAutoSwitch(false);
        } else {
            pageAdapter.setAutoSwitch(true);
        }
        notifyDataSetChanged();
    }

    private void isShowIndicator(boolean isShow) {
        loPageTurningPoint.setVisibility(isShow ? View.VISIBLE : GONE);
    }


    public boolean isAutoSwitch() {
        return isAutoSwitch;
    }

    public void setAutoSwitch(boolean autoSwitch) {
        isAutoSwitch = autoSwitch;
        pageAdapter.setAutoSwitch(autoSwitch);
        notifyDataSetChanged();
    }

    /**
     * 通知数据变化
     */
    public void notifyDataSetChanged() {
        viewPager.getAdapter().notifyDataSetChanged();
        if (page_indicatorId != null)
            setPageIndicator(page_indicatorId);
        cbLoopScaleHelper.setCurrentItem(isAutoSwitch ? mDatas.size() : 0);
    }

    /**
     * 设置底部指示器是否可见
     *
     * @param visible
     */
    public FlexibleBanner setPointViewVisible(boolean visible) {
        loPageTurningPoint.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    /**
     * 底部指示器资源图片
     *
     * @param page_indicatorId
     */
    public FlexibleBanner setPageIndicator(int[] page_indicatorId) {
        loPageTurningPoint.removeAllViews();
        mPointViews.clear();
        this.page_indicatorId = page_indicatorId;
        if (mDatas == null)
            return this;
        for (int count = 0; count < mDatas.size(); count++) {
            // 翻页指示的点
            ImageView pointView = new ImageView(getContext());
            pointView.setPadding(5, 0, 5, 0);
            if (cbLoopScaleHelper.getFirstItemPos() % mDatas.size() == count)
                pointView.setImageResource(page_indicatorId[1]);
            else
                pointView.setImageResource(page_indicatorId[0]);
            mPointViews.add(pointView);
            loPageTurningPoint.addView(pointView);
        }
        onIndicatorChangeListener = new OnIndicatorChangeListener(mPointViews,
                page_indicatorId);
        cbLoopScaleHelper.setOnPageChangeListener(onIndicatorChangeListener);
        if (onPageChangeListener != null)
            onIndicatorChangeListener.setOnPageChangeListener(onPageChangeListener);

        return this;
    }

    public OnPageChangeListener getOnPageChangeListener() {
        return onPageChangeListener;
    }

    /**
     * 设置翻页监听器
     *
     * @param onPageChangeListener
     * @return
     */
    public FlexibleBanner setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        this.onPageChangeListener = onPageChangeListener;
        //如果有默认的监听器（即是使用了默认的翻页指示器）则把用户设置的依附到默认的上面，否则就直接设置
        if (onIndicatorChangeListener != null)
            onIndicatorChangeListener.setOnPageChangeListener(onPageChangeListener);
        else
            cbLoopScaleHelper.setOnPageChangeListener(onPageChangeListener);
        return this;
    }

    /**
     * 监听item点击
     *
     * @param onItemClickListener
     */
    public FlexibleBanner setOnItemClickListener(final BannerItemListener onItemClickListener) {
        if (onItemClickListener == null) {
            pageAdapter.setOnItemClickListener(null);
            return this;
        }
        pageAdapter.setOnItemClickListener(onItemClickListener);
        return this;
    }

    /**
     * 获取当前页对应的position
     *
     * @return
     */
    public int getCurrentItem() {
        return cbLoopScaleHelper.getRealCurrentItem();
    }

    /**
     * 设置当前页对应的position
     *
     * @return
     */
    public FlexibleBanner setCurrentItem(int position, boolean smoothScroll) {
        cbLoopScaleHelper.setCurrentItem(isAutoSwitch ? mDatas.size() + position : position, smoothScroll);
        return this;
    }

    /**
     * 设置第一次加载当前页对应的position
     * setPageIndicator之前使用
     *
     * @return
     */
    public FlexibleBanner setFirstItemPos(int position) {
        cbLoopScaleHelper.setFirstItemPos(isAutoSwitch ? mDatas.size() + position : position);
        return this;
    }

    /**
     * 指示器的方向
     *
     * @param align 三个方向：居左 （RelativeLayout.ALIGN_PARENT_LEFT），居中 （RelativeLayout.CENTER_HORIZONTAL），居右 （RelativeLayout.ALIGN_PARENT_RIGHT）
     * @return
     */
    public FlexibleBanner setPageIndicatorAlign(IndicatorConfig align) {

        LayoutParams layoutParams = (LayoutParams) loPageTurningPoint.getLayoutParams();


        if (align == IndicatorConfig.INDICATOR_orientation_TOP_RIGHT) {
            loPageTurningPoint.setGravity(Gravity.RIGHT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        } else if (align == IndicatorConfig.INDICATOR_orientation_TOP_LEFT) {
            loPageTurningPoint.setGravity(Gravity.LEFT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        } else if (align == IndicatorConfig.INDICATOR_orientation_TOP_CENTER) {
            loPageTurningPoint.setGravity(Gravity.CENTER);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        } else if (align == IndicatorConfig.INDICATOR_orientation_BOTTOM_RIGHT) {
            loPageTurningPoint.setGravity(Gravity.RIGHT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        } else if (align == IndicatorConfig.INDICATOR_orientation_BOTTOM_LEFT) {
            loPageTurningPoint.setGravity(Gravity.LEFT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        } else if (align == IndicatorConfig.INDICATOR_orientation_BOTTOM_CENTER) {
            loPageTurningPoint.setGravity(Gravity.CENTER);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        }
        loPageTurningPoint.setLayoutParams(layoutParams);

        return this;
    }


    /**
     * 设置指示器的Margin
     *
     * @param marginLeft
     * @param marginRight
     * @param marginTop
     * @param marginBottom
     * @return
     */
    public FlexibleBanner setPageIndicatorMargin(int marginLeft, int marginRight, int marginTop, int marginBottom) {
        LayoutParams layoutParams = (LayoutParams) loPageTurningPoint.getLayoutParams();
        layoutParams.leftMargin = DisplayHelper.dp2px(getContext(), marginLeft);
        layoutParams.rightMargin = DisplayHelper.dp2px(getContext(), marginRight);
        layoutParams.topMargin = DisplayHelper.dp2px(getContext(), marginTop);
        layoutParams.bottomMargin = DisplayHelper.dp2px(getContext(), marginBottom);
        loPageTurningPoint.setLayoutParams(layoutParams);
        return this;
    }

    /**
     * 设置指示器的 padding
     *
     * @param paddingLeft
     * @param paddingRight
     * @param paddingTop
     * @param paddingBottom
     * @return
     */
    public FlexibleBanner setPageIndicatorPadding(int paddingLeft, int paddingRight, int paddingTop, int paddingBottom) {
        loPageTurningPoint.setPadding(DisplayHelper.dp2px(getContext(), paddingLeft),
                DisplayHelper.dp2px(getContext(), DisplayHelper.dp2px(getContext(), paddingTop)),
                DisplayHelper.dp2px(getContext(), paddingRight), DisplayHelper.dp2px(getContext(), paddingBottom));
        return this;
    }

    /**
     * 设置轮播机制
     *
     * @param bannerLoopTask
     * @return
     */
    public FlexibleBanner setLoopTask(BannerLoopTaskImpl bannerLoopTask) {
        adSwitchTask = bannerLoopTask;
        return this;
    }


    /**
     * 开始翻页
     *
     * @return
     */
    public FlexibleBanner startTurning() {


        //没有轮播时间或者小于1的  ，不支持轮播 ， 不支持一个数据轮播的情况过滤
        if (autoSwitchTime <= 0 || !isAutoSwitch || (!isAloneAutoSwitch && pageAdapter.getRealItemCount() < 2)) {
            return this;
        }


        if (adSwitchTask == null) {
            adSwitchTask = new DefaultLoopTask(this);
        }

        if (Turning) {
            stopTurning();
        }

        Turning = true;

        /**
         * 启动延时任务
         */
        postDelayed(adSwitchTask, autoSwitchTime);
        return this;
    }

    public FlexibleBanner startTurning(long autoSwitchTime) {
        this.autoSwitchTime = autoSwitchTime;
        startTurning();
        return this;
    }


    public boolean isTurning() {
        return Turning;
    }

    public void setTurning(boolean turning) {
        Turning = turning;
    }

    public void stopTurning() {
        if (adSwitchTask != null) {
            Turning = false;
            removeCallbacks(adSwitchTask);
        }

    }

    /**
     * 触碰控件的时候，翻页应该停止，离开的时候如果之前是开启了翻页的话则重新启动翻页
     *
     * @param ev
     * @return
     */

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_OUTSIDE) {
            // 开始翻页
            if (Turning)
                startTurning();
        } else if (action == MotionEvent.ACTION_DOWN) {
            // 停止翻页
            if (Turning)
                stopTurning();
        }

        return super.dispatchTouchEvent(ev);
    }


    /**
     * 通过宽高比计算显示尺寸
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        super.onMeasure(MeasureSpec.makeMeasureSpec(widthSize, widthMode), MeasureSpec.makeMeasureSpec((int) (widthSize * proportion), widthMode));

    }


    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onActivityResume() {
        //停止轮播
        stopTurning();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onActivityPause() {
        //开始轮播
        startTurning();
    }

}
