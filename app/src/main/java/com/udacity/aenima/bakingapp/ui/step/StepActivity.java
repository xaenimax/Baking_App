package com.udacity.aenima.bakingapp.ui.step;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.udacity.aenima.bakingapp.R;
import com.udacity.aenima.bakingapp.data.Recipe;
import com.udacity.aenima.bakingapp.ui.RecipeFragment;
import com.udacity.aenima.bakingapp.ui.recipedetail.DetailActivity;
import com.udacity.aenima.bakingapp.ui.recipedetail.VideoFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.udacity.aenima.bakingapp.ui.recipedetail.DetailActivity.STEP_FRAGMENT;

public class StepActivity extends AppCompatActivity {

    @BindView(R.id.fragment_container_fl)
    public FrameLayout fragmentContainer;

    VideoFragment stepFrag;
    private Recipe recipe;
    private int currentStepIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        if(intent != null && intent.hasExtra(RecipeFragment.RECIPE_EXTRA) && intent.hasExtra(DetailActivity.EXTRA_CURRENT_STEP)) {
            recipe = intent.getParcelableExtra(RecipeFragment.RECIPE_EXTRA);
            currentStepIndex = intent.getIntExtra(DetailActivity.EXTRA_CURRENT_STEP, 0);
            this.setTitle(getString(R.string.recipe_instructions_title));

            FragmentManager fragMan = getSupportFragmentManager();
            stepFrag = (VideoFragment) fragMan.findFragmentByTag(STEP_FRAGMENT);

            if (stepFrag == null) {
                FragmentTransaction fragTransaction = fragMan.beginTransaction();
                stepFrag = VideoFragment.newInstance(recipe.steps, currentStepIndex);
                fragTransaction.add(fragmentContainer.getId(), stepFrag, STEP_FRAGMENT);
                fragTransaction.commit();
            }

            //stepFrag.setStepList(recipe.steps, currentStepIndex);
        }
    }

}
