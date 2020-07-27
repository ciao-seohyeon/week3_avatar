package com.example.week3_avatar;

import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.week3_avatar.Retrofit.IMyService;
import com.example.week3_avatar.Retrofit.RetrofitClient;

import java.util.Objects;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class Gallery extends AppCompatActivity {

    Intent intent = getIntent();
    final String id = Objects.requireNonNull(intent.getExtras()).getString("id");
    final IMyService retrofitClient = RetrofitClient.getApiService();


}
