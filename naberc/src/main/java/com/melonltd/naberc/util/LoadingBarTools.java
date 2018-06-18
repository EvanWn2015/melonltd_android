package com.melonltd.naberc.util;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;

import com.melonltd.naberc.R;

public class LoadingBarTools {

    public static AlertDialog newLoading(Context context) {
        return new AlertDialog.Builder(context, R.style.dialogNoBg)
                .setCancelable(false)
                .setView(LayoutInflater.from(context).inflate(R.layout.loading_bar, null))
                .show();
    }
}
