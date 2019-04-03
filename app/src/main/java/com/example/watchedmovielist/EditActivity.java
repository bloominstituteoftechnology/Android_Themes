package com.example.watchedmovielist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Movie;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

public class EditActivity extends AppCompatActivity {

    private MovieListing movie;
    private EditText editTextView;
    private Switch watchedFlag;
    private Button saver;
    private Button ender;
    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit);
        Intent i = getIntent();
        movie = (MovieListing)i.getSerializableExtra("KEY");
        editTextView = findViewById(R.id.name_edit_text);
        watchedFlag = findViewById(R.id.watched_switch);
        saver = findViewById(R.id.save_bttn);
        ender = findViewById(R.id.delete_button);
        context = this;

        if(movie != null) {

            editTextView.setText(movie.getName());
            watchedFlag.setChecked(movie.isWatched());
        }

        saver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewDetailIntent = new Intent();
                movie = new MovieListing(editTextView.getText().toString(), watchedFlag.isChecked());
                viewDetailIntent.putExtra("KEY", movie);
               setResult(Activity.RESULT_OK,  viewDetailIntent);
               finish();
            }

        });
        ender.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewDetailIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, viewDetailIntent);
                finish();
            }
        }));

    }
}
