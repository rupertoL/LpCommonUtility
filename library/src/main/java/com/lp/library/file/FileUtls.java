package com.lp.library.file;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * @author Loren
 * Create_Time: 2019/7/8 13:45
 * description:
 */
public class FileUtls {
    private Runnable saveFileRunnable;
    private Handler messageHandler;
    private ProgressDialog mSaveDialog;
    private String filePath;
    private Bitmap mBitmap;
    private String storagePath;
    private File PARENT_PATH = Environment.getExternalStorageDirectory();
    private static String DST_PATH ;

    private static Context context;

    private FileUtls() {
    }


    private static class Holder {
        private static FileUtls fileUtls = new FileUtls();
    }


    public static FileUtls newInstance(Context context) {
        Holder.fileUtls.context = context;
        DST_PATH=  context.getPackageName();
        return Holder.fileUtls;
    }

    /**
     * 下载图片并保存
     *
     * @param contexts
     * @param filePaths
     */
    public void donwloadImg(Context contexts, String filePaths) {
        initLoadImg();
        filePath = filePaths;
        mSaveDialog = ProgressDialog.show(context, "保存图片", "图片正在保存中，请稍等...", true);
        new Thread(saveFileRunnable).start();
    }

    @SuppressLint("HandlerLeak")
    private void initLoadImg() {
        saveFileRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    if (!TextUtils.isEmpty(filePath)) { //网络图片
                        // 对资源链接
                        URL url = new URL(filePath);
                        //打开输入流
                        InputStream inputStream = url.openStream();
                        //对网上资源进行下载转换位图图片
                        mBitmap = BitmapFactory.decodeStream(inputStream);
                        inputStream.close();
                    }
                    saveImageToGallery(Holder.fileUtls.context, 90, mBitmap);
                    messageHandler.sendEmptyMessage(1);
                } catch (IOException e) {
                    messageHandler.sendEmptyMessage(2);
                    e.printStackTrace();
                } catch (Exception e) {
                    messageHandler.sendEmptyMessage(2);
                    e.printStackTrace();
                }

            }
        };
        messageHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                mSaveDialog.dismiss();

                String savemessage = msg.what == 1 ? String.format("图片保存成功，保存路径为【", initPath(), "]") : "图片保存失败";
                Toast.makeText(context, savemessage, Toast.LENGTH_SHORT).show();
            }
        };

    }

    //保存文件到指定路径
    public boolean saveImageToGallery(Context context, int quality, Bitmap bmp) {
        return saveImageToGallery(context, bmp, quality, System.currentTimeMillis() + ".jpg", Bitmap.CompressFormat.JPEG);
    }

    //保存文件到指定路径
    public boolean saveImageToGallery(Context context, Bitmap bmp, int quality, String fileName, Bitmap.CompressFormat format) {
        // 首先保存图片
        String storePath = initPath();
        File appDir = new File(storePath);
        if (!appDir.exists()) {
            appDir.mkdir();
        }

        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            //通过io流的方式来压缩保存图片
            boolean isSuccess = bmp.compress(format, quality, fos);
            fos.flush();
            fos.close();
            //保存图片后发送广播通知更新数据库
            Uri uri = Uri.fromFile(file);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            if (isSuccess) {
                Toast.makeText(context, "已保存至相册", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 初始化保存路径
     */
    public String initPath(String dstPath) {

        if ("".equals(storagePath)) {
            storagePath = String.format(PARENT_PATH.getAbsolutePath(), "/", TextUtils.isEmpty(dstPath) ? DST_PATH : dstPath);
            File f = new File(storagePath);
            if (!f.exists()) {
                f.mkdir();
            }
        }
        return storagePath;
    }

    /**
     * 初始化保存路径
     */
    public String initPath() {

        if (TextUtils.isEmpty(storagePath)) {
            storagePath = PARENT_PATH.getAbsolutePath()+ "/"+ DST_PATH;

            File f = new File(storagePath);
            if (!f.exists()) {
                f.mkdir();
            }
        }
        return storagePath;
    }


}
