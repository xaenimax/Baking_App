package com.udacity.aenima.bakingapp;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.udacity.aenima.bakingapp.ui.recipedetail.DetailActivity;
import com.udacity.aenima.bakingapp.ui.recipedetail.step.StepActivity;

import static android.support.test.espresso.Espresso.onView;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import android.support.test.espresso.contrib.RecyclerViewActions;
import static org.hamcrest.CoreMatchers.not;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class DetailActivityBasicTest {
    @Rule
    public IntentsTestRule<DetailActivity> detailActivityActivityTestRule = new IntentsTestRule<>(DetailActivity.class);


   @Before
    public void Stub_Intent(){
       intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK,null));
    }

    @Test
    public void check_ingredients(){
        onView(withId(R.id.step_list_rv)).perform(RecyclerViewActions.scrollToPosition(0)).perform(click());
        intended(hasComponent(StepActivity.class.getName()));

        //intended(allOf(hasExtra(Intent.EXTRA_TEXT,"1")));
        //onView(withId(R.id.ingrediets_recycler)).perform(RecyclerViewActions.scrollToHolder(withIngredientsRecyclerView("salt")));
    }
/*

    public static org.hamcrest.Matcher<RecyclerView.ViewHolder > withIngredientsRecyclerView(final String text) {
        return new BoundedMatcher<RecyclerView.ViewHolder, RecyclerIngredientsAdapter.IngredientsViewHolder>(RecyclerIngredientsAdapter.IngredientsViewHolder.class) {


            @Override
            public void describeTo(Description description) {
                description.appendText("No se encontró "+text);
            }

            @Override
            protected boolean matchesSafely(RecyclerIngredientsAdapter.IngredientsViewHolder item) {
                TextView textView = item.itemView.findViewById(R.id.ingredient_name);
                if (textView==null){
                    return false;
                }
                return textView.getText().toString().contains(text);
            }
        };
    }
*/
}
