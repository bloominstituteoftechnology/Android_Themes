package com.lambda.android_themes;

import android.app.UiModeManager;
import android.content.Context;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private LinearLayout llScroll;

    private Context context;
    private BookController bc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        llScroll=findViewById( R.id.scrolling_view );
        context=getApplicationContext();

        if(bc==null)bc=new BookController( context);

        llScroll.addView( bc.buildItemView());

        findViewById(R.id.button_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bc.sendEmptyData();
            }
        });

        findViewById(R.id.button_night_mode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonNIghtControl();
            }
        });
    }


    void buttonNIghtControl(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            Button bt=findViewById( R.id.button_night_mode );
            UiModeManager umm=context.getSystemService(UiModeManager.class);
            if(umm.getNightMode()==UiModeManager.MODE_NIGHT_YES ){
                umm.setNightMode( UiModeManager.MODE_NIGHT_NO );
                bt.setText( "Night mode" );//I do not know why this is not working
                TextView tv=new TextView( context ); ///I do not know why this is not working
                tv.setText( "Day" );//I do not know why this is not working
                llScroll.addView( tv ); //I do not know why this is not working


            }else{
                umm.setNightMode( UiModeManager.MODE_NIGHT_YES );
                bt.setText( "Day mode" );//I do not know why this is not working
                TextView tv=new TextView( context );//I do not know why this is not working
                tv.setText( "Night" );//I do not know why this is not working
                llScroll.addView( tv );///I do not know why this is not working
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                bc.receiveData(data);
                llScroll.removeAllViews();
              //  for(int i=0;i<size;i++){
            //        if(!spd.bkBookByID( i ).getStrID().equals( "" ))llScroll.addView(bc.buildItemView(spd.bkBookByID( i )));
                //}
            }
            if (resultCode == RESULT_CANCELED) {
                //Do nothing
            }
        }
    }






}
