package com.example.week3_avatar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.antonionicolaspina.revealtextview.RevealTextView;

public class SecondPictureUpload extends AppCompatActivity implements View.OnClickListener{
    RevealTextView textView;
    Button leftButton, middleButton, rightButton;
    Integer section = 0;

    final Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondpictureupload);
        /////////////////////// bundle example //////////////////////
//        bundle.putString("name", "hey");
//        bundle.putInt("hair", 0);

        textView = findViewById(R.id.speechBubble);
        leftButton = findViewById(R.id.leftButton);
        middleButton = findViewById(R.id.middleButton);
        rightButton = findViewById(R.id.rightButton);


        textView.setAnimatedText("너의 머리 스타일에 대해 어떻게 생각해?");

        leftButton.setOnClickListener(this);
        middleButton.setOnClickListener(this);
        rightButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if ((view.getId() == R.id.leftButton) && (section == 0)) {
            textView.setAnimatedText("정말 그렇게 생각해? 다시 한번 기회를 줄게");
            leftButton.setVisibility(View.INVISIBLE);
            section = 1;
        } else if (((view.getId() == R.id.rightButton) && (section == 1)) || ((view.getId() == R.id.rightButton) && (section == 0))) {
            leftButton.setVisibility(View.VISIBLE);
            textView.setAnimatedText("그렇다면 어떻게 바꿔줄까?");
            leftButton.setText("깔끔한 스타일로!");
            rightButton.setText("화려한 스타일로!");
            section = 2;
        } else if ((section == 2) && (view.getId() == R.id.leftButton)) {
            textView.setAnimatedText("좋아! 머리에 포인트를 주면 좋을거 같지 않아?");
            // Bundle 에 저장
            bundle.putInt("헤어스타일", 0);
            leftButton.setText("난 만족해!");
            rightButton.setText("좋은 생각이야!");
            section = 3;
        } else if ((section == 2) && (view.getId() == R.id.rightButton)) {
            // Bundle 에 저장
            bundle.putInt("헤어스타일", 1);
            textView.setAnimatedText("좋아! 머리에 포인트를 주면 좋을거 같지 않아?");
            leftButton.setText("난 만족해!");
            rightButton.setText("좋은 생각이야!");
            section = 3;
        }  else if ((section == 3) && (view.getId() == R.id.leftButton)) {
            // Bundle 에 저장
            middleButton.setVisibility(View.VISIBLE);
            textView.setAnimatedText("무슨 포인트를 줄까?");
            leftButton.setText("화려하게!");
            middleButton.setText("단정하게!");
            rightButton.setText("사랑스럽게!");
            section = 4;
        } else if ((section == 3) && (view.getId() == R.id.rightButton)) {
            // Bundle 에 저장
            middleButton.setVisibility(View.VISIBLE);

            textView.setAnimatedText("무슨 포인트를 줄까?");
            leftButton.setText("화려하게!");
            middleButton.setText("단정하게!");
            rightButton.setText("사랑스럽게!");
            section = 4;
        } else if ((section == 4) && (view.getId() == R.id.leftButton)) {
            // Bundle 에 저장
            bundle.putInt("헤어포인트", 0);

            textView.setAnimatedText("평소에 수줍음을 많이 타는 편이니?");
            leftButton.setText("아니?");
            middleButton.setText("조금!");
            rightButton.setText("많이...");
            section = 5;
        } else if ((section == 4) && (view.getId() == R.id.rightButton)) {
            // Bundle 에 저장
            bundle.putInt("헤어포인트", 1);

            textView.setAnimatedText("평소에 수줍음을 많이 타는 편이니?");
            leftButton.setText("아니?");
            middleButton.setText("조금!");
            rightButton.setText("많이...");
            section = 5;
        } else if ((section == 4) && (view.getId() == R.id.middleButton)) {
            // Bundle 에 저장
            bundle.putInt("헤어포인트", 2);

            textView.setAnimatedText("평소에 수줍음을 많이 타는 편이니?");
            leftButton.setText("아니?");
            middleButton.setText("조금!");
            rightButton.setText("많이...");
            section = 5;
        } else if ((section == 5) && (view.getId() == R.id.leftButton)) {
            //Bundle 에 저장
            bundle.putInt("수줍음", 0);

            textView.setAnimatedText("멋져! 평소 연애 스타일이 어때?");
            leftButton.setText("적극적이야");
            middleButton.setText("소극적이야");
            rightButton.setText("그게 뭐야...?");
            section = 6;
        } else if ((section == 5) && (view.getId() == R.id.middleButton)) {
            //Bundle 에 저장
            bundle.putInt("수줍음", 1);

            textView.setAnimatedText("멋져! 평소 연애 스타일이 어때?");
            leftButton.setText("적극적이야");
            middleButton.setText("소극적이야");
            rightButton.setText("그게 뭐야...?");
            section = 6;
        } else if ((section == 5) && (view.getId() == R.id.rightButton)) {
            //Bundle 에 저장
            bundle.putInt("수줍음", 2);

            textView.setAnimatedText("멋져! 평소 연애 스타일이 어때?");
            leftButton.setText("적극적이야");
            middleButton.setText("소극적이야");
            rightButton.setText("그게 뭐야...?");
            section = 6;
        } else if ((section == 6) && (view.getId() == R.id.leftButton)) {
            //Bundle 에 저장
            bundle.putInt("연애스타일", 0);

            textView.setAnimatedText("최고야! 어떤 스타일의 사람이 되고 싶어?");
            leftButton.setText("지적인 사람");
            middleButton.setText("힙한 사람");
            rightButton.setText("눈길을 끄는 사람");
            section = 7;
        } else if ((section == 6) && (view.getId() == R.id.middleButton)) {
            //Bundle 에 저장
            bundle.putInt("연애스타일", 1);

            textView.setAnimatedText("최고야! 어떤 스타일의 사람이 되고 싶어?");
            leftButton.setText("지적인 사람");
            middleButton.setText("힙한 사람");
            rightButton.setText("눈길을 끄는 사람");
            section = 7;
        } else if ((section == 6) && (view.getId() == R.id.rightButton)) {
            //Bundle 에 저장
            bundle.putInt("연애스타일", 2);

            textView.setAnimatedText("최고야! 어떤 스타일의 사람이 되고 싶어?");
            leftButton.setText("지적인 사람");
            middleButton.setText("힙한 사람");
            rightButton.setText("눈길을 끄는 사람");
            section = 7;
        } else if ((section == 7) && (view.getId() == R.id.leftButton)) {
            //Bundle 에 저장
            bundle.putInt("스타일", 0);

            textView.setAnimatedText("마지막으로 좋아하는 색이 뭐야?");
            leftButton.setText("파란색");
            middleButton.setText("빨간색");
            rightButton.setText("초록색");
            section = 8;
        } else if ((section == 7) && (view.getId() == R.id.middleButton)) {
            //Bundle 에 저장
            bundle.putInt("스타일", 1);

            textView.setAnimatedText("마지막으로 좋아하는 색이 뭐야?");
            leftButton.setText("파란색");
            middleButton.setText("빨간색");
            rightButton.setText("초록색");
            section = 8;
        } else if ((section == 7) && (view.getId() == R.id.rightButton)) {
            //Bundle 에 저장
            bundle.putInt("스타일", 2);

            textView.setAnimatedText("마지막으로 좋아하는 색이 뭐야?");
            leftButton.setText("파란색");
            middleButton.setText("빨간색");
            rightButton.setText("초록색");
            section = 8;
        } else if ((section == 8) && (view.getId() == R.id.leftButton)) {
            //Bundle 에 저장
            bundle.putInt("색", 0);

            leftButton.setVisibility(View.INVISIBLE);
            middleButton.setVisibility(View.INVISIBLE);
            rightButton.setText("");
            textView.setAnimatedText("완벽해! 내가 너의 스타일을 바꾸어 줬어. 집에 가서 거울을 한번 봐 봐!");
            section = 9;
        } else if ((section == 8) && (view.getId() == R.id.middleButton)) {
            //Bundle 에 저장
            bundle.putInt("색", 1);

            leftButton.setVisibility(View.INVISIBLE);
            middleButton.setVisibility(View.INVISIBLE);
            rightButton.setText("");
            textView.setAnimatedText("완벽해! 내가 너의 스타일을 바꾸어 줬어. 집에 가서 거울을 한번 봐 봐!");
            section = 9;

        } else if ((section == 8) && (view.getId() == R.id.rightButton)) {
            //Bundle 에 저장
            bundle.putInt("색", 2);

            leftButton.setVisibility(View.INVISIBLE);
            middleButton.setVisibility(View.INVISIBLE);
            rightButton.setText("");
            textView.setAnimatedText("완벽해! 내가 너의 스타일을 바꾸어 줬어. 집에 가서 거울을 한번 봐 봐!");
            section = 9;

        } else if ((section == 9) && (view.getId() == R.id.rightButton)) {
//            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>> 내가 선택한 색은? >>>>>>>>>>>>" + bundle.getInt("색"));
            Intent intent = new Intent(this, ImageProcessActivity.class);
            intent.putExtra("myBundle", bundle);
            startActivity(intent);
        }
    }

}
