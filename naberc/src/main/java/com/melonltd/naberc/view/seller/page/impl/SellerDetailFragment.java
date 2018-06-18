package com.melonltd.naberc.view.seller.page.impl;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.common.collect.Lists;
import com.melonltd.naberc.R;
import com.melonltd.naberc.model.preferences.SharedPreferencesService;
import com.melonltd.naberc.util.Tools;
import com.melonltd.naberc.view.common.BaseCore;
import com.melonltd.naberc.view.common.factory.PageFragmentFactory;
import com.melonltd.naberc.view.common.type.PageType;
import com.melonltd.naberc.view.customize.SwitchButton;
import com.melonltd.naberc.view.seller.SellerMainActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SellerDetailFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = SellerDetailFragment.class.getSimpleName();
    public static SellerDetailFragment FRAGMENT = null;

    private EditText bulletinEdit;
    private Button submitBtn, logoutBtn;
    private TextView storeStartText, storeEndText;
    private LinearLayout businessLayout;
    private List<String> options1Items = Lists.newArrayList("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
    private List<String> options2Items = Lists.newArrayList("00", "30");

    public SellerDetailFragment() {
        // Required empty public constructor
    }

    public Fragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new SellerDetailFragment();
        }
        FRAGMENT.setArguments(bundle);
        return FRAGMENT;
    }

    public Fragment newInstance(Object... o) {
        return new SellerDetailFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container.getTag(R.id.seller_seller_detail_page) == null) {
            View v = inflater.inflate(R.layout.fragment_seller_detail, container, false);
            getViews(v);
            setListener();
            container.setTag(R.id.seller_seller_detail_page, v);
            return v;
        }
        return (View) container.getTag(R.id.seller_seller_detail_page);
    }

    private void getViews(View v) {
        bulletinEdit = v.findViewById(R.id.bulletinEdit);
        submitBtn = v.findViewById(R.id.submitBtn);
        logoutBtn = v.findViewById(R.id.logoutBtn);
        storeStartText = v.findViewById(R.id.storeStartText);
        storeEndText = v.findViewById(R.id.storeEndText);
        businessLayout = v.findViewById(R.id.businessLayout);
    }

    private void setListener() {
        submitBtn.setOnClickListener(this);
        logoutBtn.setOnClickListener(this);
        storeStartText.setOnClickListener(this);
        storeEndText.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        SellerMainActivity.changeTabAndToolbarStatus();
        if (SellerMainActivity.toolbar != null) {
            SellerMainActivity.navigationIconDisplay(true, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    backToSellerSetUpPage();
                    SellerMainActivity.navigationIconDisplay(false, null);
                }
            });
        }
        builderThreeBusiness();
    }

    @Override
    public void onStop() {
        super.onStop();
        SellerMainActivity.navigationIconDisplay(false, null);
    }

    private void backToSellerSetUpPage() {
        BaseCore.FRAGMENT_TAG = PageType.SELLER_SET_UP.name();
        SellerSetUpFragment.TO_SELLER_DETAIL_INDEX = -1;
        Fragment f = PageFragmentFactory.of(PageType.SELLER_SET_UP, null);
        getFragmentManager().beginTransaction().remove(this).replace(R.id.sellerFrameContainer, f).commit();
    }

    private void showDatePicker(final int id) {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(bulletinEdit.getWindowToken(), 0);
        OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                String tx = options1Items.get(options1) + " : " + options2Items.get(option2);
                if (id == R.id.storeStartText) {
                    storeStartText.setText(tx);
                } else if (id == R.id.storeEndText) {
                    storeEndText.setText(tx);
                }
            }
        })
                .setTitleSize(20)
                .setTitleBgColor(getResources().getColor(R.color.naber_dividing_line_gray))
                .setCancelColor(getResources().getColor(R.color.naber_dividing_gray))
                .setSubmitColor(getResources().getColor(R.color.naber_dividing_gray))
                .build();
        pvOptions.setNPicker(options1Items, options2Items, null);
        pvOptions.show();
    }


    private void builderThreeBusiness() {
        businessLayout.removeAllViews();
        Date now = new Date();
        long day = 1000 * 60 * 60 * 24L;
        SimpleDateFormat format = new SimpleDateFormat("MM/dd");
        for (int i = 0; i < 3; i++) {
            View v = LayoutInflater.from(getContext()).inflate(R.layout.seller_select_date_switch, null);
            TextView dateText = v.findViewById(R.id.dateText);
            SwitchButton switchButton = v.findViewById(R.id.sellerDateSelectSwitch);
            switchButton.setTag(Tools.FORMAT.toUTCDateTime(now));
            switchButton.setOnCheckedChangeListener(new BusinessChangeListener());
            dateText.setText(format.format(now));
            now.setTime(now.getTime() + day);
            businessLayout.addView(v);
        }
    }


    class BusinessChangeListener implements SwitchButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(final SwitchButton view, boolean isChecked) {
            if (!isChecked) {
                new AlertView.Builder()
                        .setTitle("")
                        .setMessage("關閉營業時段，請先確認該日有無訂單，\n若有訂單請記得告知使用者")
                        .setContext(getContext())
                        .setStyle(AlertView.Style.Alert)
                        .setOthers(new String[]{"確定關閉", "取消"})
                        .setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(Object o, int position) {
                                if (position == 0) {
                                    // TODO api
                                } else if (position == 1) {
                                    view.setChecked(true);
                                }
                            }
                        })
                        .build()
                        .setCancelable(true)
                        .show();
            }
        }
    }

//    private void toLoginPage() {
//        BaseCore.FRAGMENT_TAG = PageType.LOGIN.name();
//        getFragmentManager().beginTransaction().remove(this).commit();
//        getActivity().startActivity(new Intent(getContext(), UserMainActivity.class));
////        getActivity().startActivity(new Intent(getContext(), UserMainActivity.class));
////        AbsPageFragment f = PageFragmentFactory.of(PageType.LOGIN, null);
//
//    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submitBtn:
                break;
            case R.id.logoutBtn:
//                SellerMainActivity.toLoginPage();
                SharedPreferencesService.removeAll();
                getActivity().finish();
                break;
            case R.id.storeStartText:
                showDatePicker(R.id.storeStartText);
                break;
            case R.id.storeEndText:
                showDatePicker(R.id.storeEndText);
                break;
        }
    }
}
