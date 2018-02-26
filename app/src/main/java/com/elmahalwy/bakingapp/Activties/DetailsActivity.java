package com.elmahalwy.bakingapp.Activties;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.elmahalwy.bakingapp.Adapters.DetailsAdapter;
import com.elmahalwy.bakingapp.Adapters.MainAdapter;
import com.elmahalwy.bakingapp.Models.StepsModel;
import com.elmahalwy.bakingapp.R;
import com.elmahalwy.bakingapp.Utils.CustomLoadingDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {
    @BindView(R.id.card_view)
    CardView card_view;
    RecyclerView items_recycler;
    List<StepsModel> steps_list;
    DetailsAdapter detailsAdapter;
    LinearLayoutManager linearLayoutManager;
    CustomLoadingDialog customLoadingDialog;
    @BindView(R.id.tv_toolbar_title)
    TextView tv_toolbar_title;
    boolean twoPaneMode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        customLoadingDialog = new CustomLoadingDialog(this, R.style.DialogSlideAnim);
        InitUi();
        InitEventDriven();
        get_steps();
    }

    void InitUi() {
        tv_toolbar_title.setText("Recipes");
        steps_list = new ArrayList<>();
        items_recycler = (RecyclerView) findViewById(R.id.items_recycler);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);
        detailsAdapter = new DetailsAdapter(steps_list, getApplicationContext());
        items_recycler.setLayoutManager(linearLayoutManager);
    }

    void InitEventDriven() {
        card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), IngredientActivity.class);
                intent.putExtra("type", getIntent().getStringExtra("type"));
                intent.putExtra("title", tv_toolbar_title.getText().toString());
                startActivity(intent);
            }
        });
    }

    void get_steps() {
        customLoadingDialog.show();
        String stps_url = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
        AndroidNetworking.get(stps_url)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            customLoadingDialog.dismiss();
                            Log.e("response", response.toString());
                            JSONObject jsonObject = response.getJSONObject(Integer.parseInt(getIntent().getStringExtra("type")));
                            JSONArray steps_array = jsonObject.getJSONArray("steps");
                            for (int i = 0; i < steps_array.length(); i++) {
                                JSONObject current_object = steps_array.getJSONObject(i);
                                StepsModel stepsModel = new StepsModel();
                                stepsModel.setId(current_object.getString("id"));
                                stepsModel.setShortDescription(current_object.getString("shortDescription"));
                                stepsModel.setDescription(current_object.getString("description"));
                                stepsModel.setVideoURL(current_object.getString("videoURL"));
                                steps_list.add(stepsModel);
                                Log.e("stepsModel", steps_list.toString());
                            }
                            items_recycler.setAdapter(detailsAdapter);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        customLoadingDialog.dismiss();
                        if (anError.getErrorCode() != 0) {
                            // received error from server
                            // error.getErrorCode() - the error code from server
                            // error.getErrorBody() - the error body from server
                            // error.getErrorDetail() - just an error detail
                            Log.e("onError errorCode : ", String.valueOf(anError.getErrorCode()));
                            Log.e("onError errorBody : ", anError.getErrorBody());
                            if (anError.getErrorCode() == 400) {
                                Toast.makeText(DetailsActivity.this, "حدث خطأ ما...", Toast.LENGTH_SHORT).show();
                            }
                            if (anError.getErrorCode() == 500) {
                                Toast.makeText(DetailsActivity.this, "خطأ فى الاتصال بالسيرفر...", Toast.LENGTH_SHORT).show();
                            }
                            // get parsed error object (If ApiError is your class)

                        } else {
                            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                            Log.e("onError errorDetail : ", anError.getErrorDetail());
                            if (anError.getErrorDetail().equals("connectionError")) {
                                Toast.makeText(DetailsActivity.this, "خطأ فى الاتصال بالانترنت...", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
}
