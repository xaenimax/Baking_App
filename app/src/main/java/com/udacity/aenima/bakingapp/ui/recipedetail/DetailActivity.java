package com.udacity.aenima.bakingapp.ui.recipedetail;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
    private static final String EXTRA_CURRENT_CONTAINER = "extra_current_container";

    private String currentContainer = LIST_FRAGMENT;
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

            stepFrag = (VideoFragment) fragMan.findFragmentByTag(STEP_FRAGMENT);

            if( stepFrag == null) {
                FragmentTransaction fragTransaction = fragMan.beginTransaction();
                stepFrag = VideoFragment.newInstance();
                fragTransaction.add(stepContainer.getId(), stepFrag, STEP_FRAGMENT);
                fragTransaction.commit();
            }

        }
        if(savedInstanceState != null && savedInstanceState.containsKey(EXTRA_CURRENT_CONTAINER)){
            currentContainer = savedInstanceState.getString(EXTRA_CURRENT_CONTAINER);
        }
        handleContainerVisibility();
    }


    @Override
    public void onBackPressed() {
        if(listContainer.getVisibility() == View.GONE){
            currentContainer = LIST_FRAGMENT;
            handleContainerVisibility();
        }else
            super.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putString(EXTRA_CURRENT_CONTAINER, currentContainer);
        getSupportFragmentManager().beginTransaction().remove(stepFrag).remove(listFrag).commit();
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState != null && savedInstanceState.containsKey(EXTRA_CURRENT_CONTAINER)){
            currentContainer = savedInstanceState.getString(EXTRA_CURRENT_CONTAINER);
        }
        handleContainerVisibility();
    }

    @Override
    public void onStepSelectedListener(Step selectedStep) {
        stepFrag.setStepList(recipe.steps, recipe.steps.indexOf(selectedStep));
        currentContainer = STEP_FRAGMENT;
        handleContainerVisibility();
    }

    void handleContainerVisibility(){
        stepContainer.setVisibility(currentContainer == STEP_FRAGMENT ? View.VISIBLE : View.GONE);
        listContainer.setVisibility(currentContainer == LIST_FRAGMENT ? View.VISIBLE : View.GONE);
    }
}
