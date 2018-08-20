package com.example.user.minipro5997secondapp.model.entities;

public class Driver {
    private String name;
    private String email;
    private String password;
    private String id;
    private String email___password;

    // ----------- constructor --------

    public Driver() {
    }

    public Driver(String name, String email, String password, String id) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.id = id;
        this.email___password= email+"___"+password;
    }

    // -------- getter & setters -------

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail___password() {
        return email___password;
    }

    public void sxetEmail___password(String email___password) {
        this.email___password = email___password;
    }

    // --------- methods -------

    @Override
    public String toString() {
        return "name: " + name + "\n" +
                "email: " + email + "\n"+
                "id: " + id + ":\n";
    }
}
