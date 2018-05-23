package com.melonltd.naberc.view.customize;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.melonltd.naberc.R;

public class ShoppingDetailItem {
    private View view;
    private TextView nameText, totalAmountText, bonusText;
    private LinearLayout layout;
    private Button deleteBtn, submitBtn;

    private ShoppingDetailItem setParameter(String name, String totalAmount, String bonus) {
        nameText.setText(name);
        totalAmountText.setText(totalAmount);
        bonusText.setText(bonus);
        return this;
    }

    public static Builder Builder(Context context) {
        return new Builder(context);
    }

    public void addOrdersItemsViews(View view) {
        this.layout.addView(view);
    }

    public View getView() {
        return this.view;
    }


    public static class Builder {
        private ShoppingDetailItem item;
        private String name = "", totalAmount = "", bonus = "";

        public Builder(Context context) {
            item = new ShoppingDetailItem();
            View v = LayoutInflater.from(context).inflate(R.layout.user_shopping_detail_item, null);
            this.item.nameText = v.findViewById(R.id.restaurantNameText);
            this.item.totalAmountText = v.findViewById(R.id.ordersTotalAmountText);
            this.item.bonusText = v.findViewById(R.id.bonusText);
            this.item.layout = v.findViewById(R.id.ordersItemsLayout);
            this.item.deleteBtn = v.findViewById(R.id.deleteOrdersBtn);
            this.item.submitBtn = v.findViewById(R.id.submitOrdersBtn);
            this.item.view = v;
        }

        public Builder setName(String name) {
            return this;
        }

        public Builder setTotalAmount(String totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        public Builder setBonus(String bonus) {
            this.bonus = bonus;
            return this;
        }

        public Builder setTotalAmount(int totalAmount) {
            this.totalAmount = "" + totalAmount;
            return this;
        }

        public Builder setBonus(int bonus) {
            this.bonus = "" + bonus;
            return this;
        }

        public Builder setDeleteListener(View.OnClickListener deleteListener) {
            this.item.deleteBtn.setOnClickListener(deleteListener);
            return this;
        }

        public Builder setSubmitListener(View.OnClickListener submitListener) {
            this.item.submitBtn.setOnClickListener(submitListener);
            return this;
        }

        public Builder addOrdersItemsViews(View view) {
            this.item.layout.addView(view);
            return this;
        }

        public ShoppingDetailItem build() {
            return this.item.setParameter(this.name, this.totalAmount, this.bonus);
        }

    }
}
