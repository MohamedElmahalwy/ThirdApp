package com.elmahalwy.bakingapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.elmahalwy.bakingapp.Activties.DetailsActivity;
import com.elmahalwy.bakingapp.Models.MainModel;
import com.elmahalwy.bakingapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cz on 23/02/2018.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    List<MainModel> main_list = new ArrayList<>();
    Context context;
    LayoutInflater layoutInflater;

    public MainAdapter(List<MainModel> main_list, Context context) {
        this.main_list = main_list;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.from(parent.getContext()).inflate(R.layout.main_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tv_main.setText(main_list.get(position).getTv_main());
        Picasso.with(context)
                .load(main_list.get(position).getImage())
                .into(holder.iv_main_pic);
        holder.iv_main_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("title", main_list.get(position).getTv_main());
                if (position == 0) {
                    intent.putExtra("type", "0");
                }
                if (position == 1) {
                    intent.putExtra("type", "1");
                }
                if (position == 2) {
                    intent.putExtra("type", "2");
                }
                if (position == 3) {
                    intent.putExtra("type", "3");
                }
                context.startActivity(intent);

            }
        });
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, DetailsActivity.class);
////                if (position==0){
////                    intent.putExtra("type","0");
////                }
////                if (position==1){
////                    intent.putExtra("type","1");
////                }
////                if (position==2){
////                    intent.putExtra("type","2");
////                }
////                if (position==3){
////                    intent.putExtra("type","3");
////                }
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return main_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_main)
        TextView tv_main;
        @BindView(R.id.iv_main_pic)
        ImageView iv_main_pic;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
