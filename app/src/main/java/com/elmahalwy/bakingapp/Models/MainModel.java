package com.elmahalwy.bakingapp.Models;

import java.util.ArrayList;

/**
 * Created by cz on 23/02/2018.
 */

public class MainModel {
    String tv_main;
    int image;
    ArrayList<IngredientsModel> ingredientsModels;
    ArrayList<StepsModel> stepsModelArrayList;


    public void setIngredientsModels(ArrayList<IngredientsModel> ingredientsModels) {
        this.ingredientsModels = ingredientsModels;
    }

    public void setStepsModelArrayList(ArrayList<StepsModel> stepsModelArrayList) {
        this.stepsModelArrayList = stepsModelArrayList;
    }

    public void setTv_main(String tv_main) {
        this.tv_main = tv_main;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTv_main() {
        return tv_main;
    }

    public int getImage() {
        return image;
    }

    public ArrayList<IngredientsModel> getIngredientsModels() {
        return ingredientsModels;
    }

    public ArrayList<StepsModel> getStepsModelArrayList() {
        return stepsModelArrayList;
    }
}
