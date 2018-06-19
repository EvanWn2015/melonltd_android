package com.melonltd.naberc.view.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.melonltd.naberc.R;
import com.melonltd.naberc.model.service.SPService;
import com.melonltd.naberc.view.common.page.LoginFragment;
import com.melonltd.naberc.view.factory.PageFragmentFactory;
import com.melonltd.naberc.view.common.page.RecoverPasswordFragment;
import com.melonltd.naberc.view.common.page.RegisteredFragment;
import com.melonltd.naberc.view.common.page.RegisteredSellerFragment;
import com.melonltd.naberc.view.common.page.VerifySMSFragment;
import com.melonltd.naberc.view.factory.PageType;
import com.melonltd.naberc.view.seller.SellerMainActivity;
import com.melonltd.naberc.view.user.UserMainActivity;

import java.util.Date;

public class BaseActivity extends BaseCore {
    private static final String TAG = BaseActivity.class.getSimpleName();
    public static Context context;
    public static Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        context = this;
        getViews();
    }

    private void getViews() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        long limit = SPService.getLoginLimit();
        long now = new Date().getTime();
        long day7 = 1000 * 60 * 60 * 24 * 7L * 2;
        if (now - day7 < limit){
            String identity = SPService.getRememberIdentity();
            if (identity.toUpperCase().equals("USER")){
                loadRestaurantTemplate(context);
                startActivity(new Intent(context, UserMainActivity.class));
            }else if (identity.toUpperCase().equals("SELLERS")){
               startActivity(new Intent(context, SellerMainActivity.class));
            }
        }else {
            BaseCore.FRAGMENT_TAG = PageType.LOGIN.name();
            Fragment fragment = PageFragmentFactory.of(PageType.LOGIN, null);
            getSupportFragmentManager().beginTransaction().replace(R.id.baseContainer, fragment).addToBackStack(fragment.toString()).commit();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        LoginFragment.FRAGMENT = null;
        RecoverPasswordFragment.FRAGMENT = null;
        RegisteredFragment.FRAGMENT = null;
        RegisteredSellerFragment.FRAGMENT = null;
        VerifySMSFragment.FRAGMENT = null;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public static void navigationIconDisplay(boolean show, View.OnClickListener listener) {
        if (!show) {
            toolbar.setNavigationIcon(null);
        } else {
            toolbar.setNavigationIcon(context.getResources().getDrawable(R.drawable.naber_back_icon));
        }
        toolbar.setNavigationOnClickListener(listener);
    }

    public static void changeToolbarStatus() {
        toolbar.setTitle(context.getResources().getString(PageType.equalsIdByName(FRAGMENT_TAG)));
    }

}
