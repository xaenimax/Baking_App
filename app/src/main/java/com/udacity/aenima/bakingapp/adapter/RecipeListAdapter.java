package com.udacity.aenima.bakingapp.adapter;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
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
    private RecipeListAdapterInterface mRecipeListAdapterInterface;
    private List<Recipe> mRecipeList;

    public RecipeListAdapter(List<Recipe> recipeList, RecipeListAdapterInterface recipeListAdapterInterface){
        mRecipeList = recipeList;
        mRecipeListAdapterInterface = recipeListAdapterInterface;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =  LayoutInflater.from(parent.getContext());
        RecipeItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.recipe_item, parent, false);
        return new RecipeViewHolder(binding, this);
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
        private RecipeListAdapter callback;

        public RecipeViewHolder(RecipeItemBinding binding, RecipeListAdapter callback) {
            super(binding.getRoot());
            recipeItemBinding = binding;
            this.callback = callback;
        }

        public void bind(Recipe recipe) {
            recipeItemBinding.setVariable(BR.recipe, recipe);
            recipeItemBinding.setVariable(BR.callback, this.callback);
            recipeItemBinding.executePendingBindings();
        }


    }

    public void recipeSelected(Recipe data){
        if(mRecipeListAdapterInterface != null){
            mRecipeListAdapterInterface.onRecipeSelected(data);
        }
    }
    public void checkedChanged(View v, boolean s, Recipe favourite){
        if(mRecipeListAdapterInterface != null){
            mRecipeListAdapterInterface.onCheckChanged(v, s, favourite);
        }
    }


    public interface RecipeListAdapterInterface{
        void onRecipeSelected(Recipe recipe);
        void onCheckChanged(View v, boolean state, Recipe favourite);

    }
}
