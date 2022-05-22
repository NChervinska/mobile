package com.chervinska.nure.mobile.models;

public class UserResponseModel {
    private String id;
    private String email;
    private String role;

    public UserResponseModel(String id, String email, String role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }
}
