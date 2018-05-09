package com.melonltd.naberc.model.preferences;

import android.content.SharedPreferences;

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
//        boolean c = SERVICE.preferences.getBoolean("IS_FIRST_USE", true);
//        Log.i("isFirstUse", c + "");
        return SERVICE.preferences.getBoolean("IS_FIRST_USE", true);
    }

    public static void setFirstUse() {
        SERVICE.preferences.edit().putBoolean("IS_FIRST_USE", false).commit();
//        boolean c = SERVICE.preferences.getBoolean("IS_FIRST_USE", true);
//        Log.i("isFirstUse", c + "");
    }

    public void setPreferences(SharedPreferences preferences) {
        this.preferences = preferences;
    }
}
