package com.example.android_themes;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class FullScreenView extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_view);

        Intent full = getIntent();
        String fullPicString = full.getStringExtra("pic");
        Uri fullPicUri = Uri.parse(fullPicString);
        ImageView picture = findViewById(R.id.fullscreen_pic);
        picture.setImageURI(fullPicUri);
    }


}
