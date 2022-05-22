package com.chervinska.nure.mobile.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chervinska.nure.mobile.R;
import com.chervinska.nure.mobile.adapters.AnimalAdapter;
import com.chervinska.nure.mobile.api.ApiInterface;
import com.chervinska.nure.mobile.listeners.AnimalListener;
import com.chervinska.nure.mobile.models.Animal;
import com.chervinska.nure.mobile.models.Client;
import com.chervinska.nure.mobile.models.Employee;
import com.chervinska.nure.mobile.services.ApiClient;
import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnimalsActivity extends AppCompatActivity implements AnimalListener {
    private RecyclerView recyclerView;
    private List<Animal> animalList;
    private AnimalAdapter animalAdapter;
    private String token;
    ApiInterface apiInterface;
    ImageView settingsView, addView;
    TextView ukView, enView;
    String clientId;
    int animalClickedPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animals);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Bundle arguments = getIntent().getExtras();
        token = arguments.getString("token");
        setEmployee();

        recyclerView = findViewById(R.id.listView);
        recyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        );
        animalList = new ArrayList<>();
        animalAdapter = new AnimalAdapter(animalList, this);
        recyclerView.setAdapter(animalAdapter);
        getAnimals(1);
        settingsView = findViewById(R.id.settingsIcon);
        settingsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AnimalsActivity.this, SettingsActivity.class));
            }
        });
        ukView = findViewById(R.id.ukIcon);
        ukView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLocale("uk");
            }
        });
        enView = findViewById(R.id.enIcon);
        enView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLocale("en");
            }
        });

        addView = findViewById(R.id.addIcon);
        addView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnimalsActivity.this, AnimalCreateActivity.class);
                intent.putExtra("token", token);
                intent.putExtra("client", clientId);
                startActivity(intent);
            }
        });
    }

    private void setEmployee() {
        Call<Client> call = apiInterface.getByToken("Bearer " + token);
        call.enqueue(new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {
                if (response.isSuccessful()) {
                    clientId = response.body().getId();
                }
            }

            @Override
            public void onFailure(Call<Client> call, Throwable t) {

            }
        });
    }

    @Override
    public void onAnimalClicked(Animal animalClicked, int position) {
        animalClickedPosition = position;
        Intent intent = new Intent(getApplicationContext(), AnimalCreateActivity.class);
        intent.putExtra("isViewOrUpdated", true);
        intent.putExtra("token", token);
        intent.putExtra("client", clientId);
        intent.putExtra("animal",animalClicked.getId());
        startActivity(intent);
        startActivity(intent);
    }

    private void getAnimals(final int requestCode) {

        Call<List<Animal>> listCall = apiInterface.getAnimals("Bearer " + token);
        listCall.enqueue(new Callback<List<Animal>>() {
            @Override
            public void onResponse(Call<List<Animal>> call, Response<List<Animal>> response) {
                if (response.isSuccessful()) {
                    if (requestCode != 1) {
                        animalList.removeAll(animalList);
                    }
                    animalList.addAll(response.body());
                    animalAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<List<Animal>> call, Throwable t) {
                Snackbar.make(findViewById(R.id.animalsRoot),
                        t.getLocalizedMessage(),
                        Snackbar.LENGTH_SHORT).show();
            }
        });
    }


    private void setLocale(String language) {
        Resources resources = getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(new Locale(language));
        resources.updateConfiguration(configuration, metrics);
        onConfigurationChanged(configuration);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getAnimals(0);
    }
}