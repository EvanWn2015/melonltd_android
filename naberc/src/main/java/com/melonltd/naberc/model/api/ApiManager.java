package com.melonltd.naberc.model.api;

import com.melonltd.naberc.model.service.SPService;
import com.melonltd.naberc.util.Tools;
import com.melonltd.naberc.vo.ReqData;

import okhttp3.Call;

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

    // 登入
    public static void login(Object req, ThreadCallback callback) {
        Call call = getClient().post(ApiUrl.LOGIN,  Tools.JSONPARSE.toJson(req));
        call.enqueue(callback);
    }





    // 輪播圖
    public static void advertisement(ThreadCallback callback) {
        Call call = getClient().postHeader(ApiUrl.ADVERTISEMENT, SPService.getOauth());
        call.enqueue(callback);
    }

    // 全部公告
    public static void bulletin(ThreadCallback callback) {
        Call call = getClient().postHeader(ApiUrl.BULLETIN, SPService.getOauth());
        call.enqueue(callback);
    }

    // 餐館列表 top30
    public static void restaurantList(String data, ThreadCallback callback) {
        Call call = getClient().postHeader(ApiUrl.RESTAURANT_LIST, SPService.getOauth(),  data);
        call.enqueue(callback);
    }

    public static void restaurantTop30(ThreadCallback callback) {
        ReqData req = new ReqData();
        req.search_type="TOP";
        Call call = getClient().postHeader(ApiUrl.RESTAURANT_LIST, SPService.getOauth(),  Tools.JSONPARSE.toJson(req));
        call.enqueue(callback);
    }

    public static void restaurantDetail(String uuid,ThreadCallback callback) {
        ReqData req = new ReqData();
        req.uuid = uuid;
        Call call = getClient().postHeader(ApiUrl.RESTAURANT_DETAIL, SPService.getOauth(),  Tools.JSONPARSE.toJson(req));
        call.enqueue(callback);
    }

    public static void restaurantFoodList(String uuid,ThreadCallback callback) {
        ReqData req = new ReqData();
        req.uuid = uuid;
        Call call = getClient().postHeader(ApiUrl.RESTAURANT_FOOD_LIST, SPService.getOauth(),  Tools.JSONPARSE.toJson(req));
        call.enqueue(callback);
    }

    public static void restaurantFoodDetail(String uuid,ThreadCallback callback) {
        ReqData req = new ReqData();
        req.uuid = uuid;
        Call call = getClient().postHeader(ApiUrl.RESTAURANT_FOOD_DETAIL, SPService.getOauth(),  Tools.JSONPARSE.toJson(req));
        call.enqueue(callback);
    }





    public static void test(ThreadCallback callback) {
        TData tt=  new TData();
        String gg = Tools.JSONPARSE.toJson(tt);
        Call call = getClient().post(ApiUrl.test, tt.data);
        call.enqueue(callback);
    }


    public static class TData{
        public String data = Tools.JSONPARSE.toJson(new TT("0987654321", "GVGhhGhb"));
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
