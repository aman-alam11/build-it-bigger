package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class JokeTestsAsync{

    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule = new ActivityTestRule<>(MainActivity.class);

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

    @Test
    public void checkAsync() throws Throwable {
//        http://marksunghunpark.blogspot.com/2015/05/how-to-test-asynctask-in-android.html

        // Call Async Task
        // In PostExecute, it transfers data and sends data with intent to new activity for display
        EndpointsAsyncTask asyncTask = new EndpointsAsyncTask(mainActivityTestRule.getActivity());
        asyncTask.execute(mainActivityTestRule.getActivity());
        // We can also use countdownLatch here
        Thread.sleep(2000);
        // Read from the displayed data and check if it matches
        onView(withId(com.example.libraryjokesandroid.R.id.show_joke_text_view)).check(matches((withText(asyncTask.sendJoke()))));
    }


}
