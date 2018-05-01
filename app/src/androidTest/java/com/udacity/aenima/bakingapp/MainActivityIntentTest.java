package com.udacity.aenima.bakingapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import com.udacity.aenima.bakingapp.adapter.RecipeListAdapter;
import com.udacity.aenima.bakingapp.assertions.RecyclerViewNumberOfItemAssertion;
import com.udacity.aenima.bakingapp.ui.recipedetail.DetailActivity;
import com.udacity.aenima.bakingapp.ui.recipes.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class MainActivityIntentTest {


    @Rule
    public IntentsTestRule<MainActivity> intentsTestRule = new IntentsTestRule<>(MainActivity.class);
    private int RECIPE_ITEM_RANDOM_POSITION = 3;

    private IdlingResource mIdlingResource = null;

    @Before
    public void registerIdlingResource() {
        mIdlingResource = intentsTestRule.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(mIdlingResource);
    }

    @Test
    public void onRecipeClick_OpenDetailActivity() {
        //Find the view
        //Action on the view
        onView(withId(R.id.recipe_list_fragment))
                .perform(RecyclerViewActions
                        .<RecipeListAdapter.RecipeViewHolder>actionOnItemAtPosition(RECIPE_ITEM_RANDOM_POSITION, click()));
        //Check view action
        intended(hasComponent(new ComponentName(getTargetContext(), DetailActivity.class)));
    }

    @After
    public void unregisterIdlingResource() {
        if(mIdlingResource != null){
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }
}
