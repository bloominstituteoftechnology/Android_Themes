package com.lambdaschool.android_sprint1_challenge;

import android.app.UiModeManager;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MovieList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        MovieRepository.initializeSharedPreferences(this);
        final boolean nightMode = MovieRepository.isAppStoredPrefsNightMode();


        final LinearLayout linearLayout = findViewById(R.id.linear_layout_movies);
        Intent intent = getIntent();
        MovieRepository.addMovieToList((Movie) intent.getSerializableExtra(getString(R.string.movie_key)));
        ArrayList<Movie> movieList = MovieRepository.getMovieList();

        if (movieList.size() > 0) {
            for (Movie movie : movieList) {
                if (!movieList.contains(movie)) {
                    movieList.add(movie);
                }
                final TextView addTextView = new TextView(this);
                addTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                addTextView.setText(movie.getMovieTitle());
                addTextView.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
                if (movie.isWatched())
                    addTextView.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                addTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Movie movie = new Movie(addTextView.getText().toString(), addTextView.getPaint().isStrikeThruText());
                        MovieRepository.removeMovieFromList(movie);
                        linearLayout.removeView(addTextView);
                        launchMovieEditActivity(movie);
                    }
                });

                linearLayout.addView(addTextView);
            }
        }

        Button buttonAddMovie = findViewById(R.id.button_add_movie);
        buttonAddMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchMovieEditActivity(new Movie());
            }
        });

        Button buttonNight = findViewById(R.id.button_night);
        buttonNight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    final UiModeManager uiModeManager = v.getContext().getSystemService(UiModeManager.class);
                    uiModeManager.setNightMode((Boolean)nightMode ? UiModeManager.MODE_NIGHT_YES : UiModeManager.MODE_NIGHT_NO);
                    MovieRepository.setAppStoredPrefsNightMode(!nightMode);
                }
            }
        });
    }

    private void launchMovieEditActivity(Movie movieToPass) {
        Intent intent = new Intent(getApplicationContext(), MovieEdit.class);
        intent.putExtra(getString(R.string.movie_key), movieToPass);
        startActivity(intent);
    }


}
