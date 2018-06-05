package com.melonltd.naberc.view.common;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.melonltd.naberc.R;
import com.melonltd.naberc.view.common.abs.AbsPageFragment;
import com.melonltd.naberc.view.common.factory.PageFragmentFactory;
import com.melonltd.naberc.view.common.type.PageType;
import com.melonltd.naberc.view.user.UserMainActivity;

import java.util.List;

public class BaseActivity extends BaseCore implements View.OnLayoutChangeListener {

    private Context context;
    public static Toolbar toolbar;
    private FrameLayout baseContainer;
    private static Drawable navigationIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        context = this;
        getViews();
        BaseCore.FRAGMENT_TAG = PageType.LOGIN.name();
        removeFragment();
    }

    private  void getViews(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        baseContainer = findViewById(R.id.baseContainer);
        navigationIcon = getResources().getDrawable(R.drawable.naber_back_icon);
        baseContainer.addOnLayoutChangeListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AbsPageFragment fragment = null;
        fragment = PageFragmentFactory.of(PageType.LOGIN, null);
        fragmentManager.beginTransaction().replace(R.id.baseContainer, fragment).addToBackStack(fragment.toString()).commit();
    }

    public static void navigationIconDisplay(boolean show, View.OnClickListener listener) {
        if (!show) {
            toolbar.setNavigationIcon(null);
        } else {
            toolbar.setNavigationIcon(navigationIcon);
        }
        toolbar.setNavigationOnClickListener(listener);
    }

    @Override
    public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
        toolbar.setTitle(getResources().getString(PageType.equalsIdByName(FRAGMENT_TAG)));
    }
}
