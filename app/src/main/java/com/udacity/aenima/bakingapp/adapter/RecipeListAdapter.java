package com.udacity.aenima.bakingapp.adapter;

import android.databinding.DataBindingUtil;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.aenima.bakingapp.BR;
import com.udacity.aenima.bakingapp.R;
import com.udacity.aenima.bakingapp.data.Recipe;
import com.udacity.aenima.bakingapp.databinding.RecipeItemBinding;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by marina on 22/03/2018.
 */

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> {

    private List<Recipe> mRecipeList;

    public RecipeListAdapter(List<Recipe> recipeList){
        mRecipeList = recipeList;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =  LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recipe_item, parent, false);
        RecipeItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.recipe_item, parent, false);
        return new RecipeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        Recipe recipe = mRecipeList.get(position);
        holder.bind(recipe);
    }

    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder{
        private RecipeItemBinding recipeItemBinding;

        public RecipeViewHolder(RecipeItemBinding binding) {
            super(binding.getRoot());
            recipeItemBinding = binding;
        }
        public void bind(Recipe recipe) {
            recipeItemBinding.setVariable(BR.recipe, recipe);
            recipeItemBinding.executePendingBindings();
        }
    }
}
