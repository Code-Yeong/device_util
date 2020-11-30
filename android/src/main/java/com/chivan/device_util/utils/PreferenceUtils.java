package com.chivan.device_util.utils;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceUtils {

    //====================int====================
    public static synchronized int getInt(Context context, String key, int defValue) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(key, defValue);
    }

    public static synchronized void putInt(Context context, String key, int value) {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putInt(key, value).commit();
    }

    //====================String====================
    public static synchronized String getString(Context context, String key, String defValue) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(key, defValue);
    }

    public static synchronized String getString(Context context, String preferenceName, String key, String defValue) {
        SharedPreferences preferences = context.getSharedPreferences(preferenceName, Activity.MODE_PRIVATE);
        return preferences.getString(key, defValue);
    }

    public static synchronized void putString(Context context, String key, String value) {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putString(key, value).commit();
    }

    public static synchronized void putString(Context context, String preferenceName, String key, String value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(preferenceName, Activity.MODE_PRIVATE).edit();
        editor.putString(key, value).commit();
    }

    //====================long====================
    public static synchronized long getLong(Context context, String key, long defValue) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getLong(key, defValue);
    }

    public static synchronized void putLong(Context context, String key, long value) {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putLong(key, value).commit();
    }

    public static synchronized long addLong(Context context, String key) {
        long value = PreferenceManager.getDefaultSharedPreferences(context).getLong(key, 0);
        ++value;
        PreferenceManager.getDefaultSharedPreferences(context).edit().putLong(key, value).commit();
        return value;
    }

    //====================boolean====================
    public static synchronized boolean getBoolean(Context context, String key, boolean defValue) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(key, defValue);
    }

    public static synchronized void putBoolean(Context context, String key, boolean value) {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putBoolean(key, value).commit();
    }

    //====================remove====================
    public static synchronized void remove(Context context, String key) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().remove(key).commit();
    }

    public static synchronized void remove(Context context, String preferenceName, String key) {
        SharedPreferences.Editor editor = context.getSharedPreferences(preferenceName, Activity.MODE_PRIVATE).edit();
        editor.remove(key).commit();
    }

    public static synchronized boolean contains(Context context, String key) {
        return PreferenceManager
                .getDefaultSharedPreferences(context).contains(key);
    }
}
