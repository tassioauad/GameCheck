package com.tassioauad.gamecheck.view.activity;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.tassioauad.gamecheck.R;
import com.tassioauad.gamecheck.entity.GameBuilder;
import com.tassioauad.gamecheck.entity.ImageBuilder;
import com.tassioauad.gamecheck.entity.PlatformBuilder;
import com.tassioauad.gamecheck.model.entity.Game;
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
public class GameActivityTest {

    @Rule
    public ActivityTestRule<GameActivity> activityRule =
            new ActivityTestRule<>(GameActivity.class, true, false);

    @Test
    public void checkingFields_NoDescription() {

        Game game = GameBuilder.aGame().withImage(ImageBuilder.aImage().build()).build();

        activityRule.launchActivity(GameActivity.newInstance(game));

        onView(allOf(withId(R.id.textview_name), withParent(withId(R.id.linearlayout_header)))).check(matches(withText(game.getName())));
        onView(allOf(withId(R.id.imageview_cover), hasSibling(withId(R.id.imageview_photo)))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.imageview_photo), hasSibling(withId(R.id.imageview_cover)))).check(matches(isDisplayed()));
        onView(withId(R.id.textview_description)).check(matches(not(isDisplayed())));

    }

    @Test
    public void checkingFields_NoPlatform() {

        Game game = GameBuilder.aGame().withImage(ImageBuilder.aImage().build()).build();

        activityRule.launchActivity(GameActivity.newInstance(game));

        onView(allOf(withId(R.id.textview_name), withParent(withId(R.id.linearlayout_header)))).check(matches(withText(game.getName())));
        onView(allOf(withId(R.id.imageview_cover), hasSibling(withId(R.id.imageview_photo)))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.imageview_photo), hasSibling(withId(R.id.imageview_cover)))).check(matches(isDisplayed()));
        onView(withId(R.id.textview_noplatform)).check(matches(isDisplayed()));
        onView(withId(R.id.recyclerview_platforms)).check(matches(not(isDisplayed())));

    }
    @Test
    public void checkingFields_WithPlatform() {

        Game game = GameBuilder.aGame()
                .withPlatform(PlatformBuilder.aPlatform().withImage(ImageBuilder.aImage().build()).build())
                .withImage(ImageBuilder.aImage().build())
                .build();

        activityRule.launchActivity(GameActivity.newInstance(game));

        onView(allOf(withId(R.id.textview_name), withParent(withId(R.id.linearlayout_header)))).check(matches(withText(game.getName())));
        onView(allOf(withId(R.id.imageview_cover), hasSibling(withId(R.id.imageview_photo)))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.imageview_photo), hasSibling(withId(R.id.imageview_cover)))).check(matches(isDisplayed()));
        onView(withId(R.id.textview_noplatform)).check(matches(not(isDisplayed())));
        onView(withId(R.id.recyclerview_platforms)).check(matches(isDisplayed()));

        onView(withId(R.id.recyclerview_platforms)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }

    @Test
    public void checkingFields_WithDescription() {
        String description = "A Game";
        Game game = GameBuilder.aGame().withDeck(description).withImage(ImageBuilder.aImage().build()).build();

        activityRule.launchActivity(GameActivity.newInstance(game));

        onView(allOf(withId(R.id.textview_name), withParent(withId(R.id.linearlayout_header)))).check(matches(withText(game.getName())));
        onView(allOf(withId(R.id.imageview_cover), hasSibling(withId(R.id.imageview_photo)))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.imageview_photo), hasSibling(withId(R.id.imageview_cover)))).check(matches(isDisplayed()));
        onView(withId(R.id.textview_description)).check(matches(isDisplayed()));
        onView(withId(R.id.textview_description)).check(matches(withText(description)));

    }

    @Test
    public void checkingFields_ScreenRotation() {
        String description = "A Game";
        Game game = GameBuilder.aGame()
                .withDeck(description)
                .withPlatform(PlatformBuilder.aPlatform().withImage(ImageBuilder.aImage().build()).build())
                .withImage(ImageBuilder.aImage().build())
                .build();

        activityRule.launchActivity(GameActivity.newInstance(game));
        activityRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        onView(allOf(withId(R.id.textview_name), withParent(withId(R.id.linearlayout_header)))).check(matches(withText(game.getName())));
        onView(allOf(withId(R.id.imageview_cover), hasSibling(withId(R.id.imageview_photo)))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.imageview_photo), hasSibling(withId(R.id.imageview_cover)))).check(matches(isDisplayed()));
        onView(withId(R.id.textview_description)).check(matches(isDisplayed()));
        onView(withId(R.id.textview_description)).check(matches(withText(description)));
        onView(withId(R.id.textview_noplatform)).check(matches(not(isDisplayed())));
        onView(withId(R.id.recyclerview_platforms)).check(matches(isDisplayed()));
        onView(withId(R.id.recyclerview_platforms)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }

    @Test
    public void selectRationBar() {
        String description = "A Game";
        Game game = GameBuilder.aGame()
                .withDeck(description)
                .withPlatform(PlatformBuilder.aPlatform().withImage(ImageBuilder.aImage().build()).build())
                .withImage(ImageBuilder.aImage().build())
                .build();

        activityRule.launchActivity(GameActivity.newInstance(game));

        onView(withId(R.id.ratingBar)).perform(click());
    }

    @Test
    public void selectAPlatform() {
        String description = "A Game";
        Game game = GameBuilder.aGame()
                .withDeck(description)
                .withPlatform(PlatformBuilder.aPlatform().withImage(ImageBuilder.aImage().build()).build())
                .withImage(ImageBuilder.aImage().build())
                .build();

        activityRule.launchActivity(GameActivity.newInstance(game));

        onView(withId(R.id.recyclerview_platforms)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.ratingBar)).check(matches(isDisplayed()));
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click());
    }

    @Test
    public void expandingImages() {
        String description = "A Game";
        Game game = GameBuilder.aGame()
                .withDeck(description)
                .withPlatform(PlatformBuilder.aPlatform().withImage(ImageBuilder.aImage().build()).build())
                .withImage(ImageBuilder.aImage().build())
                .build();

        activityRule.launchActivity(GameActivity.newInstance(game));

        onView(allOf(withId(R.id.imageview_cover), hasSibling(withId(R.id.imageview_photo)))).perform(click());
        Espresso.pressBack();
        onView(allOf(withId(R.id.imageview_photo), hasSibling(withId(R.id.imageview_cover)))).perform(click());
        Espresso.pressBack();
    }

}
