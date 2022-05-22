package com.chervinska.nure.mobile.models;

public class Employee {
    private String id;
    private String name;
    private String surname;
    private String phone;
    private String userId;

    public Employee(String id, String name, String surname, String phone, String userId) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return name + " " + surname;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhone() {
        return phone;
    }

    public String getUserId() {
        return userId;
    }
}
