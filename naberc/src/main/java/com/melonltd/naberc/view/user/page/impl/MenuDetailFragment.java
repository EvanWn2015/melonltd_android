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

import com.google.common.base.Strings;
import com.melonltd.naberc.R;
import com.melonltd.naberc.view.common.abs.AbsPageFragment;
import com.melonltd.naberc.view.customize.NaberCheckButton;
import com.melonltd.naberc.view.customize.NaberRadioButton;


public class MenuDetailFragment extends AbsPageFragment implements View.OnClickListener {
    private static final String TAG = MenuDetailFragment.class.getSimpleName();
    private static MenuDetailFragment FRAGMENT = null;
    private Button addToShopCartBtn;
    private ImageButton addBtn, minusBtn;
    private EditText amountEditText;
    private Handler compiledHandler = new Handler();
    private int amount = 1;
    private LinearLayout contentLayout;

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
        View v = inflater.inflate(R.layout.fragment_menu_detail, container, false);
        getViews(v);
        setButtonClickListener();
        setScopeView();
        setDemandView();
        setDemandView();
        setOptView();
        return v;
    }


    private void getViews(View v) {
        contentLayout = v.findViewById(R.id.menuDetailContentLinearLayout);
        addToShopCartBtn = v.findViewById(R.id.addToShopCartBtn);
        amountEditText = v.findViewById(R.id.amountEditText);
        amountEditText.setText(amount + "");
        addBtn = v.findViewById(R.id.addBtn);
        minusBtn = v.findViewById(R.id.minusBtn);

//        RadioButton radio = new NaberRadioButton().Builder(getContext())
//                .setTitle("加冰")
//                .setPrice("20")
//                .setPadding(100, 0, 100, 0)
//                .setChecked(false)
//                .build();
    }

    private void setButtonClickListener() {
        addToShopCartBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);
        minusBtn.setOnClickListener(this);
        amountEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Strings.isNullOrEmpty(s.toString())) {
                    amount = 0;
                } else {
                    amount = Integer.parseInt(s.toString());
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

    Runnable delayRun = new Runnable() {
        @Override
        public void run() {
            checkAmount();
        }
    };

    private void checkAmount() {
        if (amount <= 0) {
            amount = 1;
        } else if (amount > 50) {
            amount = 50;
        }
        amountEditText.setText(amount + "");
        amountEditText.setSelection(amountEditText.getText().toString().length());
    }

    private void setScopeView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.menu_detail_scope, null);
        RadioGroup group = v.findViewById(R.id.scopeRadioGroup);
        for (int i = 0; i < 5; i++) {
            RadioButton radio = new NaberRadioButton().Builder(getContext())
                    .setTitle("加冰" + i)
                    .setPrice("4" + i)
                    .setId(i + 669696)
                    .setPadding(50, 0, 0, 0)
                    .setChecked(i == 0 ? true : false)
                    .build();
            group.addView(radio);

        }
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < 5; i++) {
                    Log.d(TAG, "" + i + "::::" + ((RadioButton) group.getChildAt(i)).isChecked());
                }
                Log.d(TAG, checkedId + "checkedId :: " + checkedId);
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
                    .setPrice("4" + i)
                    .setId(i + 369646)
                    .setPadding(50, 0, 0, 0)
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
        LinearLayout optLayout = v.findViewById(R.id.optLayout);
        for (int i = 0; i < 5; i++) {
            CheckBox box = new NaberCheckButton().Builder(getContext())
                    .setTitle("加料" + i)
                    .setPrice("4" + i)
                    .setTag("Test Tag" + i)
                    .setId(i + 6645654)
                    .setPadding(50, 0, 0, 0)
                    .setChecked(false)
                    .build();
//            radio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    String tag = (String) buttonView.getTag();
//                    Log.d(TAG, "tag :: ---> " + tag);
//                }
//
//            });
            box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    String tag = (String) buttonView.getTag();
                    Log.d(TAG, "tag :: ---> " + tag);
                }
            });
            optLayout.addView(box);
        }

        contentLayout.addView(v);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addBtn:
                amount++;
                checkAmount();
                break;
            case R.id.minusBtn:
                amount--;
                checkAmount();
                break;
            case R.id.addToShopCartBtn:
                break;
        }
    }
}
