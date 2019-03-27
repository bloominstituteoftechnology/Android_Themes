package com.example.israel.imageviewer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_IMAGE = 1;

    public static final String DEBUG_TAG = "MyDebug";

    private Button buttonAddImage;
    private ArrayList<ImageData> imageDataArr = new ArrayList<>();
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ImageListAdapter imageListAdapter;

    private int currentThemeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeUtils.onActivityCreateSetTheme(this); // set theme from preferences

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar_toolbar);
        setSupportActionBar(toolbar);

        Log.d(DEBUG_TAG, "MainActivity.onCreated");

        buttonAddImage = findViewById(R.id.button_add_image);

        recyclerView = findViewById(R.id.recycler_view_image_list);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        imageListAdapter = new ImageListAdapter(this, imageDataArr);
        recyclerView.setAdapter(imageListAdapter);

        buttonAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImage();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {

            Log.d("MyDebug", "onActivityResult result not ok");
            return;
        }

        if (requestCode == REQUEST_CODE_IMAGE) {
            if (data != null) {
                Uri uri = data.getData();
                String fileName = getFileName(uri);
                ImageData newImageData = new ImageData(fileName, uri);
                imageDataArr.add(newImageData);
                imageListAdapter.notifyItemInserted(imageDataArr.size() - 1);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_settings: {
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    private void getImage() {
        Intent intent = new Intent(Intent.ACTION_PICK /*, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI*/);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_CODE_IMAGE);
    }

    @Override
    public void setTheme(int resid) {
        currentThemeId = resid;
        super.setTheme(resid);
    }

    public static String getFileName(Uri uri) {
        String result;
        result = uri.getPath();
        int cut = result.lastIndexOf('/');
        if (cut != -1) {
            result = result.substring(cut + 1);
        }
        return result;
    }

    public static int dpToPx(float dp, Context context) {
         return (int) (dp * context.getResources().getDisplayMetrics().scaledDensity);
        //return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public static int spToPx(float sp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }

    public static int dpToSp(float dp, Context context) {
        return (int) (dpToPx(dp, context) / context.getResources().getDisplayMetrics().scaledDensity);
    }

    public static int pxToDp(float px, Context context) {
        return (int) (px / context.getResources().getDisplayMetrics().scaledDensity);
    }

    @Override
    protected void onStart() {
        Log.d(DEBUG_TAG, "MainActivity.onStart");

        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d(DEBUG_TAG, "MainActivity.onResume");

        super.onResume();
    }

    // looper loops here

    @Override
    protected void onPause() {
        Log.d(DEBUG_TAG, "MainActivity.onPause");

        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(DEBUG_TAG, "MainActivity.onStop");

        super.onStop();
    }

    @Override
    protected void onRestart() {
        Log.d(DEBUG_TAG, "MainActivity.onRestart");

        // TODO this should be done in onPreferenceChanged of the settings?????
        // only restart if the theme is not the same as the selected theme
        if (currentThemeId != ThemeUtils.getSelectedTheme(this)) {
            ThemeUtils.refreshActivity(this);
        }

        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        Log.d(DEBUG_TAG, "MainActivity.onDestroy");

        super.onDestroy();
    }

}
