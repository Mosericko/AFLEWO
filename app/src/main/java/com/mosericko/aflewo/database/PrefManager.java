package com.mosericko.aflewo.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.mosericko.aflewo.member.User;

public class PrefManager {
    public static final String USER_TYPE = "userType";
    public static final String STATUS = "status";
    private static final String PREF_NAME = "com.mosericko.aflewo";
    private static final String ID = "userID";
    private static final String IS_LOGGED_IN = "loggedIn";
    public static final String CHECKED_RADIO_ID = "radioId";
    @SuppressLint("StaticFieldLeak")
    private static PrefManager prefInstance;
    @SuppressLint("StaticFieldLeak")
    private static Context mCtx;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    public PrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized PrefManager getInstance(Context context) {
        if (prefInstance == null) {
            prefInstance = new PrefManager(context);
        }
        return prefInstance;
    }

    public void setUserLogin(User user) {

        sharedPreferences = mCtx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putInt(ID, user.getId());
        editor.putString(USER_TYPE, user.getUsertype());
        editor.putString(STATUS, user.getStatus());
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.apply();
    }


    public boolean isLoggedIn() {

        sharedPreferences = mCtx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false);

    }

    public int UserID() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(ID, -1);
    }




    public String UserType() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_TYPE, null);
    }


    public String status() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(STATUS, null);
    }

    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
