package com.lp.library.webView;

import android.os.Build;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * @author Loren
 * Create_Time: 2019/7/5 15:00
 * description:
 */
public class WebViewSettingUtlis {
    private static final WebViewSettingUtlis ourInstance = new WebViewSettingUtlis();

    public static WebViewSettingUtlis getInstance() {
        return ourInstance;
    }

    private WebViewSettingUtlis() {
    }


    public WebView clearWebViewTitle(WebView webView) {
        //编写 javaScript方法
        String javascript = "javascript:function hideOther() {" +
                "document.getElementsByTagName('body')[0].innerHTML;" +
                "document.getElementsByTagName('div')[0].style.display='none';" +
                "document.getElementsByTagName('div')[3].style.display='none';" +
                "document.getElementsByClassName('dropdown')[0].style.display='none';" +
                "document.getElementsByClassName('min')[0].remove();" +
                "var divs = document.getElementsByTagName('div');" +
                "var lastDiv = divs[divs.length-1];" +
                "lastDiv.remove();" +
                "document.getElementsByClassName('showme')[0].remove();" +
                "document.getElementsByClassName('nei-t3')[1].remove();}";
        //创建方法
        webView.loadUrl(javascript);
        //加载方法
        webView.loadUrl("javascript:hideOther();");
        return webView;
    }


    //优化，对于版本小于19的，不立即加载图片
    public WebView setImagesLoading(WebView webLoadImg) {
        if (Build.VERSION.SDK_INT >= 19) {
            webLoadImg.getSettings().setLoadsImagesAutomatically(true);
        } else {
            webLoadImg.getSettings().setLoadsImagesAutomatically(false);
        }
        return webLoadImg;
    }

    public WebView setBaseSeting(WebView webView) {
        // 设置加载进来的页面自适应手机屏幕
        webView.getSettings().setUseWideViewPort(true);
        //设置渲染的优先级
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        // 允许js弹出窗口
        webView.getSettings().setJavaScriptEnabled(true);
        //允许js交互
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        //缩放
        webView.getSettings().setSupportZoom(true);
        //使用内置的缩放机制，设为false时setDisplayZoomControls无效
        webView.getSettings().setBuiltInZoomControls(true);
        //使用内置的缩放机制时是否展示缩放控件
        webView.getSettings().setDisplayZoomControls(false);
        //设置页面上的文本缩放百分比，默认100
        webView.getSettings().setTextZoom(100);

        //布局相关
        //重要！布局的宽度总是与WebView控件上的设备无关像素宽度一致
        webView.getSettings().setUseWideViewPort(true);
        //允许缩小内容以适应屏幕宽度
        webView.getSettings().setLoadWithOverviewMode(true);
        //设置布局类型，会引起WebView重新布局
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // 支持多窗口
        webView.getSettings().setSupportMultipleWindows(false);

        //缓存
        //应用缓存API可用
        webView.getSettings().setAppCacheEnabled(true);
        //使用缓存的方式，默认值LOAD_DEFAULT，LOAD_NO_CACHE
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);


        //其他
        //不允许通过URl下载图片
        webView.getSettings().setBlockNetworkImage(false);
        //定位可用
        webView.getSettings().setGeolocationEnabled(true);//默认值
        //DOM存储API可用
        webView.getSettings().setDomStorageEnabled(true);

        //数据库存储API是否可用，默认值false
        webView.getSettings().setDatabaseEnabled(true);
        //允许访问文件
        webView.getSettings().setAllowFileAccess(true);


        return webView;
    }




}
