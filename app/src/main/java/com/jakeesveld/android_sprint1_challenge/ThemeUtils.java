package com.jakeesveld.android_sprint1_challenge;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class ThemeUtils {

    public static void onActivityCreateSetTheme(Activity activity){
        activity.setTheme(getCurrentTheme(activity));
    }

    private static int getOtherTheme(int currentTheme){
        int newTheme;
        if(currentTheme == R.style.AppTheme){
            newTheme = R.style.AppThemeDarkMode;
        }else{
            newTheme = R.style.AppTheme;
        }
        return newTheme;
    }

    private static int getCurrentTheme(Activity activity){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        int cTheme = prefs.getInt("Theme", R.style.AppTheme);
        return cTheme;
    }

    private static void refreshActivity(Activity activity){
        Intent intent = activity.getIntent();
        activity.finish();
        activity.startActivity(intent);
    }

    private static void storeCurrentTheme(Activity activity, int currentTheme){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        SharedPreferences.Editor edit = prefs.edit();
        edit.putInt("Theme", currentTheme);
        edit.apply();
    }

    public static void toggleTheme(Activity activity){
        int cTheme = getCurrentTheme(activity);
        cTheme = getOtherTheme(cTheme);
        storeCurrentTheme(activity, cTheme);
        refreshActivity(activity);

    }
}
