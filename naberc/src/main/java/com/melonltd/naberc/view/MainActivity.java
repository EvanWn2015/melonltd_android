package com.melonltd.naberc.view;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.melonltd.naberc.R;
import com.melonltd.naberc.model.preferences.SharedPreferencesService;
import com.melonltd.naberc.view.intro.IntroFragment;
import com.melonltd.naberc.view.page.impl.LoginFragment;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Context context;
    private Button testBtn;

    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        testBtn = findViewById(R.id.btn);
        testBtn.setOnClickListener(this);

        if (currentUser != null) {
            Log.d(TAG, currentUser.getEmail());
        }


//        int ss = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context);
//        Log.d(TAG, ss + "");
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        Fragment fragment = new IntroFragment();
//        if (fragment != null) {
//
//        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        testBtn.setVisibility(View.GONE);
        if (SharedPreferencesService.isFirstUse()) {
            fragmentManager.beginTransaction().replace(R.id.frame_container, new IntroFragment()).commit();
        } else if (currentUser == null) {
            fragmentManager.beginTransaction().replace(R.id.frame_container, LoginFragment.newInstance()).commit();
        }else{
            testBtn.setVisibility(View.VISIBLE);
        }

    }



    @Override
    public void onClick(View view) {

//        auth.signOut();
//        Log.d(TAG, currentUser.getUid() + "");
//        Log.d(TAG, currentUser.getEmail());
//        Log.d(TAG, currentUser.getUid());
//        Log.d(TAG, currentUser.getUid());
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


//                CustomersAbstractService.findByEmail("a@gmail.com", new ThreadCallback() {
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
