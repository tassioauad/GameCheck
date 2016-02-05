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
import com.tassioauad.gamecheck.entity.PlatformBuilder;

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
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SearchPlatformActivityTest {

    @Rule
    public ActivityTestRule<SearchPlatformActivity> activityRule =
            new ActivityTestRule<>(SearchPlatformActivity.class);

    @Test
    public void searchPlatformsByName_Sucessful() {

        onView(isAssignableFrom(EditText.class))
                .perform(typeText(PlatformBuilder.DEFAULT_NAME), pressImeActionButton());

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.recyclerview_platforms)).check(matches(isDisplayed()));
        onView(withId(R.id.progressbar)).check(matches(not(isDisplayed())));
        onView(withId(R.id.textview_noplatform)).check(matches(not(isDisplayed())));
        onView(withId(R.id.textview_connectionfailed)).check(matches(not(isDisplayed())));
    }

    @Test
    public void searchPlatformsByName_noplatform() {
        onView(isAssignableFrom(EditText.class))
                .perform(typeText(PlatformBuilder.WRONG_NAME), pressImeActionButton());

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.recyclerview_platforms)).check(matches(not(isDisplayed())));
        onView(withId(R.id.progressbar)).check(matches(not(isDisplayed())));
        onView(withId(R.id.textview_noplatform)).check(matches(isDisplayed()));
        onView(withId(R.id.textview_connectionfailed)).check(matches(not(isDisplayed())));
    }

    @Test
    public void searchPlatformsByName_ConnectionFailed() {
        WifiManager wifi = (WifiManager) activityRule.getActivity().getSystemService(Context.WIFI_SERVICE);
        wifi.setWifiEnabled(false);

        onView(isAssignableFrom(EditText.class))
                .perform(typeText(PlatformBuilder.DEFAULT_NAME), pressImeActionButton());

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.recyclerview_platforms)).check(matches(not(isDisplayed())));
        onView(withId(R.id.progressbar)).check(matches(not(isDisplayed())));
        onView(withId(R.id.textview_noplatform)).check(matches(not(isDisplayed())));
        onView(withId(R.id.textview_connectionfailed)).check(matches(isDisplayed()));

        wifi.setWifiEnabled(true);
        onView(withId(R.id.textview_connectionfailed)).perform(click());

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.recyclerview_platforms)).check(matches(isDisplayed()));
        onView(withId(R.id.progressbar)).check(matches(not(isDisplayed())));
        onView(withId(R.id.textview_noplatform)).check(matches(not(isDisplayed())));
        onView(withId(R.id.textview_connectionfailed)).check(matches(not(isDisplayed())));

    }

    @Test
    public void searchPlatformsByName_AfterScreenRotation() {

        onView(isAssignableFrom(EditText.class))
                .perform(typeText(PlatformBuilder.DEFAULT_NAME), pressImeActionButton());

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        activityRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        onView(withId(R.id.recyclerview_platforms)).check(matches(isDisplayed()));
        onView(withId(R.id.progressbar)).check(matches(not(isDisplayed())));
        onView(withId(R.id.textview_noplatform)).check(matches(not(isDisplayed())));
        onView(withId(R.id.textview_connectionfailed)).check(matches(not(isDisplayed())));
    }

    @Test
    public void selectingAPlatform() {

        onView(isAssignableFrom(EditText.class))
                .perform(typeText(PlatformBuilder.DEFAULT_NAME), pressImeActionButton());

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.recyclerview_platforms)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.ratingBar)).check(matches(isDisplayed()));
    }



}