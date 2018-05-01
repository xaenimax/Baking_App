package com.udacity.aenima.bakingapp.ui.recipedetail;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.udacity.aenima.bakingapp.R;
import com.udacity.aenima.bakingapp.data.Recipe;
import com.udacity.aenima.bakingapp.data.Step;
import com.udacity.aenima.bakingapp.ui.RecipeFragment;
import com.udacity.aenima.bakingapp.ui.recipedetail.step.StepActivity;
import com.udacity.aenima.bakingapp.widget.BakingAppWidgetProvider;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements StepFragment.OnStepSelectedListener, FloatingActionButton.OnClickListener{
    //FAVOURITE RECIPE KEY
    public static final String FAVOURITE_RECIPE_PREFERENCE_KEY = "favourite_preference_key";
    public static final String LIST_FRAGMENT = "list_fragment";
    public static final String STEP_FRAGMENT = "step_fragment";
    public static final String EXTRA_CURRENT_STEP = "extra_current_step";

    private int currentStep = 0;

    @BindView(R.id.list_fragment_container_fl)
    public FrameLayout listContainer;

    @Nullable
    @BindView(R.id.step_fragment_container_fl)
    public FrameLayout stepContainer;

    @BindView(R.id.add_fav_fb)
    FloatingActionButton favouriteActionButton;

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
            }else {
                if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                    hideSystemUI();
                }else {
                    showSystemUI();
                }

            }

            favouriteActionButton.setOnClickListener(this);
        }

    }

    private boolean isPhone() {
        return stepContainer == null;
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    // Shows the system bars by removing all the flags
    // except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
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

    @Override
    public void onClick(final View view) {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String content = new Gson().toJson(recipe);
        editor.putString(FAVOURITE_RECIPE_PREFERENCE_KEY, content).commit();
        //update widget listview items
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingAppWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.ingredient_lv);
        Intent intent = new Intent(this, BakingAppWidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
        //... and widget title
        sendBroadcast(intent);
        final String message = getString(R.string.recipe_selected_as_favourite);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();

            }
        });
    }

}
