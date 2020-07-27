package com.example.week3_avatar;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.week3_avatar.Retrofit.User;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.UserViewHolder> {
    private ArrayList<MyContacts> list;
    private final Context mContext;

    public FriendAdapter(Context context, ArrayList<MyContacts> list) {
        this.mContext = context;
        this.list = list;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_users, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        MyContacts contacts = list.get(position);
        holder.textViewName.setText(contacts.getUser_Name());
        holder.textViewPhone.setText(contacts.getUser_phNumber());

        holder.imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ryu));
        Bitmap profile = loadContactPhoto(mContext.getContentResolver(),contacts.person_id, contacts
                .photo_id);
        if (profile != null) {
            if (Build.VERSION.SDK_INT >= 21) {
                holder.imageView.setBackground(new ShapeDrawable(new OvalShape()));
                holder.imageView.setClipToOutline(true);
            }
            holder.imageView.setImageBitmap(profile);
        } else {
//            viewHolder.profile.setImageDrawable(mContext.getResources().getDrawable(R.drawable.img_profile_thumnail));
            if (Build.VERSION.SDK_INT >= 21) {
                holder.imageView.setClipToOutline(false);
            }
        }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public Bitmap loadContactPhoto(ContentResolver cr, long id, long photo_id) {
        Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id);
        InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(cr, uri);
        if (input != null)
            return resizingBitmap(BitmapFactory.decodeStream(input));
        else
            Log.d("<<CONTACT_PHOTO>>", "first try failed to load photo");

        byte[] photoBytes = null;
        Uri photoUri = ContentUris.withAppendedId(ContactsContract.Data.CONTENT_URI, photo_id);
        Cursor c = cr.query(photoUri, new String[]{ContactsContract.CommonDataKinds.Photo.PHOTO},
                null,null, null);
        try {
            if (c.moveToFirst())
                photoBytes = c.getBlob(0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            c.close();
        }

        if (photoBytes != null) {
            return resizingBitmap(BitmapFactory.decodeByteArray(photoBytes, 0, photoBytes.length));
        } else
            Log.d("<<CONTACT_PHOTO>>", "second try also failed");

        return null;

    }

    public Bitmap resizingBitmap(Bitmap oBitmap) {
        if (oBitmap == null) {
            return null;
        }

        float width = oBitmap.getWidth();
        float height = oBitmap.getHeight();
        float resizing_size = 120;

        Bitmap rBitmap = null;
        if (width > resizing_size) {
            float mWidth = (float)(width / 100);
            float fScale = (float)(resizing_size / mWidth);
            width *= (fScale / 100);
            height *= (fScale / 100);

        } else if (height > resizing_size) {
            float mHeight = (float)(height / 100);
            float fScale = (float)(resizing_size / mHeight);

            width *= (fScale / 100);
            height *= (fScale / 100);
        }

        //Log.d("rBitmap : " + width + ", " + height);

        rBitmap = Bitmap.createScaledBitmap(oBitmap, (int)width, (int)height, true);
        return rBitmap;
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewPhone;
        ImageView imageView;

        public UserViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewPhone = itemView.findViewById(R.id.textViewPhone);
            imageView = itemView.findViewById(R.id.image);
        }
    }
}
