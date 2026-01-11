package com.example.CartGalaxy.admin.model;

public class Admin {
    private int admin_id;
    private String name;
    private String email;

    public Admin() {}

    public Admin(int admin_id, String name, String email) {
        this.admin_id = admin_id;
        this.name = name;
        this.email = email;
    }

    public int getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }

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
}
