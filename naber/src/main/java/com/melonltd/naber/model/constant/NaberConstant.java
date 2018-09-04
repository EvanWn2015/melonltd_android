package com.melonltd.naber.model.constant;

import com.google.common.collect.Lists;

import java.util.List;

public class NaberConstant {
    public static boolean IS_DEBUG = false;

    public static long REMEMBER_DAY = 1000 * 60 * 60 * 24 * 7L * 2;
//    public static long SELLER_STAT_REFRESH_TIMER = IS_DEBUG ? 1000 * 10L : 1000 * 60 * 60 * 10L;
//    public static long SELLER_ORDER_REFRESH_TIMER = IS_DEBUG ? 1000 * 10L : 1000 * 60 * 60 * 10L;

    public static long SELLER_STAT_REFRESH_TIMER =  1000 * 60 * 10L;
    public static long SELLER_LIVE_ORDER_REFRESH_TIMER = 1000 * 60 * 5L;
//    public static long SELLER_LIVE_ORDER_REFRESH_TIMER = 1000 * 10L;
    public static int PAGE = 10;

//    public static final String[] FILTER_CATEGORYS = new String[]{"火鍋", "燒烤/居酒屋", "鐵板燒", "素蔬食", "早午餐", "下午茶", "西式/牛排", "中式", "港式", "日式", "韓式", "異國", "美式", "義式", "熱炒", "小吃", "泰式", "咖啡輕食", "甜點", "冰飲"};
    public static String[] FILTER_CATEGORYS = new String[]{"早午餐", "西式/牛排", "中式", "日式", "冰飲"};
//    public static final String[] FILTER_AREAS = new String[]{"桃園區", "中壢區", "平鎮區", "龍潭區", "楊梅區", "新屋區", "觀音區", "龜山區", "八德區", "大溪區", "大園區", "蘆竹區", "復興區"};
    public static final String[] FILTER_AREAS = new String[]{"桃園區", "中壢區", "平鎮區","楊梅區", "龜山區", "八德區", "蘆竹區"};
    public static List<String> HOUR_OPT = Lists.newArrayList("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
    public static List<String> MINUTE_OPT = Lists.newArrayList("00", "30");

    public static final String FIREBASE_ACCOUNT = "naber_android@gmail.com";
    public static final String FIREBASE_PSW = "melonltd1102";
    public static final String STORAGE_PATH = "gs://naber-20180622.appspot.com";
    public static final String STORAGE_PATH_USER = "/user/";
    public static final String STORAGE_PATH_FOOD = "/restaurant/food/";

    // Bundle key
    public static final String RESTAURANT_INFO = "RESTAURANT_INFO";
    public static final String CATEGORY_NAME = "CATEGORY_NAME";
    public static final String RESTAURANT_CATEGORY_REL = "RESTAURANT_CATEGORY_REL";
    public static final String ORDER_INFO = "ORDER_INFO";
    public static final String FOOD_INFO = "FOOD_INFO";
    public static final String SIMPLE_INFO = "USER_SIMPLE_INFO";
    public static final String TOOLBAR_TITLE = "TOOLBAR_TITLE";
    public static final String ACCOUNT_INFO = "ACCOUNT_INFO";



    public static final String ORDER_DETAIL_INDEX = "ORDER_DETAIL_INDEX";
    public static final String SELLER_STAT_LOGS_DETAIL = "SELLER_STAT_LOGS_DETAIL";
    public static final String SELLER_CATEGORY_NAME = "SELLER_CATEGORY_NAME";
    public static final String SELLER_CATEGORY_UUID = "SELLER_CATEGORY_UUID";
    public static final String SELLER_FOOD_INFO = "SELLER_FOOD_INFO";


    //
    public static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSS'Z'";
}
