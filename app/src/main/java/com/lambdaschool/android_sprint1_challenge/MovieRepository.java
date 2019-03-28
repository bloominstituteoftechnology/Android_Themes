package com.lambdaschool.android_sprint1_challenge;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import java.util.ArrayList;

public class MovieRepository {

    private static ArrayList<Movie> movieList = new ArrayList<>();
    private static SharedPreferences appStoredPrefs;
    private static boolean appStoredPrefsNightMode;


    public static ArrayList<Movie> getMovieList() {
        return movieList;
    }

    public static void addMovieToList(Movie movieToAdd) {
        if (movieToAdd != null) {
            if (!movieList.contains(movieToAdd))
                movieList.add(movieToAdd);
        }
    }

    public static void removeMovieFromList(Movie movieToRemove) {
        for (int i = 0; i < movieList.size(); i++) {
            Movie movieInList = movieList.get(i);
            if (movieInList.getMovieTitle().equals(movieToRemove.getMovieTitle())) {
                movieList.remove(i);
                break;
            }
        }
    }

    public static boolean isAppStoredPrefsNightMode(@NonNull Context context) {
        return appStoredPrefs.getBoolean(context.getString(R.string.night_mode), appStoredPrefsNightMode);
    }

    public static void setAppStoredPrefsNightMode(@NonNull Context context,boolean appStoredPrefsNightMode) {
        SharedPreferences.Editor appStoredPrefsEditor = appStoredPrefs.edit();
        appStoredPrefsEditor.putBoolean(context.getString(R.string.night_mode), appStoredPrefsNightMode);
        appStoredPrefsEditor.apply();
        MovieRepository.appStoredPrefsNightMode = appStoredPrefsNightMode;
    }

    public static void initializeSharedPreferences(@NonNull Context context) {
        appStoredPrefs = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
    }
}
