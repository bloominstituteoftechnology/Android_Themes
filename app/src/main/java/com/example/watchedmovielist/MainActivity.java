package com.example.watchedmovielist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Context context;
    MovieRepository movies = new MovieRepository() ;
    LinearLayout lineMan;
    public Button addBttn;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_CANCELED) return;
        MovieListing movie = (MovieListing)data.getSerializableExtra("KEY");

        if(movie != null) {
            movies.add(movie);
            Log.i("Debug", "Added movie: " + movie.getName());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        lineMan.removeAllViews();
        for(MovieListing m:movies.getMovies()) {
            TextView t = createMovieView(m);
            Log.i("Debug", "Added view for " + m.getName());
            lineMan.addView(t);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        lineMan = (LinearLayout) findViewById(R.id.line_man);

        addBttn = findViewById(R.id.add_movie);
        addBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, EditActivity.class);
                startActivityForResult(i, 0);
            }
        });



        /* if(movie != null) {

            editTextView.setText(movie.getName());
            watchedFlag.setChecked(movie.isWatched());
        }
        */



    }

    private TextView createMovieView(final MovieListing m){
        TextView view = new TextView(context);
        view.setText(m.getName());
        view.setPadding(12,12,12,12);
        view.setTextSize(22);
        if(m.isWatched()) {
            view.setPaintFlags(view.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailsActivityIntent = new Intent(context, EditActivity.class);
                detailsActivityIntent.putExtra("KEY",m);
                movies.remove(m);
                startActivityForResult(detailsActivityIntent, 0);
            }
        });
        return view;
    }


}
