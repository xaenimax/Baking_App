package com.udacity.aenima.bakingapp.ui.recipedetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.udacity.aenima.bakingapp.R;
import com.udacity.aenima.bakingapp.data.Recipe;
import com.udacity.aenima.bakingapp.data.Step;
import com.udacity.aenima.bakingapp.ui.RecipeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements StepFragment.OnStepSelectedListener{

    public static final String LIST_FRAGMENT = "list_fragment";
    public static final String STEP_FRAGMENT = "step_fragment";
    @BindView(R.id.list_fragment_container_fl)
    public FrameLayout listContainer;
    @BindView(R.id.step_fragment_container_fl)
    public FrameLayout stepContainer;

    StepFragment listFrag;
    VideoFragment stepFrag;
    Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if(intent != null && intent.hasExtra(RecipeFragment.RECIPE_EXTRA)){
            recipe = intent.getParcelableExtra(RecipeFragment.RECIPE_EXTRA);
            this.setTitle(recipe.name);

            FragmentManager fragMan = getSupportFragmentManager();
            listFrag = (StepFragment) fragMan.findFragmentByTag(LIST_FRAGMENT);

            if( listFrag == null) {
                FragmentTransaction fragTransaction = fragMan.beginTransaction();
                listFrag = StepFragment.newInstance(recipe);
                listFrag.setmOnStepSelectedListener(this);
                fragTransaction.add(listContainer.getId(), listFrag, LIST_FRAGMENT);
                fragTransaction.commit();
            }

            stepFrag = (VideoFragment) fragMan.findFragmentByTag(LIST_FRAGMENT);

            if( stepFrag == null) {
                FragmentTransaction fragTransaction = fragMan.beginTransaction();
                stepFrag = VideoFragment.newInstance();
                fragTransaction.add(stepContainer.getId(), stepFrag, STEP_FRAGMENT);
                fragTransaction.commit();
                stepContainer.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if(listContainer.getVisibility() == View.GONE){
            listContainer.setVisibility(View.VISIBLE);
            stepContainer.setVisibility(View.GONE);
        }else
            super.onBackPressed();
    }

    @Override
    public void onStepSelectedListener(Step selectedStep) {
        stepFrag.setStepList(recipe.steps, recipe.steps.indexOf(selectedStep));
        listContainer.setVisibility(View.GONE);
        stepContainer.setVisibility(View.VISIBLE);
    }
}
