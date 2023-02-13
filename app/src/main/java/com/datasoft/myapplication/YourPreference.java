package com.datasoft.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

public class YourPreference {

    private static SharedPreferences sharedPreferences;

    private YourPreference(){

    }
    public static void init(Context context){
        if(sharedPreferences == null)
            sharedPreferences = context.getSharedPreferences("login", Context.MODE_PRIVATE);
    }
    public static void saveData(String key, String value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString(key, value);
        prefsEditor.apply();
    }

    public static String getData(String key) {
        if (sharedPreferences != null) {
            return sharedPreferences.getString(key, "blank");
        }
        return "";
    }

    public static void clearData() {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.clear();
        prefsEditor.apply();

    }
    }



