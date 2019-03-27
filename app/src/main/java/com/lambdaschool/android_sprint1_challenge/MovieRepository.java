package com.lambdaschool.android_sprint1_challenge;

import java.util.ArrayList;

public class MovieRepository {

    private static ArrayList<Movie> movieList = new ArrayList<>();

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
}
