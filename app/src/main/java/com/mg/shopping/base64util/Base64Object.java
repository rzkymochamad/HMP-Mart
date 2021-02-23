package com.mg.shopping.base64util;

import android.graphics.Bitmap;

public class Base64Object {
    private String text;
    private Bitmap bitmap;


    public String getText() {
        return text;
    }

    public Base64Object setText(String text) {
        this.text = text;
        return this;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public Base64Object setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        return this;
    }


    @Override
    public String toString() {
        return "Base64Object{" +
                "text='" + text + '\'' +
                ", bitmap=" + bitmap +
                '}';
    }
}
