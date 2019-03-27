package com.jakeesveld.android_sprint1_challenge;

import java.io.Serializable;
import java.util.ArrayList;

public class Movies implements Serializable {

    private String title;
    private Boolean isWatched;
    private int id;

    public Movies(String title, Boolean isWatched) {
        this.title = title;
        this.isWatched = isWatched;
    }

    public Movies(int id){
        this.title = "";
        this.isWatched = false;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getWatched() {
        return isWatched;
    }

    public void setWatched(Boolean watched) {
        isWatched = watched;
    }

    public int getId(){ return id; }
}
