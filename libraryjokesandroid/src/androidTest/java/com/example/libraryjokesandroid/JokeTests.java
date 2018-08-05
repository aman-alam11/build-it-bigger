package com.example.libraryjokesandroid;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class JokeTests {

    @Rule
    public ActivityTestRule<AndroidLibraryMain> jokeActivityTestRule = new ActivityTestRule<>(AndroidLibraryMain.class);

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.libraryjokesandroid.test", appContext.getPackageName());
    }


    @Test
    public void CheckJokeEmptyText() {
        //https://stackoverflow.com/questions/46598149/test-a-textview-value-is-not-empty-using-espresso-and-fail-if-a-textview-value-i
        onView(withId(R.id.show_joke_text_view)).check(matches(withText("")));
    }

}
