package com.lambdaschool.android_sprint1_challenge;

import android.content.Context;
import android.content.SharedPreferences;
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

    public static boolean isAppStoredPrefsNightMode() {
        appStoredPrefs.getBoolean("night_mode", appStoredPrefsNightMode);
        return appStoredPrefsNightMode;
    }

    public static void setAppStoredPrefsNightMode(boolean appStoredPrefsNightMode) {
        SharedPreferences.Editor appStoredPrefsEditor = appStoredPrefs.edit();
        appStoredPrefsEditor.putBoolean("night_mode", appStoredPrefsNightMode);
        appStoredPrefsEditor.apply();
        MovieRepository.appStoredPrefsNightMode = appStoredPrefsNightMode;
    }

    public static void initializeSharedPreferences(@NonNull Context context) {
        appStoredPrefs = context.getSharedPreferences(context.getApplicationInfo().name, Context.MODE_PRIVATE);
    }
}
