package com.melonltd.naberc.model.api;

import com.melonltd.naberc.model.service.Base64Service;
import com.melonltd.naberc.model.service.SPService;
import com.melonltd.naberc.util.Tools;
import com.melonltd.naberc.vo.AccountInfoVo;
import com.melonltd.naberc.vo.ReqData;

import java.util.Map;

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

    // 取得驗證碼
    public static void getSMSCode(Map<String,String> map, ThreadCallback callback) {
        Call call = getClient().post(ApiUrl.GET_SMS_CODE,  Base64Service.encryptBASE64(Tools.JSONPARSE.toJson(map)));
        call.enqueue(callback);
    }

    // 驗證SMS密碼
    public static void verifySMSCode(Map<String,String> map, ThreadCallback callback) {
        Call call = getClient().post(ApiUrl.SMS_VERIFY_CODE,  Base64Service.encryptBASE64(Tools.JSONPARSE.toJson(map)));
        call.enqueue(callback);
    }

    // 使用者註冊
    public static void userRegistered(AccountInfoVo req, ThreadCallback callback) {
        Call call = getClient().post(ApiUrl.USER_REGISTERED,  Base64Service.encryptBASE64(Tools.JSONPARSE.toJson(req)));
        call.enqueue(callback);
    }

    // 使用者註冊
    public static void sellerRegistered(AccountInfoVo req, ThreadCallback callback) {
        Call call = getClient().post(ApiUrl.SELLER_REGISTERED,  Base64Service.encryptBASE64(Tools.JSONPARSE.toJson(req)));
        call.enqueue(callback);
    }

    // 登入
    public static void login(Object req, ThreadCallback callback) {
        Call call = getClient().post(ApiUrl.LOGIN,  Base64Service.encryptBASE64(Tools.JSONPARSE.toJson(req)));
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

    public static void restaurantTemplate(ApiCallback callback) {
        Call call = getClient().postHeader(ApiUrl.RESTAURANT_TEMPLATE, SPService.getOauth());
        call.enqueue(callback);
    }

    // 餐館列表 TOP, AREA, CATEGORY, DISTANCE
    public static void restaurantList(ReqData req, ThreadCallback callback) {
        Call call = getClient().postHeader(ApiUrl.RESTAURANT_LIST, SPService.getOauth(),  Base64Service.encryptBASE64(Tools.JSONPARSE.toJson(req)));
        call.enqueue(callback);
    }

    public static void restaurantDetail(String uuid,ThreadCallback callback) {
        ReqData req = new ReqData();
        req.uuid = uuid;
        Call call = getClient().postHeader(ApiUrl.RESTAURANT_DETAIL, SPService.getOauth(),  Base64Service.encryptBASE64(Tools.JSONPARSE.toJson(req)));
        call.enqueue(callback);
    }

    public static void restaurantFoodList(String uuid,ThreadCallback callback) {
        ReqData req = new ReqData();
        req.uuid = uuid;
        Call call = getClient().postHeader(ApiUrl.RESTAURANT_FOOD_LIST, SPService.getOauth(),  Base64Service.encryptBASE64(Tools.JSONPARSE.toJson(req)));
        call.enqueue(callback);
    }

    public static void restaurantFoodDetail(String uuid,ThreadCallback callback) {
        ReqData req = new ReqData();
        req.uuid = uuid;
        Call call = getClient().postHeader(ApiUrl.RESTAURANT_FOOD_DETAIL, SPService.getOauth(),  Base64Service.encryptBASE64(Tools.JSONPARSE.toJson(req)));
        call.enqueue(callback);
    }

    public static void userOrderHistory(ReqData req, ThreadCallback callback) {
        Call call = getClient().postHeader(ApiUrl.USER_ORDER_HISTORY, SPService.getOauth(),  Base64Service.encryptBASE64(Tools.JSONPARSE.toJson(req)));
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
