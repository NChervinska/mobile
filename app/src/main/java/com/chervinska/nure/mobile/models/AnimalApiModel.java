package com.chervinska.nure.mobile.models;

public class AnimalApiModel {
    private String name;
    private String type;
    private double weight;
    private int age;
    private String clientId;
    private String employeeId;

    public AnimalApiModel(String name, String type, double weight, int age, String clientId, String employeeId) {
        this.name = name;
        this.type = type;
        this.weight = weight;
        this.age = age;
        this.clientId = clientId;
        this.employeeId = employeeId;
    }
}
