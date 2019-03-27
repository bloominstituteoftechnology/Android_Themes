package com.vivekvishwanath.android_readinglist;

import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ScrollView bookScrollView;
    Context context;
    Button addBookButton;
    Button nightModeButton;

    static SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        preferences = getSharedPreferences(Constants.BOOK_PREFERENCES, Context.MODE_PRIVATE);
        /* SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit(); */

        bookScrollView = findViewById(R.id.book_scroll_view);

        nightModeButton = findViewById(R.id.night_mode_button);
        nightModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean nightModeON = preferences.getBoolean("@string/night_mode_on", false);
                if (nightModeON && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    UiModeManager manager = context.getSystemService(UiModeManager.class);
                    manager.setNightMode(UiModeManager.MODE_NIGHT_NO);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("@string/night_mode_on", false);
                    editor.commit();
                }

                if (!nightModeON && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    UiModeManager manager = context.getSystemService(UiModeManager.class);
                    manager.setNightMode(UiModeManager.MODE_NIGHT_YES);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("@string/night_mode_on", true);
                    editor.apply();
                }
            }
        });
        addBookButton = findViewById(R.id.add_book_button);
        addBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditBookActivity.class);
                String nextId = Integer.toString(BookModel.nextId());
                intent.putExtra(Constants.NEW_BOOK_TAG, nextId);
                startActivityForResult(intent, Constants.NEW_BOOK_REQUEST_CODE);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        bookScrollView.removeAllViews();
        bookScrollView.addView(BooksController.getBooksView(context));

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Constants.NEW_BOOK_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                BooksController.handleEditActivityResult(data);
            }
        }
        if (requestCode == Constants.EDIT_BOOK_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                BooksController.handleEditActivityResult(data);
            }
        }
    }
}
