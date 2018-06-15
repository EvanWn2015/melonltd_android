package com.melonltd.naberc.view.common;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.melonltd.naberc.R;
import com.melonltd.naberc.view.common.abs.AbsPageFragment;
import com.melonltd.naberc.view.common.factory.PageFragmentFactory;
import com.melonltd.naberc.view.common.type.PageType;

public class BaseActivity extends BaseCore implements View.OnLayoutChangeListener {
    private static final String TAG = BaseActivity.class.getSimpleName();
    private Context context;
    public static Toolbar toolbar;
    private FrameLayout baseContainer;
    private static Drawable navigationIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        context = this;

//        FirebaseStorage storage = FirebaseStorage.getInstance();
//        gs://naber-test.appspot.com




        getViews();
        BaseCore.FRAGMENT_TAG = PageType.LOGIN.name();
        removeFragment();
    }

    private void getViews() {
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
