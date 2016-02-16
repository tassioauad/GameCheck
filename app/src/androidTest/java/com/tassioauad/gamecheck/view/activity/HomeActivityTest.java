package com.tassioauad.gamecheck.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.wifi.WifiManager;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.widget.EditText;

import com.tassioauad.gamecheck.R;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasSibling;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

public class HomeActivityTest {

    @Rule
    public ActivityTestRule<HomeActivity> activityRule = new ActivityTestRule<>(HomeActivity.class);

    @Test
    public void screenRotation() {
        try {
            Thread.sleep(10000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        activityRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        onView(withId(R.id.recyclerview_games)).check(matches(isDisplayed()));
        onView(withId(R.id.textview_failuredtolistgame)).check(matches(not(isDisplayed())));
        onView(allOf(withId(R.id.progressbar), hasSibling(withId(R.id.textview_failuredtolistgame)))).check(matches(not(isDisplayed())));
        onView(withId(R.id.viewpager)).perform(swipeLeft());
        onView(withId(R.id.recyclerview_platforms)).check(matches(isDisplayed()));
        onView(withId(R.id.textview_failuredtolistplatform)).check(matches(not(isDisplayed())));
        onView(allOf(withId(R.id.progressbar), hasSibling(withId(R.id.textview_failuredtolistplatform)))).check(matches(not(isDisplayed())));
    }

    @Test
    public void failuredToListGames_NoInternet() {
        WifiManager wifi = (WifiManager) activityRule.getActivity().getSystemService(Context.WIFI_SERVICE);
        wifi.setWifiEnabled(false);

        activityRule.getActivity().finish();
        activityRule.launchActivity(new Intent());

        try {
            Thread.sleep(2500);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        onView(withId(R.id.recyclerview_games)).check(matches(not(isDisplayed())));
        onView(withId(R.id.textview_failuredtolistgame)).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.progressbar), hasSibling(withId(R.id.textview_failuredtolistgame)))).check(matches(not(isDisplayed())));

        onView(withId(R.id.textview_failuredtolistgame)).perform(click());

        try {
            Thread.sleep(2500);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        onView(withId(R.id.recyclerview_games)).check(matches(not(isDisplayed())));
        onView(withId(R.id.textview_failuredtolistgame)).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.progressbar), hasSibling(withId(R.id.textview_failuredtolistgame)))).check(matches(not(isDisplayed())));

        activityRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        try {
            Thread.sleep(2500);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        onView(withId(R.id.recyclerview_games)).check(matches(not(isDisplayed())));
        onView(withId(R.id.textview_failuredtolistgame)).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.progressbar), hasSibling(withId(R.id.textview_failuredtolistgame)))).check(matches(not(isDisplayed())));

        wifi.setWifiEnabled(true);
        try {
            Thread.sleep(10000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        onView(withId(R.id.textview_failuredtolistgame)).perform(click());

        try {
            Thread.sleep(10000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        onView(withId(R.id.recyclerview_games)).check(matches(isDisplayed()));
        onView(withId(R.id.textview_failuredtolistgame)).check(matches(not(isDisplayed())));
        onView(allOf(withId(R.id.progressbar), hasSibling(withId(R.id.textview_failuredtolistgame)))).check(matches(not(isDisplayed())));

    }

    @Test
    public void failuredToListPlatform_NoInternet() {
        WifiManager wifi = (WifiManager) activityRule.getActivity().getSystemService(Context.WIFI_SERVICE);
        wifi.setWifiEnabled(false);
        onView(withId(R.id.viewpager)).perform(swipeLeft());

        try {
            Thread.sleep(2500);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        onView(withId(R.id.recyclerview_platforms)).check(matches(not(isDisplayed())));
        onView(withId(R.id.textview_failuredtolistplatform)).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.progressbar), hasSibling(withId(R.id.textview_failuredtolistgame)))).check(matches(not(isDisplayed())));

        onView(withId(R.id.textview_failuredtolistplatform)).perform(click());

        try {
            Thread.sleep(2500);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        onView(withId(R.id.recyclerview_platforms)).check(matches(not(isDisplayed())));
        onView(withId(R.id.textview_failuredtolistplatform)).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.progressbar), hasSibling(withId(R.id.textview_failuredtolistgame)))).check(matches(not(isDisplayed())));

        activityRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        try {
            Thread.sleep(2500);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        onView(withId(R.id.recyclerview_platforms)).check(matches(not(isDisplayed())));
        onView(withId(R.id.textview_failuredtolistplatform)).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.progressbar), hasSibling(withId(R.id.textview_failuredtolistgame)))).check(matches(not(isDisplayed())));

        wifi.setWifiEnabled(true);
        try {
            Thread.sleep(10000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        onView(withId(R.id.textview_failuredtolistplatform)).perform(click());

        try {
            Thread.sleep(10000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        onView(withId(R.id.recyclerview_platforms)).check(matches(isDisplayed()));
        onView(withId(R.id.textview_failuredtolistplatform)).check(matches(not(isDisplayed())));
        onView(allOf(withId(R.id.progressbar), hasSibling(withId(R.id.textview_failuredtolistgame)))).check(matches(not(isDisplayed())));

    }

    @Test
    public void selectAGame() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.recyclerview_games)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.ratingBar)).check(matches(isDisplayed()));
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click());
    }

    @Test
    public void selectAPlatform() {
        onView(withId(R.id.viewpager)).perform(swipeLeft());
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.recyclerview_platforms)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.ratingBar)).check(matches(isDisplayed()));
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click());
    }

    @Test
    public void goToSearchGames() {
        onView(allOf(withId(R.id.floatingactionbutton_search), hasSibling(withId(R.id.textview_failuredtolistgame)))).perform(click());
        onView(isAssignableFrom(EditText.class)).check(matches(isDisplayed()));
        Espresso.pressBack();

    }

    @Test
    public void goToSearchPlatforms() {
        onView(withId(R.id.viewpager)).perform(swipeLeft());
        onView(allOf(withId(R.id.floatingactionbutton_search), hasSibling(withId(R.id.textview_failuredtolistplatform)))).perform(click());
        onView(isAssignableFrom(EditText.class)).check(matches(isDisplayed()));
        Espresso.pressBack();
    }



}
