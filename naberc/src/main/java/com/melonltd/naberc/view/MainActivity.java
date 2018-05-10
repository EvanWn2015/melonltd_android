package com.melonltd.naberc.view;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.melonltd.naberc.R;
import com.melonltd.naberc.model.helper.ThreadCallback;
import com.melonltd.naberc.model.preferences.SharedPreferencesService;
import com.melonltd.naberc.model.service.CustomersService;
import com.melonltd.naberc.util.GsonUtil;
import com.melonltd.naberc.view.intro.IntroFragment;
import com.melonltd.naberc.view.page.abs.AbsPageFragment;
import com.melonltd.naberc.view.page.factory.PageFragmentFactory;
import com.melonltd.naberc.view.page.type.PageType;
import com.melonltd.naberc.vo.CustomersVo;

public class MainActivity extends BaseCore implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Context context;
    public static LinearLayout bottomMenuLayout;

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
        bottomMenuLayout = findViewById(R.id.bottomMenuLayout);
    }

    @Override
    protected void onResume() {
        super.onResume();

        AbsPageFragment fragment = null;
        if (SharedPreferencesService.isFirstUse()) {
            fragmentManager.beginTransaction().replace(R.id.frame_container, new IntroFragment()).commit();
        } else if (currentUser == null) {
            bottomMenuLayout.setVisibility(View.GONE);
            fragment = PageFragmentFactory.of(PageType.LOGIN, null);
            fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
        } else {
            fragment = PageFragmentFactory.of(PageType.equalsName(FRAGMENT_TAG), null);
            if (fragment == null){
                fragment = PageFragmentFactory.of(PageType.HOME, null);
            }
            fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    @Override
    public void onClick(View v) {

        AbsPageFragment fragment = PageFragmentFactory.of(PageType.ofId(v.getId()), null);
        if (fragment != null) {
            FRAGMENT_TAG = PageType.ofId(v.getId()).name();
            fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
        }

        Log.d(TAG, v.getId() + "");
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
}
