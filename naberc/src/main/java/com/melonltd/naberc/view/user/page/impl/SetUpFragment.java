package com.melonltd.naberc.view.user.page.impl;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.melonltd.naberc.R;
import com.melonltd.naberc.view.common.BaseCore;
import com.melonltd.naberc.view.common.abs.AbsPageFragment;
import com.melonltd.naberc.view.common.factory.PageFragmentFactory;
import com.melonltd.naberc.view.common.type.PageType;
import com.melonltd.naberc.view.customize.SwitchButton;
import com.melonltd.naberc.view.user.UserMainActivity;


public class SetUpFragment extends AbsPageFragment implements View.OnClickListener {
    private static final String TAG = HomeFragment.class.getSimpleName();
    public static SetUpFragment FRAGMENT = null;
    private TextView accountNumberText, dividendText;
    private TextView toAccountEdit, toAboutUsText, toHelpText, toTeachingText;
    private SwitchButton soundSwitch, shakeSwitch;
    public static int TO_ACCOUNT_DETAIL_INDEX = -1;
    public static int TO_SIMPLE_INFO_INDEX = -1;

    public SetUpFragment() {
    }

    @Override
    public AbsPageFragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new SetUpFragment();
            FRAGMENT.setArguments(bundle);
            TO_ACCOUNT_DETAIL_INDEX = -1;
            TO_SIMPLE_INFO_INDEX = -1;
        }
        return FRAGMENT;
    }

    @Override
    public AbsPageFragment newInstance(Object... o) {
        return new SetUpFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container.getTag(R.id.user_set_up_page) == null) {
            View v = inflater.inflate(R.layout.fragment_set_up, container, false);
            getViews(v);
            setListener();
            container.setTag(R.id.user_set_up_page, v);
            return v;
        } else {
            return (View) container.getTag(R.id.user_set_up_page);
        }
    }

    private void getViews(View v) {
        toAccountEdit = v.findViewById(R.id.toSellerEdit);
        toAboutUsText = v.findViewById(R.id.toAboutUsText);
        toHelpText = v.findViewById(R.id.toHelpText);
        toTeachingText = v.findViewById(R.id.toTeachingText);

        accountNumberText = v.findViewById(R.id.accountNumberText);
        dividendText = v.findViewById(R.id.dividendText);
        soundSwitch = v.findViewById(R.id.soundSwitch);
        shakeSwitch = v.findViewById(R.id.shakeSwitch);
    }

    private void setListener() {
        toAccountEdit.setOnClickListener(this);
        toAboutUsText.setOnClickListener(this);
        toHelpText.setOnClickListener(this);
        toTeachingText.setOnClickListener(this);
    }


    @Override
    public void onResume() {
        super.onResume();
        UserMainActivity.changeTabAndToolbarStatus();
        if (TO_ACCOUNT_DETAIL_INDEX >= 0) {
            toAccountDetail(1);
        } else if (TO_SIMPLE_INFO_INDEX >= 0) {
            toSimpleInfo(1);
        }
    }

    private void toAccountDetail(int i) {
        Bundle b = new Bundle();
        b.putString("user detail", "");
        TO_ACCOUNT_DETAIL_INDEX = i;
        BaseCore.FRAGMENT_TAG = PageType.ACCOUNT_DETAIL.name();
        AbsPageFragment f = PageFragmentFactory.of(PageType.ACCOUNT_DETAIL, b);
        getFragmentManager().beginTransaction().replace(R.id.frameContainer, f).addToBackStack(f.toString()).commit();
    }

    private void toSimpleInfo(int i) {
        Bundle b = new Bundle();
        b.putString("user detail", "");
        TO_SIMPLE_INFO_INDEX = i;
        BaseCore.FRAGMENT_TAG = PageType.SIMPLE_INFO.name();
        AbsPageFragment f = PageFragmentFactory.of(PageType.SIMPLE_INFO, b);
        getFragmentManager().beginTransaction().replace(R.id.frameContainer, f).addToBackStack(f.toString()).commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toSellerEdit:
                Log.d(TAG, "toAccoutEdit");
                toAccountDetail(1);
                break;
            case R.id.toAboutUsText:
            case R.id.toHelpText:
            case R.id.toTeachingText:
                toSimpleInfo(1);
                break;
        }
    }
}
