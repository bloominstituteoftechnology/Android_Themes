package com.example.android_themes;

import android.annotation.SuppressLint;
import android.app.UiModeManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String MY_PREFS = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textViewBanner = findViewById(R.id.textview_banner);
        final SharedPreferences preferences = this.getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
        textViewBanner.setText(preferences.getString(getString(R.string.bool_key), getString(R.string.string_not_found)));


        final FloatingActionButton nightToggleButton = findViewById(R.id.button_night_mode_toggle);



        nightToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UiModeManager uiHandle = getApplicationContext().getSystemService(UiModeManager.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if(uiHandle.getNightMode() == UiModeManager.MODE_NIGHT_YES){
                        uiHandle.setNightMode(UiModeManager.MODE_NIGHT_NO);

                    } else {
                        uiHandle.setNightMode(UiModeManager.MODE_NIGHT_YES);
                        /*  nightToggleButton.setImageDrawable(getDrawable(R.drawable.ic_action_name_dark)); //Doesn't work for some reason
                          nightToggleButton.hide();*/


                    }
                } else {
                    Toast.makeText(MainActivity.this, getString(R.string.night_mode_not_supported), Toast.LENGTH_LONG).show();
                }

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(getString(R.string.bool_key), getString(R.string.night_mode_setting) + Integer.toString(uiHandle.getNightMode()));
                editor.apply();;
            }
        });
    }
}
