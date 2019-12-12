package com.example.grocerymark.Model;

public class User {
    private String Name;
    private String Password;
    private String Email;
    private String Address;
    private String Phone;


    public User() {
    }

    public User(String name, String password, String email, String address, String phone) {
        Name = name;
        Password = password;
        Email = email;
        Address = address;
        Phone = phone;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public User(String name, String password) {
        Name = name;
        Password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
