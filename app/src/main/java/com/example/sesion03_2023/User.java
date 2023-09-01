package com.example.sesion03_2023;

import java.io.Serializable;

public class User implements Serializable {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String gender;
    private String phone;
    private Boolean status;

    private int profileImageRes;

    public User(String firstName, String lastName, String username, String password, String gender, String phone, Boolean status, int profileImageRes) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.phone = phone;
        this.status = status;
        this.profileImageRes = profileImageRes;
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

    public String getPhone() {
        return phone;
    }

    public Boolean getStatus() {
        return status;
    }

    public int getProfileImageRes() {
        return profileImageRes;
    }
}
