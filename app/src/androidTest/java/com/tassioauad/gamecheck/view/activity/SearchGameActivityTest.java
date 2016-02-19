package com.tassioauad.gamecheck.view.activity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.wifi.WifiManager;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.widget.EditText;

import com.tassioauad.gamecheck.R;
import com.tassioauad.gamecheck.entity.GameBuilder;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SearchGameActivityTest {

    @Rule
    public ActivityTestRule<SearchGameActivity> activityRule =
            new ActivityTestRule<>(SearchGameActivity.class);

    @Test
    public void searchGamesByName_Sucessful() {

        onView(isAssignableFrom(EditText.class))
                .perform(typeText(GameBuilder.DEFAULT_NAME), pressImeActionButton());

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.recyclerview_games)).check(matches(isDisplayed()));
        onView(withId(R.id.progressbar)).check(matches(not(isDisplayed())));
        onView(withId(R.id.textview_nogame)).check(matches(not(isDisplayed())));
        onView(withId(R.id.textview_connectionfailed)).check(matches(not(isDisplayed())));
    }

    @Test
    public void searchGamesByName_NoGame() {
        onView(isAssignableFrom(EditText.class))
                .perform(typeText(GameBuilder.WRONG_NAME), pressImeActionButton());

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.recyclerview_games)).check(matches(not(isDisplayed())));
        onView(withId(R.id.progressbar)).check(matches(not(isDisplayed())));
        onView(withId(R.id.textview_nogame)).check(matches(isDisplayed()));
        onView(withId(R.id.textview_connectionfailed)).check(matches(not(isDisplayed())));
    }

    @Test
    public void searchGamesByName_ConnectionFailed() {
        WifiManager wifi = (WifiManager) activityRule.getActivity().getSystemService(Context.WIFI_SERVICE);
        wifi.setWifiEnabled(false);

        onView(isAssignableFrom(EditText.class))
                .perform(typeText(GameBuilder.DEFAULT_NAME), pressImeActionButton());

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.recyclerview_games)).check(matches(not(isDisplayed())));
        onView(withId(R.id.progressbar)).check(matches(not(isDisplayed())));
        onView(withId(R.id.textview_nogame)).check(matches(not(isDisplayed())));
        onView(withId(R.id.textview_connectionfailed)).check(matches(isDisplayed()));

        wifi.setWifiEnabled(true);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.textview_connectionfailed)).perform(click());

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.recyclerview_games)).check(matches(isDisplayed()));
        onView(withId(R.id.progressbar)).check(matches(not(isDisplayed())));
        onView(withId(R.id.textview_nogame)).check(matches(not(isDisplayed())));
        onView(withId(R.id.textview_connectionfailed)).check(matches(not(isDisplayed())));

    }

    @Test
    public void searchGamesByName_AfterScreenRotation() {

        onView(isAssignableFrom(EditText.class))
                .perform(typeText(GameBuilder.DEFAULT_NAME), pressImeActionButton());

        activityRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        onView(withId(R.id.recyclerview_games)).check(matches(isDisplayed()));
        onView(withId(R.id.progressbar)).check(matches(not(isDisplayed())));
        onView(withId(R.id.textview_nogame)).check(matches(not(isDisplayed())));
        onView(withId(R.id.textview_connectionfailed)).check(matches(not(isDisplayed())));
    }

    @Test
    public void searchGamesByName_BeforeScreenRotation() {

        onView(isAssignableFrom(EditText.class))
                .perform(typeText(GameBuilder.DEFAULT_NAME), pressImeActionButton());

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        activityRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        onView(withId(R.id.recyclerview_games)).check(matches(isDisplayed()));
        onView(withId(R.id.progressbar)).check(matches(not(isDisplayed())));
        onView(withId(R.id.textview_nogame)).check(matches(not(isDisplayed())));
        onView(withId(R.id.textview_connectionfailed)).check(matches(not(isDisplayed())));
    }

    @Test
    public void selectingAGame() {

        onView(isAssignableFrom(EditText.class))
                .perform(typeText(GameBuilder.DEFAULT_NAME), pressImeActionButton());

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.recyclerview_games)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.ratingBar)).check(matches(isDisplayed()));
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click());
    }


}