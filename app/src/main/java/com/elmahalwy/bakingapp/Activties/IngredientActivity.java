package com.elmahalwy.bakingapp.Activties;

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
import com.elmahalwy.bakingapp.Adapters.IngredientsAdapter;
import com.elmahalwy.bakingapp.Models.IngredientsModel;
import com.elmahalwy.bakingapp.R;
import com.elmahalwy.bakingapp.Utils.CustomLoadingDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.elmahalwy.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientActivity extends AppCompatActivity {
    RecyclerView rv_ingredient;
    ArrayList<IngredientsModel> ingredients_array_list;
    IngredientsAdapter ingredientsAdapter;
    LinearLayoutManager linearLayoutManager;
    CustomLoadingDialog customLoadingDialog;
    @BindView(R.id.tv_toolbar_title)
    TextView tv_toolbar_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);
        ButterKnife.bind(this);
        InitEventDriven();
        get_ingredients();
    }


    void InitEventDriven() {
        tv_toolbar_title.setText(getIntent().getStringExtra("title"));
        customLoadingDialog = new CustomLoadingDialog(this, R.style.DialogSlideAnim);
        rv_ingredient = (RecyclerView) findViewById(R.id.rv_ingredient);
        ingredients_array_list = new ArrayList<>();
        ingredientsAdapter = new IngredientsAdapter(ingredients_array_list, this);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);
        rv_ingredient.setLayoutManager(linearLayoutManager);

    }

    void get_ingredients() {
        String url = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
        customLoadingDialog.show();
        AndroidNetworking.get(url)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            customLoadingDialog.dismiss();
                            JSONObject current_object = response.getJSONObject(Integer.parseInt(getIntent().getStringExtra("type")));
                            JSONArray ingredient = current_object.getJSONArray("ingredients");
                            for (int i = 0; i < ingredient.length(); i++) {
                                IngredientsModel ingredientsModel = new IngredientsModel();
                                JSONObject curet_object_ingredient = ingredient.getJSONObject(i);
                                ingredientsModel.setIngredient(curet_object_ingredient.getString("ingredient"));
                                ingredientsModel.setMeasure(curet_object_ingredient.getString("measure"));
                                ingredientsModel.setQuantity(curet_object_ingredient.getString("quantity"));
                                ingredients_array_list.add(ingredientsModel);
                            }
                            rv_ingredient.setAdapter(ingredientsAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        customLoadingDialog.dismiss();
                        if (error.getErrorCode() != 0) {
                            // received error from server
                            // error.getErrorCode() - the error code from server
                            // error.getErrorBody() - the error body from server
                            // error.getErrorDetail() - just an error detail
                            Log.e("onError errorCode : ", String.valueOf(error.getErrorCode()));
                            Log.e("onError errorBody : ", error.getErrorBody());
                            if (error.getErrorCode() == 400) {
                                Toast.makeText(IngredientActivity.this, "حدث خطأ ما...", Toast.LENGTH_SHORT).show();
                            }

                            if (error.getErrorCode() == 500) {
                                Toast.makeText(IngredientActivity.this, "خطأ فى الاتصال بالسيرفر...", Toast.LENGTH_SHORT).show();
                            }

                            // get parsed error object (If ApiError is your class)

                        } else {
                            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                            Log.e("onError errorDetail : ", error.getErrorDetail());
                            if (error.getErrorDetail().equals("connectionError")) {
                                Toast.makeText(IngredientActivity.this, "خطأ فى الاتصال بالانترنت...", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
}
