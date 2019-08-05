package com.lp.commonutility;

import android.app.Application;

import com.lp.library.webView.X5WebViewSettingUtlis;

/**
 * @author Loren
 * Create_Time: 2019/8/5 10:31
 * description:
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        X5WebViewSettingUtlis.getInstance().initX5(getApplicationContext());
    }
}
