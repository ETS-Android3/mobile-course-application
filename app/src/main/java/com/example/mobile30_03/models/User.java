package com.example.mobile30_03.models;

public class User {
    private static int nextId = 1;
    public String username;
    public String password;
    public String email;
    public String phoneNumber;
    public int id;

    public User(String username,String password,String email,String phoneNumber) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        id = nextId++;
    }
}

