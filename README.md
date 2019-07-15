# LpCommonUtility工具类，目前只有webview适配本地文件上传和网页图片保存等功能

## 使用

>>1.配置书写如下 ：
     
>>>初始化webView

     WebView webView = WebViewConfig.with(this, null)
     

                .isOpenBasicFunction()
                
                .isDelayLoadingIamge(true) //是否延迟加载图片
                
                .isPermitMixedPrint(true) //是否支持混排
                
                .isAllowSaveIamge(true) //是否支持保存网页图片
                
                .isAllowCopyText(true) //是否支持网页复制文字
                
                .isSupportUploadingFile(true) //持上传文件，默认为false为true是配合setWebChromeClient使用
                
                .setWebChromeClient(mDefaultWebChromeClient)
                
                .build();

     
>>>初始化WebChromeClient 默认使用DefaultWebChromeClient  如果需要扩展继承DefaultWebChromeClient将减少您实现部分功能的代码
 

                mDefaultWebChromeClient = new DefaultWebChromeClient(this,1);
                
>>>获取到文件的处理如下 ：继承或者使用DefaultWebChromeClient您将不用管里html获取文件已经获取到文件后的相关处理，并且requestCode可以根据您的需要在初始化DefaultWebChromeClient是指定，resultCode的值为Activiyt或者Fragment中的 RESULT_OK值

               @Override
                protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                    super.onActivityResult(requestCode, resultCode, data);
                     //权限管理
                    sult(requestCode, resultCode, data);
               }
 
#### 自己项目中经常用到，经常写重复代码比较麻烦就简单封装到这个工具项目中方面自己使用，也乐意提供给大家参考，后续也会将陆续将功能整理的通用一点放入这个项目
