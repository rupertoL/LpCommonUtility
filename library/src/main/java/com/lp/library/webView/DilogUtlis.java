package com.lp.library.webView;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

/**
 * @author Loren
 * Create_Time: 2019/7/5 16:05
 * description:
 */
public class DilogUtlis {

    public static void showNormalMoreButtonDialog(Activity activity, String title, String message,
                                                  @DrawableRes int icon, String cancel, String affirm,
                                                  final DilogClickListenter dilogClickListenter, final boolean isTimelyDimiss) {
        AlertDialog.Builder normalMoreButtonDialog = new AlertDialog.Builder(activity);
        normalMoreButtonDialog.setTitle(TextUtils.isEmpty(title) ? "提示!" : title);
        normalMoreButtonDialog.setIcon(icon);
        normalMoreButtonDialog.setMessage(TextUtils.isEmpty(message) ? "确认继续操作吗" : message);

        //设置按钮
        normalMoreButtonDialog.setPositiveButton(affirm
                , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dilogClickListenter.cancel();
                        dialog.dismiss();
                    }
                });
        normalMoreButtonDialog.setNegativeButton(cancel
                , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dilogClickListenter.affirm(dialog);
                        if (isTimelyDimiss) {

                            dialog.dismiss();
                        }
                    }
                });

        normalMoreButtonDialog.create().show();
    }
}
