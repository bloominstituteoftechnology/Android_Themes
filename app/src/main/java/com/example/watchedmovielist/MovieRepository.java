package com.example.watchedmovielist;

import java.util.ArrayList;

public class MovieRepository {
    public MovieRepository() {
    }

    ArrayList<MovieListing> movies = new ArrayList<MovieListing>();

     public void add(MovieListing movie){
         movies.add(movie);
    }

    public void remove(MovieListing movie) {
         movies.remove(movie);
    }

    public ArrayList<MovieListing> getMovies() {
        return movies;
    }
}
