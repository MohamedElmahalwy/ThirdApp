package com.elmahalwy.bakingapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.elmahalwy.bakingapp.Activties.StepsActivity;
import com.elmahalwy.bakingapp.Models.MainModel;
import com.elmahalwy.bakingapp.Models.StepsModel;
import com.elmahalwy.bakingapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cz on 26/02/2018.
 */

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.ViewHolder> {
    List<StepsModel> steps_list = new ArrayList<>();
    Context context;
    LayoutInflater layoutInflater;

    public DetailsAdapter(List<StepsModel> steps_list, Context context) {
        this.steps_list = steps_list;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.from(parent.getContext()).inflate(R.layout.details_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tv_step_name.setText(steps_list.get(position).getShortDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, StepsActivity.class);
                intent.putExtra("shortDescription",steps_list.get(position).getShortDescription());
                intent.putExtra("description",steps_list.get(position).getDescription());
                intent.putExtra("id",steps_list.get(position).getId());
                intent.putExtra("video_url",steps_list.get(position).getVideoURL());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return steps_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_step_name)
        TextView tv_step_name;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
