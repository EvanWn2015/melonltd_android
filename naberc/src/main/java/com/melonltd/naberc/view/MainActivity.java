package com.melonltd.naberc.view;


import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.google.common.util.concurrent.Service;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.melonltd.naberc.R;
import com.melonltd.naberc.model.helper.ThreadCallback;
import com.melonltd.naberc.model.preferences.SharedPreferencesService;
import com.melonltd.naberc.model.service.AdminsService;
import com.melonltd.naberc.model.service.CustomersService;
import com.melonltd.naberc.util.GsonUtil;
import com.melonltd.naberc.view.intro.IntroFragment;
import com.melonltd.naberc.view.page.abs.AbsPageFragment;
import com.melonltd.naberc.view.page.factory.PageFragmentFactory;
import com.melonltd.naberc.view.page.type.PageType;
import com.melonltd.naberc.vo.AdminsVo;
import com.melonltd.naberc.vo.CustomersVo;

import java.util.HashMap;
import java.util.List;

public class MainActivity extends BaseCore implements View.OnClickListener, TabLayout.OnTabSelectedListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Context context;
    public static TabLayout bottomMenuTabLayout;
    private FrameLayout frameContainer;

    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        getView();

        if (currentUser != null) {
            Log.d(TAG, currentUser.getEmail());
        }
    }

    private void getView() {
        frameContainer = findViewById(R.id.frameContainer);
        bottomMenuTabLayout = findViewById(R.id.bottomMenuTabLayout);
        bottomMenuTabLayout.addOnTabSelectedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        AbsPageFragment fragment = null;
        if (SharedPreferencesService.isFirstUse()) {
            fragmentManager.beginTransaction().replace(R.id.frameContainer, new IntroFragment()).commit();
//        }else if (BaseCore.currentUser == null){
//            bottomMenuTabLayout.setVisibility(View.GONE);
//            fragment = PageFragmentFactory.of(PageType.LOGIN, null);
//            fragmentManager.beginTransaction().replace(R.id.frameContainer, fragment).commit();
        } else {
            fragment = PageFragmentFactory.of(PageType.equalsName(FRAGMENT_TAG), null);
            if (fragment == null) {
                fragment = PageFragmentFactory.of(PageType.HOME, null);
            }
            fragmentManager.beginTransaction().replace(R.id.frameContainer, fragment).commit();
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    @Override
    public void onClick(View v) {

//        String uid = SharedPreferencesService.getUserUID();
//        Log.d(TAG, uid);
//        AdminsService.findByKey(uid, new ThreadCallback() {
//            @Override
//            public void onSuccess(DataSnapshot dataSnapshot) {
//                AdminsVo data = AdminsVo.valueOf(dataSnapshot);
//            }
//
//            @Override
//            public void onFail(DatabaseError error) {
//                Log.e(TAG, error.getMessage());
//            }
//        });
//
//        if (true) {
//            return;
//        }

        // 判斷當前頁面 不再重新載入
//        if (PageType.ofId(v.getId()).name().equals(FRAGMENT_TAG)) {
//            return;
//        }
//
//        AbsPageFragment fragment = PageFragmentFactory.of(PageType.ofId(v.getId()), null);
//        if (fragment != null) {
//            FRAGMENT_TAG = PageType.ofId(v.getId()).name();
//            fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
//        }

//        auth.signInWithEmailAndPassword("evantest@gmail.com", "123456")
//                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<AuthResult> task) {
//                                        if (task.isSuccessful()) {
//                                            // Sign in success, update UI with the signed-in user's information
//                                            Log.d(TAG, "createUserWithEmail:success");
//                                            user = auth.getCurrentUser();
//                                            Log.d(TAG, user.getEmail());
//                                        } else {
//                                            // If sign in fails, display a message to the user.
//                                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
//                                            Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//                                });


//                CustomersService.findByEmail("a@gmail.com", new ThreadCallback() {
//                            @Override
//                            public void onSuccess(DataSnapshot dataSnapshot) {
//                                for (DataSnapshot data : dataSnapshot.getChildren()) {
//                                    Log.d(TAG, data.getKey());
//                                    Log.d(TAG, GsonUtil.toJson(data.getValue(CustomersVo.class)));
//                                }
//                            }
//
//                            @Override
//                            public void onFail(DatabaseError error) {
//                                Log.e(TAG, error.getMessage());
//                            }
//                        }
//                );
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();
        AbsPageFragment fragment = PageFragmentFactory.of(PageType.ofPosition(position), null);
        if (fragment != null) {
            tab.getIcon().setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
            FRAGMENT_TAG = PageType.ofPosition(position).name();
            fragmentManager.beginTransaction().replace(R.id.frameContainer, fragment).commit();
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        tab.getIcon().setColorFilter(ContextCompat.getColor(context, R.color.colorAccent), PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }
}
