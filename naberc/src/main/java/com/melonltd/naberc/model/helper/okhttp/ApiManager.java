package com.melonltd.naberc.model.helper.okhttp;

import android.util.Log;

import com.melonltd.naberc.model.api.ApiUrl;

import java.io.IOException;
import java.net.ConnectException;

import okhttp3.Call;
import okhttp3.HttpUrl;

/**
 * Created by evan on 2018/1/5.
 */

public class ApiManager {
    private static final String TAG = ApiManager.class.getSimpleName();
    private static ClientManager CLIENT_MANAGER = ClientManager.getInstance();

    public static ClientManager getClient() {
        if (CLIENT_MANAGER == null) {
            CLIENT_MANAGER = ClientManager.getInstance();
        }
        return CLIENT_MANAGER;
    }

    public static void getUserInfo(int rule_id, ApiCallback callback) {
        HttpUrl url = HttpUrl.parse(ApiUrl.userInfo).newBuilder()
                .addQueryParameter("user_id", "ecadmin")
                .addQueryParameter("apsystem", "ECA")
                .build();

        Call call = getClient().get(url);
        call.enqueue(callback);
    }

    public static <T> void test(ApiCallback callback) {
        HttpUrl url = HttpUrl.parse(ApiUrl.test).newBuilder()
                .build();
        Log.d(TAG, url.toString());
        Call call = getClient().get(url);
        call.enqueue(callback);
    }
}
