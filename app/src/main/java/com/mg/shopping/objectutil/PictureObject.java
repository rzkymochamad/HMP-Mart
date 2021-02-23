package com.mg.shopping.objectutil;

import android.graphics.Bitmap;

public class PictureObject {
    private String pictureUrl;
    private Bitmap pictureBitmap;
    private boolean isLongTap;
    private boolean isRefundPicture;


    public String getPictureUrl() {
        return pictureUrl;
    }

    public PictureObject setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
        return this;
    }

    public Bitmap getPictureBitmap() {
        return pictureBitmap;
    }

    public PictureObject setPictureBitmap(Bitmap pictureBitmap) {
        this.pictureBitmap = pictureBitmap;
        return this;
    }

    public boolean isLongTap() {
        return isLongTap;
    }

    public PictureObject setLongTap(boolean longTap) {
        isLongTap = longTap;
        return this;
    }

    public boolean isRefundPicture() {
        return isRefundPicture;
    }

    public PictureObject setRefundPicture(boolean refundPicture) {
        isRefundPicture = refundPicture;
        return this;
    }
}
