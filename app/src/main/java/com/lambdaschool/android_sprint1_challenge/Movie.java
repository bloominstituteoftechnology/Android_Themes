package com.lambdaschool.android_sprint1_challenge;

import android.content.res.Resources;

import java.io.Serializable;

public class Movie implements Serializable {

    private String movieTitle;
    private boolean watched;

    public Movie() {
        this.movieTitle = "";//Resources.getSystem().getString(R.string.Movie_title_default);
        this.watched = false;
    }

    public Movie(String movieTitle, boolean watched) {
        this.movieTitle = movieTitle;
        this.watched = watched;

    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public boolean isWatched() {
        return watched;
    }
}
