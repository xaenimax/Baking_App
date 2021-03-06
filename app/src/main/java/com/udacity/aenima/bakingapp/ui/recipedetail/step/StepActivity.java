package com.udacity.aenima.bakingapp.ui.recipedetail.step;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.udacity.aenima.bakingapp.R;
import com.udacity.aenima.bakingapp.data.Recipe;
import com.udacity.aenima.bakingapp.ui.BaseActivity;
import com.udacity.aenima.bakingapp.ui.recipes.RecipeFragment;
import com.udacity.aenima.bakingapp.ui.recipedetail.DetailActivity;
import com.udacity.aenima.bakingapp.ui.recipedetail.VideoFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.udacity.aenima.bakingapp.ui.recipedetail.DetailActivity.STEP_FRAGMENT;

public class StepActivity extends BaseActivity {

    @Nullable
    @BindView(R.id.land_frame_layout)
    public FrameLayout landFrameLayout;

    @Nullable
    @BindView(R.id.fragment_container_fl)
    public FrameLayout fragmentContainer;

    @Nullable
    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    VideoFragment stepFrag;
    private Recipe recipe;
    private int currentStepIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_step);
        ButterKnife.bind(this);

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

            if(landFrameLayout != null) {
                hideToolBar();
            }
        }

    }


    private void hideToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();
    }

    private void showToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().show();
    }

}
