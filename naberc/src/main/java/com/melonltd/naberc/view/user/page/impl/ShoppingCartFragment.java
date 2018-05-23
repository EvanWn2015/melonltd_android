package com.melonltd.naberc.view.user.page.impl;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.common.collect.Lists;
import com.melonltd.naberc.R;
import com.melonltd.naberc.model.helper.okhttp.ApiCallback;
import com.melonltd.naberc.model.helper.okhttp.ApiManager;
import com.melonltd.naberc.view.common.abs.AbsPageFragment;
import com.melonltd.naberc.view.customize.GlideImageLoader;
import com.melonltd.naberc.view.customize.OnLoadLayout;

import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class ShoppingCartFragment extends AbsPageFragment {
    private static final String TAG = ShoppingCartFragment.class.getSimpleName();
    private static ShoppingCartFragment FRAGMENT = null;
    private OnLoadLayout onLoadLayout;
    private ListView listView;
    private ShoppingCartAdapter adapter;
    private List<String> list = Lists.newArrayList();

    public ShoppingCartFragment() {
    }

    @Override
    public AbsPageFragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new ShoppingCartFragment();
            FRAGMENT.setArguments(bundle);
        }
        return FRAGMENT;
    }

    @Override
    public AbsPageFragment newInstance(Object... o) {
        return new ShoppingCartFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(getContext());
        adapter = new ShoppingCartAdapter(getContext(), list);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container.getTag(R.id.user_shopping_cart_page) == null) {
            View v = inflater.inflate(R.layout.fragment_shopping_cart, container, false);
            getViews(v);
            setListener();
            testLoadData(true);
            container.setTag(R.id.user_shopping_cart_page, v);
            return v;
        }
        return (View) container.getTag(R.id.user_shopping_cart_page);
    }


    // TODO test
    private void testLoadData(boolean isRefresh) {
        if (isRefresh) {
            list.clear();
        }
        for (int i = 0; i < 100; i++) {
            list.add("" + i);
        }
        adapter.notifyDataSetChanged();
    }

    private void getViews(View v) {
        onLoadLayout = v.findViewById(R.id.shoppingCartOnLoadLayout);
        listView = v.findViewById(R.id.shoppingCartListView);
        listView.setAdapter(adapter);
    }

    private void setListener() {
        onLoadLayout.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {

                ApiManager.test(new ApiCallback(getActivity()) {
                    @Override
                    public void onSuccess(String responseBody) {
                        testLoadData(true);
                        onLoadLayout.refreshComplete();
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFail(Exception error) {

                    }
                });

            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return ((OnLoadLayout) frame).isTop();
            }
        });

        onLoadLayout.setOnLoadListener(new OnLoadLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                ApiManager.test(new ApiCallback(getActivity()) {
                    @Override
                    public void onSuccess(String responseBody) {
                        testLoadData(false);
                        onLoadLayout.refreshComplete();
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFail(Exception error) {

                    }
                });
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    class ShoppingCartAdapter extends BaseAdapter {
        private Context context;
        private List<String> list;
        private List<ShoppingDetailItem> items = Lists.newArrayList();

        ShoppingCartAdapter(Context context, List<String> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Nullable
        @Override
        public CharSequence[] getAutofillOptions() {
            return new CharSequence[0];
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ShoppingDetailItem item = null;
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.user_shopping_detail_item, null);
                item = new ShoppingDetailItem().Builder(view).build();
                view.setTag(item);
            } else {
                item = (ShoppingDetailItem) view.getTag();
            }

            List<View> ordersItems = Lists.newArrayList();
            for (int j = 0; j <= i; j++) {
                View subV = LayoutInflater.from(context).inflate(R.layout.user_shopping_order_item, null);
                new ShoppingCartOrderItem()
                        .Builder(subV)
                        .setIconPath("http://i3fresh.tw/upload/product/f_a8f0cd10efd5efe21fad620302e5a30f.jpg")
                        .setName("test Order" + j)
                        .setOpt("追加")
                        .setScope("規格")
                        .setQuantity(1)
                        .setPrice((j + 1) * 20)
                        .setDeleteListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.d(TAG, "Delete");
                            }
                        }).build();
                ordersItems.add(subV);
            }

//            item.addOrdersItemsViews(OrdersItems);
            item.setName("Test name" + i)
                    .setBonus(200 * i)
                    .addOrdersItemsViews(ordersItems)
                    .setTotalAmount(400 * i)
                    .setSubmitListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    })
                    .setDeleteListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    }).build();


            // TODO 要做的事
            return view;
        }
    }


    static class ShoppingDetailItem {
        private TextView nameText, totalAmountText, bonusText;
        private LinearLayout layout;
        private Button deleteBtn, submitBtn;
        private String name = "", totalAmount = "", bonus = "";


        private Builder Builder(View view) {
            return new Builder(view);
        }

        private ShoppingDetailItem setName(String name) {
            this.name = name;
            return this;
        }

        private ShoppingDetailItem setTotalAmount(int totalAmount) {
            this.totalAmount = "" + totalAmount;
            return this;
        }

        private ShoppingDetailItem setBonus(int bonus) {
            this.bonus = "" + bonus;
            return this;
        }

        private ShoppingDetailItem setDeleteListener(View.OnClickListener deleteListener) {
            this.deleteBtn.setOnClickListener(deleteListener);
            return this;
        }

        private ShoppingDetailItem setSubmitListener(View.OnClickListener submitListener) {
            this.submitBtn.setOnClickListener(submitListener);
            return this;
        }

        private ShoppingDetailItem addOrdersItemsViews(@NonNull List<View> views) {
            for(View v : views){
                this.layout.addView(v);
            }
            return this;
        }

        private void build() {
            nameText.setText(name);
            totalAmountText.setText(totalAmount);
            bonusText.setText(bonus);
        }

        private static class Builder {
            private ShoppingDetailItem item;
            public Builder(View v) {
                item = new ShoppingDetailItem();
                this.item.nameText = v.findViewById(R.id.restaurantNameText);
                this.item.totalAmountText = v.findViewById(R.id.ordersTotalAmountText);
                this.item.bonusText = v.findViewById(R.id.bonusText);
                this.item.layout = v.findViewById(R.id.ordersItemsLayout);
                this.item.deleteBtn = v.findViewById(R.id.deleteOrdersBtn);
                this.item.submitBtn = v.findViewById(R.id.submitOrdersBtn);
            }
            private ShoppingDetailItem build() {
                return this.item;
            }
        }
    }

    static class ShoppingCartOrderItem {
        private SimpleDraweeView iconImageView;
        private TextView nameText, scopeText, optText;
        private ImageButton minusBtn, addBtn, deleteBtn;
        private TextView quantityText, priceText;
        public int quantity = 0, price = 0;

        public void setAddAndMinusListener() {
            this.addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    quantity++;
                    if (quantity > 50) {
                        quantity = 50;
                    }
                    setPrice();
                }
            });
            this.minusBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    quantity--;
                    if (quantity <= 0) {
                        quantity = 1;
                    }
                    setPrice();
                }
            });
        }

        public void setPrice() {
            int sun = this.price * this.quantity;
            this.quantityText.setText(this.quantity + "");
            this.priceText.setText(sun + "");
        }

        public ShoppingCartOrderItem setParameter(String name, String scope, String opt, int quantity, int price) {
            this.quantity = quantity;
            this.price = price;
            this.nameText.setText(name);
            this.scopeText.setText(scope);
            this.optText.setText(opt);
            this.quantityText.setText(quantity + "");
            setAddAndMinusListener();
            setPrice();
            return this;
        }

        public Builder Builder(View view) {
            return new Builder(view);
        }

        public static class Builder {
            private ShoppingCartOrderItem item;
            private String name = "", scope = "", opt = "";
            private int quantity = 1, price = 0;

            public Builder(View v) {
                this.item = new ShoppingCartOrderItem();
                this.item.iconImageView = v.findViewById(R.id.ordersItemIconImageView);
                this.item.nameText = v.findViewById(R.id.ordersItemNameText);
                this.item.scopeText = v.findViewById(R.id.ordersItemScopeText);
                this.item.optText = v.findViewById(R.id.ordersItemOptText);
                this.item.minusBtn = v.findViewById(R.id.ordersItemMinusBtn);
                this.item.addBtn = v.findViewById(R.id.ordersItemAddBtn);
                this.item.deleteBtn = v.findViewById(R.id.ordersItemDeleteBtn);
                this.item.quantityText = v.findViewById(R.id.ordersItemQuantityText);
                this.item.priceText = v.findViewById(R.id.ordersItemPriceText);
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

            public Builder setIconPath(String path) {
                Uri uri = Uri.parse(path);
                setIconPath(uri);
                return this;
            }

            public Builder setIconPath(Uri uri) {
                this.item.iconImageView.setImageURI(uri);
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
}
