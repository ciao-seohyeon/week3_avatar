package com.example.week3_avatar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.antonionicolaspina.revealtextview.RevealTextView;

public class SecondPictureUpload extends AppCompatActivity implements View.OnClickListener{
    RevealTextView textView;
    Button nextButton;
    final Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondpictureupload);

        bundle.putString("name", "hey");
        bundle.putInt("hair", 0);

        textView = findViewById(R.id.speechBubble);
        nextButton = findViewById(R.id.nextButton);

        textView.setAnimatedText("어서와! 이제 너의 취향을 말해주면 내가 멋지게 만들어 줄게!");

        nextButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.nextButton) {
            Intent intent = new Intent(this, ImageProcessActivity.class);
            intent.putExtra("myBundle", bundle);
            startActivity(intent);
        }
    }

}
