# LpCommonUtility工具类，目前只有webview适配本地文件上传和网页图片保存等功能

>##使用

>>1.配置书写如下 ：
     
···
WebView webView = WebViewConfig.with(this, null)
                .isOpenBasicFunction()
                .isDelayLoadingIamge(true)
                .isPermitMixedPrint(true)
                .isAllowSaveIamge(true)
                .isAllowCopyText(true)
                .isSupportUploadingFile(true)
                .setWebChromeClient(mDefaultWebChromeClient)
                .build();
···
                
