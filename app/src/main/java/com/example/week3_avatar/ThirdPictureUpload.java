package com.example.week3_avatar;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ThirdPictureUpload extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thirdpictureupload);
        Toast.makeText(getApplicationContext(), "to third page", Toast.LENGTH_LONG).show();

    }
}
