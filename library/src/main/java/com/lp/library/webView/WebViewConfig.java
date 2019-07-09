package com.lp.library.webView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.lp.library.R;
import com.lp.library.file.FileUtls;

/**
 * @author Loren
 * Create_Time: 2019/7/5 14:49
 * description:
 */
public class WebViewConfig {

    private WebView mWebView;

    @SuppressLint("JavascriptInterface")
    private WebViewConfig(final Builder builder) {
        if(builder.webView==null){
            mWebView = new WebView(builder.mContext);
        }else{
            mWebView = builder.webView;
        }

        if (builder.isClearWebViewTitle) {
            mWebView = WebViewSettingUtlis.getInstance().clearWebViewTitle(mWebView);
        }
        if (builder.isDelayLoadingIamge) {
            mWebView = WebViewSettingUtlis.getInstance().setImagesLoading(mWebView);
        }


        if (builder.isPermitMixedPrint) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            }
        }
        if (builder.isOpenBasicFunction) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                WebViewSettingUtlis.getInstance().setBaseSeting(mWebView);
            }
        }
        if (builder.baseJavascriptInterface != null) {
            mWebView.addJavascriptInterface(builder.baseJavascriptInterface, builder.baseJavascriptInterface.getAliasName());
        }


        if (builder.onLongClickListener == null) {

            mWebView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (builder.isAllowSaveIamge) {
                        final WebView.HitTestResult hitTestResult = mWebView.getHitTestResult();
                        // 如果是图片类型或者是带有图片链接的类型
                        if (hitTestResult.getType() == android.webkit.WebView.HitTestResult.IMAGE_TYPE ||
                                hitTestResult.getType() == android.webkit.WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
                            // 弹出保存图片的对话框
                            // 获取到图片地址后做相应的处理
                            //获取图片链接
                            final String picUrl = hitTestResult.getExtra();
                            DilogUtlis.showNormalMoreButtonDialog((Activity) builder.mContext, "提示", "是否保存图片？",
                                    R.drawable.ic_launcher, "取消", "保存",
                                    new DilogClickListenter() {
                                        @Override
                                        public void cancel(DialogInterface dialogInterface) {
                                            dialogInterface.dismiss();
                                        }

                                        @Override
                                        public void affirm(DialogInterface dialogInterface) {

                                            if (builder.webViewSaveImageListenter != null) {
                                                builder.webViewSaveImageListenter.SaveImage(picUrl);
                                            } else {
                                                FileUtls.newInstance(builder.mContext).donwloadImg(builder.mContext,picUrl);
                                                Toast.makeText(builder.mContext, "请实现保存监听方法", Toast.LENGTH_SHORT).show();
                                            }
                                            dialogInterface.dismiss();
                                        }
                                    }, true);
                            return true;
                        }
                    }


                    if (!builder.isAllowCopyText) {
                        return true;
                    }

                    return false;//保持长按可以复制文字
                }
            });
        } else {
            mWebView.setOnLongClickListener(builder.onLongClickListener);
        }

        if (builder.isSupportUploadingFile && builder.defaultWebChromeClient != null) {
            mWebView.setWebChromeClient(builder.defaultWebChromeClient);
        }


    }


    private WebView getWebView() {
        return mWebView;
    }

    public static WebViewConfig.Builder with(Context context, WebView webView) {
        return new WebViewConfig.Builder(context, webView);
    }

    public static class Builder {
        private Context mContext;
        private boolean isClearWebViewTitle;
        private boolean isDelayLoadingIamge;
        private boolean isPermitMixedPrint;

        private boolean isOpenBasicFunction;

        private boolean isAllowSaveIamge;
        private boolean isAllowCopyText;
        private boolean isSupportUploadingFile;
        private WebView webView;
        private View.OnLongClickListener onLongClickListener;
        private WebViewSaveImageListenter webViewSaveImageListenter;
        private DefaultWebChromeClient defaultWebChromeClient;
        private BaseJavascriptInterface baseJavascriptInterface;

        private Builder(Context context, WebView webView) {
            this.mContext = context;
            this.webView = webView;
        }

        /**
         * 是否清理缓存
         *
         * @param isClear
         * @return
         */
        public Builder isClearWebViewTitle(boolean isClear) {
            this.isClearWebViewTitle = isClear;
            return this;
        }

        /**
         * 是否延迟加载图片
         *
         * @param isDelay
         * @return
         */
        public Builder isDelayLoadingIamge(boolean isDelay) {
            this.isDelayLoadingIamge = isDelay;
            return this;
        }

        /**
         * 是否支持混排
         *
         * @param isPermit
         * @return
         */
        public Builder isPermitMixedPrint(boolean isPermit) {
            this.isPermitMixedPrint = isPermit;
            return this;
        }


        /**
         * 初始化基础设置
         *
         * @return
         */
        public Builder isOpenBasicFunction() {
            this.isOpenBasicFunction = true;
            return this;
        }

        /**
         * 是否支持保存图片
         *
         * @param isAllow
         * @return
         */
        public Builder isAllowSaveIamge(boolean isAllow) {
            this.isAllowSaveIamge = isAllow;
            return this;
        }

        /**
         * 是否支持复制文件
         *
         * @param isAllow
         * @return
         */
        public Builder isAllowCopyText(boolean isAllow) {
            this.isAllowCopyText = isAllow;
            return this;
        }


        /**
         * @param isSupport 是否支持上传文件，默认为false ，
         *                  如果设置为true使得功能可用必须设置setWebChromeClient(DefaultWebChromeClient defaultWebChromeClient)
         * @return
         */
        public Builder isSupportUploadingFile(boolean isSupport) {
            this.isSupportUploadingFile = isSupport;
            return this;
        }

        /**
         * @return
         */
        public Builder setWebChromeClient(DefaultWebChromeClient defaultWebChromeClient) {
            this.defaultWebChromeClient = defaultWebChromeClient;
            return this;
        }

        /**
         * @param onLongClickListener 页面长安效果设置该属性将导致isAllowSaveIamge与isAllowCopyText设置无效
         * @return
         */
        public Builder setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
            this.onLongClickListener = onLongClickListener;
            return this;
        }

        /**
         * 下载图片保存
         *
         * @param webViewSaveImageListenter
         * @return
         */
        public Builder setWebViewSaveImageListenter(WebViewSaveImageListenter webViewSaveImageListenter) {
            this.webViewSaveImageListenter = webViewSaveImageListenter;
            return this;
        }


        /**
         * 与js交互的本地类
         *
         * @param baseJavascriptInterface
         * @return
         */
        public Builder addJavascriptInterface(BaseJavascriptInterface baseJavascriptInterface) {
            this.baseJavascriptInterface = baseJavascriptInterface;
            return this;
        }

        public WebView build() {
            if (mContext == null) {
                throw new IllegalArgumentException("Context must not be null.");
            }


            return new WebViewConfig(this).getWebView();
        }


    }


}
