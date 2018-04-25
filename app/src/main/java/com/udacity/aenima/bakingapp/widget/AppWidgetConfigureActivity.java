package com.udacity.aenima.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.udacity.aenima.bakingapp.R;
import com.udacity.aenima.bakingapp.data.BakingAppAPI;
import com.udacity.aenima.bakingapp.data.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppWidgetConfigureActivity extends AppCompatActivity {

    @BindView(R.id.recipe_list_lv)
    ListView recipeListView;

    private int mAppWidgetId;
    private List<Recipe> mRecipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_widget_configure);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        initializeAppWidget();

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        RemoteViews views = new RemoteViews(this.getPackageName(),
                R.layout.baking_app_widget_provider);
        appWidgetManager.updateAppWidget(mAppWidgetId, views);

        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();
    }

    private void initializeAppWidget(){
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }else {
            if (mRecipeList == null) {
                Call<List<Recipe>> recipeCallback = BakingAppAPI.getRecipes();
                recipeCallback.enqueue(new Callback<List<Recipe>>() {
                    @Override
                    public void onResponse(Call<List<Recipe>> call, final Response<List<Recipe>> response) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mRecipeList = response.body();
                                ArrayAdapter<Recipe> adapter = new ArrayAdapter<>(mRecipeList);

/*
                                RecipeListAdapter listAdapter = new RecipeListAdapter(mRecipeList, new RecipeListAdapter.RecipeListAdapterInterface() {
                                    @Override
                                    public void onRecipeSelected(Recipe recipe) {
                                        Intent detailActivityIntent = new Intent(getActivity(), DetailActivity.class);
                                        detailActivityIntent.putExtra(RECIPE_EXTRA, recipe);
                                        startActivity(detailActivityIntent);
                                    }
                                });
                                recipeRecyclerView.setAdapter(listAdapter);
*/
                            }
                        });

                    }

                    @Override
                    public void onFailure(Call<List<Recipe>> call, Throwable t) {
                        showErrorMessage();
                    }
                });
            }
        }
    }

    void showErrorMessage() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(this, getString(R.string.no_connection_error_message), Toast.LENGTH_LONG);
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_CANCELED);
    }
}
