package com.melonltd.naberc.view.customize;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

import com.melonltd.naberc.view.BaseCore;

public class PopUpDialog extends DialogFragment {

    private static PopUpDialog POPUP = null;
    private static FragmentManager fm;
    private static String message = "";

    public PopUpDialog() {
    }

    public static PopUpDialog getInstance() {
        if (POPUP == null) {
            POPUP = new PopUpDialog();
        }
        return POPUP;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }


    public void setTransaction(FragmentManager fm) {
        this.fm = fm;
    }

    public static void show() {
        POPUP.show(fm, "");
    }

    public static void show(String message) {
        POPUP.setMessage(message).show(fm, "");
    }

    public static void show(int id) {
        POPUP.setMessage(BaseCore.context.getString(id)).show(fm, "");
    }

    public PopUpDialog setMessage(String message) {
        this.message = message;
        return POPUP;
    }


}
