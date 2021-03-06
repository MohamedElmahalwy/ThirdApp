package com.elmahalwy.bakingapp.Frgments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.elmahalwy.bakingapp.Models.Ingredients;
import com.elmahalwy.bakingapp.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class IngredientFragment extends Fragment {
    @BindView(R.id.ingredient_card)
    CardView mIngredientCard;

    private ArrayList<Ingredients> ingredients;
    OnIngredientClickListener onIngredientClickListener;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredients, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    public void setIngredients(ArrayList<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    @OnClick(R.id.ingredient_card)
    void onCardClick(){
        onIngredientClickListener.onIngredientClicked(ingredients);
    }

    public interface OnIngredientClickListener {
        void onIngredientClicked(ArrayList<Ingredients> ingredients);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onIngredientClickListener = (OnIngredientClickListener) context;
        } catch (Exception e) {
            Log.e("IngredientFragment", e.getMessage());
        }
    }
}
