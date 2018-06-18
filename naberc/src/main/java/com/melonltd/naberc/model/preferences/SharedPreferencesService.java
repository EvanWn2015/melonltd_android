package com.melonltd.naberc.model.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.melonltd.naberc.R;

import java.util.Date;

public class SharedPreferencesService {
    private static SharedPreferencesService SERVICE = null;
    private SharedPreferences preferences = null;

    public SharedPreferencesService() {

    }

    public static SharedPreferencesService getInstance(SharedPreferences preferences) {
        if (SERVICE == null) {
            SERVICE = new SharedPreferencesService(preferences);
        }
        return SERVICE;
    }


    public static void setOauth(String oauth) {
        SERVICE.preferences.edit().putString(String.valueOf(R.string.oauth_token), oauth).commit();
    }
    public static String getOauth() {
        return SERVICE.preferences.getString(String.valueOf(R.string.oauth_token),"");
    }


    public static void setLoginLimit(long timeLimit) {
        SERVICE.preferences.edit().putLong(String.valueOf(R.string.login_limit),timeLimit).commit();
    }
    public static long getLoginLimit() {
        return SERVICE.preferences.getLong(String.valueOf(R.string.login_limit),0L);
    }
    public static void setRememberAccount(String account) {
        SERVICE.preferences.edit().putString(String.valueOf(R.string.remember_account),account).commit();
    }
    public static String getRememberAccount() {
        return SERVICE.preferences.getString(String.valueOf(R.string.remember_account),"");
    }
    public static void setRememberIdentity(String identity) {
        SERVICE.preferences.edit().putString(String.valueOf(R.string.remember_identity),identity).commit();
    }
    public static String getRememberIdentity() {
        return SERVICE.preferences.getString(String.valueOf(R.string.remember_identity),"");
    }


    public SharedPreferencesService(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public static boolean isFirstUse() {
        return SERVICE.preferences.getBoolean(String.valueOf(R.string.is_first_use), true);
    }

    public static void setFirstUse() {
        SERVICE.preferences.edit().putBoolean(String.valueOf(R.string.is_first_use), false).commit();
    }

    public static void setUserUID(String uid) {
        SERVICE.preferences.edit().putString(String.valueOf(R.string.user_uid), uid).commit();
    }

    public static String getUserUID() {
        return SERVICE.preferences.getString(String.valueOf(R.string.user_uid),"");
    }

    public static void removeAll() {
        SERVICE.preferences.edit().clear().commit();
    }


    public void setPreferences(SharedPreferences preferences) {
        this.preferences = preferences;
    }
}
