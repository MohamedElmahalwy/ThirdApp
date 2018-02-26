package com.elmahalwy.bakingapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.elmahalwy.bakingapp.Models.IngredientsModel;
import com.elmahalwy.bakingapp.Models.StepsModel;
import com.elmahalwy.bakingapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cz on 26/02/2018.
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {
    List<IngredientsModel> ingredients_list = new ArrayList<>();
    Context context;
    LayoutInflater layoutInflater;

    public IngredientsAdapter(List<IngredientsModel> ingredients_list, Context context) {
        this.ingredients_list = ingredients_list;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_ingredient.setText(ingredients_list.get(position).getIngredient());
        holder.tv_quantity.setText(ingredients_list.get(position).getQuantity());

    }

    @Override
    public int getItemCount() {
        return ingredients_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_descprtion)
        TextView tv_ingredient;
        @BindView(R.id.tv_content)
        TextView tv_quantity;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
