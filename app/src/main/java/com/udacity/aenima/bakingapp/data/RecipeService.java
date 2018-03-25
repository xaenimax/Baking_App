package com.udacity.aenima.bakingapp.data;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by marina on 25/03/2018.
 */

public interface RecipeService {

    @GET("/topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipe>> getRecipes();

}
