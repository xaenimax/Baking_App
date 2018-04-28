package com.udacity.aenima.bakingapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.udacity.aenima.bakingapp.ui.recipedetail.DetailActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class DetailActivityBasicTest {
    @Rule
    public ActivityTestRule<DetailActivity> detailActivityActivityTestRule = new ActivityTestRule<>(DetailActivity.class);

    @Test
    public void onSelectRecipeAsFavourite_WriteInSharedPreferences(){
        onView(withId(R.id.add_fav_fb)).perform(click())

    }
}
