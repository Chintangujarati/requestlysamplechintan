package com.pinkoochintantest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;
import com.pinkoochintantest.api.RetrofitApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText etCrn, etPassword;
    CardView cvLogin;
    ProgressDialog progress;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etCrn = findViewById(R.id.etCrn);
        etPassword = findViewById(R.id.etPassword);
        cvLogin = findViewById(R.id.cvLogin);

        progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Please wait");
        progress.setCancelable(false);


        preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        editor = preferences.edit();

        cvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (etCrn.getText().toString().trim().matches("")) {
                    Toast.makeText(LoginActivity.this, "Please enter CRN number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (etPassword.getText().toString().trim().matches("")) {
                    Toast.makeText(LoginActivity.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                    return;
                }
                loginAPI();
            }
        });


    }

    private void loginAPI() {
        progress.show();
        HashMap<String, Object> map = new HashMap<>();
        map.put("customerRelationShipNumber", etCrn.getText().toString());
        map.put("password", etPassword.getText().toString());
        map.put("type", 0);
        map.put("deviceId", "0lfwejw");
        map.put("token", "Bearer qwerty123456");
        RetrofitApi.getInstance(getApplicationContext()).getMyApi().login(map)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                        progress.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().toString());
                            String token = jsonObject.getString("token");
                            editor.putString("token", token);
                            editor.putBoolean("isLogin",true);
                            editor.apply();

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("token", token);
                            startActivity(intent);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                        progress.dismiss();

                    }
                });
    }
}