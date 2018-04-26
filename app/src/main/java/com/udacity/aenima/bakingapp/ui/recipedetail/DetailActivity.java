package com.udacity.aenima.bakingapp.ui.recipedetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.udacity.aenima.bakingapp.R;
import com.udacity.aenima.bakingapp.data.Recipe;
import com.udacity.aenima.bakingapp.data.Step;
import com.udacity.aenima.bakingapp.ui.RecipeFragment;
import com.udacity.aenima.bakingapp.ui.recipedetail.step.StepActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements StepFragment.OnStepSelectedListener{

    public static final String LIST_FRAGMENT = "list_fragment";
    public static final String STEP_FRAGMENT = "step_fragment";
    public static final String EXTRA_CURRENT_STEP = "extra_current_step";

    private int currentStep = 0;

    @BindView(R.id.list_fragment_container_fl)
    public FrameLayout listContainer;

    @Nullable
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
            if(!isPhone()) {
                //if tabletlayout we add another fragment
                stepFrag = (VideoFragment) fragMan.findFragmentByTag(STEP_FRAGMENT);

                if(savedInstanceState != null && savedInstanceState.containsKey(EXTRA_CURRENT_STEP)){
                    currentStep = savedInstanceState.getInt(EXTRA_CURRENT_STEP);
                }
                if (stepFrag == null) {
                    FragmentTransaction fragTransaction = fragMan.beginTransaction();
                    stepFrag = VideoFragment.newInstance(recipe.steps, currentStep);
                    fragTransaction.add(stepContainer.getId(), stepFrag, STEP_FRAGMENT);
                    fragTransaction.commit();
                }else {
                    FragmentTransaction fragTransaction = fragMan.beginTransaction();
                    fragTransaction.replace(stepContainer.getId(), stepFrag, STEP_FRAGMENT);
                    fragTransaction.commit();
                }
                fragMan.executePendingTransactions();
            }
        }

    }

    private boolean isPhone() {
        return stepContainer == null;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(EXTRA_CURRENT_STEP, currentStep);
        //getSupportFragmentManager().beginTransaction().remove(stepFrag).commit();
        //getSupportFragmentManager().executePendingTransactions();
        stepFrag = null;
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState != null && savedInstanceState.containsKey(EXTRA_CURRENT_STEP)){
            currentStep = savedInstanceState.getInt(EXTRA_CURRENT_STEP);
        }

    }

    @Override
    public void onStepSelectedListener(Step selectedStep) {
        if(isPhone()){
            Intent intent = new Intent(this, StepActivity.class);
            intent.putExtra(RecipeFragment.RECIPE_EXTRA, recipe);
            intent.putExtra(EXTRA_CURRENT_STEP, recipe.steps.indexOf(selectedStep));
            startActivity(intent);
        }else {
            stepFrag = (VideoFragment) getSupportFragmentManager().findFragmentByTag(STEP_FRAGMENT);
            stepFrag.setStepList(recipe.steps, recipe.steps.indexOf(selectedStep));
        }
    }
}
