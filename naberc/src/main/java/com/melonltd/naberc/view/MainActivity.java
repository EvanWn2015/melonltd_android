package com.melonltd.naberc.view;


import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.melonltd.naberc.R;
import com.melonltd.naberc.view.page.abs.AbsPageFragment;
import com.melonltd.naberc.view.page.factory.PageFragmentFactory;
import com.melonltd.naberc.view.page.type.PageType;

public class MainActivity extends BaseCore implements View.OnClickListener, TabLayout.OnTabSelectedListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Context context;
    private Toolbar toolbar;
    public static TabLayout bottomMenuTabLayout;
    private FrameLayout frameContainer;

    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        getView();
        setSupportActionBar(toolbar);
        serTab();

        if (currentUser != null) {
            Log.d(TAG, currentUser.getEmail());
        }
//        bottomMenuTabLayout.removeAllTabs();
    }

    private void getView() {
        toolbar = findViewById(R.id.toolbar);
        frameContainer = findViewById(R.id.frameContainer);
        bottomMenuTabLayout = findViewById(R.id.bottomMenuTabLayout);
        bottomMenuTabLayout.addOnTabSelectedListener(this);

//        bottomMenuTabLayout.setNextFocusUpId(1);
        frameContainer.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                Log.i(TAG, FRAGMENT_TAG);
//              bottomMenuTabLayout.getSelectedTabPosition();
                TabLayout.Tab tab = bottomMenuTabLayout.getTabAt(PageType.equalsPositionByName(FRAGMENT_TAG, true));
                if (tab != null){
                    tab.select();
                }
            }
        });
    }

    private static void serTab() {
        if (IS_USER) {
//            bottomMenuTabLayout.addTab(bottomMenuTabLayout.newTab().setText("首頁").setIcon(R.drawable.ic_launcher_background), 0,true);
//            bottomMenuTabLayout.addTab(bottomMenuTabLayout.newTab().setText("餐館").setIcon(R.drawable.ic_launcher_background), 1,true);
//            bottomMenuTabLayout.addTab(bottomMenuTabLayout.newTab().setText("購物車").setIcon(R.drawable.ic_launcher_background), 2,true);
//            bottomMenuTabLayout.addTab(bottomMenuTabLayout.newTab().setText("紀錄").setIcon(R.drawable.ic_launcher_background), 3,true);
//            bottomMenuTabLayout.addTab(bottomMenuTabLayout.newTab().setText("設定").setIcon(R.drawable.ic_launcher_background), 4,true);
        } else {
            bottomMenuTabLayout.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        AbsPageFragment fragment = null;
//        bottomMenuTabLayout.setVisibility(View.GONE);
        fragment = PageFragmentFactory.of(PageType.LOGIN, null);
        fragmentManager.beginTransaction().replace(R.id.frameContainer, fragment).commit();

//        if (SharedPreferencesService.isFirstUse()) {
//            fragmentManager.beginTransaction().replace(R.id.frameContainer, new IntroFragment()).commit();
////        }else if (BaseCore.currentUser == null){
////            bottomMenuTabLayout.setVisibility(View.GONE);
////            fragment = PageFragmentFactory.of(PageType.LOGIN, null);
////            fragmentManager.beginTransaction().replace(R.id.frameContainer, fragment).commit();
//        } else {
//            fragment = PageFragmentFactory.of(PageType.equalsName(FRAGMENT_TAG), null);
//            if (fragment == null) {
//                fragment = PageFragmentFactory.of(PageType.HOME, null);
//            }
//            fragmentManager.beginTransaction().replace(R.id.frameContainer, fragment).commit();
//        }
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
        if (PageType.equalsPositionByName(FRAGMENT_TAG, false) > 10){
            tab.getIcon().setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
            FRAGMENT_TAG = PageType.ofPosition(position).name();
            return;
        }else if(fragment != null) {
            fragmentManager.beginTransaction().replace(R.id.frameContainer, fragment).commit();
            tab.getIcon().setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
            FRAGMENT_TAG = PageType.ofPosition(position).name();
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
