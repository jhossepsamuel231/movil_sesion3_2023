package com.example.sesion03_2023;

import java.io.Serializable;

public class User implements Serializable {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String gender;

    public User(String firstName, String lastName, String username, String password, String gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.gender = gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getGender() {
        return gender;
    }
}
