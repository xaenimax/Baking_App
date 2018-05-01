package com.udacity.aenima.bakingapp;



import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.udacity.aenima.bakingapp.ui.recipes.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityBasicTest {
    private final static String BROWNIES_RECIPE_NAME = "Nutella Pie";
    private final static int RECIPE_LIST_NUTELLA_POSITION = 0;

    private IdlingResource mIdlingResource;


    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);


    @Before
    public void registerIdlingResource() {
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(mIdlingResource);
    }


    @Test
    public void recipeList_IsDisplayed(){
        onView(withId(R.id.recipe_list_fragment))
                .check(matches(isDisplayed()));
    }

    @Test
    public void recipeList_HasBrownies() {
        onView(withId(R.id.recipe_list_fragment))
                .perform(RecyclerViewActions
                        .scrollToPosition(RECIPE_LIST_NUTELLA_POSITION));

        onView(withText(BROWNIES_RECIPE_NAME))
                .check(matches(isDisplayed()));
    }
    /*
    @Test
    public void onRecipeClicked_IngredientButtonIsDiplayed() {
        onView(withId(R.id.recipe_list_fragment))
                .perform(RecyclerViewActions
                        .actionOnItemAtPosition(0, click()));

        onView(withId(R.id.ingredient_card_view))
                .check(matches(isDisplayed()));
    }
    */

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }


}
