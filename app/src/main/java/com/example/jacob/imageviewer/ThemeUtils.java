package com.example.jacob.imageviewer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class ThemeUtils {

    static int getSelectedTheme(Activity activity) {
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        int selectedThemeId;
        boolean specialTheme = preferences.getBoolean(activity.getResources().getString(R.string.theme_key), false);
        boolean largeTextStatus =  preferences.getBoolean(activity.getResources().getString(R.string.text_size_key), false);
        if (specialTheme) {
            if (largeTextStatus) {
                selectedThemeId = R.style.AlternateLargeTextTheme;
            } else {
                selectedThemeId = R.style.AlternateAppTheme;
            }
        } else {
            if (largeTextStatus) {
                selectedThemeId = R.style.LargeTextTheme;
            } else {
                selectedThemeId = R.style.AppTheme;
            }
        }
        return selectedThemeId;
    }

    static void onActivityCreateSetTheme(Activity activity) {
        activity.setTheme(getSelectedTheme(activity));

    }

    static void refreshActivity(Activity activity) {
        Intent intent = activity.getIntent();
        activity.finish();
        activity.startActivity(intent);
    }

    static boolean checkTheme(Activity activity, int activeTheme) {
        return getSelectedTheme(activity) == activeTheme;
    }
}