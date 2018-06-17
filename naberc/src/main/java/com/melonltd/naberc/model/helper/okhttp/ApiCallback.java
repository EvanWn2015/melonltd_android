package com.melonltd.naberc.model.helper.okhttp;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import com.bigkoo.alertview.AlertView;
import com.melonltd.naberc.model.service.Base64Service;
import com.melonltd.naberc.util.Tools;
import com.melonltd.naberc.view.customize.LoadingBar;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public abstract class ApiCallback implements Callback {
    private static final String TAG = ApiCallback.class.getSimpleName();
//    private static LoadingBar BAR;

    abstract public void onSuccess(final String responseBody);

    abstract public void onFail(final Exception error);

    private Activity activity;

    public ApiCallback(Activity activity) {
        this.activity = activity;
//        this.BAR = new LoadingBar(activity);
    }

    public ApiCallback(Context context) {
        this.activity = (Activity) context;
        if (context instanceof Activity){
            Log.d(TAG, "");
        }
//        this.BAR = new LoadingBar(activity);
    }


    @Override
    public void onFailure(Call call, final IOException e) {
        this.activity.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        // 如果是 network 錯誤
                        if (checkNetWork()) {
//                            BAR.hide();
                            getAlertView().show();
                            return;
                        }
                        e.printStackTrace();
                        if (e.getMessage().contains("Canceled") || e.getMessage().contains("Socket closed")) {
                            Log.e(TAG, "fail", e);
                        } else {
                            onFail(e);
                        }
//                        BAR.hide();
                    }
                });
    }

    @Override
    public void onResponse(final Call call, final Response response) throws IOException {
        if (!response.isSuccessful() || response.body() == null) {
            onFailure(call, new IOException("Failed"));
            return;
        }
        this.activity.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final String resp = Base64Service.decryptBASE64(response.body().string());
                            onSuccess(resp);
                        } catch (IOException e) {
                            Log.e(TAG, "fail", e);
                            onFailure(call, new IOException("Failed"));
                        }
//                        BAR.hide();
                    }
                });
    }


    public boolean checkNetWork() {
        ConnectivityManager cm = (ConnectivityManager) this.activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        return !Tools.NETWORK.hasNetWork(cm);
    }
//    private void runOnUiThread(Runnable task) {
//        new Handler(Looper.getMainLooper()).post(task);
//    }

    private AlertView getAlertView() {
        return new AlertView.Builder()
                .setContext(this.activity)
                .setStyle(AlertView.Style.Alert)
                .setTitle("網路連線錯誤")
                .setMessage("請檢查 Wi-Fi 或 4G是否已連接。")
                .setDestructive("確定")
                .build()
                .setCancelable(true);
    }
}
