package com.example.myapplication;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView   mapView;
    static int yPos = 20;
    static int xPos = 20;
    static int DISPLAY_WIDTH;
    static int DISPLAY_HEIGHT;
    ImageView  upButton;
    ImageView  downButton;
    ImageView  leftButton;
    ImageView  rightButton;
    ImageView  settingsButton;
    Display    display;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display        = getWindowManager().getDefaultDisplay();
        DISPLAY_HEIGHT = display.getHeight();
        DISPLAY_WIDTH  = display.getWidth();

        mapView        = findViewById(R.id.map_view);
        settingsButton = findViewById(R.id.button_settings);
        upButton       = findViewById(R.id.button_up);
        downButton     = findViewById(R.id.button_down);
        leftButton     = findViewById(R.id.button_left);
        rightButton    = findViewById(R.id.button_right);
        Handler.createMap();
        updateMap();

        upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Handler.checkCollision(yPos - 1, xPos) != true) {
                    yPos--;
                    mapView.setText("");
                    updateMap();
                }
            }
        });

        downButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Handler.checkCollision(yPos + 1, xPos) != true) {
                    yPos++;
                    mapView.setText("");
                    updateMap();
                }
            }
        });

        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Handler.checkCollision(yPos, xPos - 1) != true) {
                    xPos--;
                    mapView.setText("");
                    updateMap();
                }
            }
        });

        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Handler.checkCollision(yPos, xPos + 1) != true) {
                    xPos++;
                    mapView.setText("");
                    updateMap();
                }
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });


    }

    public void updateMap() {
        printMapToView(Handler.charArr2ToStringArr(Handler.mapDisplayArea(yPos, xPos)));
    }



    public void printMapToView(String[] map) {
        mapView.setTypeface(Typeface.MONOSPACE);
        mapView.setTextScaleX(1.5f);

        for (int i = -2; i < map[1].length(); i++) {
            mapView.append("#");
        }

        mapView.append("\n");
        for (String maps : map) {
            mapView.append("#");
            mapView.append(maps);
            mapView.append("#\n");
        }
        for (int i = -2; i < map[1].length(); i++) {
            mapView.append("#");
        }

    }


}
