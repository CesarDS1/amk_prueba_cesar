package com.test.cesar.amkprueba.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by Cesar on 26/08/2017.
 */

public class SharedPreferencesManager {
    private static final String TAG = SharedPreferencesManager.class.getSimpleName();

    public static final String ACCESS_TOKEN = "access_token";
    private static SharedPreferences sharedPreferences;

    public SharedPreferencesManager(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public String getAccessToken() {
        return getValueString(ACCESS_TOKEN);
    }

    public void setValueString(String key, String value) {
        try {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(key, value);
            editor.apply();
        } catch (Exception e) {
            Log.e(TAG, "Error on 'setValueString'" + e.getMessage());
        }
    }

    public String getValueString(String key) {
        return sharedPreferences.getString(key, "");
    }

}
