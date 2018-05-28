package com.melonltd.naberc.view.user.page.impl;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
            ApiManager.test(new ApiCallback(getActivity()) {
                @Override
                public void onSuccess(String responseBody) {
                    testLoadData(true);
                }

                @Override
                public void onFail(Exception error) {

                }
            });
            container.setTag(R.id.user_shopping_cart_page, v);
            return v;
        }
        return (View) container.getTag(R.id.user_shopping_cart_page);
    }


    // TODO test
    private void testLoadData(boolean isRefresh) {
        if (isRefresh) {
            list.clear();
            adapter.notifyDataSetChanged();
        }
        for (int i = 0; i < 15; i++) {
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
            ShoppingDetailItemHolder holder = null;
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.user_shopping_detail_item, null);
                holder = new ShoppingDetailItemHolder(view);
                view.setTag(holder);
            } else {
                holder = (ShoppingDetailItemHolder) view.getTag();
            }

            holder.setName("test name" + i)
                    .setTotalAmount(200 * (1 + i))
                    .setBonus(20 * (1 + i))
                    .setSubView(i)
                    .setDeleteListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    })
                    .setSubmitListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).build();


            return view;
        }

        class ShoppingDetailItemHolder {
            private TextView nameText, totalAmountText, bonusText;
            private LinearLayout layout;
            private Button deleteBtn, submitBtn;
            private String name = "", totalAmount = "", bonus = "";

            ShoppingDetailItemHolder(View v) {
                this.nameText = v.findViewById(R.id.restaurantNameText);
                this.totalAmountText = v.findViewById(R.id.ordersTotalAmountText);
                this.bonusText = v.findViewById(R.id.bonusText);
                this.layout = v.findViewById(R.id.ordersItemsLayout);
                this.deleteBtn = v.findViewById(R.id.deleteOrdersBtn);
                this.submitBtn = v.findViewById(R.id.submitOrdersBtn);
            }

            private ShoppingDetailItemHolder setName(String name) {
                this.name = name;
                return this;
            }

            private ShoppingDetailItemHolder setTotalAmount(int totalAmount) {
                this.totalAmount = "" + totalAmount;
                return this;
            }

            private ShoppingDetailItemHolder setBonus(int bonus) {
                this.bonus = "" + bonus;
                return this;
            }

            private ShoppingDetailItemHolder setDeleteListener(View.OnClickListener deleteListener) {
                this.deleteBtn.setOnClickListener(deleteListener);
                return this;
            }

            private ShoppingDetailItemHolder setSubmitListener(View.OnClickListener submitListener) {
                this.submitBtn.setOnClickListener(submitListener);
                return this;
            }

            private ShoppingDetailItemHolder setSubView(int i) {
                this.layout.removeAllViews();
                for (int j = 0; j < i; j++) {
                    View sebView = LayoutInflater.from(context).inflate(R.layout.user_shopping_order_item, null);
                    new OrderSubItemHolder(sebView)
                            .setName("subName")
                            .setDeleteListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Log.d(TAG, "delete btn ");
                                }
                            })
                            .setIconPath("http://i.epochtimes.com/assets/uploads/2017/09/Fotolia_58802987_Subscription_L-600x400.jpg")
                            .setPrice(20)
                            .setQuantity(1)
                            .setScope("規格:" + "\n" + "追加")
                            .build();
                    this.layout.addView(sebView);
                }
                return this;
            }

            private void build() {
                nameText.setText(name);
                totalAmountText.setText(totalAmount);
                bonusText.setText("應得紅利" + bonus);
            }

            class OrderSubItemHolder {
                private SimpleDraweeView iconImageView;
                private TextView nameText, scopeText;
                private ImageButton minusBtn, addBtn, deleteBtn;
                private TextView quantityText, priceText;
                public int quantity = 0, price = 0;
                private String name = "", scope = "";


                OrderSubItemHolder(View v) {
                    this.iconImageView = v.findViewById(R.id.ordersItemIconImageView);
                    this.nameText = v.findViewById(R.id.ordersItemNameText);
                    this.scopeText = v.findViewById(R.id.ordersItemScopeText);
                    this.minusBtn = v.findViewById(R.id.ordersItemMinusBtn);
                    this.addBtn = v.findViewById(R.id.ordersItemAddBtn);
                    this.deleteBtn = v.findViewById(R.id.ordersItemDeleteBtn);
                    this.quantityText = v.findViewById(R.id.ordersItemQuantityText);
                    this.priceText = v.findViewById(R.id.ordersItemPriceText);
                }


                private OrderSubItemHolder setName(String name) {
                    this.name = name;
                    return this;
                }

                private OrderSubItemHolder setScope(String scope) {
                    this.scope = scope;
                    return this;
                }

                private OrderSubItemHolder setQuantity(int quantity) {
                    this.quantity = quantity < 1 ? 1 : quantity;
                    return this;
                }

                private OrderSubItemHolder setIconPath(String path) {
                    Uri uri = Uri.parse(path);
                    setIconPath(uri);
                    return this;
                }

                private OrderSubItemHolder setIconPath(Uri uri) {
                    this.iconImageView.setImageURI(uri);
                    return this;
                }

                private OrderSubItemHolder setPrice(int price) {
                    this.price = price;
                    return this;
                }

                private OrderSubItemHolder setDeleteListener(View.OnClickListener listener) {
                    this.deleteBtn.setOnClickListener(listener);
                    return this;
                }

                private void setAddAndMinusListener() {
                    this.addBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            quantity++;
                            if (quantity > 50) {
                                quantity = 50;
                            }
                            setPriceComp();
                        }
                    });
                    this.minusBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            quantity--;
                            if (quantity <= 0) {
                                quantity = 1;
                            }
                            setPriceComp();
                        }
                    });
                }

                private void setPriceComp() {
                    int sun = this.price * this.quantity;
                    this.quantityText.setText(this.quantity + "");
                    this.priceText.setText(sun + "");
                }

                private OrderSubItemHolder build() {
                    this.nameText.setText(name);
                    this.scopeText.setText(scope);
                    this.quantityText.setText(quantity + "");
                    setAddAndMinusListener();
                    setPriceComp();
                    return this;
                }
            }
        }
    }


}
