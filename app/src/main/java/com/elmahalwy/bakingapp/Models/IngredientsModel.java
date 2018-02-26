package com.elmahalwy.bakingapp.Models;

/**
 * Created by cz on 25/02/2018.
 */

public class IngredientsModel {
    String quantity, measure, ingredient;

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }
}
