package com.lp.library.webView;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.Locale;

/**
 * @author Loren
 * Create_Time: 2019/7/5 16:43
 * description:
 */
public class DefaultWebChromeClient extends WebChromeClient {


    private ValueCallback<Uri> mUploadMessage;
    private android.webkit.ValueCallback<Uri[]> mUploadCallbackAboveL;

    private int FILECHOOSER_RESULTCODE = 0;
    private WeakReference<Activity> mActivityReference;
    private WeakReference<Fragment> mFragmentReference;
    private Uri imageUri;
    File file;

    public DefaultWebChromeClient(Activity activity, ValueCallback<Uri> uploadMessage, android.webkit.ValueCallback<Uri[]> uploadCallbackAboveL, int resultCode, Uri imageUri, File file) {

        this.mUploadCallbackAboveL = uploadCallbackAboveL;
        this.mUploadMessage = uploadMessage;
        this.mActivityReference = new WeakReference<>(activity);
        this.FILECHOOSER_RESULTCODE = resultCode;
        this.imageUri = imageUri;
        this.file = file;

    }

    public DefaultWebChromeClient(Fragment fragment, ValueCallback<Uri> uploadMessage, android.webkit.ValueCallback<Uri[]> uploadCallbackAboveL, int resultCode, Uri imageUri, File file) {

        this.mUploadCallbackAboveL = uploadCallbackAboveL;
        this.mUploadMessage = uploadMessage;
        this.mFragmentReference = new WeakReference<>(fragment);
        this.FILECHOOSER_RESULTCODE = resultCode;
        this.imageUri = imageUri;
        this.file = file;
    }

    public DefaultWebChromeClient(Activity activity) {
        this.mActivityReference = new WeakReference<>(activity);
        initPath();
    }
    public DefaultWebChromeClient(Fragment fragment) {
        this.mFragmentReference = new WeakReference<>(fragment);
        initPath();
    }
    private  void initPath(){
        String filePath = Environment.getExternalStorageDirectory() + File.separator
                + Environment.DIRECTORY_PICTURES + File.separator;
        String fileName = "IMG_" + DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
        file = new File(filePath + fileName);
        imageUri = Uri.fromFile(file);
    }



    // For Android 3.0+
    public void openFileChooser(ValueCallback<Uri> uploadMsg) {
        if (mUploadMessage != null) {
            mUploadMessage.onReceiveValue(null);
        }
        mUploadMessage = uploadMsg;
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("*/*");
        startActivityForResult(i);
    }

    // For Android 3.0+
    public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
        if (mUploadMessage != null) {
            mUploadMessage.onReceiveValue(null);
        }
        mUploadMessage = uploadMsg;
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        String type = TextUtils.isEmpty(acceptType) ? "*/*" : acceptType;
        i.setType(type);
        startActivityForResult(i);
    }

    // For Android 4.1
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
        if (mUploadMessage != null) {
            mUploadMessage.onReceiveValue(null);
        }
        mUploadMessage = uploadMsg;
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        String type = TextUtils.isEmpty(acceptType) ? "*/*" : acceptType;
        i.setType(type);
        startActivityForResult(i);
    }

    //Android 5.0+
    @Override
    @SuppressLint("NewApi")
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
        if (mUploadMessage != null) {
            mUploadMessage.onReceiveValue(null);
        }
        mUploadCallbackAboveL = filePathCallback;
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        if (fileChooserParams != null && fileChooserParams.getAcceptTypes() != null
                && fileChooserParams.getAcceptTypes().length > 0) {
            i.setType(fileChooserParams.getAcceptTypes()[0]);
        } else {
            i.setType("*/*");
        }
        startActivityForResult(i);
        return true;
    }



    private void startActivityForResult(Intent i) {

        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (captureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            /*获取当前系统的android版本号*/
            int currentapiVersion = android.os.Build.VERSION.SDK_INT;
            Log.e("currentapiVersion", "currentapiVersion====>" + currentapiVersion);
            if (currentapiVersion < 24) {
                captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                //startActivityForResult(intent, FILECHOOSER_RESULTCODE);
            } else {
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
                Uri uri = getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                //startActivityForResult(intent, FILECHOOSER_RESULTCODE);
            }
        } else {
            Toast.makeText(getContext(), "照相机不存在", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mActivityReference != null) {
            Activity activity = mActivityReference.get();
            if (activity != null) {
                activity.startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
            }
        } else if (mFragmentReference != null) {
            Fragment fragment = mFragmentReference.get();
            if (fragment != null) {
                fragment.startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
            }
        }

    }


    private Context getContext() {



        if (mActivityReference != null) {
            Activity activity = mActivityReference.get();
            return activity;
        }else{
            Fragment fragment = mFragmentReference.get();
            return fragment.getContext();
        }
    }


    private void updatePhotos() {
        // 该广播即使多发（即选取照片成功时也发送）也没有关系，只是唤醒系统刷新媒体文件
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(imageUri);
        getContext().sendBroadcast(intent);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent data) {
        if (requestCode != FILECHOOSER_RESULTCODE
                || mUploadCallbackAboveL == null) {
            return;
        }

        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (data == null) {
                results = new Uri[]{imageUri};
            } else {
                String dataString = data.getDataString();
                ClipData clipData = data.getClipData();

                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }

                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        if (results != null) {
            mUploadCallbackAboveL.onReceiveValue(results);
            mUploadCallbackAboveL = null;
        } else {
            results = new Uri[]{imageUri};
            mUploadCallbackAboveL.onReceiveValue(results);
            mUploadCallbackAboveL = null;
        }

        return;
    }


    public void Result(int requestCode, int resultCode, Intent data) {
        if (requestCode == FILECHOOSER_RESULTCODE) {
            updatePhotos();
            if (null == mUploadMessage && null == mUploadCallbackAboveL)
                return;
            Uri result = data == null || resultCode != Activity.RESULT_OK ? null : data.getData();
            if (mUploadCallbackAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data);
            } else if (mUploadMessage != null) {
                Log.e("result", result + "");
                if (result == null) {
                    //                        mUploadMessage.onReceiveValue(imageUri);
                    mUploadMessage.onReceiveValue(imageUri);
                    mUploadMessage = null;

                    Log.e("imageUri", imageUri + "");
                } else {
                    mUploadMessage.onReceiveValue(result);
                    mUploadMessage = null;
                }

            }
        }
    }



}
