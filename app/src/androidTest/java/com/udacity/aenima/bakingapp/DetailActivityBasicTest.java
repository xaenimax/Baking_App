package com.udacity.aenima.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.udacity.aenima.bakingapp.data.Ingredient;
import com.udacity.aenima.bakingapp.data.Recipe;
import com.udacity.aenima.bakingapp.data.Step;
import com.udacity.aenima.bakingapp.ui.recipedetail.DetailActivity;

import static android.support.test.espresso.Espresso.onView;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import android.support.test.espresso.contrib.RecyclerViewActions;

import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.udacity.aenima.bakingapp.ui.recipes.RecipeFragment.RECIPE_EXTRA;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class DetailActivityBasicTest {
    public static final int STEP_POSITION = 0;
    List<Ingredient> fakeIngredients = new ArrayList<Ingredient>(){
        {
            add(new Ingredient(100, "G", "Flour"));
            add(new Ingredient(200, "G", "Sugar"));
            add(new Ingredient(2, "Unit", "Eggs"));
        }
    };
    List<Step> fakeSteps = new ArrayList<Step>(){
        {
            add(new Step("Introduction", "Easy to bake, it has a nice flavour", "", ""));
            add(new Step("Add Flour", "Start by adding the flour in a bowl", "", ""));
            add(new Step("Add eggs", "Add the eggs and mix carefully", "", ""));
            add(new Step("Add Sugar", "Add sugar and put in the oven", "", ""));
        }
    };
    private Recipe fakeRecipe = new Recipe("Plumcake", fakeIngredients, fakeSteps, 4, "");

    @Rule
    public ActivityTestRule<DetailActivity> mActivityRule =
            new ActivityTestRule<DetailActivity>(DetailActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    Context targetContext = InstrumentationRegistry.getInstrumentation()
                            .getTargetContext();
                    Intent detailActivityIntent = new Intent(targetContext, DetailActivity.class);
                    detailActivityIntent.putExtra(RECIPE_EXTRA, fakeRecipe);
                    return detailActivityIntent;
                }
            };

    @Test
    public void detailActivity_showsSteps() {
        onView(withId(R.id.step_list_rv))
                .check(matches(isDisplayed()));

    }
}
