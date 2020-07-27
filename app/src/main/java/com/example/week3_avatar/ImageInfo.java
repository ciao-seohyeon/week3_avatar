package com.example.week3_avatar;

import android.graphics.Bitmap;
import android.net.Uri;

public class ImageInfo {
    String image; // saveFileName
    String imageTitle; // Title

    public ImageInfo(String image, String imageTitle) {
        this.image = image;
        this.imageTitle = imageTitle;
    }

    public String getImage(){
        return image;
    }
    public String getImageTitle(){
        return imageTitle;
    }

    public void setImage(String image){
        this.image = image;
    }
    public void setImageTitle(String imageTitle){
        this.imageTitle = imageTitle;
    }

}
