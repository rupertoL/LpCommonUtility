package com.lp.library.webView;

import android.content.Context;

/**
 * @author Loren
 * Create_Time: 2019/7/5 16:58
 * description:
 */
public abstract class BaseJavascriptInterface {
    Context context;


    public BaseJavascriptInterface(Context c) {
        context = c;
    }


    public abstract String getAliasName();
}
