package com.example.week3_avatar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.week3_avatar.Retrofit.IMyService;
import com.example.week3_avatar.Retrofit.RetrofitClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static android.os.Looper.getMainLooper;

public class ImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // TYPE 정의용 상수들
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;

    private ArrayList<ImageInfo> mData;
    private Context mContext;
    private String id;

    final IMyService retrofitClient = RetrofitClient.getApiService();

    public ImageAdapter(Context context, ArrayList<ImageInfo> mData, String id) {
        this.mContext = context;
        this.mData = mData;
        this.id = id;
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        //  ItemView에 들어갈 list의 개수 + Footer 1개
        return mData.size();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //  2번째 인자로 넘어온 viewType은 getItemViewType에서 return된 viewType이다
        //  viewType에 따라 사용할 ViewHolder를 return
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.recyclerview_item, parent, false) ;
        RecyclerView.ViewHolder vh = new CustomViewHolder(view) ;

        return vh ;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            if (mContext == null) {
                System.out.println("context is null >>>>>>>>>>>");
            }

            System.out.println("position is"+position);
            CustomViewHolder customViewHolder = (CustomViewHolder) holder;

            final String image = mData.get(position).getImage();
            final String title = mData.get(position).getImageTitle();

            Uri printUri = Uri.parse("http://192.249.19.242:7480/api/files/download/" + image);

            Glide.with(mContext).load(printUri).into(customViewHolder.image);
            customViewHolder.title.setText(title + "");

    }

    // Item의 ViewHolder
    public class CustomViewHolder extends RecyclerView.ViewHolder {
        // RecylerView 항목 하나를 갖고 있는 ViewHolder
        ImageView image;
        TextView title;

        public CustomViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.cafe_image);
            title = itemView.findViewById(R.id.cafe_name);

        }

    }
}
