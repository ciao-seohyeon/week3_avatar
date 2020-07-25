package com.example.week3_avatar;

import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.android.gms.vision.face.Landmark;

public class ImageProcessActivity extends AppCompatActivity {

    ImageView imageView;
    Button button;

    Bitmap glasses;
    Bitmap hairband;
    Canvas canvas;
    Bitmap myBitmap;
    Bitmap tempBitmap;


    Paint rectPaint = new Paint();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imageprocess);
        /////////// get Bundle from other activity ////////////
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("myBundle");
        Toast.makeText(getApplicationContext(), bundle.getString("name"), Toast.LENGTH_LONG).show();

        /////////////////////// start ////////////////////////
        imageView = (ImageView) findViewById(R.id.imageView);
        button = (Button) findViewById(R.id.button);

        final Bitmap myBitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.jung);

        final Bitmap tempBitmap = Bitmap.createBitmap(myBitmap.getWidth(), myBitmap.getHeight(), Bitmap.Config.RGB_565);

        canvas = new Canvas(tempBitmap);
        canvas.drawBitmap(myBitmap,0,0,null);

        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                FaceDetector faceDetector = new FaceDetector.Builder(getApplicationContext())
                        .setTrackingEnabled(false)
                        .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                        .setMode(FaceDetector.FAST_MODE)
                        .build();
                if (!faceDetector.isOperational()) {
                    Toast.makeText(ImageProcessActivity.this,"Face detector could not be set up on your device", Toast.LENGTH_SHORT).show();
                    return;
                }

                Frame frame = new Frame.Builder().setBitmap(myBitmap).build();
                SparseArray<Face> sparseArray = faceDetector.detect(frame);
                int eyeLeft_x = 0;
                int eyeLeft_y = 0;
                int eyeRight_x = 0;
                int eyeRight_y = 0;
                int earLeft = 0;
                int earRight = 0;
                int nose_x = 0;
                int nose_y = 0;
                int mouthLeft_x = 0;
                int mouthLeft_y = 0;
                int mouthRight_x = 0;
                int mouthRight_y = 0;

                for (int i=0; i<sparseArray.size(); i++) {
                    com.google.android.gms.vision.face.Face face = sparseArray.valueAt(i);
                    for(Landmark landmark:face.getLandmarks()) {
                        int cx = (int) (landmark.getPosition().x);
                        int cy = (int)(landmark.getPosition().y);

                        if (landmark.getType() == Landmark.LEFT_EYE){
                            eyeLeft_x = cx;
                            eyeLeft_y = cy;
                        }
                        if (landmark.getType() == Landmark.RIGHT_EYE){
                            eyeRight_x = cx;
                            eyeRight_y = cy;
                        }
                        if (landmark.getType() == Landmark.NOSE_BASE) {
                            nose_x = cx;
                            nose_y = cy;
                        }
                        if (landmark.getType() == Landmark.RIGHT_EAR) {
                            earRight = cx;
                        }
                        if (landmark.getType() == Landmark.LEFT_EAR) {
                            earLeft = cx;
                        }
                        if (landmark.getType() == Landmark.LEFT_MOUTH) {
                            mouthLeft_x = cx;
                            mouthLeft_y = cy;
                        }
                        if (landmark.getType() == Landmark.RIGHT_MOUTH) {
                            mouthRight_x = cx;
                            mouthRight_y = cy;
                        }
                    }
                }

                int face_width = (int) (earRight-earLeft);

                ///////////////////////////////////////////////////////////////
                //for hair_man
                final Bitmap hairman_raw = BitmapFactory.decodeResource(getResources(), R.drawable.hair_man);
                Bitmap hair_man = resizeBitmap((int) (face_width*1.35), hairman_raw);
                //canvas.drawBitmap(hair_man, (float)(earLeft-face_width*0.2), (float) (nose_y-face_width*1.1), null);

                //for hair_short_girl
                final Bitmap hairgirl_raw = BitmapFactory.decodeResource(getResources(), R.drawable.hair_short_girl);
                Bitmap hair_girl = resizeBitmap((int) (face_width*1.5), hairgirl_raw);
                canvas.drawBitmap(hair_girl, (float)(earLeft-face_width*0.2), (float) (nose_y-face_width*1.2), null);

                ////////////////////////////////////////////////////////////////////


                //////////////////////////////////////////////////////////////////////
                //for hairband
                final Bitmap hairBand_raw = BitmapFactory.decodeResource(getResources(), R.drawable.hairband);
                hairband = resizeBitmap((int) (face_width*1.1), hairBand_raw);
                //canvas.drawBitmap(hairband, (float)(eyeLeft_x-face_width*0.45), (float) (nose_y-face_width), null);

                //for hairband2
                final Bitmap hairBand_raw2 = BitmapFactory.decodeResource(getResources(), R.drawable.hairband2);
                Bitmap hairband2 = resizeBitmap((int) (face_width*1.2), hairBand_raw2);
                canvas.drawBitmap(hairband2, (float)(eyeLeft_x-face_width*0.3), (float) (nose_y-face_width), null);

                //for ribbon
                final Bitmap ribbon_raw = BitmapFactory.decodeResource(getResources(), R.drawable.ribbon);
                Bitmap ribbon = resizeBitmap((int) (face_width*0.5), ribbon_raw);
                //canvas.drawBitmap(ribbon, (float)(eyeLeft_x-face_width*0.45), (float) (nose_y-face_width), null);
                ///////////////////////////////////////////////////////////////////////////

                //for eyelash

                //////////////////////////////////////////////////////////////////////////////
                //for red_nose
                final Bitmap red_nose_raw = BitmapFactory.decodeResource(getResources(), R.drawable.red_nose);
                Bitmap red_nose = resizeBitmap((int) ((eyeRight_x-eyeLeft_x)*0.75), red_nose_raw);
                //canvas.drawBitmap(red_nose, (float)(nose_x-red_nose.getWidth()*0.4), (float) (nose_y - red_nose.getHeight()*0.75), null);

                //for blue_nose
                final Bitmap blue_nose_raw = BitmapFactory.decodeResource(getResources(), R.drawable.blue_nose);
                Bitmap blue_nose = resizeBitmap((int) ((eyeRight_x-eyeLeft_x)*0.75), blue_nose_raw);
                //canvas.drawBitmap(blue_nose, (float)(nose_x-blue_nose.getWidth()*0.4), (float) (nose_y - blue_nose.getHeight()*0.75), null);

                //for green_nose
                final Bitmap green_nose_raw = BitmapFactory.decodeResource(getResources(), R.drawable.green_nose);
                Bitmap green_nose = resizeBitmap((int) ((eyeRight_x-eyeLeft_x)*0.75), green_nose_raw);
                canvas.drawBitmap(green_nose, (float)(nose_x-green_nose.getWidth()*0.4), (float) (nose_y - green_nose.getHeight()*0.75), null);

                //for purple_nose
                final Bitmap purple_nose_raw = BitmapFactory.decodeResource(getResources(), R.drawable.purple_nose);
                Bitmap purple_nose = resizeBitmap((int) ((eyeRight_x-eyeLeft_x)*0.75), purple_nose_raw);
                //canvas.drawBitmap(purple_nose, (float)(nose_x-red_nose.getWidth()*0.4), (float) (nose_y - red_nose.getHeight()*0.75), null);
                ////////////////////////////////////////////////////////////////////////////

                /////////////////////////////////////////////////////////////////////////////
                //for cheek
                final Bitmap cheek_raw = BitmapFactory.decodeResource(getResources(), R.drawable.cheek);
                Bitmap cheek = resizeBitmap(face_width, cheek_raw);
                //canvas.drawBitmap(cheek, (float)(nose_x - cheek.getWidth()*0.45), (float) (nose_y - cheek.getHeight()*0.2), null);

                //for cheek
                final Bitmap cheek_raw2 = BitmapFactory.decodeResource(getResources(), R.drawable.cheek2);
                Bitmap cheek2 = resizeBitmap((int)(face_width*1.3), cheek_raw2);
                canvas.drawBitmap(cheek2, (float)(nose_x - cheek2.getWidth()*0.5), (float) (nose_y - cheek2.getHeight()*0.5), null);

                //////////////////////////////////////////////////////////////////////////////

                ////////////////////////////////////////////////////////////////////////////////
                //for mouth
                int mouth_size = (int) ((mouthRight_x-mouthLeft_x)*0.9);
                final Bitmap lip_raw = BitmapFactory.decodeResource(getResources(), R.drawable.lip);
                Bitmap lip = resizeBitmap((int) (mouth_size * 1.2), lip_raw);
                //canvas.drawBitmap(lip, (float)(mouthLeft_x), (float) (mouthLeft_y-lip.getHeight()*0.35), null);

                //mouth2
                final Bitmap lip_raw2 = BitmapFactory.decodeResource(getResources(), R.drawable.lip2);
                Bitmap lip2 = resizeBitmap((int) (mouth_size * 1.2), lip_raw2);
                //canvas.drawBitmap(lip2, (float)(mouthLeft_x), (float) (mouthLeft_y-lip2.getHeight()*0.3), null);

                //mouth3
                final Bitmap lip_raw3 = BitmapFactory.decodeResource(getResources(), R.drawable.lip3);
                Bitmap lip3 = resizeBitmap((int) (mouth_size * 1.2), lip_raw3);
                canvas.drawBitmap(lip3, (float)(mouthLeft_x), (float) (mouthLeft_y-lip3.getHeight()*0.35), null);
                ////////////////////////////////////////////////////////////////////////////////


                ////////////////////////////////////////////////////////////////////////////////
                //for glass
                final Bitmap glasses_raw = BitmapFactory.decodeResource(getResources(), R.drawable.glasses2);
                glasses = resizeBitmap((int) (face_width), glasses_raw);
                //canvas.drawBitmap(glasses, (float)(eyeLeft_x -(eyeRight_x-eyeLeft_x)/2) , (float)((eyeRight_y+eyeLeft_y)/2 - glasses.getHeight()*0.3) , null);

                //for pink_glass
                final Bitmap pink_glasses_raw = BitmapFactory.decodeResource(getResources(), R.drawable.pink_glasses);
                Bitmap glasses_pink = resizeBitmap((int) (face_width), pink_glasses_raw);
                //canvas.drawBitmap(glasses_pink, (float)(eyeLeft_x -(eyeRight_x-eyeLeft_x)/2) , (float)((eyeRight_y+eyeLeft_y)/2 - glasses_pink.getHeight()*0.5) , null);

                //for black_glass
                final Bitmap black_glasses_raw = BitmapFactory.decodeResource(getResources(), R.drawable.black_glasses);
                Bitmap black_glasses = resizeBitmap((int) (face_width), black_glasses_raw);
                canvas.drawBitmap(black_glasses, (float)(eyeLeft_x -(eyeRight_x-eyeLeft_x)/2) , (float)((eyeRight_y+eyeLeft_y)/2 - black_glasses.getHeight()*0.5) , null);
                ////////////////////////////////////////////////////////////////////////////////

                //print
                imageView.setImageBitmap(tempBitmap);

            }


        });
    }

    private Bitmap resizeBitmap(int width, Bitmap bitmap) {
        float bmpWidth = bitmap.getWidth();
        float bmpHeight = bitmap.getHeight();
        float mWidth = bmpWidth/100;
        float scale = width/mWidth;
        bmpWidth *= (scale/100);
        bmpHeight *= (scale/100);

        return Bitmap.createScaledBitmap(bitmap, (int) bmpWidth, (int) bmpHeight, true);
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
            int scaleWidth = hairband.getScaledWidth(canvas);
            int scaleHeight = hairband.getScaledHeight(canvas);
            System.out.println(scaleHeight);
            canvas.drawBitmap(hairband, (float) (cx - (scaleWidth * 0.5)), cy - (scaleHeight * 2), null);
            // draw glasses
            canvas.drawBitmap(glasses, cx - 500, cy - (scaleHeight) + 120, null);
            imageView.setImageBitmap(tempBitmap);
        }
    }
}
