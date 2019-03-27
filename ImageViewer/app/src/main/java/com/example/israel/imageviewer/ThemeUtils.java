package com.example.israel.imageviewer;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;

public class ThemeUtils {

    //private static String KEY_IS_DARK_THEME_SELECTED = Resources.getSystem().getString(R.string.dark_theme_selected_key);

    public static int getSelectedTheme(Activity activity) {
        String isDarkThemeSelectedKey = activity.getString(R.string.key_dark_theme_selected);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        boolean isDarkThemeSelected = sharedPreferences.getBoolean(isDarkThemeSelectedKey, false);

        return isDarkThemeSelected ? R.style.DarkTheme : R.style.AppTheme;
    }

    public static void onActivityCreateSetTheme(Activity activity) {
        activity.setTheme(getSelectedTheme(activity));
    }

    public static void refreshActivity(Activity activity) {
        Intent intent = activity.getIntent();
        activity.finish();
        activity.startActivity(intent);
    }

}
