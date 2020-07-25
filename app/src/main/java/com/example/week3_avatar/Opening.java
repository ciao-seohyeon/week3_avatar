package com.example.week3_avatar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.antonionicolaspina.revealtextview.RevealTextView;

public class Opening extends AppCompatActivity implements View.OnClickListener {
    RevealTextView textView;
    Button nextBtn, noButton, yesButton;
    Integer section = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opening);

        textView = findViewById(R.id.speechBubble);
        nextBtn = findViewById(R.id.nextButton);
        noButton = findViewById(R.id.noButton);
        yesButton = findViewById(R.id.yesButton);

        textView.setAnimatedText("안녕 나는 패션요정이야 반가워!");

        nextBtn.setOnClickListener(this);
        noButton.setOnClickListener(this);
        yesButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if ((view.getId() == R.id.nextButton) && (section == 0)) {
            textView.setAnimatedText("혹시 너의 컴플렉스가 있니? 내가 패션을 통해 보완해줄 수 있어!");
            noButton.setVisibility(View.VISIBLE);
            yesButton.setVisibility(View.VISIBLE);
            nextBtn.setVisibility(View.INVISIBLE);
        } else if (view.getId() == R.id.noButton) {
            textView.setAnimatedText("시무룩... 다시 한번 생각해봐.....");
            noButton.setVisibility(View.INVISIBLE);
            yesButton.setVisibility(View.INVISIBLE);
            nextBtn.setVisibility(View.VISIBLE);
        } else if (view.getId() == R.id.yesButton) {
            textView.setAnimatedText("좋아 그럼 가보쟈으으으아!");
            noButton.setVisibility(View.INVISIBLE);
            yesButton.setVisibility(View.INVISIBLE);
            nextBtn.setVisibility(View.VISIBLE);
            section = 1;
        } else if ((view.getId() == R.id.nextButton) && (section == 1)) {
            Intent intent = new Intent(this, SecondPictureUpload.class);
            startActivity(intent);
        }
    }
}
