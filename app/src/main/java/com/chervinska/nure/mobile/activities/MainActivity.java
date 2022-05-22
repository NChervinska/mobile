package com.chervinska.nure.mobile.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.chervinska.nure.mobile.services.ApiClient;
import com.chervinska.nure.mobile.api.ApiInterface;
import com.chervinska.nure.mobile.R;
import com.chervinska.nure.mobile.models.JwtResponseModel;
import com.chervinska.nure.mobile.models.LoginApiModel;
import com.google.android.material.snackbar.Snackbar;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button register, login;
    ConstraintLayout relativeLayout;
    MaterialEditText loginEmail, loginPassword;
    ApiInterface apiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        relativeLayout = findViewById(R.id.root);

        register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });

        loginEmail = findViewById(R.id.emailLoginField);
        loginPassword = findViewById(R.id.passwordLoginField);

        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(loginEmail.getText().toString())) {
                    Snackbar.make(relativeLayout,
                            getResources().getString(R.string.emptyEmail),
                            Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(loginPassword.getText().toString())) {
                    Snackbar.make(relativeLayout,
                            getResources().getString(R.string.emptyPassword),
                            Snackbar.LENGTH_SHORT).show();
                    return;
                }
                Call<JwtResponseModel> login = apiInterface.login(
                        new LoginApiModel(loginEmail.getText().toString(),
                                loginPassword.getText().toString()));
                login.enqueue(new Callback<JwtResponseModel>() {
                    @Override
                    public void onResponse(Call<JwtResponseModel> call, Response<JwtResponseModel> response) {
                        if (response.isSuccessful()) {
                            final String token = response.body().getJwt();
                            Intent intent = new Intent(MainActivity.this, AnimalsActivity.class);
                            intent.putExtra("token", token);
                            startActivity(intent);
                        } else {
                            try {
                                Snackbar.make(relativeLayout,
                                        response.errorBody().string(),
                                        Snackbar.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<JwtResponseModel> call, Throwable t) {
                        Snackbar.make(relativeLayout,
                                t.getLocalizedMessage(),
                                Snackbar.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

}