package com.elmahalwy.bakingapp.Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitBuilder {
    Retrofit retrofit;

    Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    OkHttpClient client = new OkHttpClient();

    public RetrofitBuilder() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public RetrofitInterfaces.RecipesInterface createRecipesInterface() {
        return retrofit.create(RetrofitInterfaces.RecipesInterface.class);
    }
}
