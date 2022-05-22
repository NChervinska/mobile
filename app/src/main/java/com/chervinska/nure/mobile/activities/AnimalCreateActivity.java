package com.chervinska.nure.mobile.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.chervinska.nure.mobile.R;
import com.chervinska.nure.mobile.api.ApiInterface;
import com.chervinska.nure.mobile.models.Animal;
import com.chervinska.nure.mobile.models.AnimalApiModel;
import com.chervinska.nure.mobile.models.Employee;
import com.chervinska.nure.mobile.services.ApiClient;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnimalCreateActivity extends AppCompatActivity {

    ImageView backButton, checkIcon, deleteIcon, recommendationIcon, mealIcon, paymentIcon;
    Spinner typeSpinner, employeeSpinner;
    EditText nameField, weightField, ageField;
    Animal updateAnimal;
    ApiInterface apiInterface;
    private String token;
    CoordinatorLayout root;
    String selectedType;
    Employee selectedEmployee;
    String clientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_create);

        Bundle arguments = getIntent().getExtras();
        token = arguments.getString("token");
        clientId = arguments.getString("client");

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        root = findViewById(R.id.createRoot);
        ImageView imageBack = findViewById(R.id.imageBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        nameField = findViewById(R.id.animalName);
        weightField = findViewById(R.id.animalWeight);
        ageField = findViewById(R.id.animalAge);

        checkIcon = findViewById(R.id.imageSave);
        checkIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveAnimal();
            }
        });

        deleteIcon = findViewById(R.id.deleteNote);
        deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAnimal();
            }
        });

        typeSpinner = findViewById(R.id.spinnerType);
        String[] types = {getResources().getString(R.string.cat), getResources().getString(R.string.dog),
                getResources().getString(R.string.parrot), getResources().getString(R.string.hamster),
                getResources().getString(R.string.turtle)};
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);
        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedType = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        typeSpinner.setOnItemSelectedListener(itemSelectedListener);

        employeeSpinner = findViewById(R.id.spinnerEmployee);
        employeeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedEmployee = (Employee) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        setEmployee();

        if (getIntent().getBooleanExtra("isViewOrUpdated", false)) {
            String updateAnimalId = arguments.getString("animal");

            Call<Animal> animalCall = apiInterface.getAnimal(updateAnimalId, "Bearer " + token);
            animalCall.enqueue(new Callback<Animal>() {
                @Override
                public void onResponse(Call<Animal> call, Response<Animal> response) {
                    if (response.isSuccessful()) {
                        updateAnimal = response.body();
                        setUpdateAnimal();
                        return;
                    }
                    try {
                        Snackbar.make(root,
                                response.errorBody().string(), -
                                        Snackbar.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<Animal> call, Throwable t) {
                    Snackbar.make(root,
                            t.getLocalizedMessage(),
                            Snackbar.LENGTH_SHORT).show();
                }
            });


        }

        recommendationIcon = findViewById(R.id.imageRecommendation);
        recommendationIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRecommendation();
            }
        });

        mealIcon = findViewById(R.id.imageMeal);
        mealIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFeedRecommendation();
            }
        });

        paymentIcon = findViewById(R.id.imagePayment);
        paymentIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(updateAnimal == null) return;
                Intent intent = new Intent(AnimalCreateActivity.this, PaymentActivity.class);
                intent.putExtra("token", token);
                intent.putExtra("animal", updateAnimal.getId());
                startActivity(intent);
            }
        });
    }

    private void getFeedRecommendation() {
        if (updateAnimal == null) return;
        Call<String> call = apiInterface.feedRecommendation(updateAnimal.getId(), "Bearer " + token);
        call.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(response.body())));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Snackbar.make(root,
                        t.getLocalizedMessage(),
                        Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void getRecommendation() {
        if (updateAnimal == null) return;
        Call<String> call = apiInterface.careRecommendation(updateAnimal.getId(), "Bearer " + token);
        call.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(response.body())));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Snackbar.make(root,
                        t.getLocalizedMessage(),
                        Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    private void setUpdateAnimal() {
        nameField.setText(updateAnimal.getName());
        ageField.setText("" + updateAnimal.getAge());
        weightField.setText("" + updateAnimal.getWeight());
    }


    private void setEmployee() {
        Call<List<Employee>> callEmployee = apiInterface.getAllEmployee("Bearer " + token);
        callEmployee.enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                if (response.isSuccessful()) {
                    List<Employee> emp = response.body();
                    ArrayAdapter<Employee> adapter = new ArrayAdapter(getApplicationContext(),
                            android.R.layout.simple_spinner_item, emp);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    employeeSpinner.setAdapter(adapter);
                    return;
                }
                try {
                    Snackbar.make(root,
                            response.errorBody().string(),
                            Snackbar.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {
                Snackbar.make(root,
                        t.getLocalizedMessage(),
                        Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteAnimal() {
        if (updateAnimal == null) {
            AnimalCreateActivity.this.finishActivity(0);
            return;
        }
        Call<String> call = apiInterface.deleteAnimal("Bearer " + token, updateAnimal.getId());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    AnimalCreateActivity.this.finishActivity(0);
                    return;
                }
                try {
                    Snackbar.make(root,
                            response.errorBody().string(),
                            Snackbar.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Snackbar.make(root,
                        t.getLocalizedMessage(),
                        Snackbar.LENGTH_SHORT).show();
            }
        });
    }


    private void saveAnimal() {
        final String name = nameField.getText().toString().trim();
        final String age = ageField.getText().toString().trim();
        final String weight = weightField.getText().toString().trim();
        if (name.isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.emptyName), Toast.LENGTH_SHORT).show();
            return;
        }

        final AnimalApiModel animal = new AnimalApiModel(name, selectedType,
                Double.parseDouble(weight), Integer.parseInt(age), clientId, selectedEmployee.getId());

        Callback<Animal> callBack = new Callback<Animal>() {
            @Override
            public void onResponse(Call<Animal> call, Response<Animal> response) {
                if (response.isSuccessful()) {
                    AnimalCreateActivity.this.finishActivity(0);
                    return;
                }
                try {
                    Snackbar.make(root,
                            response.errorBody().string(),
                            Snackbar.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Animal> call, Throwable t) {
                Snackbar.make(root,
                        t.getLocalizedMessage(),
                        Snackbar.LENGTH_SHORT).show();
            }
        };

        if (updateAnimal == null) {
            Call<Animal> animalCall = apiInterface.addAnimal("Bearer " + token, animal);
            animalCall.enqueue(callBack);

            AnimalCreateActivity.this.finishActivity(0);
            return;
        }

        final Call<Animal> animalCall = apiInterface.editAnimal(updateAnimal.getId(), "Bearer " + token, animal);
        animalCall.enqueue(callBack);

        AnimalCreateActivity.this.finishActivity(0);
        return;
    }
}