package com.chervinska.nure.mobile.models;

public class Animal {
    private String id;
    private String name;
    private String type;
    private double weight;
    private int age;


    public Animal(String id, String name, String type, double weight, int age) {
        this.name = name;
        this.type = type;
        this.weight = weight;
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public double getWeight() {
        return weight;
    }

    public int getAge() {
        return age;
    }

}
