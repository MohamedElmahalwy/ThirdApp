package com.elmahalwy.bakingapp.Activties;

import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.elmahalwy.bakingapp.Adapters.RecipesAdapter;
import com.elmahalwy.bakingapp.Models.Ingredients;
import com.elmahalwy.bakingapp.Models.Recipes;
import com.elmahalwy.bakingapp.Models.Steps;
import com.elmahalwy.bakingapp.R;
import com.elmahalwy.bakingapp.Utils.Constants;
import com.elmahalwy.bakingapp.Utils.RetrofitBuilder;
import com.elmahalwy.bakingapp.Utils.RetrofitInterfaces;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView rv_main;
    RecipesAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    List<Recipes> recipes;
    List<Steps> steps;
    List<Ingredients> ingredients;

    @BindView(R.id.tv_toolbar_title)
    TextView tv_toolbar_titiel;
    Parcelable layoutManagerSavedState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        InitUi();
        if (findViewById(R.id.relative_x_large) != null) {
            rv_main.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
        } else {
            rv_main.setLayoutManager(new LinearLayoutManager(this));

        }
        if (savedInstanceState != null) {
            recipes = savedInstanceState.getParcelableArrayList(Constants.RECIPE);
            layoutManagerSavedState = savedInstanceState.getParcelable(Constants.LIST_STATE);
            InitRecyclerView(recipes);
        } else {
            get_data();
        }
        tv_toolbar_titiel.setText("Baking now");
    }

    void InitUi() {
        rv_main = (RecyclerView) findViewById(R.id.rv_main);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);
        recipes = new ArrayList<>();
        steps = new ArrayList<>();
        ingredients = new ArrayList<>();

    }

    void InitRecyclerView(List recipes) {
        final List<Recipes> mRecipes = recipes;
        adapter = new RecipesAdapter(mRecipes, this);
        rv_main.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        onRestoreScrolling();
    }

    void get_data() {
        RetrofitBuilder builder = new RetrofitBuilder();
        RetrofitInterfaces.RecipesInterface recipesInterface = builder.createRecipesInterface();
        Call<List<Recipes>> recipesCall = recipesInterface.getRecipes();
        recipesCall.enqueue(new Callback<List<Recipes>>() {
            @Override
            public void onResponse(Call<List<Recipes>> call, Response<List<Recipes>> response) {
                Log.e("connection", "success");
                if (response.body() != null) {
                    recipes = response.body();
                    InitRecyclerView(recipes);

                }
            }

            @Override
            public void onFailure(Call<List<Recipes>> call, Throwable t) {
                Log.e("connection", "failure" + " -- " + t.getMessage());

            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(Constants.RECIPE, (ArrayList<? extends Parcelable>) recipes);
        outState.putParcelable(Constants.LIST_STATE, rv_main.getLayoutManager().onSaveInstanceState());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        savedInstanceState.getParcelableArrayList(Constants.RECIPE);
        savedInstanceState.getParcelable(Constants.LIST_STATE);
    }

    private void onRestoreScrolling() {
        if (layoutManagerSavedState != null) {
            rv_main.getLayoutManager().onRestoreInstanceState(layoutManagerSavedState);
        }
    }
}
