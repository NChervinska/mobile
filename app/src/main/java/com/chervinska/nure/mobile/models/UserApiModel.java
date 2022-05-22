package com.chervinska.nure.mobile.models;

public class UserApiModel {
    private String email;
    private String password;
    private String role;
    private String name;
    private String surname;
    private String phone;

    public UserApiModel(String email, String password, String role, String name, String surname, String phone) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
    }
}
