package com.example.learnapi.Model;


public class User {
    private String url;
    private String username;
    private String email;
    private String password;
    private String token;




    public User(String url,
                String username,
                String email,
                String password,
                String token
    ) {
        this.url = url;
        this.username = username;
        this.email = email;
        this.password = password;
        this.token = token;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getToken(){
        return token;
    }





}