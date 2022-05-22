package com.chervinska.nure.mobile.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.chervinska.nure.mobile.services.ApiClient;
import com.chervinska.nure.mobile.api.ApiInterface;
import com.chervinska.nure.mobile.R;
import com.chervinska.nure.mobile.models.UserApiModel;
import com.chervinska.nure.mobile.models.UserResponseModel;
import com.google.android.material.snackbar.Snackbar;
import com.rengwuxian.materialedittext.MaterialEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    Button register, login;
    ConstraintLayout relativeLayout;
    MaterialEditText email, name, surname, phone, password;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        relativeLayout = findViewById(R.id.registerRoot);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        email = findViewById(R.id.emailField);
        name = findViewById(R.id.nameField);
        surname = findViewById(R.id.surnameField);
        phone = findViewById(R.id.phoneField);
        password = findViewById(R.id.passwordField);
        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(email.getText().toString())) {
                    Snackbar.make(relativeLayout,
                            getResources().getString(R.string.emptyEmail),
                            Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(name.getText().toString())) {
                    Snackbar.make(relativeLayout,
                            getResources().getString(R.string.emptyName),
                            Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(surname.getText().toString())) {
                    Snackbar.make(relativeLayout,
                            getResources().getString(R.string.emptySurname),
                            Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phone.getText().toString())) {
                    Snackbar.make(relativeLayout,
                            getResources().getString(R.string.emptyPhone),
                            Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password.getText().toString())) {
                    Snackbar.make(relativeLayout,
                            getResources().getString(R.string.emptyPassword),
                            Snackbar.LENGTH_SHORT).show();
                    return;
                }
                Call<UserResponseModel> login = apiInterface.register(
                        new UserApiModel(email.getText().toString(),
                                password.getText().toString(),
                                "client",
                                name.getText().toString(),
                                surname.getText().toString(),
                                phone.getText().toString()));
                login.enqueue(new Callback<UserResponseModel>() {
                    @Override
                    public void onResponse(Call<UserResponseModel> call, Response<UserResponseModel> response) {
                        if (response.isSuccessful()) {
                            Snackbar.make(relativeLayout,
                                    getResources().getText(R.string.registerSuccess),
                                    Snackbar.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserResponseModel> call, Throwable t) {
                        Snackbar.make(relativeLayout,
                                t.getLocalizedMessage(),
                                Snackbar.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}