package com.example.jacob.imageviewer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int IMAGE_REQUEST_CODE = 3;
    private Context context;
    private ArrayList<ImageData> imageArrayList = new ArrayList<>();
    private GridLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private ImageListAdapter listAdapter;
    private int currentTheme;
    private Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeUtils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_main);
        context = this;
        activity = this;

        findViewById(R.id.button_pickimage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imageIntent = new Intent(Intent.ACTION_GET_CONTENT);
                imageIntent.setType("image/*");
                startActivityForResult(imageIntent, IMAGE_REQUEST_CODE);
            }
        });

        recyclerView = findViewById(R.id.note_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        listAdapter = new ImageListAdapter(imageArrayList);
        recyclerView.setAdapter(listAdapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_REQUEST_CODE) {
                Uri imageUri = data.getData();

                Cursor cursor = getContentResolver().query(imageUri, null, null,
                        null, null);
                cursor.moveToFirst();
                String imageName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME));
                cursor.close();
                ImageData selectedImage = new ImageData(imageUri, imageName);
                imageArrayList.add(selectedImage);
                listAdapter.notifyItemInserted(imageArrayList.size() - 1);
            }
        }
    }


    @Override
    public void setTheme(int resid) {
        currentTheme = resid;
        super.setTheme(resid);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!ThemeUtils.checkTheme(activity, currentTheme)) {
            ThemeUtils.refreshActivity(activity);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!ThemeUtils.checkTheme(activity, currentTheme)) {
            ThemeUtils.refreshActivity(activity);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(context, SettingsActivity.class);
            startActivity(intent);
        }
        return true;
    }
}
