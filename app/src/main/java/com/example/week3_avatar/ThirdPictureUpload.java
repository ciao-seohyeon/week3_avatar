package com.example.week3_avatar;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.antonionicolaspina.revealtextview.RevealTextView;

public class ThirdPictureUpload extends AppCompatActivity {
    Button rightButton;
    RevealTextView textView;
    Integer section = 0;

    ImageView userImage;
    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thirdpictureupload);
        Toast.makeText(getApplicationContext(), "to third page", Toast.LENGTH_LONG).show();
        // upload image
        userImage = findViewById(R.id.userImage);


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
                    // handle upload button
                    // check runtime permission
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                            // permission not granted, request it
                            String [] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                            // show popup for runtime permission
                            requestPermissions(permissions, PERMISSION_CODE);
                        } else {
                            // permission already granted
                            pickImageFromGallery();
                        }
                    } else {
                        // system os is less then marshmallow
                        pickImageFromGallery();
                    }

                    // to next activity
//                    Intent intent = new Intent(getApplicationContext(), ImageProcessActivity.class);
//                    intent.putExtra("myBundle", bundle);
//                    startActivity(intent);
                }
            }
        });

    }

    private void pickImageFromGallery() {
        // intent to pick image
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)  {
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                    pickImageFromGallery();
                } else {
                    // permission was denied
                    Toast.makeText(this, "Permission denied...!" ,Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            // set image to image view
            userImage.setImageURI(data.getData());
        }
    }
}


