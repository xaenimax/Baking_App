package com.udacity.aenima.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import com.udacity.aenima.bakingapp.assertions.RecyclerViewNumberOfItemAssertion;
import com.udacity.aenima.bakingapp.ui.recipes.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class MainActivityRecipeFragmentTest {
    private static final int mNumberOfRecipes = 4;
    private Intent mIntent;
    private Context mContext;


    @Rule
    public IntentsTestRule<MainActivity> intentsTestRule =
            new IntentsTestRule<>(MainActivity.class, true, false);

    @Before
    public void setSharedPreferences() {
        mIntent = new Intent();
        mContext = getInstrumentation().getTargetContext();
    }

    @Test
    public void recipesList_IsPopulatedCorrectly() {
        // Set up
        setUpActivity();

        onView(withId(R.id.recipe_list_rv)).check(new RecyclerViewNumberOfItemAssertion(mNumberOfRecipes));
    }

    private void setUpActivity() {
        launchActivity();
        registerIdlingResource();
    }

    private void launchActivity() {
        intentsTestRule.launchActivity(mIntent);
    }

    private void registerIdlingResource() {
        IdlingRegistry.getInstance().register(intentsTestRule.getActivity().getIdlingResource());
    }
}
