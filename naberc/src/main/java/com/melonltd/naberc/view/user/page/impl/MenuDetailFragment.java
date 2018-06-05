package com.melonltd.naberc.view.user.page.impl;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.google.common.base.Strings;
import com.melonltd.naberc.R;
import com.melonltd.naberc.view.common.BaseCore;
import com.melonltd.naberc.view.common.abs.AbsPageFragment;
import com.melonltd.naberc.view.common.factory.PageFragmentFactory;
import com.melonltd.naberc.view.common.type.PageType;
import com.melonltd.naberc.view.customize.NaberCheckButton;
import com.melonltd.naberc.view.customize.NaberRadioButton;
import com.melonltd.naberc.view.user.UserMainActivity;


public class MenuDetailFragment extends AbsPageFragment implements View.OnClickListener {
    private static final String TAG = MenuDetailFragment.class.getSimpleName();
    public static MenuDetailFragment FRAGMENT = null;
    private Button addToShopCartBtn;
    private ImageButton addBtn, minusBtn;
    private TextView totalAmountText;
    private EditText quantityEditText;
    //    private Handler compiledHandler = new Handler();
    private int scope = 0, quantity = 1, totalAmount = 0;
    private LinearLayout contentLayout;

    private int CATEGORY_MENU_TO_THIS_INDEX = -1;
    private int RESTAURANT_TO_THIS_INDEX = -1;

    public MenuDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public AbsPageFragment newInstance(Object... o) {
        return new MenuDetailFragment();
    }

    @Override
    public AbsPageFragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new MenuDetailFragment();
            CATEGORY_MENU_TO_THIS_INDEX = -1;
        }
        FRAGMENT.setArguments(null);
        FRAGMENT.setArguments(bundle);
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
            setButtonClickListener();
            container.setTag(R.id.user_menu_detail_page, v);
            CATEGORY_MENU_TO_THIS_INDEX = CategoryMenuFragment.TO_MENU_DETAIL_INDEX;
            return v;
        }
        return (View) container.getTag(R.id.user_menu_detail_page);
    }


    private void getViews(View v) {
        contentLayout = v.findViewById(R.id.menuDetailContentLinearLayout);
        addToShopCartBtn = v.findViewById(R.id.addToShopCartBtn);
        quantityEditText = v.findViewById(R.id.quantityEditText);
        quantityEditText.setText(quantity + "");
        totalAmountText = v.findViewById(R.id.totalAmountText);
        totalAmountText.setText(totalAmount + "");
        addBtn = v.findViewById(R.id.addBtn);
        minusBtn = v.findViewById(R.id.minusBtn);
    }

    private void setButtonClickListener() {
        final Handler compiledHandler = new Handler();
        final Runnable delayRun = new Runnable() {
            @Override
            public void run() {
                if (quantity <= 0) {
                    quantity = 1;
                } else if (quantity > 50) {
                    quantity = 50;
                }
                quantityEditText.setText(quantity + "");
                quantityEditText.setSelection(quantityEditText.getText().toString().length());
            }
        };
        addToShopCartBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);
        minusBtn.setOnClickListener(this);
        quantityEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Strings.isNullOrEmpty(s.toString())) {
                    quantity = 0;
                } else {
                    quantity = Integer.parseInt(s.toString());
                }
                if (delayRun != null) {
                    compiledHandler.removeCallbacks(delayRun);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                compiledHandler.postDelayed(delayRun, 800);
            }
        });
    }

    private void checkQuantity() {
        if (quantity <= 0) {
            quantity = 1;
        } else if (quantity > 50) {
            quantity = 50;
        }
        quantityEditText.setText(quantity + "");
        quantityEditText.setSelection(quantityEditText.getText().toString().length());
        countTotalAmount();
    }

    private void countTotalAmount() {
        totalAmountText.setText((totalAmount + scope) * quantity + "");
    }

    private void setScopeView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.menu_detail_scope, null);
        RadioGroup group = v.findViewById(R.id.scopeRadioGroup);
        for (int i = 0; i < 5; i++) {
            if (i == 0) {
                scope = 40;
            }
            RadioButton radio = new NaberRadioButton().Builder(getContext())
                    .setTitle("加冰" + i)
                    .setPrice("4" + i)
                    .setSymbol("$")
                    .setTag(40 + i)
                    .setId(i + 669696)
                    .setChecked(i == 0 ? true : false)
                    .build();
            group.addView(radio);
        }

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton b = group.findViewById(checkedId);
                int index = group.indexOfChild(b);
                scope = (int) b.getTag();
                countTotalAmount();
            }
        });
        contentLayout.addView(v);
    }

    private void setDemandView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.menu_detail_demand, null);
        TextView demandText = v.findViewById(R.id.demandText);
        demandText.setText("容量");
        RadioGroup group = v.findViewById(R.id.demandRadioGroup);
        for (int i = 0; i < 5; i++) {
            RadioButton radio = new NaberRadioButton().Builder(getContext())
                    .setTitle("容量" + i)
//                    .setPrice("40")
//                    .setSymbol("$")
                    .setId(i + 369646)
                    .setChecked(i == 0 ? true : false)
                    .build();
            group.addView(radio);
        }
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.d(TAG, checkedId + "checkedId :: " + checkedId);
            }
        });
        contentLayout.addView(v);
    }

    private void setOptView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.menu_detail_optional, null);
        LinearLayout optLayout = v.findViewById(R.id.demandLayout);
        for (int i = 0; i < 5; i++) {
            CheckBox box = new NaberCheckButton().Builder(getContext())
                    .setTitle("加料" + i)
                    .setTag(20 + i)
                    .setSymbol("$")
                    .setPrice("2" + i)
                    .setId(i + 6645654)
                    .setChecked(false)
                    .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            int tag = (int) buttonView.getTag();
                            if (isChecked) {
                                totalAmount += tag;
                            } else {
                                totalAmount -= tag;
                            }
                            countTotalAmount();
                        }
                    }).build();

            optLayout.addView(box);
        }
        contentLayout.addView(v);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (contentLayout.getChildCount() == 0) {
            refreshContent();
        }

        if (CATEGORY_MENU_TO_THIS_INDEX != CategoryMenuFragment.TO_MENU_DETAIL_INDEX) {
            refreshContent();
            CATEGORY_MENU_TO_THIS_INDEX = CategoryMenuFragment.TO_MENU_DETAIL_INDEX;
        }

        if (UserMainActivity.toolbar != null) {
            UserMainActivity.navigationIconDisplay(true, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toCategoryMenuPage();
                    UserMainActivity.navigationIconDisplay(false, null);
                }
            });
        }
    }

    private void refreshContent() {
        contentLayout.removeAllViews();
        scope = 0;
        quantity = 1;
        totalAmount = 0;
        setScopeView();
        setDemandView();
        setDemandView();
        setOptView();
        countTotalAmount();
    }

    private void toCategoryMenuPage() {
        BaseCore.FRAGMENT_TAG = PageType.CATEGORY_MENU.name();
        CategoryMenuFragment.TO_MENU_DETAIL_INDEX = -1;
        AbsPageFragment f = PageFragmentFactory.of(PageType.CATEGORY_MENU, null);
        getFragmentManager().beginTransaction().remove(this).replace(R.id.frameContainer, f).commit();
    }

    @Override
    public void onStop() {
        super.onStop();
        UserMainActivity.navigationIconDisplay(false, null);
//        compiledHandler.removeCallbacks(delayRun);
//        scope = 0;
//        quantity = 1;
//        totalAmount = 0;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addBtn:
                quantity++;
                checkQuantity();
                break;
            case R.id.minusBtn:
                quantity--;
                checkQuantity();
                break;
            case R.id.addToShopCartBtn:
                String msg = "品項：珍珠奶茶\n規格：大杯\n溫度：正常冰\n甜度：正常甜\n追加:布丁\n數量:2\n金額：90$";
                new AlertView.Builder()
                        .setContext(getContext())
                        .setStyle(AlertView.Style.Alert)
                        .setTitle("訂單詳情")
                        .setMessage(msg)
                        .setOthers(new String[]{"前往購物車", "繼續購物"})
                        .setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(Object o, int position) {
                                if (position == 0) {
                                    //TODO 選填內容存入SQLite，並導向購物車畫面
                                    toPageShoppingCartPage();
                                } else {
                                    //TODO 暫存選去內容，導系列頁面
                                    toRestaurantDetailPage();
                                }
                                Log.d(TAG, o.toString());
                            }
                        })
                        .build()
                        .setCancelable(true)
                        .show();
                break;
        }
    }

    private void toPageShoppingCartPage() {
        BaseCore.FRAGMENT_TAG = PageType.SHOPPING_CART.name();
        CategoryMenuFragment.TO_MENU_DETAIL_INDEX = -1;
        AbsPageFragment f = PageFragmentFactory.of(PageType.SHOPPING_CART, null);
        getFragmentManager().beginTransaction().remove(this).replace(R.id.frameContainer, f).commit();
    }

    private void toRestaurantDetailPage() {
        BaseCore.FRAGMENT_TAG = PageType.RESTAURANT_DETAIL.name();
        RestaurantDetailFragment.TO_CATEGORY_MENU_INDEX = -1;
        CategoryMenuFragment.TO_MENU_DETAIL_INDEX = -1;
        AbsPageFragment f = PageFragmentFactory.of(PageType.RESTAURANT_DETAIL, null);
        getFragmentManager().beginTransaction().remove(this).replace(R.id.frameContainer, f).commit();
    }
}
