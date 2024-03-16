package com.domondon.angeline.block4.p1.pahiyaspalate.model;

public class User {
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }


    private int id;
    private String username;

    public String getEmail() {
        return email;
    }

    private String email;

    public User(int id,String username, String email)
    {
        this.id = id;
        this.username = username;
        this.email = email;
    }
}
