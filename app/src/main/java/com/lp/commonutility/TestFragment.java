package com.lp.commonutility;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.lp.library.webView.DefaultWebChromeClient;
import com.lp.library.webView.WebViewConfig;

/**
 * @author Loren
 * Create_Time: 2019/7/8 9:37
 * description:
 */
public class TestFragment extends Fragment {

    private View mRootView;
    private DefaultWebChromeClient mDefaultWebChromeClient;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_test,container,false);

        return mRootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FrameLayout fl_root = mRootView.findViewById(R.id.fl_web_root);

        mDefaultWebChromeClient = new DefaultWebChromeClient(this);
        WebView webView = WebViewConfig.with(getContext(), null)
                .isOpenBasicFunction()
                .isDelayLoadingIamge(true)
                .isPermitMixedPrint(true)
                .isAllowSaveIamge(true)
                .isAllowCopyText(true)
                .isSupportUploadingFile(true)
                .setWebChromeClient(mDefaultWebChromeClient)
                .build();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        webView.setLayoutParams(params);
        fl_root.addView(webView);

        webView.loadUrl("http://cyydh5.shoppingyizhan.com/privateRoom/privateRoom?token=AT-642-cq6Ivc3u75xkfrOHpW47qRsacN5vTvIOCyE");

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mDefaultWebChromeClient.Result(requestCode, resultCode, data);
    }
}
