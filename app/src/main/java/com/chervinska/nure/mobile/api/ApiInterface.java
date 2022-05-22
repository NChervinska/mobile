package com.chervinska.nure.mobile.api;

import com.chervinska.nure.mobile.models.Animal;
import com.chervinska.nure.mobile.models.AnimalApiModel;
import com.chervinska.nure.mobile.models.Client;
import com.chervinska.nure.mobile.models.Employee;
import com.chervinska.nure.mobile.models.JwtResponseModel;
import com.chervinska.nure.mobile.models.LoginApiModel;
import com.chervinska.nure.mobile.models.UserApiModel;
import com.chervinska.nure.mobile.models.UserResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;

public interface ApiInterface {


    @GET("animal/getByClientId")
    Call<List<Animal>> getAnimals(@Header("Authorization") String token);

    @POST("user/login")
    Call<JwtResponseModel> login(@Body() LoginApiModel loginApiModel);

    @POST("user/register")
    Call<UserResponseModel> register(@Body() UserApiModel userApiModel);

    @Headers({"Accept: application/json"})
    @DELETE("animal/delete/{id}")
    Call<String> deleteAnimal(@Header("Authorization") String token, @Path("id") String id);

    @GET("employee/getAll")
    Call<List<Employee>> getAllEmployee(@Header("Authorization") String token);

    @GET("client")
    Call<Client> getByToken(@Header("Authorization") String token);

    @Headers({"Accept: application/json",
            "Content-Type: application/json"})
    @POST("animal/add")
    Call<Animal> addAnimal(@Header("Authorization") String token, @Body AnimalApiModel animalApiModel);

    @Headers({"Accept: application/json",
            "Content-Type: application/json"})
    @PUT("animal/edit/{id}")
    Call<Animal> editAnimal(
            @Path("id") String id,
            @Header("Authorization") String token,
            @Body AnimalApiModel animalApiModel
    );

    @Headers({"Accept: application/json"})
    @GET("animal/care/{id}")
    Call<String> careRecommendation(@Path("id") String id, @Header("Authorization") String token);

    @Headers({"Accept: application/json"})
    @GET("animal/feed/{id}")
    Call<String> feedRecommendation(@Path("id") String id, @Header("Authorization") String token);

    @Headers({"Accept: application/json"})
    @GET("animal/get/{id}")
    Call<Animal> getAnimal(@Path("id") String id, @Header("Authorization") String token);

    @GET("activity/getCheck/{id}")
    Call<Integer> getCheck(@Path("id") String animalId, @Header("Authorization") String token);
}
