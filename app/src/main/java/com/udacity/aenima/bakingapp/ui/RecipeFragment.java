package com.udacity.aenima.bakingapp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.udacity.aenima.bakingapp.R;
import com.udacity.aenima.bakingapp.adapter.RecipeListAdapter;
import com.udacity.aenima.bakingapp.data.BakingAppAPI;
import com.udacity.aenima.bakingapp.data.Recipe;
import com.udacity.aenima.bakingapp.ui.recipedetail.DetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
  * to handle interaction events.
 * create an instance of this fragment.
 */
public class RecipeFragment extends Fragment {
    private static final String GRIDLAYOUT_STATE_EXTRA = "GRIDLAYOUT_STATE_EXTRA";
    private List<Recipe> mRecipeList;
    private static String RECIPE_EXTRA="recipe_extra";

    private Parcelable state;

    GridLayoutManager layoutManager;

    @BindView(R.id.recipe_list_rv)
    public RecyclerView recipeRecyclerView;

    public RecipeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe, container, false);
        ButterKnife.bind(this, view);
        int columns = Integer.parseInt(getString(R.string.grid_layout_column_number));
        layoutManager = new GridLayoutManager(this.getContext(), columns);
        if (savedInstanceState != null && savedInstanceState.containsKey(GRIDLAYOUT_STATE_EXTRA)) {
            state = savedInstanceState.getParcelable(GRIDLAYOUT_STATE_EXTRA);
            layoutManager.onRestoreInstanceState(state);
        }
        recipeRecyclerView.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mRecipeList == null) {
            Call<List<Recipe>> recipeCallback = BakingAppAPI.getRecipes();
            recipeCallback.enqueue(new Callback<List<Recipe>>() {
                @Override
                public void onResponse(Call<List<Recipe>> call, final Response<List<Recipe>> response) {

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mRecipeList = response.body();
                            RecipeListAdapter listAdapter = new RecipeListAdapter(mRecipeList, new RecipeListAdapter.RecipeListAdapterInterface() {
                                @Override
                                public void onRecipeSelected(Recipe recipe) {
                                        Intent detailActivityIntent = new Intent(getActivity(), DetailActivity.class);
                                        detailActivityIntent.putExtra(RECIPE_EXTRA, recipe);
                                        startActivity(detailActivityIntent);
                                }
                            });
                            recipeRecyclerView.setAdapter(listAdapter);
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

    void showErrorMessage() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), getString(R.string.no_connection_error_message), Toast.LENGTH_LONG);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        state = layoutManager.onSaveInstanceState();
        outState.putParcelable(GRIDLAYOUT_STATE_EXTRA, state);
    }
}


