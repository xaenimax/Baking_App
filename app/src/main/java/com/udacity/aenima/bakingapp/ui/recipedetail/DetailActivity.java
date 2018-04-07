package com.udacity.aenima.bakingapp.ui.recipedetail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.udacity.aenima.bakingapp.R;
import com.udacity.aenima.bakingapp.data.Recipe;
import com.udacity.aenima.bakingapp.ui.RecipeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.fragment_container_fl)
    public FrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        if(savedInstanceState != null && savedInstanceState.containsKey(RecipeFragment.RECIPE_EXTRA)){
            Recipe recipe = savedInstanceState.getParcelable(RecipeFragment.RECIPE_EXTRA);
            FragmentManager fragMan = getSupportFragmentManager();
            FragmentTransaction fragTransaction = fragMan.beginTransaction();

            Fragment myFrag = StepFragment.newInstance(recipe);
            fragTransaction.add(container.getId(), myFrag , "fragment");
            fragTransaction.commit();

        }
    }

}
