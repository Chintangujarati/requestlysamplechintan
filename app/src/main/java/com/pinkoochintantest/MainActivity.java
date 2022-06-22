package com.pinkoochintantest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.pinkoochintantest.adapter.CategoryAdapter;
import com.pinkoochintantest.adapter.ProductAdapter;
import com.pinkoochintantest.api.RetrofitApi;
import com.pinkoochintantest.model.CategoryModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    String token;
    List<CategoryModel> categoryModels = new ArrayList<>();
    RecyclerView rvCategory, rvProduct;
    ProgressDialog progress;
    TextView tvSize;
    JSONArray jsonProductArray;
    ImageView ivChange;
    boolean isGrid = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvCategory = findViewById(R.id.rvCategory);
        rvProduct = findViewById(R.id.rvProduct);
        ivChange = findViewById(R.id.ivChange);
        tvSize = findViewById(R.id.tvSize);

        progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Please wait");
        progress.setCancelable(false);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        token = preferences.getString("token", "");

        ivChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(jsonProductArray != null && jsonProductArray.length() > 0){
                    if(isGrid) {
                        ivChange.setImageResource(R.drawable.ic_grid);
                        setRecyclerView();
                    }else {
                        ivChange.setImageResource(R.drawable.ic_list);
                        setGridView();
                    }
                }
            }
        });

        getCategory();

    }

    private void getCategory() {
        progress.show();
        RetrofitApi.getInstance(getApplicationContext()).getMyApi().getCategory("Bearer " + token)
                .enqueue(new Callback<JsonArray>() {
                    @Override
                    public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {

                        if (response.isSuccessful()) {
                            JSONArray jsonArray = new JSONArray();
                            try {
                                jsonArray = new JSONArray(response.body().toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            for (int i = 0; i < jsonArray.length(); i++) {

                                try {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    categoryModels.add(new CategoryModel(jsonObject.getString("id"), jsonObject.getString("name"), jsonObject.getString("description"), jsonObject.getString("categoryImageUrl")));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            rvCategory.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
                            CategoryAdapter favChapterAdapter = new CategoryAdapter(MainActivity.this, categoryModels);
                            rvCategory.setAdapter(favChapterAdapter);

                            getProductAPI();

                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<JsonArray> call, Throwable t) {

                        progress.dismiss();

                    }
                });
    }

    private void getProductAPI() {
        RetrofitApi.getInstance(getApplicationContext()).getMyApi().getProduct("Bearer " + token)
                .enqueue(new Callback<JsonArray>() {
                    @Override
                    public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {

                        progress.dismiss();

                        try {
                            jsonProductArray = new JSONArray(response.body().toString());
                            tvSize.setText(jsonProductArray.length()+" Product Found");

                            setGridView();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonArray> call, Throwable t) {

                        progress.dismiss();

                    }
                });
    }

    void setGridView() {
        isGrid = true;
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rvProduct.setLayoutManager(layoutManager);
        ProductAdapter favChapterAdapter = new ProductAdapter(MainActivity.this, jsonProductArray,isGrid);
        rvProduct.setAdapter(favChapterAdapter);
    }

    void setRecyclerView(){
        isGrid = false;
        rvProduct.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        ProductAdapter favChapterAdapter = new ProductAdapter(MainActivity.this, jsonProductArray,isGrid);
        rvProduct.setAdapter(favChapterAdapter);
    }
}