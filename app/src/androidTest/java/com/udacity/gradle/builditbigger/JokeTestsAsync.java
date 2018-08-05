package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.MainActivity;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class JokeTestsAsync {

    @Rule
    public ActivityTestRule<MainActivity> jokeActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.udacity.gradle.builditbigger", appContext.getPackageName());
    }

    @Before
    public void CheckJokeEmptyText() {
        onView(withId(com.udacity.gradle.builditbigger.R.id.get_joke_button)).perform(click());
    }

    @Test
    public void checkViewTest() {
        //https://stackoverflow.com/questions/46598149/test-a-textview-value-is-not-empty-using-espresso-and-fail-if-a-textview-value-i
        onView(withId(com.example.libraryjokesandroid.R.id.show_joke_text_view)).check(matches(not(withText(""))));
    }


//    https://stackoverflow.com/a/5722193/8279637
    @Test
    public void checkAsync() throws Throwable {
        final CountDownLatch signal = new CountDownLatch(1);

        final AsyncTask<Void, Void, String> myTask = new AsyncTask<Void, Void, String>() {
            private MyApi myApiService = null;

            @Override
            protected String doInBackground(Void... voids) {
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

                try {

                    return myApiService.getJokesMethod().execute().getData();
                } catch (IOException e) {
                    Log.e("Error", e.getMessage());
                }

                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                signal.countDown();
            }
        };

        // Execute the async task on the UI thread! THIS IS KEY!
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                myTask.execute();
            }
        });

        signal.await(5, TimeUnit.SECONDS);

        // The task is done, and now you can assert some things!
        onView(withId(com.example.libraryjokesandroid.R.id.show_joke_text_view)).check(matches(not(withText(""))));
    }
}
