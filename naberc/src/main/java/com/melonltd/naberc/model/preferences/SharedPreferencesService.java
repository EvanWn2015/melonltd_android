package com.melonltd.naberc.model.preferences;

import android.content.SharedPreferences;

import com.melonltd.naberc.R;

public class SharedPreferencesService {
    private static SharedPreferencesService SERVICE = null;
    private SharedPreferences preferences = null;

    public SharedPreferencesService() {

    }

    public static SharedPreferencesService newInstance(SharedPreferences preferences) {
        if (SERVICE == null) {
            SERVICE = new SharedPreferencesService(preferences);
        }
        return SERVICE;
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

    public void setPreferences(SharedPreferences preferences) {
        this.preferences = preferences;
    }
}
