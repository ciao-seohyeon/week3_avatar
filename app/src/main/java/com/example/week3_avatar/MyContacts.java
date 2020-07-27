package com.example.week3_avatar;

import java.io.Serializable;

public class MyContacts implements Serializable {

    public String user_phNumber, user_Name;
    public long photo_id, person_id;
    public int id;

    public MyContacts() {}
    public long getPhoto_id() { return photo_id; }
    public long getPerson_id() { return person_id; }
    public void setPhoto_id(long id) {this.photo_id = id;}
    public void setPerson_id(long id) {this.photo_id = id;}

    public String getUser_phNumber() { return user_phNumber; }
    public String getUser_Name() { return user_Name; }

    public void setId(int id) { this.id = id; }
    public int getId() { return id; }
    public void setUser_phNumber(String string) { this.user_phNumber = string; }
    public void setUser_Name(String string) { this.user_Name = string; }

}

