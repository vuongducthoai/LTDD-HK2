package com.example.ui.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharedPrefManager(Context context){
        sharedPreferences = context.getSharedPreferences("appPrefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    //Save token into SharedPreferences
    public void saveToken(String token){
        editor.putString("JWT_TOKEN", token);
        editor.apply();
    }

    //Get token from SharedPreferences
    public String getToken(){
        return sharedPreferences.getString("JWT_TOKEN", null);
    }

    public boolean hasToken(){
        return sharedPreferences.contains("JWT_TOKEN");
    }
}
