package com.example.richa_764947_ft;

public class Person {

    int id;
    String fname,lname,phone,address;

    public Person(int id, String fname, String lname, String phone, String address) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.phone = phone;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }
}
