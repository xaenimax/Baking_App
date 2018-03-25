package com.udacity.aenima.bakingapp.data;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by marina on 25/03/2018.
 */

public class BakingAppAPI {
    private final static String RECIPE_URL = "https://d17h27t6h515a5.cloudfront.net";


    public static Call<List<Recipe>> getRecipes(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RECIPE_URL).callbackExecutor(Executors.newSingleThreadExecutor())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RecipeService service = retrofit.create(RecipeService.class);
        return service.getRecipes();
    }
}
