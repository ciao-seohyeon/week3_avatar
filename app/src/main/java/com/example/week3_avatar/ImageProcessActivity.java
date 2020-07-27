package com.example.week3_avatar;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.week3_avatar.Retrofit.IMyService;
import com.example.week3_avatar.Retrofit.RetrofitClient;
import com.example.week3_avatar.Retrofit.User;
import com.example.week3_avatar.Retrofit.UserPhoto;
import com.example.week3_avatar.Retrofit.myFile;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.android.gms.vision.face.Landmark;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

public class ImageProcessActivity extends AppCompatActivity {
    ImageView imageView;

    Bitmap glasses;
    Bitmap hairband;
    Canvas canvas;
    Bitmap myBitmap;
    Bitmap tempBitmap;

    Uri imageUri;

    final IMyService retrofitClient = RetrofitClient.getApiService();

    Paint rectPaint = new Paint();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imageprocess);
        /////////// get Bundle from other activity ////////////
        Intent intent = getIntent();
        final Bundle bundle = intent.getBundleExtra("myBundle");
        final String id_st = Objects.requireNonNull(intent.getExtras()).getString("id");

        System.out.println("id is "+id_st);
        imageUri = Uri.parse(bundle.getString("uri"));
        Bitmap myBitmap = null;
        try {
            myBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /////////////////////// start ////////////////////////
        imageView = (ImageView) findViewById(R.id.userImage);

//        final Bitmap myBitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.jung);

        final Bitmap tempBitmap = Bitmap.createBitmap(myBitmap.getWidth(), myBitmap.getHeight(), Bitmap.Config.RGB_565);

        canvas = new Canvas(tempBitmap);
        canvas.drawBitmap(myBitmap,0,0,null);

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
                if (bundle.getInt("헤어스타일") == 0) {
                    //for hair_man
                    final Bitmap hairgirl_raw = BitmapFactory.decodeResource(getResources(), R.drawable.hair_short_girl);
                    Bitmap hair_girl = resizeBitmap((int) (face_width*1.5), hairgirl_raw);
                    canvas.drawBitmap(hair_girl, (float)(earLeft-face_width*0.2), (float) (nose_y-face_width*1.2), null);
                } else if (bundle.getInt("헤어스타일") == 1) {
                    //for hair_short_girl
                    final Bitmap hairman_raw = BitmapFactory.decodeResource(getResources(), R.drawable.hair_man);
                    Bitmap hair_man = resizeBitmap((int) (face_width*1.35), hairman_raw);
                    canvas.drawBitmap(hair_man, (float)(earLeft-face_width*0.2), (float) (nose_y-face_width*1.1), null);
                }
                ////////////////////////////////////////////////////////////////////


                //////////////////////////////////////////////////////////////////////
                if (bundle.getInt("헤어포인트") == 0) {
                    //for hairband
                    final Bitmap hairBand_raw = BitmapFactory.decodeResource(getResources(), R.drawable.hairband);
                    hairband = resizeBitmap((int) (face_width*1.1), hairBand_raw);
                    canvas.drawBitmap(hairband, (float)(eyeLeft_x-face_width*0.45), (float) (nose_y-face_width), null);
                } else if (bundle.getInt("헤어포인트") == 1) {
                    //for hairband2
                    final Bitmap hairBand_raw2 = BitmapFactory.decodeResource(getResources(), R.drawable.hairband2);
                    Bitmap hairband2 = resizeBitmap((int) (face_width*1.2), hairBand_raw2);
                    canvas.drawBitmap(hairband2, (float)(eyeLeft_x-face_width*0.3), (float) (nose_y-face_width), null);
                } else if (bundle.getInt("헤어포인트") == 2) {
                    //for ribbon
                    final Bitmap ribbon_raw = BitmapFactory.decodeResource(getResources(), R.drawable.ribbon);
                    Bitmap ribbon = resizeBitmap((int) (face_width*0.5), ribbon_raw);
                    canvas.drawBitmap(ribbon, (float)(eyeLeft_x-face_width*0.45), (float) (nose_y-face_width), null);
                }
                ///////////////////////////////////////////////////////////////////////////

                //////////////////////////////////////////////////////////////////////////////
                if (bundle.getInt("색") == 0) {
                    //for blue_nose
                    final Bitmap blue_nose_raw = BitmapFactory.decodeResource(getResources(), R.drawable.blue_nose);
                    Bitmap blue_nose = resizeBitmap((int) ((eyeRight_x-eyeLeft_x)*0.75), blue_nose_raw);
                    canvas.drawBitmap(blue_nose, (float)(nose_x-blue_nose.getWidth()*0.4), (float) (nose_y - blue_nose.getHeight()*0.75), null);
                } else if (bundle.getInt("색") == 1 ) {
                    //for red_nose
                    final Bitmap red_nose_raw = BitmapFactory.decodeResource(getResources(), R.drawable.red_nose);
                    Bitmap red_nose = resizeBitmap((int) ((eyeRight_x-eyeLeft_x)*0.75), red_nose_raw);
                    canvas.drawBitmap(red_nose, (float)(nose_x-red_nose.getWidth()*0.4), (float) (nose_y - red_nose.getHeight()*0.75), null);
                } else if (bundle.getInt("색") == 2) {
                    //for green_nose
                    final Bitmap green_nose_raw = BitmapFactory.decodeResource(getResources(), R.drawable.green_nose);
                    Bitmap green_nose = resizeBitmap((int) ((eyeRight_x-eyeLeft_x)*0.75), green_nose_raw);
                    canvas.drawBitmap(green_nose, (float)(nose_x-green_nose.getWidth()*0.4), (float) (nose_y - green_nose.getHeight()*0.75), null);
                }
                ////////////////////////////////////////////////////////////////////////////

                /////////////////////////////////////////////////////////////////////////////
                if (bundle.getInt("수줍음") == 0) {

                } else if (bundle.getInt("수줍음") == 1) {
                    //for cheek
                    final Bitmap cheek_raw = BitmapFactory.decodeResource(getResources(), R.drawable.cheek);
                    Bitmap cheek = resizeBitmap(face_width, cheek_raw);
                    canvas.drawBitmap(cheek, (float)(nose_x - cheek.getWidth()*0.45), (float) (nose_y - cheek.getHeight()*0.2), null);
                } else if (bundle.getInt("수줍음") == 2) {
                    //for cheek
                    final Bitmap cheek_raw2 = BitmapFactory.decodeResource(getResources(), R.drawable.cheek2);
                    Bitmap cheek2 = resizeBitmap((int)(face_width*1.3), cheek_raw2);
                    canvas.drawBitmap(cheek2, (float)(nose_x - cheek2.getWidth()*0.5), (float) (nose_y - cheek2.getHeight()*0.5), null);
                }
                //////////////////////////////////////////////////////////////////////////////

                ////////////////////////////////////////////////////////////////////////////////
                int mouth_size = (int) ((mouthRight_x-mouthLeft_x)*0.9);
                if (bundle.getInt("연애스타일") == 0) {
                    //for mouth
                    final Bitmap lip_raw = BitmapFactory.decodeResource(getResources(), R.drawable.lip);
                    Bitmap lip = resizeBitmap((int) (mouth_size * 1.2), lip_raw);
                    canvas.drawBitmap(lip, (float)(mouthLeft_x), (float) (mouthLeft_y-lip.getHeight()*0.35), null);
                } else if (bundle.getInt("연애스타일") == 1) {
                    //mouth2
                    final Bitmap lip_raw2 = BitmapFactory.decodeResource(getResources(), R.drawable.lip2);
                    Bitmap lip2 = resizeBitmap((int) (mouth_size * 1.2), lip_raw2);
                    canvas.drawBitmap(lip2, (float)(mouthLeft_x), (float) (mouthLeft_y-lip2.getHeight()*0.3), null);
                } else if (bundle.getInt("연애스타일") == 2) {
                    //mouth3
                    final Bitmap lip_raw3 = BitmapFactory.decodeResource(getResources(), R.drawable.lip3);
                    Bitmap lip3 = resizeBitmap((int) (mouth_size * 1.2), lip_raw3);
                    canvas.drawBitmap(lip3, (float)(mouthLeft_x), (float) (mouthLeft_y-lip3.getHeight()*0.35), null);
                }
                ////////////////////////////////////////////////////////////////////////////////

                ////////////////////////////////////////////////////////////////////////////////
                if (bundle.getInt("스타일") == 0) {
                    //for glass
                    final Bitmap glasses_raw = BitmapFactory.decodeResource(getResources(), R.drawable.glasses2);
                    glasses = resizeBitmap((int) (face_width), glasses_raw);
                    canvas.drawBitmap(glasses, (float)(eyeLeft_x -(eyeRight_x-eyeLeft_x)/2) , (float)((eyeRight_y+eyeLeft_y)/2 - glasses.getHeight()*0.3) , null);
                } else if (bundle.getInt("스타일") == 2) {
                    //for pink_glass
                    final Bitmap pink_glasses_raw = BitmapFactory.decodeResource(getResources(), R.drawable.pink_glasses);
                    Bitmap glasses_pink = resizeBitmap((int) (face_width), pink_glasses_raw);
                    canvas.drawBitmap(glasses_pink, (float)(eyeLeft_x -(eyeRight_x-eyeLeft_x)/2) , (float)((eyeRight_y+eyeLeft_y)/2 - glasses_pink.getHeight()*0.5) , null);
                } else if (bundle.getInt("스타일") == 1) {
                    //for black_glass
                    final Bitmap black_glasses_raw = BitmapFactory.decodeResource(getResources(), R.drawable.black_glasses);
                    Bitmap black_glasses = resizeBitmap((int) (face_width), black_glasses_raw);
                    canvas.drawBitmap(black_glasses, (float)(eyeLeft_x -(eyeRight_x-eyeLeft_x)/2) , (float)((eyeRight_y+eyeLeft_y)/2 - black_glasses.getHeight()*0.5) , null);
                }
                ////////////////////////////////////////////////////////////////////////////////

                //print
                imageView.setImageBitmap(tempBitmap);
      
        // submit 시 db에 사진 upload //
        Button submit_button =  (Button) findViewById(R.id.button2);
        final EditText titleView = (EditText) findViewById(R.id.name);

        submit_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                final String titleString = titleView.getText().toString();
                final RequestBody title = RequestBody.create(MultipartBody.FORM, titleString);

                ////
                File newFile = new File(getApplicationContext().getCacheDir(), titleString);
                FileOutputStream fileOutputStream = null;
                try {
                    fileOutputStream = new FileOutputStream(newFile);
                    tempBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                    fileOutputStream.close();

                    RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), newFile);
                    final MultipartBody.Part body = MultipartBody.Part.createFormData("imgFile", newFile.getName(), reqFile);

                    new Thread(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void run() {
                            try {

                                System.out.println("the title is "+titleString);
                                RequestBody id = RequestBody.create(MultipartBody.FORM, id_st);

                                retrofitClient.addToPhotoList(id_st, titleString).execute();

                                Response<myFile> response = retrofitClient.uploadFile(body, title, id).execute();
                                //String savedName = response.body().getSaveFileName();
                                //로그인 하면 id 받아서 방금 업로드 한 파일 이름 포토리스트에 추가

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ////
                //create a file to write bitmap data
//                File f = new File(getApplicationContext().getCacheDir(), titleString);
//
//                try {
//                    f.createNewFile();
//                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                    tempBitmap.compress(Bitmap.CompressFormat.JPEG, 0 /*ignored for PNG*/, bos);
//                    byte[] bitmapdata = bos.toByteArray();
//
//                    FileOutputStream fos = null;
//                    try {
//                        fos = new FileOutputStream(f);
//                        fos.write(bitmapdata);
//                        fos.flush();
//                        fos.close();
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), f);
//                    final MultipartBody.Part body = MultipartBody.Part.createFormData("imgFile", f.getName(), reqFile);
//
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                Response<myFile> response = retrofitClient.uploadFile(body, title).execute();
//                                String savedName = response.body().getSaveFileName();
//                                //로그인 하면 id 받아서 방금 업로드 한 파일 이름 포토리스트에 추가
//                                //retrofitClient.addToPhotoList(id,new UserPhoto(savedName)).execute();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }).start();
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                ImageProcessActivity.this.finish();
            }
        });

    }


    private File createFileFromBitmap(Bitmap bitmap) throws IOException {
        File newFile = new File(getFilesDir(), makeImageFileName());
        FileOutputStream fileOutputStream = new FileOutputStream(newFile);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
        fileOutputStream.close();
        return newFile;
    }

    private String makeImageFileName() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_hhmmss");
        Date date = new Date();
        String strDate = simpleDateFormat.format(date);
        return strDate + ".png";
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
