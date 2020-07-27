package com.example.week3_avatar.Retrofit;

public class myFile {
    private String title;
    private String saveFileName;
    private String size;


    public myFile (String title){
        this.title = title;
    }

    public String getTitle(){ return title; }

    public String getSaveFileName(){
        return saveFileName;
    }
}
