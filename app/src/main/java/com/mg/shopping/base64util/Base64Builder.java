package com.mg.shopping.base64util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

/**
 * <p>
 *
 *     Base64Builder base64Builder = new Base64Builder()
 *             .setBase64Object(new Base64Object()
 *                     .setBitmap(photo))
 *             .setRequiredHeight(250) //required height , used when we select scaling
 *             .setScaling(true);  //scaling
 *
 * </p>
 */
public class Base64Builder {
    private Base64Object base64Object;
    private boolean isScaling;
    private int requiredHeight;


    public Base64Builder setBase64Object(Base64Object base64Object) {
        this.base64Object = base64Object;
        return this;
    }

    public Base64Builder setScaling(boolean scaling) {
        isScaling = scaling;
        return this;
    }

    public Base64Builder setRequiredHeight(int requiredHeight) {
        this.requiredHeight = requiredHeight;
        return this;
    }



    public String encodedBitmapIntoString(){

        int width = 0;
        int height = 0;
        float aspectRatio;

        if (isScaling) {

            aspectRatio = base64Object.getBitmap().getWidth() /
                    (float) base64Object.getBitmap().getHeight();
            width = requiredHeight;
            height = Math.round(width / aspectRatio);

        } else {

            width = base64Object.getBitmap().getWidth();
            height = base64Object.getBitmap().getHeight();

        }

        Bitmap yourSelectedImage = Bitmap.createScaledBitmap(
                base64Object.getBitmap(), width, height, false);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        yourSelectedImage.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteFormat = stream.toByteArray();
        // get the base 64 string

        return Base64.encodeToString(byteFormat, Base64.NO_WRAP);
    }

    public Bitmap decodedStringIntoBitmap(){

        byte[] decodedString = Base64.decode(base64Object.getText(), Base64.NO_WRAP);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }


    public String encodedStringIntoBase64String(){

        byte[] data = base64Object.getText().getBytes(StandardCharsets.UTF_8);
        // get the base 64 string
        return Base64.encodeToString(data, Base64.DEFAULT);
    }

    public String decodedBase64StringIntoString(){

        byte[] decodedString = Base64.decode(base64Object.getText(), Base64.DEFAULT);
        return new String(decodedString, StandardCharsets.UTF_8);
    }



}
