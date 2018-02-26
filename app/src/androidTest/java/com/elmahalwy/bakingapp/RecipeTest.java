package com.elmahalwy.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.elmahalwy.thirdproject.Activties.DetailsActivity;
import com.elmahalwy.thirdproject.Activties.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by cz on 26/02/2018.
 */
@RunWith(AndroidJUnit4.class)
public class RecipeTest {
    @Rule
    public ActivityTestRule<MainActivity> recipe_test
            = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void onStartTheMain() {
// find the view
        onView(withId(R.id.tv_toolbar_title))
                // check matches
                .check(matches(withText("Recipes")));
    }


    @Test
    public void onRecipeItemClicked_OpenRecipeDetails() {
        onView(withId(R.id.rv_main))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        intended(hasComponent(DetailsActivity.class.getName()));

        onView(withId(R.id.rv_main))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        intended(hasComponent(DetailsActivity.class.getName()));

        onView(withId(R.id.rv_main))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        intended(hasComponent(DetailsActivity.class.getName()));

        onView(withId(R.id.rv_main))
                .perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
        intended(hasComponent(DetailsActivity.class.getName()));
    }


    @Test
    public void onLoadFinished_DisplaysRecipesRecyclerView() {
        onView(withId(R.id.rv_main)).check(matches(isCompletelyDisplayed()));
    }
}
