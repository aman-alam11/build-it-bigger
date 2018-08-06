package com.udacity.gradle.builditbigger;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.libraryjokesandroid.AndroidLibraryMain;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;


public class MainActivity extends AppCompatActivity implements EndpointsAsyncTask.JokeInterface {
    //    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        mProgressBar = findViewById(R.id.loading_pb);
//        FrameLayout mRootFragmentHolder = findViewById(R.id.fragment);
        addFragment();
    }

    public void addFragment() {
        MainActivityFragment mainActivityFragment = new MainActivityFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment, mainActivityFragment).commit();
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {
//        mProgressBar.setVisibility(View.VISIBLE);
        startAsyncTask();
    }

    public void startAsyncTask() {
        if (NetworkConnectivityChecker.isInternetConnectivityAvailable(MainActivity.this)) {
            new EndpointsAsyncTask(MainActivity.this).execute(this);
        } else {
            noInternetToast();
        }
    }

    /**
     * Send a Toast in case internet is not available
     */
    private void noInternetToast() {
        Toast.makeText(MainActivity.this,
                getResources().getString(R.string.no_internet_error),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void transferJoke(String joke) {
        if (joke == null || TextUtils.isEmpty(joke)) {
            noInternetToast();
            return;
        }
        Intent intent = new Intent(this, AndroidLibraryMain.class);
        intent.putExtra("JOKE_EXTRA", joke);
        startActivity(intent);
    }

}

