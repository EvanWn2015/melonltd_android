package com.melonltd.naberc.vo;

import java.io.Serializable;
import java.util.List;

import com.google.common.base.MoreObjects;

public class OrderDetail implements Serializable{

	private static final long serialVersionUID = -5397846887657210105L;
	public String restaurant_uuid;
	public String fetch_date;
	public String user_message;
	public List<OrderData> orders;


	
	
	public static OrderDetail ofOrders (List<OrderData> orders) {
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
	

	public class OrderData implements Serializable{
		private static final long serialVersionUID = -7710327114343945469L;
		public String category_uuid;
		public String food_uuid;
		public FoodItemVo item;

//		public String getCategory_uuid() {
//			return category_uuid;
//		}
//
//		public void setCategory_uuid(String category_uuid) {
//			this.category_uuid = category_uuid;
//		}
//
//		public String getFood_uuid() {
//			return food_uuid;
//		}
//
//		public void setFood_uuid(String food_uuid) {
//			this.food_uuid = food_uuid;
//		}
//
//		public FoodItemVo getItem() {
//			return item;
//		}
//
//		public void setItem(FoodItemVo item) {
//			this.item = item;
//		}
		
		@Override
		public String toString() {
			return MoreObjects.toStringHelper(this.getClass())
					.add("category_uuid", category_uuid)
					.add("food_uuid", food_uuid)
					.add("item", item)
					.toString();
		}

	}
}
