package com.udacity.aenima.bakingapp.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.udacity.aenima.bakingapp.R;
import com.udacity.aenima.bakingapp.data.Recipe;
import com.udacity.aenima.bakingapp.ui.recipedetail.DetailActivity;
import com.udacity.aenima.bakingapp.ui.recipes.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.udacity.aenima.bakingapp.ui.recipedetail.DetailActivity.FAVOURITE_RECIPE_PREFERENCE_KEY;
import static com.udacity.aenima.bakingapp.ui.recipes.RecipeFragment.RECIPE_EXTRA;


public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.drawer_layout)
    public DrawerLayout drawerLayout;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.view_stub)
    public FrameLayout viewStub;

    @BindView(R.id.toolbar)
    public Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        ButterKnife.setDebug(true);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            if(!this.getClass().getName().equals(MainActivity.class.getName())){
                startNewActivity(MainActivity.class, null);
            }
        } else if (id == R.id.nav_fav_recipe) {
            SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
            if(sharedPreferences.contains(FAVOURITE_RECIPE_PREFERENCE_KEY)){
                String favRecipeString = sharedPreferences.getString(FAVOURITE_RECIPE_PREFERENCE_KEY, null);
                if(favRecipeString != null){
                    Recipe favRecipe = new Gson().fromJson(favRecipeString, Recipe.class);
                    startNewActivity(DetailActivity.class, favRecipe);
                }
            }else {
                Toast.makeText(this, getString(R.string.no_favourite_recipe_yet), Toast.LENGTH_LONG).show();
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);  // OPEN DRAWER
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setContentView(int layoutResID) {
        if (viewStub != null) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            View stubView = inflater.inflate(layoutResID, viewStub, false);
            viewStub.addView(stubView, lp);
        }else {
            super.setContentView(layoutResID);
        }
    }

    @Override
    public void setContentView(View view) {
        if (viewStub != null) {
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            viewStub.addView(view, lp);
        }else {
            super.setContentView(view);
        }
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        if (viewStub != null) {
            viewStub.addView(view, params);
        }else {
            super.setContentView(view, params);
        }
    }



    private void startNewActivity(Class klass, Recipe recipe) {
        Intent intent = new Intent(BaseActivity.this, klass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if(recipe !=null){
            intent.putExtra(RECIPE_EXTRA, recipe);
        }
        startActivity(intent);
    }
}
