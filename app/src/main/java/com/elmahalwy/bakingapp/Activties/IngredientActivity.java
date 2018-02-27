package com.elmahalwy.bakingapp.Activties;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.elmahalwy.bakingapp.Adapters.IngredientsDetailsAdapter;
import com.elmahalwy.bakingapp.Models.Ingredients;
import com.elmahalwy.bakingapp.R;
import com.elmahalwy.bakingapp.Utils.Constants;
import com.elmahalwy.bakingapp.Utils.CustomLoadingDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientActivity extends AppCompatActivity {
    @BindView(R.id.rv_ingredient)
    RecyclerView rv_ingredient;
    @BindView(R.id.tv_toolbar_title)
    TextView tv_toolbar;
    ArrayList<Ingredients> ingredients_array_list;
    IngredientsDetailsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);
        ButterKnife.bind(this);
        Intent intent = this.getIntent();
        if (intent != null) {
            ingredients_array_list = intent.getParcelableArrayListExtra(Constants.INGREDIENTS);
            setUpRV();
        }
tv_toolbar.setText("Ingradients");
    }

    private void setUpRV() {
        rv_ingredient.setLayoutManager(new LinearLayoutManager(this));
        if (rv_ingredient != null)
            adapter = new IngredientsDetailsAdapter(ingredients_array_list, this);
        rv_ingredient.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
