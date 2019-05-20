package com.rybarstudios.imageviewer;

import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public static final int IMAGE_REQUEST_CODE = 54;
    public static final String PREFS_KEY = "prefsKey";
    static int nextId = 0;
    private Context context;
    private ImageData imageData;
    ArrayList<ImageData> imageList;
    private ImageViewerListAdapter mListAdapter;
    SharedPreferences prefs;
    private boolean darkMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("ActivityLifecycle", getLocalClassName() + " - onCreate");
        context = this;

        prefs = this.context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = prefs.edit();

        findViewById(R.id.button_add_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imageIntent = new Intent(Intent.ACTION_GET_CONTENT);
                imageIntent.setType("image/*");
                startActivityForResult(imageIntent, IMAGE_REQUEST_CODE);

            }
        });

        findViewById(R.id.dark_mode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(darkMode) {
                        darkMode = false;
                    }else {
                        darkMode = true;
                    }
                    editor.putBoolean(PREFS_KEY, darkMode);
                    editor.apply();
                    UiModeManager uiModeManager = context.getSystemService(UiModeManager.class);
                    uiModeManager.setNightMode(darkMode ? UiModeManager.MODE_NIGHT_YES : UiModeManager.MODE_NIGHT_NO);

                }
            }
        });

        imageList = new ArrayList<>();

        mListAdapter = new ImageViewerListAdapter(imageList);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setAdapter(mListAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == IMAGE_REQUEST_CODE) {
           if(data != null) {
               Uri imageUri = data.getData();
               if (imageUri != null) {
                   imageList.add(new ImageData(imageUri, nextId++));
                   mListAdapter.notifyItemChanged(imageList.size() - 1);
               }

           }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i("ActivityLifecycle", getLocalClassName() + " - onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("ActivityLifecycle", getLocalClassName() + " - onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("ActivityLifecycle", getLocalClassName() + " - onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("ActivityLifecycle", getLocalClassName() + " - onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("ActivityLifecycle", getLocalClassName() + " - onDestroy");
    }
}
