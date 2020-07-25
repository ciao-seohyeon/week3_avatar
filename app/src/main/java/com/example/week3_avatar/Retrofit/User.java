package com.example.week3_avatar.Retrofit;

public class User {

    private String name;
    private String id;
    private String password;
    private String[] photo_list = {};

    public User(String name, String id, String password, String[] photo_list){
        this.name = name;
        this.id = id;
        this.password = password;
        this.photo_list = photo_list;
    }

    public User(String name, String id, String password){
        this.name = name;
        this.id = id;
        this.password = password;
    }

    public String getName(){ return name; }
    public String getId(){ return id; }
    public String getPassword(){ return password; }
    public String[] getPhoto_list(){ return photo_list; }

    public void setName(String name){this.name = name;}
    public void setId(String id){ this.id = id; }
    public void setPassword(String password){ this.password = password; }
    public void setPhoto_list(String[] photo_list){ this.photo_list = photo_list; }

}
