package com.jakeesveld.android_sprint1_challenge;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

public class EditActivity extends AppCompatActivity {

    Button buttonDelete;
    Button buttonAdd;
    EditText textEditTitle;
    Switch switchWatched;
    Boolean isWatched;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        final Intent intent = getIntent();
        final Movies newMovie = (Movies) intent.getSerializableExtra("movie");

        buttonAdd = findViewById(R.id.button_add);
        buttonDelete = findViewById(R.id.button_delete);
        textEditTitle = findViewById(R.id.text_edit_title);
        switchWatched = findViewById(R.id.switch_watched);
        context = this;
        isWatched = false;



        if (newMovie.getTitle() != ""){
            textEditTitle.setText(newMovie.getTitle());
        }

        textEditTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                newMovie.setTitle(textEditTitle.getText().toString());
            }
        });

        switchWatched.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    newMovie.setWatched(true);
                }else{
                    newMovie.setWatched(false);
                }
            }
        });

        if (newMovie.getWatched()){
            switchWatched.setChecked(true);
        }



        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("movie", newMovie);
                intent.putExtra("add", true);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("movie", newMovie);
                intent.putExtra("add", false);
                setResult(RESULT_OK, intent);
                finish();

            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }
}
