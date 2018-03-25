package com.udacity.aenima.bakingapp.data;

import android.util.SparseLongArray;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by marina on 25/03/2018.
 */

public class Recipe {
    private final String ID_JSON_HEADER             = "id";
    private final String NAME_JSON_HEADER           = "name";
    private final String INGREDIENTS_JSON_HEADER    = "ingredients";
    private final String STEPS_JSON_HEADER          = "steps";
    private final String SERVINGS_JSON_HEADER       = "servings";
    private final String IMAGE_JSON_HEADER          = "image";

    @SerializedName(ID_JSON_HEADER)
    public long id;
    @SerializedName(NAME_JSON_HEADER)
    public String name;
    @SerializedName(INGREDIENTS_JSON_HEADER)
    public List<Ingredient> ingredients;
    @SerializedName(STEPS_JSON_HEADER)
    public List<Step> steps;
    @SerializedName(SERVINGS_JSON_HEADER)
    public int servings;
    @SerializedName(IMAGE_JSON_HEADER)
    public String image;
}
