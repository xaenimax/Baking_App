package com.udacity.aenima.bakingapp.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by marina on 25/03/2018.
 */

class Ingredient {
    private final String QUANTITY_JSON_HEADER      = "quantity";
    private final String MEASURE_JSON_HEADER       = "measure";
    private final String INGREDIENT_JSON_HEADER    = "ingredient";


    @SerializedName(QUANTITY_JSON_HEADER)
    public float quantity;
    @SerializedName(MEASURE_JSON_HEADER)
    public String measure;
    @SerializedName(INGREDIENT_JSON_HEADER)
    public String ingredient;

}
