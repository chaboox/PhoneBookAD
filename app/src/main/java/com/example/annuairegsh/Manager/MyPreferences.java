package com.example.annuairegsh.Manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class MyPreferences {

    private static SharedPreferences app_preferences;




    public static void saveString(String key, String value, Context context){
        app_preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = app_preferences.edit();
        editor.putString(key, value);
        editor.commit(); // Very important
    }


    public static void saveInt(String key, int value){
        SharedPreferences.Editor editor = app_preferences.edit();
        editor.putInt(key, value);
        editor.commit(); // Very important
    }

    public static void saveLong(String key, long value){
        SharedPreferences.Editor editor = app_preferences.edit();
        editor.putLong(key, value);
        editor.commit(); // Very important
    }

    public static String getMyString(Context context, String key, String defValue){
        app_preferences = PreferenceManager.getDefaultSharedPreferences(context);
        // Get the value for the run counter
        String value = app_preferences.getString(key, defValue);
        return value;
    }


    public static int getMyInt(Context context, String key, int defValue){
        app_preferences = PreferenceManager.getDefaultSharedPreferences(context);
        // Get the value for the run counter
        int value = app_preferences.getInt(key, defValue);
        return value;
    }

    public static long getMyLong(Context context, String key, long defValue){
        app_preferences = PreferenceManager.getDefaultSharedPreferences(context);
        // Get the value for the run counter
        long value = app_preferences.getLong(key, defValue);
        return value;
    }

    public static boolean getMyBool(Context context, String key, boolean defValue){
        app_preferences = PreferenceManager.getDefaultSharedPreferences(context);
        // Get the value for the run counter
        boolean value = app_preferences.getBoolean(key, defValue);
        return value;
    }

    public static void saveMyBool(Context context, String key, boolean value){
        app_preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = app_preferences.edit();
        editor.putBoolean(key, value);
        editor.commit(); //

    }


    public static void deletePreference(String key){
        SharedPreferences.Editor editor = app_preferences.edit();
        editor.remove(key).commit();
    }





}
