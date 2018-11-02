package com.example.android.bakingfun;
import android.content.Context;
import android.content.res.Configuration;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.AndroidJUnitRunner;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import android.support.test.rule.ActivityTestRule;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import android.support.test.espresso.contrib.RecyclerViewActions;
import static org.hamcrest.CoreMatchers.containsString;
@RunWith(AndroidJUnit4.class)
@LargeTest
public class RecipeCardClickedTest extends AndroidJUnitRunner {
    // Context of the app under test.
    private final Context appContext = InstrumentationRegistry.getTargetContext();
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    @Test
    public void listDisplayed() {

     SystemClock.sleep(1000);

        onView(ViewMatchers.withId(R.id.recycler_view)).perform(RecyclerViewActions.scrollToPosition(1));
        onView(withText("Nutella Pie")).check(matches(isDisplayed())).perform(click());
            onView(ViewMatchers.withId(R.id.ingredients)).perform(RecyclerViewActions.scrollToPosition(4));
            onView(withText("salt")).check(matches(isDisplayed()));
            onView(withText(appContext.getResources().getString(R.string.steps))).check(matches(isDisplayed())).perform(click());
            onView(ViewMatchers.withId(R.id.steps_lv)).perform(RecyclerViewActions.scrollToPosition(1));
            onView(withText("Recipe Introduction")).check(matches(isDisplayed())).perform(click());
            onView(ViewMatchers.withId(R.id.video_view)).check(matches(isDisplayed()));
        if((appContext.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE){
            onView(ViewMatchers.withId(R.id.steps_lv)).perform(RecyclerViewActions.scrollToPosition(2));
            onView(withText("Starting prep")).check(matches(isDisplayed())).perform(click());
            onView(ViewMatchers.withId(R.id.description_tv)).check(matches(isDisplayed()));
        }else {
            onView(ViewMatchers.withId(R.id.next_button)).perform(click());
            onView(ViewMatchers.withId(R.id.pre_button)).check(matches(isDisplayed()));
            onView(ViewMatchers.withId(R.id.stepnumbers_tv)).check(matches(withText(containsString("1/6"))));
        }
    }

}