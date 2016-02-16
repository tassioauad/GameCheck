package com.tassioauad.gamecheck.view.activity;


import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.wifi.WifiManager;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.tassioauad.gamecheck.R;
import com.tassioauad.gamecheck.entity.ImageBuilder;
import com.tassioauad.gamecheck.entity.PlatformBuilder;
import com.tassioauad.gamecheck.model.entity.Platform;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasSibling;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class PlatformActivityTest {

    @Rule
    public ActivityTestRule<PlatformActivity> activityRule =
            new ActivityTestRule<>(PlatformActivity.class, true, false);

    @Test
    public void checkingFields_NoDescription() {

        Platform platform = PlatformBuilder.aPlatform().withImage(ImageBuilder.aImage().build()).build();

        activityRule.launchActivity(PlatformActivity.newInstance(platform));

        onView(allOf(withId(R.id.textview_name), withParent(withId(R.id.linearlayout_header)))).check(matches(withText(platform.getName())));
        onView(allOf(withId(R.id.imageview_cover), hasSibling(withId(R.id.imageview_photo)))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.imageview_photo), hasSibling(withId(R.id.imageview_cover)))).check(matches(isDisplayed()));
        onView(withId(R.id.textview_description)).check(matches(not(isDisplayed())));

    }

    @Test
    public void checkingFields_NoGame() {

        Platform platform = PlatformBuilder.aInvalidPlatform().withImage(ImageBuilder.aImage().build()).build();

        activityRule.launchActivity(PlatformActivity.newInstance(platform));
        try {
            Thread.sleep(10000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        onView(withId(R.id.linearlayout_loading)).check(matches(not(isDisplayed())));
        onView(allOf(withId(R.id.textview_name), withParent(withId(R.id.linearlayout_header)))).check(matches(withText(platform.getName())));
        onView(allOf(withId(R.id.imageview_cover), hasSibling(withId(R.id.imageview_photo)))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.imageview_photo), hasSibling(withId(R.id.imageview_cover)))).check(matches(isDisplayed()));
        onView(withId(R.id.textview_nogame)).check(matches(isDisplayed()));
        onView(withId(R.id.recyclerview_games)).check(matches(not(isDisplayed())));
        onView(withId(R.id.textview_failuredTolist)).check(matches(not(isDisplayed())));

    }
    @Test
    public void checkingFields_WithGame() {

        Platform platform = PlatformBuilder.aPlatform()
                .withImage(ImageBuilder.aImage().build())
                .build();

        activityRule.launchActivity(PlatformActivity.newInstance(platform));

        try {
            Thread.sleep(10000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        onView(withId(R.id.linearlayout_loading)).check(matches(not(isDisplayed())));
        onView(allOf(withId(R.id.textview_name), withParent(withId(R.id.linearlayout_header)))).check(matches(withText(platform.getName())));
        onView(allOf(withId(R.id.imageview_cover), hasSibling(withId(R.id.imageview_photo)))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.imageview_photo), hasSibling(withId(R.id.imageview_cover)))).check(matches(isDisplayed()));
        onView(withId(R.id.textview_nogame)).check(matches(not(isDisplayed())));
        onView(withId(R.id.recyclerview_games)).check(matches(isDisplayed()));
        onView(withId(R.id.textview_failuredTolist)).check(matches(not(isDisplayed())));

        try {
            Thread.sleep(10000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        onView(withId(R.id.recyclerview_games)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }

    @Test
    public void checkingFields_ConnectionFailed() {

        Platform platform = PlatformBuilder.aPlatform()
                .withImage(ImageBuilder.aImage().build())
                .build();

        activityRule.launchActivity(PlatformActivity.newInstance(platform));
        WifiManager wifi = (WifiManager) activityRule.getActivity().getSystemService(Context.WIFI_SERVICE);
        wifi.setWifiEnabled(false);
        activityRule.getActivity().finish();
        activityRule.launchActivity(PlatformActivity.newInstance(platform));

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.recyclerview_games)).check(matches(not(isDisplayed())));
        onView(withId(R.id.linearlayout_loading)).check(matches(not(isDisplayed())));
        onView(withId(R.id.textview_nogame)).check(matches(not(isDisplayed())));
        onView(withId(R.id.textview_failuredTolist)).check(matches(isDisplayed()));

        wifi.setWifiEnabled(true);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.textview_failuredTolist)).perform(click());

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.recyclerview_games)).check(matches(isDisplayed()));
        onView(withId(R.id.linearlayout_loading)).check(matches(not(isDisplayed())));
        onView(withId(R.id.textview_nogame)).check(matches(not(isDisplayed())));
        onView(withId(R.id.textview_failuredTolist)).check(matches(not(isDisplayed())));

    }

    @Test
    public void checkingFields_WithDescription() {
        String description = "A Platform";
        Platform platform = PlatformBuilder.aPlatform().withDeck(description).withImage(ImageBuilder.aImage().build()).build();

        activityRule.launchActivity(PlatformActivity.newInstance(platform));

        onView(allOf(withId(R.id.textview_name), withParent(withId(R.id.linearlayout_header)))).check(matches(withText(platform.getName())));
        onView(allOf(withId(R.id.imageview_cover), hasSibling(withId(R.id.imageview_photo)))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.imageview_photo), hasSibling(withId(R.id.imageview_cover)))).check(matches(isDisplayed()));
        onView(withId(R.id.textview_description)).check(matches(isDisplayed()));
        onView(withId(R.id.textview_description)).check(matches(withText(description)));

    }

    @Test
    public void checkingFields_ScreenRotation() {
        String description = "A Platform";
        Platform platform = PlatformBuilder.aPlatform()
                .withDeck(description)
                .withImage(ImageBuilder.aImage().build())
                .build();

        activityRule.launchActivity(PlatformActivity.newInstance(platform));

        try {
            Thread.sleep(10000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        activityRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        onView(withId(R.id.linearlayout_loading)).check(matches(not(isDisplayed())));
        onView(allOf(withId(R.id.textview_name), withParent(withId(R.id.linearlayout_header)))).check(matches(withText(platform.getName())));
        onView(allOf(withId(R.id.imageview_cover), hasSibling(withId(R.id.imageview_photo)))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.imageview_photo), hasSibling(withId(R.id.imageview_cover)))).check(matches(isDisplayed()));
        onView(withId(R.id.textview_description)).check(matches(isDisplayed()));
        onView(withId(R.id.textview_description)).check(matches(withText(description)));
        onView(withId(R.id.textview_nogame)).check(matches(not(isDisplayed())));
        onView(withId(R.id.recyclerview_games)).check(matches(isDisplayed()));
        onView(withId(R.id.recyclerview_games)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

    }

    @Test
    public void selectRationBar() {
        String description = "A Platform";
        Platform platform = PlatformBuilder.aPlatform()
                .withDeck(description)
                .withImage(ImageBuilder.aImage().build())
                .build();

        activityRule.launchActivity(PlatformActivity.newInstance(platform));

        onView(withId(R.id.ratingBar)).perform(click());
    }

    @Test
    public void expandingImages() {
        String description = "A Platform";
        Platform platform = PlatformBuilder.aPlatform()
                .withDeck(description)
                .withImage(ImageBuilder.aImage().build())
                .build();

        activityRule.launchActivity(PlatformActivity.newInstance(platform));

        onView(allOf(withId(R.id.imageview_cover), hasSibling(withId(R.id.imageview_photo)))).perform(click());
        Espresso.pressBack();
        onView(allOf(withId(R.id.imageview_photo), hasSibling(withId(R.id.imageview_cover)))).perform(click());
        Espresso.pressBack();
    }

    @Test
    public void selectAGame() {
        String description = "A Platform";
        Platform platform = PlatformBuilder.aPlatform()
                .withDeck(description)
                .withImage(ImageBuilder.aImage().build())
                .build();

        activityRule.launchActivity(PlatformActivity.newInstance(platform));

        try {
            Thread.sleep(10000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        onView(withId(R.id.recyclerview_games)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.ratingBar)).check(matches(isDisplayed()));
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click());
    }



}
