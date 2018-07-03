package com.melonltd.naber.vo;

import com.melonltd.naber.model.type.SwitchStatus;

import java.io.Serializable;

public class RestaurantCategoryRelVo implements Serializable{
    private static final long serialVersionUID = -9022043460680866466L;

    public String category_uuid;
    public String restaurant_uuid;
    public String category_name;
    public SwitchStatus status;
//
//    public String getCategory_uuid() {
//        return category_uuid;
//    }
//
//    public void setCategory_uuid(String category_uuid) {
//        this.category_uuid = category_uuid;
//    }
//
//    public String getRestaurant_uuid() {
//        return restaurant_uuid;
//    }
//
//    public void setRestaurant_uuid(String restaurant_uuid) {
//        this.restaurant_uuid = restaurant_uuid;
//    }
//
//    public String getCategory_name() {
//        return category_name;
//    }
//
//    public void setCategory_name(String category_name) {
//        this.category_name = category_name;
//    }
}
