package com.melonltd.naber.view.user.page;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.google.common.collect.Lists;
import com.melonltd.naber.R;
import com.melonltd.naber.model.api.ApiManager;
import com.melonltd.naber.model.api.ThreadCallback;
import com.melonltd.naber.model.bean.Model;
import com.melonltd.naber.model.constant.NaberConstant;
import com.melonltd.naber.util.Tools;
import com.melonltd.naber.view.customize.NaberCheckButton;
import com.melonltd.naber.view.customize.NaberRadioButton;
import com.melonltd.naber.view.factory.PageType;
import com.melonltd.naber.view.user.UserMainActivity;
import com.melonltd.naber.vo.CategoryFoodRelVo;
import com.melonltd.naber.vo.DemandsItemVo;
import com.melonltd.naber.vo.ItemVo;
import com.melonltd.naber.vo.OrderDetail;

import java.util.List;


public class MenuDetailFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = MenuDetailFragment.class.getSimpleName();
    public static MenuDetailFragment FRAGMENT = null;
    private TextView totalAmountText;
    private TextView quantityEditText;
    private int totalAmount = 0;
    private LinearLayout contentLayout;
    private OrderDetail.OrderData orderData = new OrderDetail.OrderData();

    public MenuDetailFragment() {
    }

    public Fragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new MenuDetailFragment();
        }
        if (bundle != null) {
            FRAGMENT.setArguments(bundle);
        }
        return FRAGMENT;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container.getTag(R.id.user_menu_detail_page) == null) {
            View v = inflater.inflate(R.layout.fragment_menu_detail, container, false);
            getViews(v);
            container.setTag(R.id.user_menu_detail_page, v);
            return v;
        }
        return (View) container.getTag(R.id.user_menu_detail_page);
    }

    private void getViews(View v) {
        contentLayout = v.findViewById(R.id.menuDetailContentLinearLayout);
        quantityEditText = v.findViewById(R.id.quantityEditText);
        totalAmountText = v.findViewById(R.id.totalAmountText);

        Button addToShopCartBtn = v.findViewById(R.id.addToShopCartBtn);
        ImageButton addBtn = v.findViewById(R.id.addBtn);
        ImageButton minusBtn = v.findViewById(R.id.minusBtn);

        // setListener
        addBtn.setOnClickListener(new AddMinusClickListener());
        minusBtn.setOnClickListener(new AddMinusClickListener());
        addToShopCartBtn.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        UserMainActivity.changeTabAndToolbarStatus();
        if (UserMainActivity.toolbar != null) {
            UserMainActivity.navigationIconDisplay(true, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UserMainActivity.navigationIconDisplay(false, null);
                    CategoryMenuFragment.TO_MENU_DETAIL_INDEX = -1;
                    UserMainActivity.removeAndReplaceWhere(FRAGMENT, PageType.CATEGORY_MENU, null);
                }
            });
        }

        contentLayout.removeAllViews();
        orderData.count = 1;
        totalAmount = 0;

        final CategoryFoodRelVo vo = (CategoryFoodRelVo) getArguments().getSerializable(NaberConstant.FOOD_INFO);
        if (vo != null) {
            UserMainActivity.toolbar.setTitle(vo.food_name);
            contentLayout.removeAllViews();
            orderData = new OrderDetail.OrderData();
            orderData.count = 1;
            ApiManager.restaurantFoodDetail(vo.food_uuid, new ThreadCallback(getContext()) {
                @Override
                public void onSuccess(String responseBody) {
                    CategoryFoodRelVo food = Tools.JSONPARSE.fromJson(responseBody, CategoryFoodRelVo.class);
                    setScopeView(food.food_data.scopes);
                    setDemandView(food.food_data.demands);
                    setOptView(food.food_data.opts);
                    countTotalAmount();
                }

                @Override
                public void onFail(Exception error, String msg) {
                    Log.i(TAG, msg);
                }
            });
        }
    }

    // 計算價格
    private void countTotalAmount() {
        int scopes = Integer.parseInt(orderData.item.scopes.get(0).price);
        int optPrice = 0;
        for (ItemVo opt : orderData.item.opts) {
            optPrice += Integer.parseInt(opt.price);
        }
        quantityEditText.setText(orderData.count + "");
        totalAmountText.setText(((scopes + optPrice) * orderData.count) + "$");
    }

    private void setScopeView(final List<ItemVo> scopes) {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.menu_detail_scope, null);
        RadioGroup group = v.findViewById(R.id.scopeRadioGroup);
        for (int i = 0; i < scopes.size(); i++) {
            if (i == 0) {
                orderData.item.scopes.add(scopes.get(i));
            }
            RadioButton radio = new NaberRadioButton().Builder(getContext())
                    .setTitle(scopes.get(i).name)
                    .setPrice(scopes.get(i).price)
                    .setSymbol("$")
                    .setTag(scopes.get(i))
                    .setId(i + 669696)
                    .setChecked(i == 0 ? true : false)
                    .build();
            group.addView(radio);
        }

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radio = group.findViewById(checkedId);
                orderData.item.scopes.clear();
                orderData.item.scopes.add((ItemVo) radio.getTag());
                countTotalAmount();
            }
        });
        contentLayout.addView(v);
    }

    private void setDemandView(final List<DemandsItemVo> demands) {
        for (int i = 0; i < demands.size(); i++) {
            View v = LayoutInflater.from(getContext()).inflate(R.layout.menu_detail_demand, null);
            TextView demandText = v.findViewById(R.id.demandText);
            demandText.setText(demands.get(i).name);
            RadioGroup group = v.findViewById(R.id.demandRadioGroup);
            final DemandsItemVo demand = new DemandsItemVo();
            for (int j = 0; j < demands.get(i).datas.size(); j++) {
                if (j == 0) {
                    demand.name = demands.get(i).name;
                    demand.datas.add(demands.get(i).datas.get(j));
                    orderData.item.demands.add(demand);
                }
                RadioButton radio = new NaberRadioButton().Builder(getContext())
                        .setTitle(demands.get(i).datas.get(j).name)
                        .setTag(demands.get(i).datas.get(j))
                        .setPrice("\u3000")
                        .setSymbol("")
                        .setId(j + 369646)
                        .setChecked(j == 0 ? true : false)
                        .build();
                group.addView(radio);
            }
            group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    orderData.item.demands.remove(demand);
                    RadioButton radio = group.findViewById(checkedId);
                    demand.datas.clear();
                    demand.datas.add((ItemVo) radio.getTag());
                    orderData.item.demands.add(demand);
                }
            });
            contentLayout.addView(v);
        }
    }

    private void setOptView(final List<ItemVo> opts) {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.menu_detail_optional, null);
        final LinearLayout optLayout = v.findViewById(R.id.optsLayout);
        for (int i = 0; i < opts.size(); i++) {
            CheckBox box = new NaberCheckButton().Builder(getContext())
                    .setTitle(opts.get(i).name)
                    .setTag(opts.get(i))
                    .setSymbol("$")
                    .setPrice(opts.get(i).price)
                    .setChecked(false)
                    .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                orderData.item.opts.add((ItemVo) buttonView.getTag());
                            } else {
                                orderData.item.opts.remove(buttonView.getTag());
                            }
                            countTotalAmount();
                        }
                    }).build();

            optLayout.addView(box);
        }
        contentLayout.addView(v);
    }

    @Override
    public void onStop() {
        super.onStop();
        UserMainActivity.navigationIconDisplay(false, null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {

        OrderDetail orderDetail = OrderDetail.ofOrders(Lists.newArrayList(orderData));
        Log.d(TAG,orderDetail.toString());
        if(Model.USER_CACH_SHOPPING_CART.get("") == null){

        }

        Log.d(TAG,Model.USER_CACH_SHOPPING_CART.get("").toString());

        String msg = "品項：珍珠奶茶\n規格：大杯\n溫度：正常冰\n甜度：正常甜\n追加:布丁\n數量:2\n金額：90$";
        new AlertView.Builder()
                .setContext(getContext())
                .setStyle(AlertView.Style.Alert)
                .setTitle("已成功加入購物車")
                .setMessage(msg)
                .setOthers(new String[]{"前往購物車", "繼續購物"})
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        if (position == 0) {
                            //TODO 選填內容存入SQLite，並導向購物車畫面
                            CategoryMenuFragment.TO_MENU_DETAIL_INDEX = -1;
                            UserMainActivity.removeAndReplaceWhere(FRAGMENT, PageType.SHOPPING_CART, null);
                        } else {
                            //TODO 暫存選去內容，導系列頁面

                            RestaurantDetailFragment.TO_CATEGORY_MENU_INDEX = -1;
                            CategoryMenuFragment.TO_MENU_DETAIL_INDEX = -1;
                            UserMainActivity.removeAndReplaceWhere(FRAGMENT, PageType.RESTAURANT_DETAIL, null);
                        }
                        Log.d(TAG, o.toString());
                    }
                })
                .build()
                .setCancelable(true)
                .show();
    }

    class AddMinusClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.addBtn:
                    orderData.count++;
                    break;
                case R.id.minusBtn:
                    orderData.count--;
                    break;
            }

            if (orderData.count <= 0) {
                orderData.count = 1;
            } else if (orderData.count > 50) {
                orderData.count = 50;
            }
            quantityEditText.setText(orderData.count + "");
            countTotalAmount();
        }
    }

}
