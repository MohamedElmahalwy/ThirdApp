package com.elmahalwy.bakingapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.elmahalwy.bakingapp.Activties.DetailsActivity;
import com.elmahalwy.bakingapp.Models.Recipes;
import com.elmahalwy.bakingapp.R;
import com.elmahalwy.bakingapp.Utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;



public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder> {

    public RecipesAdapter(List<Recipes> recipes, Context context) {
        this.recipes = recipes;
        this.context = context;
    }

    List<Recipes> recipes;
    Context context;

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.main_item, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, final int position) {
        String recipeTitle = recipes.get(position).getName();
        if (!(recipes.get(position).getImage() == null || TextUtils.isEmpty(recipes.get(position).getImage()))) {
            switch (recipeTitle) {
                case "Nutella Pie":
                    Picasso.with(context)
                            .load(recipes.get(position).getImage())
                            .error(R.drawable.nuttla_cake)
                            .into(holder.iv_main_pic);
                    break;
                case "Brownies":
                    Picasso.with(context)
                            .load(recipes.get(position).getImage())
                            .error(R.drawable.bronise_cake)
                            .into(holder.iv_main_pic);
                    break;
                case "Yellow Cake":
                    Picasso.with(context)
                            .load(recipes.get(position).getImage())
                            .error(R.drawable.yellow_cake)
                            .into(holder.iv_main_pic);
                    break       ;
                case "Cheesecake":
                    Picasso.with(context)
                            .load(recipes.get(position).getImage())
                            .error(R.drawable.chese_cake)
                            .into(holder.iv_main_pic);
                    break;
            }
        } else {
            switch (recipeTitle) {
                case "Nutella Pie":
                    holder.iv_main_pic.setImageResource(R.drawable.nuttla_cake);
                    break;
                case "Brownies":
                holder.iv_main_pic.setImageResource(R.drawable.bronise_cake);
                    break;
                case "Yellow Cake":
                holder.iv_main_pic.setImageResource(R.drawable.yellow_cake);
                    break;
                case "Cheesecake":
                holder.iv_main_pic.setImageResource(R.drawable.chese_cake);
                    break;
            }
        }

        holder.tv_main.setText(recipeTitle);
        holder.iv_main_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra(Constants.RECIPE, recipes.get(position).getName());
                intent.putParcelableArrayListExtra(Constants.INGREDIENTS, (ArrayList<? extends Parcelable>) recipes.get(position).getIngredients());
                intent.putParcelableArrayListExtra(Constants.STEPS, (ArrayList<? extends Parcelable>) recipes.get(position).getSteps());
               context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {

        if ((recipes != null) && recipes.size() > 0)
            return recipes.size();
        else
            return 0;
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_main_pic)
        ImageView iv_main_pic;
        @BindView(R.id.tv_main)
        TextView tv_main;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
