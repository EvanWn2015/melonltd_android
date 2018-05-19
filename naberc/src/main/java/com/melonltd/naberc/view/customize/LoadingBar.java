package com.melonltd.naberc.view.customize;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.melonltd.naberc.R;
import com.wang.avi.AVLoadingIndicatorView;


/**
 * Created by evan on 2018/1/4.
 */

public class LoadingBar extends Activity {

    private static RelativeLayout RL = null;
    private static AVLoadingIndicatorView AVI = null;

    public static boolean IS_SHOW = false;
    public static boolean IS_LOCK = false;

    public LoadingBar(Context context, boolean show) {
        ViewGroup layout = (ViewGroup) ((Activity) context).findViewById(android.R.id.content).getRootView();
        AVI = (AVLoadingIndicatorView) LayoutInflater.from(context).inflate(R.layout.loading_bar, null);
        RelativeLayout.LayoutParams params = new
                RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        AVI.hide();
        RL = new RelativeLayout(context);
        RL.setBackgroundColor(Color.argb(0, 0, 0, 0));
        RL.setClickable(false);
        RL.setGravity(Gravity.CENTER);
        LayoutInflater.from(context).inflate(R.layout.loading_bar, null);
        RL.addView(AVI);
        layout.addView(RL, params);
        if (show) {
            show();
        }
    }

    public static void show() {
        RL.setBackgroundColor(Color.argb(63, 0, 0, 0));
        RL.setClickable(true);
        AVI.show();
        IS_SHOW = true;
        IS_LOCK = true;
        // or AVI.smoothToShow();
    }

    public static void hide() {
        RL.setBackgroundColor(Color.argb(0, 0, 0, 0));
        RL.setClickable(false);
        AVI.hide();
        IS_SHOW = false;
        IS_LOCK = false;
        // or AVI.smoothToHide();
    }

    public static void lockEvent() {
        RL.setBackgroundColor(Color.argb(63, 0, 0, 0));
        RL.setClickable(true);
        IS_LOCK = true;
    }

    public static void unlockEvent() {
        RL.setBackgroundColor(Color.argb(0, 0, 0, 0));
        RL.setClickable(false);
        IS_LOCK = false;
    }

}
