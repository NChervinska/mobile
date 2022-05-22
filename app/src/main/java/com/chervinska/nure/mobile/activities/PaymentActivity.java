package com.chervinska.nure.mobile.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chervinska.nure.mobile.R;
import com.chervinska.nure.mobile.api.ApiInterface;
import com.chervinska.nure.mobile.services.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {

    String token, animalId;
    ApiInterface apiInterface;
    TextView textView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Bundle arguments = getIntent().getExtras();
        token = arguments.getString("token");
        animalId = arguments.getString("animal");

        textView = findViewById(R.id.textView);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Integer> call = apiInterface.getCheck(animalId, "Bearer " + token);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    textView.setText(textView.getText() + " " + response.body());
                    return;
                }
                textView.setText(getResources().getString(R.string.emptyPatment));
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                textView.setText(getResources().getString(R.string.emptyPatment));
            }
        });

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}