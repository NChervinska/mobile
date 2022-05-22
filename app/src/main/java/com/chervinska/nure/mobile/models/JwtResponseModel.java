package com.chervinska.nure.mobile.models;

public class JwtResponseModel {
    private String jwt;

    public JwtResponseModel(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}
