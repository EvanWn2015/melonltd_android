package com.melonltd.naber.vo;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;

public class FoodItemVo implements Serializable{
    private static final long serialVersionUID = -2613643417174779593L;
    public String food_uuid;
    public String food_name;
    public String price;
    public String food_photo = "";

    public List<ItemVo> scopes = Lists.<ItemVo>newArrayList();
    public List<ItemVo> opts = Lists.<ItemVo>newArrayList();
    public List<DemandsItemVo> demands = Lists.<DemandsItemVo>newArrayList();


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this.getClass())
                .add("food_uuid", food_uuid)
                .add("food_name", food_name)
//				.add("photo", photo)
                .add("price", price)
                .add("scopes", scopes)
                .add("opts", opts)
                .add("demands", demands)
                .toString();
    }
}
