package com.example.zivbru.myfamilycalanderfinal;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;

/**
 * Created by zivbru on 5/9/2016.
 */
public class ProgressCircle {

    private ProgressDialog progressBar;
    private int progressBarStatus = 0;
    private Handler progressBarbHandler = new Handler();
    public ProgressCircle() {
    }

    public void startProgressBar(String msg,Context context) {
        progressBar = new ProgressDialog(context);
        progressBar.setCancelable(true);
        progressBar.setMessage(msg);
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        progressBar.show();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        progressBar.dismiss();
    }
}


