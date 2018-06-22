package com.melonltd.naber.model.bean;

import android.location.Location;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.melonltd.naber.vo.CategoryFoodRelVo;
import com.melonltd.naber.vo.OrderDetail;
import com.melonltd.naber.vo.OrderVo;
import com.melonltd.naber.vo.RestaurantCategoryRelVo;
import com.melonltd.naber.vo.RestaurantInfoVo;
import com.melonltd.naber.vo.RestaurantTemplate;

import java.util.List;
import java.util.Map;

public class Model {

    public static List<String> OPT_ITEM_1 = Lists.newArrayList();
    public static List<List<String>> OPT_ITEM_2 = Lists.newArrayList();

    public static Map<String, Map<String, String>> BULLETIN_VOS = Maps.<String, Map<String, String>>newHashMap();
    public static List<String> BANNER_IMAGES = Lists.<String>newArrayList();
    public static Location LOCATION;
    public static List<List<RestaurantTemplate>> RESTAURANT_TEMPLATE = Lists.<List<RestaurantTemplate>>newArrayList();
    public static List<RestaurantInfoVo> RESTAURANT_INFO_LIST = Lists.<RestaurantInfoVo>newArrayList();
    public static List<RestaurantCategoryRelVo> RESTAURANT_CATEGORY_REL_LIST = Lists.<RestaurantCategoryRelVo>newArrayList();
    public static List<RestaurantInfoVo> RESTAURANT_INFO_FILTER_LIST = Lists.<RestaurantInfoVo>newArrayList();
    public static List<CategoryFoodRelVo> CATEGORY_FOOD_REL_LIST = Lists.<CategoryFoodRelVo>newArrayList();
    public static List<OrderVo> USER_ORDER_HISTORY_LIST = Lists.newArrayList();

    // key restaurant_uuid
    public static Map<String, OrderDetail> USER_CACH_SHOPPING_CART = Maps.<String, OrderDetail>newHashMap();

}
