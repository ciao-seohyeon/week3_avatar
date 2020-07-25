package com.example.week3_avatar;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.android.gms.vision.face.Landmark;

import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    Button button;

    Bitmap glasses;
    Bitmap hearBand;
    Canvas canvas;

    Paint rectPaint = new Paint();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageView);
        button = (Button) findViewById(R.id.button);

        final Bitmap myBitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.trump);
        imageView.setImageBitmap(myBitmap);

        glasses = BitmapFactory.decodeResource(getResources(), R.drawable.glasses);
        hearBand = BitmapFactory.decodeResource(getResources(), R.drawable.cat);

        Bitmap tempBitmap = Bitmap.createBitmap(myBitmap.getWidth(), myBitmap.getHeight(), Bitmap.Config.RGB_565);
        canvas = new Canvas(tempBitmap);
        canvas.drawBitmap(myBitmap, 0,0,null);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                FaceDetector faceDetector = new FaceDetector.Builder(getApplicationContext())
                        .setTrackingEnabled(false)
                        .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                        .setMode(FaceDetector.FAST_MODE)
                        .build();
                if (!faceDetector.isOperational()) {

                    Toast.makeText(MainActivity.this,"Face detector could not be set up on your device", Toast.LENGTH_SHORT).show();
                    return;
                }
                Frame frame = new Frame.Builder().setBitmap(myBitmap).build();
                SparseArray<Face> sparseArray = faceDetector.detect(frame);
                for (int i=0; i<sparseArray.size(); i++) {
                    com.google.android.gms.vision.face.Face face = sparseArray.valueAt(i);
                    detectLandmasks(face);
                }

            }
        });
    }

    private void detectLandmasks(Face face) {
        for(Landmark landmark:face.getLandmarks()) {
            int cx = (int) (landmark.getPosition().x);
            int cy = (int)(landmark.getPosition().y);

            drawOnImageView(landmark.getType(),cx,cy);
        }
    }

    private void drawOnImageView(int type, int cx, int cy) {
        // Draw hearband
        if (type == Landmark.NOSE_BASE) {
            int scaleWidth = hearBand.getScaledWidth(canvas);
            int scaleHeight = hearBand.getScaledHeight(canvas);
            canvas.drawBitmap(hearBand, cx-(scaleWidth/2), cy-(scaleHeight*2), null);

            // draw glasses
            canvas.drawBitmap(glasses, cx-500, cy-(scaleHeight)+120, null);
        }
    }
}