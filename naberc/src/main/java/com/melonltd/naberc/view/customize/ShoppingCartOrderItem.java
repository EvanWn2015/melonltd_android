package com.melonltd.naberc.view.customize;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.melonltd.naberc.R;

import java.io.Serializable;
//shoppingDetailItem.addOrdersItemsView(LayoutInflater.from(this.context).inflate(R.layout.user_shopping_order_item, null));

public class ShoppingCartOrderItem implements Serializable {
    private static final long serialVersionUID = -8136368397118297325L;
    private static final String TAG = ShoppingCartOrderItem.class.getSimpleName();
    private View view;
    private ImageView iconImageView;
    private TextView nameText, scopeText, optText;
    private ImageButton minusBtn, addBtn, deleteBtn;
    private TextView quantityText, priceText;
    private int quantity = 0, price = 0;

    private void setAddAndMinusListener() {
        this.minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity <= 0) {
                    quantity = 1;
                }
                quantityText.setText("" + quantity);
                setPrice();
            }
        });

        this.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity > 50) {
                    quantity = 50;
                }
                quantityText.setText("" + quantity);
                setPrice();
            }
        });
    }

    private void setPrice() {
        int sun = this.price * this.quantity;
        this.priceText.setText(sun + "");
    }

    public ShoppingCartOrderItem setParameter(String name, String scope, String opt, int quantity, int price) {
        this.quantity = quantity;
        this.price = price;
        this.nameText.setText(name);
        this.scopeText.setText(scope);
        this.optText.setText(opt);
        setAddAndMinusListener();
        setPrice();
        return this;
    }

    public View getView() {
        return this.view;
    }


    public static class Builder {
        private ShoppingCartOrderItem item;
        private String name = "", scope = "", opt = "";
        private int quantity = 1, price = 0;

        public Builder(Context context) {
            this.item = new ShoppingCartOrderItem();
            View v = LayoutInflater.from(context).inflate(R.layout.user_shopping_order_item, null);
            this.item.iconImageView = v.findViewById(R.id.ordersItemIconImageView);
            this.item.nameText = v.findViewById(R.id.ordersItemNameText);
            this.item.scopeText = v.findViewById(R.id.ordersItemScopeText);
            this.item.optText = v.findViewById(R.id.ordersItemOptText);
            this.item.minusBtn = v.findViewById(R.id.ordersItemMinusBtn);
            this.item.addBtn = v.findViewById(R.id.ordersItemAddBtn);
            this.item.deleteBtn = v.findViewById(R.id.ordersItemDeleteBtn);
            this.item.quantityText = v.findViewById(R.id.ordersItemQuantityText);
            this.item.priceText = v.findViewById(R.id.ordersItemPriceText);
            this.item.view = v;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setScope(String scope) {
            this.scope = scope;
            return this;
        }

        public Builder setOpt(String opt) {
            this.opt = opt;
            return this;
        }

        public Builder setQuantity(int quantity) {
            this.quantity = quantity < 1 ? 1 : quantity;
            return this;
        }

        public Builder setPrice(int price) {
            this.price = price;
            return this;
        }

        public Builder setDeleteListener(View.OnClickListener listener) {
            this.item.deleteBtn.setOnClickListener(listener);
            return this;
        }

        public ShoppingCartOrderItem build() {
            return this.item.setParameter(this.name, this.scope, this.opt, this.quantity, this.price);
        }
    }
}
