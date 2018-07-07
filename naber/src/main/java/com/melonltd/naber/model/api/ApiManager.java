package com.melonltd.naber.model.api;

import com.melonltd.naber.model.service.Base64Service;
import com.melonltd.naber.model.service.SPService;
import com.melonltd.naber.util.Tools;
import com.melonltd.naber.vo.AccountInfoVo;
import com.melonltd.naber.vo.CategoryFoodRelVo;
import com.melonltd.naber.vo.OrderDetail;
import com.melonltd.naber.vo.ReqData;
import com.melonltd.naber.vo.RestaurantInfoVo;

import java.util.Map;

import okhttp3.Call;

/**
 * Created by evan on 2018/1/5.
 */

public class ApiManager {
//    private static final String TAG = ApiManager.class.getSimpleName();
    private static ClientManager CLIENT_MANAGER = ClientManager.getInstance();

    public static ClientManager getClient() {
        if (CLIENT_MANAGER == null) {
            CLIENT_MANAGER = ClientManager.getInstance();
        }
        return CLIENT_MANAGER;
    }

    /**
     * 以下為共用 API
     */
    // 取得驗證碼
    public static void getSMSCode(Map<String, String> map, ThreadCallback callback) {
        Call call = getClient().post(ApiUrl.GET_SMS_CODE, Base64Service.encryptBASE64(Tools.JSONPARSE.toJson(map)));
        call.enqueue(callback);
    }

    // 驗證SMS密碼
    public static void verifySMSCode(Map<String, String> map, ThreadCallback callback) {
        Call call = getClient().post(ApiUrl.SMS_VERIFY_CODE, Base64Service.encryptBASE64(Tools.JSONPARSE.toJson(map)));
        call.enqueue(callback);
    }

    // 使用者註冊
    public static void userRegistered(AccountInfoVo req, ThreadCallback callback) {
        Call call = getClient().post(ApiUrl.USER_REGISTERED, Base64Service.encryptBASE64(Tools.JSONPARSE.toJson(req)));
        call.enqueue(callback);
    }

    // 商家註冊
    public static void sellerRegistered(Map<String, String> map, ThreadCallback callback) {
        Call call = getClient().post(ApiUrl.SELLER_REGISTERED, Base64Service.encryptBASE64(Tools.JSONPARSE.toJson(map)));
        call.enqueue(callback);
    }

    // 登入
    public static void login(AccountInfoVo req, ThreadCallback callback) {
        Call call = getClient().post(ApiUrl.LOGIN, Base64Service.encryptBASE64(Tools.JSONPARSE.toJson(req)));
        call.enqueue(callback);
    }


    /**
     * 以下為使用者是使用 API
     */
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

    // 取得餐館地理位置模板
    public static void restaurantTemplate(ApiCallback callback) {
        Call call = getClient().postHeader(ApiUrl.RESTAURANT_TEMPLATE, SPService.getOauth());
        call.enqueue(callback);
    }

    // 餐館列表 TOP, AREA, CATEGORY, DISTANCE
    public static void restaurantList(ReqData req, ThreadCallback callback) {
        Call call = getClient().postHeader(ApiUrl.RESTAURANT_LIST, SPService.getOauth(), Base64Service.encryptBASE64(Tools.JSONPARSE.toJson(req)));
        call.enqueue(callback);
    }

    // 餐館細節，系列列表
    public static void restaurantDetail(String uuid, ThreadCallback callback) {
        ReqData req = new ReqData();
        req.uuid = uuid;
        Call call = getClient().postHeader(ApiUrl.RESTAURANT_DETAIL, SPService.getOauth(), Base64Service.encryptBASE64(Tools.JSONPARSE.toJson(req)));
        call.enqueue(callback);
    }

    // 系列下品項列表
    public static void restaurantFoodList(String uuid, ThreadCallback callback) {
        ReqData req = new ReqData();
        req.uuid = uuid;
        Call call = getClient().postHeader(ApiUrl.RESTAURANT_FOOD_LIST, SPService.getOauth(), Base64Service.encryptBASE64(Tools.JSONPARSE.toJson(req)));
        call.enqueue(callback);
    }

    // 品項細節
    public static void restaurantFoodDetail(String uuid, ThreadCallback callback) {
        ReqData req = new ReqData();
        req.uuid = uuid;
        Call call = getClient().postHeader(ApiUrl.RESTAURANT_FOOD_DETAIL, SPService.getOauth(), Base64Service.encryptBASE64(Tools.JSONPARSE.toJson(req)));
        call.enqueue(callback);
    }

    // 使用者訂單記錄
    public static void userOrderHistory(ReqData req, ThreadCallback callback) {
        Call call = getClient().postHeader(ApiUrl.USER_ORDER_HISTORY, SPService.getOauth(), Base64Service.encryptBASE64(Tools.JSONPARSE.toJson(req)));
        call.enqueue(callback);
    }

    // 使用者資訊
    public static void userFindAccountInfo(ThreadCallback callback) {
        Call call = getClient().postHeader(ApiUrl.FIND_ACCOUNT_INFO, SPService.getOauth());
        call.enqueue(callback);
    }

    // 上傳圖片
    public static void uploadPhoto(ReqData req, ThreadCallback callback) {
        Call call = getClient().postHeader(ApiUrl.IMAGE_UPLOAD, SPService.getOauth(), Base64Service.encryptBASE64(Tools.JSONPARSE.toJson(req)));
        call.enqueue(callback);
    }

    // 更新密碼
    public static void reseatPassword(Map<String, String> req, ThreadCallback callback) {
        Call call = getClient().postHeader(ApiUrl.RESEAT_PSW, SPService.getOauth(), Base64Service.encryptBASE64(Tools.JSONPARSE.toJson(req)));
        call.enqueue(callback);
    }

    // 忘記密碼
    public static void forgetPassword(Map<String, String> req, ThreadCallback callback) {
        Call call = getClient().postHeader(ApiUrl.FORGET_PSW, SPService.getOauth(), Base64Service.encryptBASE64(Tools.JSONPARSE.toJson(req)));
        call.enqueue(callback);
    }

    // 提交訂單
    public static void userOrderSubmit(OrderDetail req, ThreadCallback callback) {
        Call call = getClient().postHeader(ApiUrl.ORDER_SUBMIT, SPService.getOauth(), Base64Service.encryptBASE64(Tools.JSONPARSE.toJson(req)));
        call.enqueue(callback);
    }


    /////// SELLSE API /////

    // 取得每日營業時段
    public static void sellerBusinessTime(ThreadCallback callback) {
        Call call = getClient().postHeader(ApiUrl.BUSINESS_TIME, SPService.getOauth());
        call.enqueue(callback);
    }

    // 更新每日營業時段
    public static void sellerChangeBusinessTime(RestaurantInfoVo req, ThreadCallback callback) {
        Call call = getClient().postHeader(ApiUrl.CHANGE_BUSINESS_TIME, SPService.getOauth(), Base64Service.encryptBASE64(Tools.JSONPARSE.toJson(req)));
        call.enqueue(callback);
    }

    // 快速查詢訂單
    public static void sellerQuickSearch(Map<String, String> req, ThreadCallback callback) {
        Call call = getClient().postHeader(ApiUrl.QUICK_SEARCH, SPService.getOauth(), Base64Service.encryptBASE64(Tools.JSONPARSE.toJson(req)));
        call.enqueue(callback);
    }

    // 更改訂單狀況
    public static void sellerChangeOrder(ReqData req, ThreadCallback callback) {
        Call call = getClient().postHeader(ApiUrl.CHANGE_ORDER, SPService.getOauth(), Base64Service.encryptBASE64(Tools.JSONPARSE.toJson(req)));
        call.enqueue(callback);
    }

    // 取得訂單列表
    public static void sellerOrderList(ReqData req, ThreadCallback callback) {
        Call call = getClient().postHeader(ApiUrl.ORDER_LIST, SPService.getOauth(), Base64Service.encryptBASE64(Tools.JSONPARSE.toJson(req)));
        call.enqueue(callback);
    }

    // 取得營運概況
    public static void sellerStat(ThreadCallback callback) {
        Call call = getClient().postHeader(ApiUrl.SELLER_STAT, SPService.getOauth());
        call.enqueue(callback);
    }

    // 取得營運概況已完成訂單列表
    public static void sellerStatLog(ReqData req, ThreadCallback callback) {
        Call call = getClient().postHeader(ApiUrl.SELLER_STAT_LOG, SPService.getOauth(), Base64Service.encryptBASE64(Tools.JSONPARSE.toJson(req)));
        call.enqueue(callback);
    }

    // 取得種類列表
    public static void sellerCategoryList(ThreadCallback callback) {
        Call call = getClient().postHeader(ApiUrl.SELLER_CATEGORY_LIST, SPService.getOauth());
        call.enqueue(callback);
    }

    //    {"uuid":"RESTAURANT_CATEGORY_db001826-9169-4230-a747-c6d9f8d0a582","status":"open"}
    // 新增種類
    public static void sellerAddCategory(ReqData req, ThreadCallback callback) {
        Call call = getClient().postHeader(ApiUrl.SELLER_ADD_CATEGORY, SPService.getOauth(), Base64Service.encryptBASE64(Tools.JSONPARSE.toJson(req)));
        call.enqueue(callback);
    }

    // 更新種類狀態
    public static void sellerChangeCategoryStatus(ReqData req, ThreadCallback callback) {
        Call call = getClient().postHeader(ApiUrl.SELLER_CHANGE_CATEGORY, SPService.getOauth(), Base64Service.encryptBASE64(Tools.JSONPARSE.toJson(req)));
        call.enqueue(callback);
    }

    // 刪除種類
    public static void sellerDeleteCategory(ReqData req, ThreadCallback callback) {
        Call call = getClient().postHeader(ApiUrl.SELLER_DELETE_CATEGORY, SPService.getOauth(), Base64Service.encryptBASE64(Tools.JSONPARSE.toJson(req)));
        call.enqueue(callback);
    }

    // 品項列表
    public static void sellerFoodList(ReqData req, ThreadCallback callback) {
        Call call = getClient().postHeader(ApiUrl.SELLER_FOOD_LIST, SPService.getOauth(), Base64Service.encryptBASE64(Tools.JSONPARSE.toJson(req)));
        call.enqueue(callback);
    }

    // 品項更新
    public static void sellerFoodUpdate(CategoryFoodRelVo req, ThreadCallback callback) {
        Call call = getClient().postHeader(ApiUrl.SELLER_CHANGE_FOOD, SPService.getOauth(), Base64Service.encryptBASE64(Tools.JSONPARSE.toJson(req)));
        call.enqueue(callback);
    }

    // 品項刪除
    public static void sellerFoodDelete(ReqData req, ThreadCallback callback) {
        Call call = getClient().postHeader(ApiUrl.SELLER_DELETE_FOOD, SPService.getOauth(), Base64Service.encryptBASE64(Tools.JSONPARSE.toJson(req)));
        call.enqueue(callback);
    }

    // 品項刪除
    public static void sellerFoodAdd(CategoryFoodRelVo req, ThreadCallback callback) {
        Call call = getClient().postHeader(ApiUrl.SELLER_ADD_FOOD, SPService.getOauth(), Base64Service.encryptBASE64(Tools.JSONPARSE.toJson(req)));
        call.enqueue(callback);
    }

    // 取得餐館資訊
    public static void sellerRestaurantInfo(ThreadCallback callback) {
        Call call = getClient().postHeader(ApiUrl.SELLER_RESTAURANT_INFO, SPService.getOauth());
        call.enqueue(callback);
    }

    // 設定餐館資訊
    public static void sellerRestaurantSetting(RestaurantInfoVo req, ThreadCallback callback) {
        Call call = getClient().postHeader(ApiUrl.SELLER_RESTAURANT_SETTING, SPService.getOauth(), Base64Service.encryptBASE64(Tools.JSONPARSE.toJson(req)));
        call.enqueue(callback);
    }

    // 設定餐館隔日接單開關
    public static void sellerRestaurantSettingBusiness(Map<String, String> req, ThreadCallback callback) {
        Call call = getClient().postHeader(ApiUrl.SELLER_RESTAURANT_SETTING_BUSINESS, SPService.getOauth(), Base64Service.encryptBASE64(Tools.JSONPARSE.toJson(req)));
        call.enqueue(callback);
    }

}
