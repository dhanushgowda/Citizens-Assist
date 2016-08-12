package com.tw.awayday.citizensassist;

public class User {
    private String name;
    private String password;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public void is(String userEmail, String userPassword) {
        this.name = userEmail;
        this.password = userPassword;
    }
}
