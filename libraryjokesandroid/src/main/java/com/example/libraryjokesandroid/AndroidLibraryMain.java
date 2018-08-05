package com.example.libraryjokesandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import java.util.Objects;

public class AndroidLibraryMain extends AppCompatActivity {

    private final String JOKE_EXTRA = "JOKE_EXTRA";
    private String joke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_library_main);
        TextView jokesDisplayTextView = findViewById(R.id.show_joke_text_view);


        if (savedInstanceState == null && getIntent().hasExtra(JOKE_EXTRA)) {
            joke = Objects.requireNonNull(getIntent().getExtras()).getString(JOKE_EXTRA);
        }

        if (savedInstanceState != null && savedInstanceState.getString(JOKE_EXTRA) != null) {
            if (!TextUtils.isEmpty(savedInstanceState.getString(JOKE_EXTRA))) {
                joke = savedInstanceState.getString(JOKE_EXTRA);
            }
        }

        if (joke == null) {
            return;
        }
        jokesDisplayTextView.setText(joke);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (joke != null && !TextUtils.isEmpty(joke)) {
            outState.putString(JOKE_EXTRA, joke);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
