package com.chervinska.nure.mobile.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;

import com.chervinska.nure.mobile.R;
import com.chervinska.nure.mobile.adapters.ActivityAdapter;
import com.chervinska.nure.mobile.adapters.AnimalAdapter;
import com.chervinska.nure.mobile.api.ApiInterface;
import com.chervinska.nure.mobile.models.Activity;
import com.chervinska.nure.mobile.models.Animal;
import com.chervinska.nure.mobile.services.ApiClient;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivitiesActivity extends AppCompatActivity {
    String token, animal;
    RecyclerView recyclerView;
    List<Activity> activities;
    ActivityAdapter activityAdapter;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Bundle arguments = getIntent().getExtras();
        token = arguments.getString("token");
        animal = arguments.getString("animal");

        recyclerView = findViewById(R.id.activityView);
        recyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        );
        activities = new ArrayList<>();
        activityAdapter = new ActivityAdapter(activities);
        recyclerView.setAdapter(activityAdapter);
        getActivities();
    }

    void getActivities() {
        Call<List<Activity>> listCall = apiInterface.getActivityByAnimalId(animal, "Bearer " + token);
        listCall.enqueue(new Callback<List<Activity>>() {
            @Override
            public void onResponse(Call<List<Activity>> call, Response<List<Activity>> response) {
                if (response.isSuccessful()) {
                    activities.addAll(response.body());
                    activityAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<List<Activity>> call, Throwable t) {

            }
        });

    }
}