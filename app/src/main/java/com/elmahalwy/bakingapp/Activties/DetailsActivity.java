package com.elmahalwy.bakingapp.Activties;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.elmahalwy.bakingapp.Frgments.IngredientDetailsFragment;
import com.elmahalwy.bakingapp.Frgments.IngredientFragment;
import com.elmahalwy.bakingapp.Frgments.StepDetailsFragment;
import com.elmahalwy.bakingapp.Frgments.StepsFragment;
import com.elmahalwy.bakingapp.Models.Ingredients;
import com.elmahalwy.bakingapp.Models.Steps;
import com.elmahalwy.bakingapp.R;
import com.elmahalwy.bakingapp.Utils.Constants;
import com.elmahalwy.bakingapp.Utils.CustomLoadingDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailsActivity extends AppCompatActivity
        implements IngredientFragment.OnIngredientClickListener, StepsFragment.OnStepClickListener {


    @BindView(R.id.recipe_ingredient_container)
    FrameLayout mRecipeIngredientsFrame;
    @BindView(R.id.recipe_steps_container)
    FrameLayout mRecipeStepsFrame;
    @BindView(R.id.tv_toolbar_title)
    TextView tv_toolbar_title;
    ArrayList<Steps> steps;
    ArrayList<Ingredients> ingredients;
    IngredientFragment ingredientFragment;
    StepsFragment stepsFragment;
    IngredientDetailsFragment ingredientDetailsFragment;
    StepDetailsFragment stepDetailsFragment;
    FragmentManager manager;
    public static String title;
    boolean twoPaneMode;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        preferences = getSharedPreferences(Constants.INGREDIENTS, MODE_PRIVATE);
        initialFragments();
        Intent intent = this.getIntent();

        if (intent != null) {
            steps = intent.getParcelableArrayListExtra(Constants.STEPS);
            ingredients = intent.getParcelableArrayListExtra(Constants.INGREDIENTS);
            stepsFragment.setSteps(steps);
            ingredientFragment.setIngredients(ingredients);
            title = intent.getStringExtra(Constants.RECIPE);
        }
        if (savedInstanceState != null) {
            steps = savedInstanceState.getParcelableArrayList(Constants.STEPS);
            ingredients = savedInstanceState.getParcelableArrayList(Constants.INGREDIENTS);
            stepsFragment.setSteps(steps);
            ingredientFragment.setIngredients(ingredients);
            title = savedInstanceState.getString(Constants.RECIPE);
        }
        if (findViewById(R.id.details_container) != null) {
            twoPaneMode = true;
        }
        tv_toolbar_title.setText(title);
        prefCreator(ingredients);

    }


    private void initialFragments() {
        ingredientFragment = new IngredientFragment();
        stepsFragment = new StepsFragment();

        ingredientDetailsFragment = new IngredientDetailsFragment();
        stepDetailsFragment = new StepDetailsFragment();
        manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.recipe_ingredient_container, ingredientFragment, "ingredientFragment")
                .add(R.id.recipe_steps_container, stepsFragment, "stepsFragment")
                .commit();
    }

    @Override
    public void onIngredientClicked(ArrayList<Ingredients> ingredients) {
        if (twoPaneMode) {
            ingredientDetailsFragment.setIngredients(ingredients);
            manager.beginTransaction().replace(R.id.details_container, ingredientDetailsFragment)
                    .commit();
        } else {
            if (ingredientDetailsFragment != null) {
                ingredientDetailsFragment.setIngredients(ingredients);
                manager.beginTransaction().replace(R.id.recipe_ingredient_container, ingredientDetailsFragment)
                        .addToBackStack("ingredientFragment")
                        .commit();
            }
        }
    }


    @Override
    public void onStepClicked(String description, String video, String thumbnail) {
        if (stepDetailsFragment != null) {
            if (twoPaneMode) {
                stepDetailsFragment.setVideo(video);
                stepDetailsFragment.setDescription(description);
                manager.beginTransaction().replace(R.id.details_container, stepDetailsFragment, "stepDetailsFragment")
                        .detach(stepDetailsFragment)
                        .attach(stepDetailsFragment)
                        .commit();
            } else {
                Intent intent = new Intent(DetailsActivity.this, StepsActivity.class);
                intent.putExtra(Constants.VIDEO, video);
                intent.putExtra(Constants.DESCRIPTION, description);
                intent.putExtra(Constants.THUMBNAIL, thumbnail);
                startActivity(intent);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(Constants.STEPS, steps);
        outState.putParcelableArrayList(Constants.INGREDIENTS, ingredients);
        outState.putString(Constants.RECIPE, title);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        savedInstanceState.getParcelableArrayList(Constants.STEPS);
        savedInstanceState.getParcelableArrayList(Constants.INGREDIENTS);
        savedInstanceState.getString(Constants.RECIPE);
    }


    private void prefCreator(ArrayList<Ingredients> ingredients) {
        if (ingredients != null) {
            for (int i = 0; i < ingredients.size(); i++) {
                preferences.edit().putString(Constants.INGREDIENT + " " + i, ingredients.get(i).getIngredient()).apply();
                preferences.edit().putString(Constants.MEASURE + " " + i, ingredients.get(i).getMeasure()).apply();
                preferences.edit().putFloat(Constants.QUANTITY + " " + i, ingredients.get(i).getQuantity()).apply();
                Log.e("size", ingredients.get(i).getIngredient() + " **-** " + ingredients.get(i).getQuantity() + " **-** " + ingredients.get(i).getMeasure());

            }
            preferences.edit().putInt(Constants.INGREDIENTS_SIZE, ingredients.size()).apply();
            Log.e("size", ingredients.size() + " **-** ");

        }
    }
}
