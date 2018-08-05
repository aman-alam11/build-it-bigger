package com.example.libraryjokesandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AndroidLibraryMain extends AppCompatActivity {

    private final String JOKE_EXTRA = "JOKE_EXTRA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_library_main);
        TextView jokesDisplayTextView = findViewById(R.id.show_joke_text_view);


        if (getIntent().hasExtra(JOKE_EXTRA)) {
            jokesDisplayTextView.setText(getIntent().getExtras().getString(JOKE_EXTRA));
        }
    }
}
