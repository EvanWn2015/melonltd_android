package com.melonltd.naber.view.common;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.common.base.Strings;
import com.melonltd.naber.R;
import com.melonltd.naber.model.api.ApiManager;
import com.melonltd.naber.model.api.ThreadCallback;

public class BaseIntroActivity extends AppCompatActivity {
//    private static final String TAG =  BaseIntroActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_baseintro);
        getImage();
    }

    private void getImage() {
        final SimpleDraweeView img_intro = findViewById(R.id.imageView_intro);
        final Button btn_intro = findViewById(R.id.btn_intro);
        btn_intro.setVisibility(View.GONE);

        ApiManager.appIntroBulletin(new ThreadCallback(getApplicationContext()) {
            @Override
            public void onSuccess(String responseBody) {
//                Log.i(TAG, responseBody);
                if (Strings.isNullOrEmpty(responseBody)) {
                    startActivity();
                } else {
                    img_intro.setImageURI(Uri.parse(responseBody));
                }
                btn_intro.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFail(Exception error, String msg) {
                btn_intro.setVisibility(View.VISIBLE);
            }
        });

        btn_intro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity();
            }
        });
    }

    private void startActivity () {
        startActivity(new Intent(BaseIntroActivity.this,BaseActivity.class));
    }
}
