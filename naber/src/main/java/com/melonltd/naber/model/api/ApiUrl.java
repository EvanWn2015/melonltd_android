package com.melonltd.naber.model.api;

/**
 * Created by evan on 2018/1/5.
 */

public class ApiUrl {

    public final static String DOMAIN = "http://192.168.31.252:8080/melonltd-ap";
//    public final static String DOMAIN = "http://192.168.1.104:8080/melonltd-ap";
    public final static String LOGIN = DOMAIN + "/login";
    public final static String GET_SMS_CODE = DOMAIN + "/sms/get/code";
    public final static String SMS_VERIFY_CODE = DOMAIN + "/sms/verify/code";
    public final static String USER_REGISTERED = DOMAIN  +"/registered/user";
    public final static String SELLER_REGISTERED = DOMAIN  +"/registered/seller";


    // user
    public final static String ADVERTISEMENT = DOMAIN + "/naber/advertisement";
    public final static String BULLETIN = DOMAIN + "/naber/bulletin";
    public final static String RESTAURANT_TEMPLATE = DOMAIN + "/restaurant/location/template";
    public final static String RESTAURANT_LIST = DOMAIN + "/restaurant/list";
    public final static String RESTAURANT_DETAIL = DOMAIN + "/restaurant/detail";
    public final static String RESTAURANT_FOOD_LIST = DOMAIN + "/restaurant/food/list";
    public final static String RESTAURANT_FOOD_DETAIL = DOMAIN + "/restaurant/food/detail";
    public final static String USER_ORDER_HISTORY = DOMAIN + "/user/order/history";
    public final static String FIND_ACCOUNT_INFO = DOMAIN  + "/account/find/info";
    public final static String RESEAT_PSW = DOMAIN +"/account/update/password";

    public final static String IMAGE_UPLOAD = DOMAIN +"/image/upload";





    // seller

//        public final static String test = "https://211.75.132.15:8443/api/eca/1/fen/device/list?group_id=11&query_type=4&apsystem=ECA&user_id=ecadmin&dev_categorys=SENSOR";
    public final static String test = "https://ap.melonltd.com.tw/login";
//    public final static String test = "https://www.ap.melonltd.com.tw/login";
//    public final static String test ="http://ap.melonltd.com.tw/users";

}
