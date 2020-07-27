package com.example.week3_avatar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.week3_avatar.Retrofit.IMyService;
import com.example.week3_avatar.Retrofit.RetrofitClient;

import java.util.Objects;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button startBtn;

    private static String data = "hey";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        startBtn = findViewById(R.id.startButton);
        startBtn.setOnClickListener(this);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.startButton) {
            Intent intent = new Intent(this, Opening.class);
            Intent i = getIntent();
            final String id = Objects.requireNonNull(i.getExtras()).getString("id");
            intent.putExtra("id", id);
            startActivity(intent);
        }

        if (view.getId() == R.id.galleryButton){
            Intent intent = new Intent(this, Gallery.class);
            Intent i = getIntent();
            final String id = Objects.requireNonNull(i.getExtras()).getString("id");
            intent.putExtra("id", id);
            startActivity(intent);
        }


    }
}