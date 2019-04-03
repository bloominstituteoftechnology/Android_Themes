package com.example.watchedmovielist;

import java.io.Serializable;

public class MovieListing implements Serializable {
    String name = "";
    boolean watched;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isWatched() {
        return watched;
    }

    public void setWatched(boolean watched) {
        this.watched = watched;
    }

    public MovieListing(String name, boolean watched) {
        this.name = name;
        this.watched = watched;
    }


}
