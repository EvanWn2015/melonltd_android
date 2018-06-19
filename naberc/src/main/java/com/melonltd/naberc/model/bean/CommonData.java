package com.melonltd.naberc.model.bean;

import android.location.Location;

import com.google.common.collect.Lists;
import com.melonltd.naberc.vo.BulletinVo;
import com.melonltd.naberc.vo.RestaurantCategoryRelVo;

import java.util.List;

public class CommonData {

    public static List<BulletinVo> BULLETIN_VOS = Lists.<BulletinVo>newArrayList();

    public static List<RestaurantCategoryRelVo> RESTAURANT_CATEGORY_REL_LIST= Lists.newArrayList();
    public static Location LOCATION;
}
