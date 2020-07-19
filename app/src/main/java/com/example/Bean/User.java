package com.example.Bean;

import cn.bmob.v3.BmobUser;

public class User extends BmobUser {
    /**
     * 用户基本信息类
     */
    private String name;
    private String password;

    public User() {

    }
    public User(String name, String password){
        this.name = name;
        this.password = password;
    }
    public void setName(String name) { this.name = name; }
    public void setPassword(String passwprd) {this.password = passwprd; }
    public String getName() { return name; }
    public String getPassword() { return password; }
}