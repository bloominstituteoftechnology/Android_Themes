package com.lambdaschool.android_sprint1_challenge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class MovieEdit extends AppCompatActivity {

    Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_edit);

        final EditText editTextTitle = findViewById(R.id.edit_text_movie_title);
        final CheckBox checkBoxWatched = findViewById(R.id.check_box_watched);
        Intent intent = getIntent();
        Movie movieToAdd = (Movie) intent.getSerializableExtra(getString(R.string.movie_key));

        if (movieToAdd != null) {
            editTextTitle.setText(movieToAdd.getMovieTitle());
            checkBoxWatched.setChecked(movieToAdd.isWatched());
        }

        buttonSave = findViewById(R.id.button_save);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String movieText = editTextTitle.getText().toString();

                if (movieText.equals("")) {
                    launchMovieListActivity(null);
                } else {
                    launchMovieListActivity(new Movie(movieText, checkBoxWatched.isChecked()));
                }
            }
        });

        Button buttonDelete = findViewById(R.id.button_delete);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchMovieListActivity(null);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        buttonSave.performClick();
    }

    private void launchMovieListActivity(Movie movieToPass) {
        Intent intent = new Intent(getApplicationContext(), MovieList.class);
        intent.putExtra(getString(R.string.movie_key), movieToPass);
        startActivity(intent);
    }
}
