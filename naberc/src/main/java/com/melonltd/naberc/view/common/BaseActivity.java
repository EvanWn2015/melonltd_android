package com.melonltd.naberc.view.common;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.melonltd.naberc.R;
import com.melonltd.naberc.view.common.factory.PageFragmentFactory;
import com.melonltd.naberc.view.common.page.impl.LoginFragment;
import com.melonltd.naberc.view.common.page.impl.RecoverPasswordFragment;
import com.melonltd.naberc.view.common.page.impl.RegisteredFragment;
import com.melonltd.naberc.view.common.page.impl.RegisteredSellerFragment;
import com.melonltd.naberc.view.common.page.impl.VerifySMSFragment;
import com.melonltd.naberc.view.common.type.PageType;

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
        BaseCore.FRAGMENT_TAG = PageType.LOGIN.name();
        Fragment fragment = PageFragmentFactory.of(PageType.LOGIN, null);
        getSupportFragmentManager().beginTransaction().replace(R.id.baseContainer, fragment).addToBackStack(fragment.toString()).commit();
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
