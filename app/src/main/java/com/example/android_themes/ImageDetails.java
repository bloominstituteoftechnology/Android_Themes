package com.example.android_themes;



import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageDetails extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_details);

        TextView fullScreen = findViewById(R.id.fullscreen_content);
        ImageView fullPic = findViewById(R.id.fullscreen_picture);
        Intent intent = getIntent();
        String imageUriString = intent.getStringExtra("names");

        final StoredImage image = new StoredImage(imageUriString);
        fullPic.setImageURI(image.getUriPic());
        fullScreen.setText(image.getUriString());

        fullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fullScreen = new Intent(ImageDetails.this, FullScreenView.class);
                String imageString = image.getUriString();
                fullScreen.putExtra("pic", imageString);
                startActivity(fullScreen);
            }
        });
    }
}
