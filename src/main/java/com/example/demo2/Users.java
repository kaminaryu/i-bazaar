package com.example.demo2;

public class Users {
    protected String name;
    protected String matricNum;
    protected String password;

    public Users(String name, String matricNum, String password) {
        this.name = name;
        this.matricNum = matricNum;
        this.password = password;
    }

    public String getName() { return name; }
    public String getMatricNum() { return matricNum; }
    public String getPassword() { return password; }
}
