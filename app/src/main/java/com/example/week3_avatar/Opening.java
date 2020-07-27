package com.example.week3_avatar;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.antonionicolaspina.revealtextview.RevealTextView;

import java.util.Objects;

public class Opening extends AppCompatActivity implements View.OnClickListener {
    RevealTextView textView;
    Button leftButton, rightButton, nextBUtton;
    Integer section = 0;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opening);

        textView = findViewById(R.id.speechBubble);
        rightButton = findViewById(R.id.rightButton);
        leftButton = findViewById(R.id.leftButton);
        nextBUtton = findViewById(R.id.nextButton);

        textView.setAnimatedText("안녕? 나는 패션 요정이야 반가워! 너의 간절한 목소리를 듣고 나타났어");
        rightButton.setVisibility(View.VISIBLE);

        rightButton.setOnClickListener(this);
        leftButton.setOnClickListener(this);
        nextBUtton.setOnClickListener(this);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View view) {
        if ((view.getId() == R.id.rightButton) && (section == 0)) {
            textView.setAnimatedText("평소에 외모 때문에 고문이 많았지? 너에 대해서 이야기 해주면 내가 완벽하게 꾸며줄게!");
            leftButton.setText("괜찮아!");
            rightButton.setText("그럼 내 고민을 해결해줘!");
            leftButton.setVisibility(View.VISIBLE);
            section = 1;
        } else if ((view.getId() == R.id.rightButton) && (section == 1)) {
            leftButton.setVisibility(View.INVISIBLE);
            textView.setAnimatedText("좋아 그럼 가보자!");
            rightButton.setVisibility(View.GONE);
            nextBUtton.setVisibility(View.VISIBLE);
            section = 2;
        } else if ((view.getId() == R.id.leftButton) && (section == 1)) {
            textView.setAnimatedText("시무룩... 다시 한번 생각해봐 ...");
            leftButton.setText("");
            rightButton.setText("그럼 내 고민을 해결해줘!");
            leftButton.setVisibility(View.INVISIBLE);
        } else if ((view.getId() == R.id.nextButton) && (section == 2)) {
            Intent i = getIntent();
            final String id = Objects.requireNonNull(i.getExtras()).getString("id");

            Intent intent = new Intent(this, SecondPictureUpload.class);
            intent.putExtra("id", id);
            System.out.println("id is"+id);
            startActivity(intent);
        }
    }
}
