package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

/**
 * Async task to get jokes
 */
public class EndpointsAsyncTask extends AsyncTask<Context, Void, String> {
    private static MyApi myApiService = null;
    private JokeInterface mJokeInterface;
    private String mJoke;

    public EndpointsAsyncTask(JokeInterface jokeInterface) {
        this.mJokeInterface = jokeInterface;
    }

    @Override
    protected String doInBackground(Context... params) {
        if (myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest)
                                throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }

//            Context context = params[0];
        try {

            return myApiService.getJokesMethod().execute().getData();
        } catch (IOException e) {
            Log.e("Error", e.getMessage());
        }

        return null;
    }

    @Override
    protected void onPostExecute(String joke) {
        super.onPostExecute(joke);

        if (joke != null && !TextUtils.isEmpty(joke))
            this.mJoke = joke;

        // Pass Data for display
        mJokeInterface.transferJoke(joke);

    }

    public String sendJoke() {
        return this.mJoke;
    }


    public interface JokeInterface {
        void transferJoke(String joke);
    }

}