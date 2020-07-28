package com.example.week3_avatar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.week3_avatar.Retrofit.IMyService;
import com.example.week3_avatar.Retrofit.RetrofitClient;
import com.example.week3_avatar.Retrofit.myFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.os.Looper.getMainLooper;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class Gallery extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayoutManager linearLayoutManager;
    public static ArrayList<ImageInfo> mImages;
    public static ArrayList<ImageInfo> mImages_search;
    private ImageAdapter galleryRecyclerAdapter;
    private List<Integer> count;
    private int i = 0;
    private Context myContext ;
    private Bitmap storeImage;


    IMyService retrofitClient;
    String id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery);


        Intent intent = getIntent();
        final String id = Objects.requireNonNull(intent.getExtras()).getString("id");
        retrofitClient = RetrofitClient.getApiService();

        recyclerView = findViewById(R.id.recyclerView);
        myContext = getApplicationContext();
        mImages = new ArrayList<>();
        mImages_search = new ArrayList<>();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ArrayList<myFile> loginUser_res = retrofitClient.getAllFile().execute().body();
                    for (myFile elt : loginUser_res){
                        if (elt.getId().equals(id)) {
                            mImages.add(new ImageInfo(elt.getSaveFileName(), elt.getTitle()));
                            mImages_search.add(new ImageInfo(elt.getSaveFileName(), elt.getTitle()));
                        }
                    }

                    final EditText editSearch = findViewById(R.id.editSearch);

                    new Handler(getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            galleryRecyclerAdapter = new ImageAdapter(myContext, mImages, id);
                            recyclerView.setAdapter(galleryRecyclerAdapter);

                            linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(linearLayoutManager);

                            editSearch.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                }

                                @Override
                                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                }

                                @Override
                                public void afterTextChanged(Editable editable) {
                                    String text = editSearch.getText().toString();
                                    search(text);
                                }
                            });
                        }
                    });


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        //mImages = getImagesFromStorage();
        //mImages_search = getImagesFromStorage();

        ////////////////////////////////////// 검색 //////////////////////////////////////////////

    }

    private ArrayList<ImageInfo> getImagesFromStorage() {
        final ArrayList<ImageInfo> res = new ArrayList<>();
        /////////////////////////////// db에서 데이터 받아오기 /////////////////////////////////////
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ArrayList<myFile> loginUser_res = retrofitClient.getAllFile().execute().body();
                    System.out.println("size is " +loginUser_res.size());
                    for (myFile elt : loginUser_res){
                        if (elt.getId() == id) {
                            res.add(new ImageInfo(elt.getSaveFileName(), elt.getTitle()));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return res;
    }

    private void setImagesFromStorage() {
        final ArrayList<ImageInfo> res = new ArrayList<>();
        /////////////////////////////// db에서 데이터 받아오기 /////////////////////////////////////
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ArrayList<myFile> loginUser_res = retrofitClient.getAllFile().execute().body();
                    for (myFile elt : loginUser_res){
                        if (elt.getId() == id) {
                            res.add(new ImageInfo(elt.getSaveFileName(), elt.getTitle()));
                        }
                    }
                    new Handler(getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            mImages.clear();
                            mImages.addAll(res);
                            mImages_search = res;
                            galleryRecyclerAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void search(String charText) {
        mImages.clear();
        if (charText.length() == 0) {
            mImages.addAll(mImages_search);
        } else {
            // 리스트의 모든 데이터를 검색한다.
            for (int i = 0; i < mImages_search.size(); i++) {
                if (mImages_search.get(i).getImageTitle().toLowerCase().contains(charText.toLowerCase())) {
                    mImages.add(mImages_search.get(i));
                }
            }
        }
        galleryRecyclerAdapter.notifyDataSetChanged();
    }




    }
