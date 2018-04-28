package com.udacity.aenima.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.udacity.aenima.bakingapp.R;
import com.udacity.aenima.bakingapp.data.Ingredient;
import com.udacity.aenima.bakingapp.data.Recipe;

import static com.udacity.aenima.bakingapp.ui.recipedetail.DetailActivity.FAVOURITE_RECIPE_PREFERENCE_KEY;

public class ListViewService extends RemoteViewsService{

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListViewsRemoteFactory(this.getApplicationContext());
    }
}

class ListViewsRemoteFactory implements RemoteViewsService.RemoteViewsFactory {
    Context context;
    Recipe mRecipe = null;

    public ListViewsRemoteFactory(Context applicationContext){
        context = applicationContext;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String favRecipe = sharedPreferences.getString(FAVOURITE_RECIPE_PREFERENCE_KEY, null);
        if(favRecipe != null){
            mRecipe = new Gson().fromJson(favRecipe, Recipe.class);
        }

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if(mRecipe != null)
            return mRecipe.ingredients.size();
        return 0;
    }

    @Override
    public RemoteViews getViewAt(int i) {
        Log.i(this.getClass().getName(), "Loading " + i + "ingredient");
        Ingredient ingredient = mRecipe.ingredients.get(i);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredient_item);
        views.setTextViewText(R.id.ingredient_name_tv, ingredient.ingredient + " " + ingredient.quantity + " " + ingredient.measure);
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
