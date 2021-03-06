package com.elmahalwy.bakingapp.Frgments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.elmahalwy.bakingapp.Adapters.IngredientsDetailsAdapter;
import com.elmahalwy.bakingapp.Models.Ingredients;
import com.elmahalwy.bakingapp.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;



public class IngredientDetailsFragment extends Fragment {

    @BindView(R.id.ingredient_details_rv)
    RecyclerView mIngredientDetailsRV;

    IngredientsDetailsAdapter adapter;
    ArrayList<Ingredients> ingredients;

    public void setIngredients(ArrayList<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredient_details, container, false);
        ButterKnife.bind(this, view);
        setUpRV();
        return view;
    }

    private void setUpRV() {
        mIngredientDetailsRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (ingredients != null)
            adapter = new IngredientsDetailsAdapter(ingredients, getActivity());
        mIngredientDetailsRV.setAdapter(adapter);
    }
}
