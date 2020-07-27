package com.example.week3_avatar.Retrofit;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface IMyService {

    /* -------------------------------[USER API]------------------------------- */
    /* Get all Users */
    @GET("/api/users")
    Call<ArrayList<User>> getAllUser();

    /* Get Single User By Email */
    @GET("/api/users/{user_id}")
    Call<User> getUser(@Path("user_id") String user_id);

    /* CREATE USER */
    @POST("/api/users")
    Call<User> createUser(@Body User user);

    /* UPDATE THE USER */
    @PUT("/api/users/{user_id}")
    Call<User> updateUser(@Path("user_id") String user_id, @Body User user);

    /* UPDATE THE User Photo */
    @PUT("/api/users/photoList/{user_id}")
    Call<User> addToPhotoList(@Path("user_id") String user_id, @Body UserPhoto userphoto);

    /* DELETE THE USER Photo */
    @DELETE("/api/users/photoList/{user_id}")
    Call<User> deleteInPhotoList(@Path("user_id") String user_id, @Body UserPhoto userphoto);

    /* -------------------------------[FILE API]------------------------------- */
    /* UPLOAD (IMAGE) FILE */
    @Multipart
    @POST("/api/files/upload")
    Call<myFile> uploadFile(@Part MultipartBody.Part filePart, @Part("title") RequestBody title);

    /* GET ALL FILE */
    @GET("/api/files")
    Call<ArrayList<myFile>> getAllFile();

    /* GET SELECTED FILE */
    @GET("/api/files/download/{saveFileName}")
    Call<myFile> getSelectedFile(@Path("saveFileName") String saveFileName);
}
