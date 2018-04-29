package com.udacity.aenima.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.udacity.aenima.bakingapp.ui.recipedetail.DetailActivity;
import com.udacity.aenima.bakingapp.ui.recipedetail.step.StepActivity;
import com.udacity.aenima.bakingapp.ui.recipes.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityBasicTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    //private IdlingResource mIdlingResource;
    /*
    @Before
    public void registerIdlingResource(){

        mIdlingResource = mainActivityActivityTestRule.getActivity().getIdlingResource();
        //Espresso.registerIdlingResources() deprecated
        IdlingRegistry.getInstance().register(mIdlingResource);

    }
    */

    @Test
    public void onRecipeClick_OpenDetailActivity(){
    //Find the view
    //Action on the view
        onView(withText("Baking App")).check(matches(isDisplayed()));
       // onView(withId(R.id.recipe_list_rv)).perform(RecyclerViewActions.scrollToPosition(0)).perform(click());
        //intended(hasComponent(DetailActivity.class.getName()));
    //Check view action
    }
/*
    @After
    public void unregisterIdlingResource(){
        if(mIdlingResource != null){
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }
    */
}
