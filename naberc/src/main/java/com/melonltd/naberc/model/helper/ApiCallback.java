package com.melonltd.naberc.model.helper;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.melonltd.naberc.view.customize.LoadingBar;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public abstract class ApiCallback implements Callback {
    private static final String TAG = ApiCallback.class.getSimpleName();
    private LoadingBar loadingBar;
    abstract public void onSuccess(final String responseBody);
    abstract public void onFail(final Exception error);

    public ApiCallback(Context context) {
        this.loadingBar = new LoadingBar(context, true);
    }

    @Override
    public void onFailure(Call call, final IOException e) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                e.printStackTrace();
                if (e.getMessage().contains("Canceled") || e.getMessage().contains("Socket closed")) {
                    Log.e(TAG, "fail", e);
                } else {
                    onFail(e);
                }
                loadingBar.hide();
            }
        });
    }

    @Override
    public void onResponse(final Call call, final Response response) throws IOException {
        if (!response.isSuccessful() || response.body() == null) {
            onFailure(call, new IOException("Failed"));
            return;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    onSuccess(response.body().string());
                } catch (IOException e) {
                    Log.e(TAG, "fail", e);
                    onFailure(call, new IOException("Failed"));
                }
                loadingBar.hide();
            }
        });
    }

    private void runOnUiThread(Runnable task) {
        new Handler(Looper.getMainLooper()).post(task);
    }

}
