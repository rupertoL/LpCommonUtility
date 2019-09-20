package com.lp.library.file;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Loren
 * Create_Time: 2019/8/5 14:54
 * description:
 */
public class AssetsFilesUtils {

    public static String getAssetsData(Context context, String fileName) {
        String result = "";
        try {
            //获取输入流
            InputStream mAssets = context.getAssets().open(fileName);

            //获取文件的字节数
            int lenght = mAssets.available();
            //创建byte数组
            byte[] buffer = new byte[lenght];
            //将文件中的数据写入到字节数组中
            mAssets.read(buffer);
            mAssets.close();
            result = new String(buffer);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("fuck", "error");
            return result;
        }
    }

    public static String getAssetsData(Context context, String filePath, String fileName) {
        String result = "";
        try {
            //获取输入流
            InputStream mAssets = context.getAssets().open(TextUtils.concat(filePath, "/", fileName).toString());

            //获取文件的字节数
            int lenght = mAssets.available();
            //创建byte数组
            byte[] buffer = new byte[lenght];
            //将文件中的数据写入到字节数组中
            mAssets.read(buffer);
            mAssets.close();
            result = new String(buffer);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("fuck", "error");
            return result;
        }
    }

}
