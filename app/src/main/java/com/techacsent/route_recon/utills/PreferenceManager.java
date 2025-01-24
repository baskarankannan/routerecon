package com.techacsent.route_recon.utills;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONObject;

import java.util.Set;

public class PreferenceManager {

    private static PreferenceManager ourInstance;
    private static Context mContext;

    public static void setInstance(Context context) {
        if (ourInstance == null) {
            mContext = context;
            ourInstance = new PreferenceManager();
        }
    }

    public static PreferenceManager getInstance() {
        return ourInstance;
    }

    private PreferenceManager() {
    }

    public static void updateValue(String key, Boolean value) {
        SharedPreferences preferences = mContext.getSharedPreferences(Constant.SHARED_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (value == null)
            editor.remove(key);
        else
            editor.putBoolean(key, value);
        editor.apply();
    }
    public static void updateValue(String key, Long value) {
        SharedPreferences preferences = mContext.getSharedPreferences(Constant.SHARED_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (value == null)
            editor.remove(key);
        else
            editor.putLong(key, value);
        editor.apply();
    }

    public static void updateValue(String key, String value) {
        SharedPreferences preferences = mContext.getSharedPreferences(Constant.SHARED_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (value == null)
            editor.remove(key);
        else
            editor.putString(key, value);


        editor.apply();
    }

    public static void updateValue(String key, JSONObject value) {
        SharedPreferences preferences = mContext.getSharedPreferences(Constant.SHARED_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (value == null)
            editor.remove(key);
        else
            editor.putString(key, String.valueOf(value));
        editor.apply();
    }

    public static void updateValue(String key, Integer value) {
        SharedPreferences preferences = mContext.getSharedPreferences(Constant.SHARED_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (value == null)
            editor.remove(key);
        else
            editor.putInt(key, value);
        editor.apply();

    }



    public static int getInt(String key) {
        SharedPreferences preferences = mContext.getSharedPreferences(Constant.SHARED_PREFERENCE, Context.MODE_PRIVATE);
        return preferences.getInt(key, 0);
    }
    public static long getLong(String key) {
        SharedPreferences preferences = mContext.getSharedPreferences(Constant.SHARED_PREFERENCE, Context.MODE_PRIVATE);
        return preferences.getLong(key, 0);
    }

    public static String getString(String key) {
        SharedPreferences preferences = mContext.getSharedPreferences(Constant.SHARED_PREFERENCE, Context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }

    public static Boolean getBool(String key) {
        return getBool(key, false);
    }

    public static Boolean getBool(String key, boolean def) {
        SharedPreferences preferences = mContext.getSharedPreferences(Constant.SHARED_PREFERENCE, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, def);
    }


    public static void updateValue(String key, Set<String> value) {
        SharedPreferences preferences = mContext.getSharedPreferences(Constant.SHARED_PREFERENCE, Context.MODE_PRIVATE);
        if (value != null) {
            preferences.edit().putStringSet(key, value).apply();
        } else {
            preferences.edit().remove(key).apply();
        }
    }

    public static Set<String> getStringSet(String key) {
        SharedPreferences preferences = mContext.getSharedPreferences(Constant.SHARED_PREFERENCE, Context.MODE_PRIVATE);
        return preferences.getStringSet(key, null);
    }
}
