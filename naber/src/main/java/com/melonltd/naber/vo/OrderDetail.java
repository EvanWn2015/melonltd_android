package com.melonltd.naber.vo;

import com.google.common.base.MoreObjects;

import java.io.Serializable;
import java.util.List;

public class OrderDetail implements Serializable {

    private static final long serialVersionUID = -5397846887657210105L;
    public String restaurant_uuid;
    public String restaurant_name = "";
    public String restaurant_address;
    public String user_name;
    public String user_phone;
    public String fetch_date;
    public String user_message;
    public List<OrderData> orders;

    public static OrderDetail ofOrders(List<OrderData> orders) {
        OrderDetail data = new OrderDetail();
        data.orders = orders;
        return data;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this.getClass())
                .add("restaurant_uuid", restaurant_uuid)
                .add("fetch_date", fetch_date)
                .add("user_message", user_message)
                .add("orders", orders)
                .toString();
    }

    public static class OrderData implements Serializable {
        private static final long serialVersionUID = -7710327114343945469L;
        public int count;
        public String category_uuid;
        public FoodItemVo item = new FoodItemVo();

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this.getClass())
                    .add("category_uuid", category_uuid)
                    .add("count", count)
                    .add("item", item)
                    .toString();
        }

    }
}
