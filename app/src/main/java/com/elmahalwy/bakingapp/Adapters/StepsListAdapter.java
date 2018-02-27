package com.elmahalwy.bakingapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.elmahalwy.bakingapp.Models.Steps;
import com.elmahalwy.bakingapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;



public class StepsListAdapter extends RecyclerView.Adapter<StepsListAdapter.StepsViewHolder> {

    List<Steps> steps;
    Context context;

    public StepsListAdapter(List<Steps> steps, Context context) {
        this.steps = steps;
        this.context = context;
    }

    @Override
    public StepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.details_item, parent, false);
        return new StepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepsViewHolder holder, int position) {
        holder.tv_step_name.setText(steps.get(position).getShortDescription());
    }

    @Override
    public int getItemCount() {
        if ( steps == null ||steps.size() == 0)
            return 0;
        else
            return steps.size();
    }

    class StepsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_step_name)
        TextView tv_step_name;

        public StepsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
