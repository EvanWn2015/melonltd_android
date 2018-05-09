package com.melonltd.naberc.view.intro;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.melonltd.naberc.R;
import com.melonltd.naberc.model.preferences.SharedPreferencesService;

public class IntroActivity extends AppIntro {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(Slide.newInstance(R.layout.intro_1));
        addSlide(Slide.newInstance(R.layout.intro_1));
        addSlide(Slide.newInstance(R.layout.intro_1));
        addSlide(Slide.newInstance(R.layout.intro_1));
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        SharedPreferencesService.setFirstUse();
        finish();
    }
}
