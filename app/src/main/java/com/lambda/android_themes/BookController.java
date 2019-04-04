package com.lambda.android_themes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class BookController  extends AppCompatActivity {
    private Context context;
    private SharedPrefsDao spd;
    private SharedPreferences preferences;
    private String strRetrieved;
    private Book bookCurrent;
    BookController(Context context){
        this.context=context;

        this.preferences = context.getSharedPreferences("BookRecord", MODE_PRIVATE);

        strRetrieved=preferences.getString(    "IDS_FOR_BOOK"     ,"" );
        if(strRetrieved.contains( "new" )) {//deleting garbage from earlier version
            strRetrieved=strRetrieved.replace("new","");
            strRetrieved=strRetrieved.replace(",,","");
        }
        if(!strRetrieved.equals( "" )){
            spd=new SharedPrefsDao(strRetrieved);
            String[] straTemp=          strRetrieved.split("," );
            for(int i=0;i<straTemp.length;i++){
                strRetrieved=preferences.getString(    "DATA_FOR_BOOK"+straTemp[i]     ,"" );
             }
        }
    }

    public TextView buildItemView() {
        if(strRetrieved.equals( "" ))return null;
        Book book=new  Book(strRetrieved);
        if(book==null)return null;
        TextView tv = new TextView( context );
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

    private void sendData(TextView tv){
        Intent intent = new Intent(context, EditBookActivity.class);
        String strTemp=tv.getText().toString().split( "," )[0];
        strTemp=spd.strBookCSVByID( strTemp );
        int i=spd.size();
        if(strTemp.equals( "Invalid ID" ))return;

        intent.putExtra("DATA", strTemp);
        setContentView( R.layout.activity_main );
        startActivityForResult(intent, 1);
    }

    public void setSharedPreferences(Book bk){
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

    public void sendEmptyData(){
        Intent intent = new Intent(context, EditBookActivity.class);
        intent.putExtra("DATA", "");
        //onActivityResult( 1, RESULT_OK ,intent);
        //startActivityForResult(intent, 1);
        startActivity(intent);
    }

    private void writeSharedPreference(String str){
        SharedPreferences.Editor editor = this.preferences.edit();
        editor.putString("string", str);
        editor.apply();
    }
    private String readSharedPreference(String str){
        return  this.preferences.getString("string","");
    }



    public Book receiveData(Intent intent){
        //   if(bookCurrent==null)return;
        Book bookCurrent;
        String strTemp=(String) intent.getStringExtra("DATA");

        if(strTemp==null)return null;
        bookCurrent=new Book(strTemp);

        if(spd==null) {
            spd=new SharedPrefsDao(bookCurrent.toCsvString());
        } else{
            if(bookCurrent.getStrTitle().equals( "" )){//for deleting

                setSharedPreferences( bookCurrent);
                spd= spd.updateBook(bookCurrent);
                if(spd.size()==0)bookCurrent=null;
            }else{  //for adding or modifying
                spd= spd.updateBook(bookCurrent);
                setSharedPreferences( bookCurrent);
            }

        }
        return bookCurrent;

    }
}
