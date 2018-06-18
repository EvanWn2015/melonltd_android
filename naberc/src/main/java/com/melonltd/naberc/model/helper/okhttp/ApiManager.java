package com.melonltd.naberc.model.helper.okhttp;

import com.melonltd.naberc.model.api.ApiUrl;
import com.melonltd.naberc.util.Tools;

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

    public static void login(Object req, ApiCallback callback) {
        Call call = getClient().post(ApiUrl.LOGIN,  Tools.GSON.toJson(req));
        call.enqueue(callback);
    }



    public static void test(ApiCallback callback) {
        TData tt=  new TData();
        String gg = Tools.GSON.toJson(tt);
        Call call = getClient().post(ApiUrl.test, tt.data);
        call.enqueue(callback);
    }


    public static class TData{
        public String data = Tools.GSON.toJson(new TT("0987654321", "GVGhhGhb"));
    }

    public static class TT {

        private String password;
        private String phone;

        public TT(String phone, String password){
            this.phone = phone;
            this.password = password;
        }
    }

}
