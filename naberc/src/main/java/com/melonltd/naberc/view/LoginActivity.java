package com.melonltd.naberc.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.melonltd.naberc.R;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private Context context;

    private Button loginBtn;
    private EditText mailEdit, passwordEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        getView();
        setListener();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void getView() {
//        mailEdit = findViewById(R.id.emailEdit);
//        passwordEdit = findViewById(R.id.passwordEdit);
//        loginBtn = findViewById(R.id.loginBtn);
    }


    private void setListener() {
        loginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.loginBtn) {
            LOADING_BAR.show();
            auth.signInWithEmailAndPassword(mailEdit.getText().toString(), passwordEdit.getText().toString())
                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                currentUser = auth.getCurrentUser();
                                Log.d(TAG, currentUser.getUid());
                                Log.d(TAG, currentUser.getDisplayName());
                                Log.d(TAG, currentUser.getPhotoUrl().toString());
                                Log.d(TAG, currentUser.getEmail());
                                startActivity(new Intent(context, MainActivity.class));
                            } else {
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                            LOADING_BAR.hide();
                        }
                    });
        }
    }
}
