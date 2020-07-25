package com.example.week3_avatar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.antonionicolaspina.revealtextview.RevealTextView;

public class ThirdPictureUpload extends AppCompatActivity {
    Button rightButton;
    RevealTextView textView;
    Integer section = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thirdpictureupload);
        Toast.makeText(getApplicationContext(), "to third page", Toast.LENGTH_LONG).show();
        // get bundle from previous activity
        Intent intent0 = getIntent();
        final Bundle bundle = intent0.getBundleExtra("myBundle");

        rightButton = findViewById(R.id.rightButton);
        textView = findViewById(R.id.speechBubble);

        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((section == 0) && (view.getId() == R.id.rightButton)) {
                    textView.setAnimatedText("당신의 얼굴을 업로드 해주세요\n" + "(이목구비가 정확하게 나와야 합니다.)");
                    // 사진 업로드 후, 사진에 대한 정도 bundle 에 담기
                    section = 1;
                } else if ((section == 1) && (view.getId() == R.id.rightButton)) {
                    // to next activity
                    Intent intent = new Intent(getApplicationContext(), ImageProcessActivity.class);
                    intent.putExtra("myBundle", bundle);
                    startActivity(intent);
                }
            }
        });

    }
}
