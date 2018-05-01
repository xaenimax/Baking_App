package com.udacity.aenima.bakingapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.aenima.bakingapp.R;
import com.udacity.aenima.bakingapp.data.Ingredient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientListAdapter extends RecyclerView.Adapter<IngredientListAdapter.IngredientViewHolder>{
    List<Ingredient> mIngredients;
    Context mContext;


    public IngredientListAdapter(Context context, List<Ingredient> ingredients){
        mContext = context;
        mIngredients = ingredients;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.ingredient_item, parent, false);
        ButterKnife.bind(this, view);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        Ingredient ingredient = mIngredients.get(position);
        holder.setIngredient(ingredient);
    }

    @Override
    public int getItemCount() {
        return mIngredients.size();
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ingredient_name_tv)
        public TextView ingredientNameTextView;

        public IngredientViewHolder(View itemView) {
            super(itemView);
        }

        public void setIngredient(Ingredient ingredient) {
            ingredientNameTextView.setText(ingredient.quantity + " " + ingredient.measure + " " + ingredient.ingredient);
        }
    }
}
