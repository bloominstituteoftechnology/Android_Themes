package com.jakeesveld.android_sprint1_challenge;

import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    public static final int EDIT_MOVIE_REQUEST_CODE = 2;
    LinearLayout layoutList;
    Button buttonAdd;
    Context context;
    public final int MOVIE_REQUEST_CODE = 1;
    static int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layoutList = findViewById(R.id.layout_list);
        buttonAdd = findViewById(R.id.button_add);
        context = this;

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditActivity.class);
                Movies movie = new Movies(id++);
                intent.putExtra("movie", movie);
                startActivityForResult(intent, MOVIE_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        layoutList.removeAllViews();
        ArrayList<Movies> moviesList = MoviesRepository.getMoviesList();
        if(moviesList != null) {
            for (Movies allmovies : moviesList) {
                layoutList.addView(createMovieView(allmovies));
            }
        }

    }

    private TextView createMovieView(final Movies movieView) {
        final TextView movieTextView = new TextView(context);
        movieTextView.setText(movieView.getTitle());
        movieTextView.setTextSize(22);
        movieTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        if (movieView.getWatched()){
            movieTextView.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
        movieTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditActivity.class);
                intent.putExtra("movie", movieView);
                startActivityForResult(intent, MOVIE_REQUEST_CODE);
            }
        });
        return movieTextView;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == MOVIE_REQUEST_CODE) {
            if (data != null) {
                Movies movie = (Movies) data.getSerializableExtra("movie");
                if ((Boolean) data.getSerializableExtra("add")) {
                    MoviesRepository.addMovieToList(movie);

                }else{
                    MoviesRepository.removeMovieFromList(movie.getId());
                    }
                }
            }
        }
    }
