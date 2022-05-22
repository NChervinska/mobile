package com.chervinska.nure.mobile.models;

public class Client {
    private String id;
    private String name;
    private String surname;
    private String phone;
    private int bonus;
    private String userId;

    public Client(String id, String name, String surname, String phone, int bonus, String userId) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.bonus = bonus;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }
}
