package com.lambda.android_themes;

import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private Book bookCurrent;
    private LinearLayout llScroll;
    private SharedPreferences preferences;
    private static SharedPrefsDao spd;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        llScroll=findViewById( R.id.scrolling_view );
        context=getApplicationContext();

        if(this.preferences==null){
            this.preferences = getApplicationContext().getSharedPreferences("BookRecord3", MODE_PRIVATE);
            String strRetrieved=preferences.getString(    "IDS_FOR_BOOK"     ,"" );
            if(strRetrieved.contains( "new" )) {
                strRetrieved=strRetrieved.replace("new","");
                strRetrieved=strRetrieved.replace(",,","");

            }
            if(strRetrieved.equals( "" )){
//test
                //   spd.updateBook( new Book("new", "test,title,comma","to test comma",true  ) );

            }else{
                spd=new SharedPrefsDao(strRetrieved);
                String[] straTemp=          strRetrieved.split("," );
                for(int i=0;i<straTemp.length;i++){
                    strRetrieved=preferences.getString(    "DATA_FOR_BOOK"+straTemp[i]     ,"" );
                    if(strRetrieved.equals( "" )){
                       // setSharedPreferences(straTemp[i],""  );

                        // break;
                    }else{
                        llScroll.addView( buildItemView(new  Book(strRetrieved) ) );
                    }
                }
            }




        }

        findViewById(R.id.button_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendEmptyData();
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
            bt.setText( "Night mode" );
        }else{
            umm.setNightMode( UiModeManager.MODE_NIGHT_YES );
            bt.setText( "Day mode" );

        }


    }
}


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                receiveData(data);
                llScroll.removeAllViews();
                int size=spd.size();
                for(int i=0;i<size;i++){

                    if(!spd.bkBookByID( i ).getStrID().equals( "" ))llScroll.addView(buildItemView( spd.bkBookByID( i )));

                }

            }
            if (resultCode == RESULT_CANCELED) {

            }
        }
    }

    private TextView buildItemView(Book book) {
        if(book==null)return null;
        TextView tv = new TextView( getApplicationContext() );
        bookCurrent=book;
        String strTemp=book.toCsvString();

        if(book.isbHasBeenRead()==true) {
            tv.setText(strTemp.replace( ",true","" ));
            tv.setPaintFlags(tv.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        }else{

            tv.setText(strTemp.replace( ",false","" ));

        }
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView currentTextView = (TextView)v;
                sendData(currentTextView);
            }
        });
        spd=spd.updateBook( book);
        setSharedPreferences(book);
        return tv;
    }

    private void setSharedPreferences(Book bk){
        SharedPreferences.Editor editor = preferences.edit();
        String strRetrieved=preferences.getString(    "IDS_FOR_BOOK"     ,"" );
        if(strRetrieved.contains(bk.getStrID() )){
            if(bk.getStrTitle().equals( "" )){
                strRetrieved=strRetrieved.replace(bk.getStrID(),"" );
                strRetrieved=strRetrieved.replace(",,", "," );
                editor.putString("IDS_FOR_BOOK", strRetrieved);
                editor.remove( "DATA_FOR_BOOK"+bk.getStrID());
            }
        }else{
            if(strRetrieved.equals( "" )){
                strRetrieved=bk.getStrID();
            }else{
                strRetrieved+=","+bk.getStrID();
            }

            editor.putString("IDS_FOR_BOOK", strRetrieved);
            editor.putString("DATA_FOR_BOOK"+bk.getStrID(), bk.toCsvString());

        }
//editor.clear();
        editor.apply();
    }



    private void writeSharedPreference(String str){
        SharedPreferences.Editor editor = this.preferences.edit();
        editor.putString("string", str);
        editor.apply();
    }
    private String readSharedPreference(String str){
        return  this.preferences.getString("string","");

    }


    private void sendData(TextView tv){
        Context context = getApplicationContext();


        Intent intent = new Intent(context, EditBookActivity.class);
        String strTemp=tv.getText().toString().split( "," )[0];
        strTemp=spd.strBookCSVByID( strTemp );
        int i=spd.size();
        if(strTemp.equals( "Invalid ID" ))return;

        intent.putExtra("DATA", strTemp);
        startActivityForResult(intent, 1);
    }
    private void sendEmptyData(){
        Context context = getApplicationContext();


        Intent intent = new Intent(context, EditBookActivity.class);
        intent.putExtra("DATA", "");
        startActivityForResult(intent, 1);
    }

    private void receiveData(Intent intent){
        //   if(bookCurrent==null)return;

        String strTemp=(String) intent.getStringExtra("DATA");

        if(strTemp==null)return;
        bookCurrent=new Book(strTemp);

        if(spd==null) {
            spd=new SharedPrefsDao(bookCurrent.toCsvString());
        } else{
            if(bookCurrent.getStrTitle().equals( "" )){//for deleting

                setSharedPreferences( bookCurrent);
                spd= spd.updateBook(bookCurrent);
                if(spd.size()==0)bookCurrent=null
            }else{  //for adding or modifying
                spd= spd.updateBook(bookCurrent);
                setSharedPreferences( bookCurrent);
            }

        }
        return;

    }



}
