package com.elmahalwy.bakingapp.Activties;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
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
import com.elmahalwy.bakingapp.Adapters.MainAdapter;
import com.elmahalwy.bakingapp.Models.MainModel;
import com.elmahalwy.bakingapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    RecyclerView rv_main;
    MainAdapter mainAdapter;
    LinearLayoutManager linearLayoutManager;
    ArrayList<MainModel> main_list;
    @BindView(R.id.tv_toolbar_title)
    TextView tv_toolbar_titiel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        InitUi();

    }

    void InitUi() {
        tv_toolbar_titiel.setText("Recipes");
        main_list = new ArrayList<>();
        rv_main = (RecyclerView) findViewById(R.id.rv_main);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);

        mainAdapter = new MainAdapter(main_list, this);

        MainModel first_model = new MainModel();
        first_model.setTv_main("Nutilla ");
        first_model.setImage(R.drawable.nuttla_cake);

        MainModel seconde_model = new MainModel();
        seconde_model.setTv_main("Brounies ");
        seconde_model.setImage(R.drawable.bronise_cake);

        MainModel third_model = new MainModel();
        third_model.setTv_main("Yellow cake ");
        third_model.setImage(R.drawable.yellow_cake);

        MainModel fourth_model = new MainModel();
        fourth_model.setTv_main("Cheese cake ");
        fourth_model.setImage(R.drawable.chese_cake);

        main_list.add(0, first_model);
        main_list.add(1, seconde_model);
        main_list.add(2, third_model);
        main_list.add(3, fourth_model);

        if (findViewById(R.id.relative_x_large)!=null){
            rv_main.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
        }else {
            rv_main.setLayoutManager(linearLayoutManager);
        }
        rv_main.setAdapter(mainAdapter);
    }


}
