package com.elmahalwy.bakingapp.Utils;

import com.elmahalwy.bakingapp.Models.Recipes;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;



public class RetrofitInterfaces {
    public interface RecipesInterface {
        @GET("baking.json")
        Call<List<Recipes>> getRecipes();
    }
}
