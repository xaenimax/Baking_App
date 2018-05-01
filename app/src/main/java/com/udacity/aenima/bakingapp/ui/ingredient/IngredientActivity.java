package com.udacity.aenima.bakingapp.ui.ingredient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.udacity.aenima.bakingapp.R;
import com.udacity.aenima.bakingapp.adapter.IngredientListAdapter;
import com.udacity.aenima.bakingapp.data.Ingredient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientActivity extends AppCompatActivity {

    public static final String INGREDIENT_EXTRA = "ingredient_extra";
    private static final String LIST_VIEW_STATE_EXTRA = "list_view_state_extra";

    @BindView(R.id.ingredient_rv)
    public RecyclerView ingredientRecyclerView;

    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);
        ButterKnife.bind(this);
        this.setTitle(getString(R.string.ingredients_title));
        Intent intent = getIntent();
        if(intent != null && intent.hasExtra(INGREDIENT_EXTRA)){
            List<Ingredient> ingredientList = intent.getParcelableArrayListExtra(INGREDIENT_EXTRA);
            IngredientListAdapter ingredientArrayAdapter = new IngredientListAdapter(ingredientList);
            mLinearLayoutManager = new LinearLayoutManager(this);
            ingredientRecyclerView.setAdapter(ingredientArrayAdapter);

            if(savedInstanceState != null && savedInstanceState.containsKey(LIST_VIEW_STATE_EXTRA)){
                mLinearLayoutManager.onRestoreInstanceState(savedInstanceState.getParcelable(LIST_VIEW_STATE_EXTRA));
            }
            ingredientRecyclerView.setLayoutManager(mLinearLayoutManager);

        }


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(LIST_VIEW_STATE_EXTRA, mLinearLayoutManager.onSaveInstanceState());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState.containsKey(LIST_VIEW_STATE_EXTRA)){
            mLinearLayoutManager.onRestoreInstanceState(savedInstanceState.getParcelable(LIST_VIEW_STATE_EXTRA));
        }
    }
}
