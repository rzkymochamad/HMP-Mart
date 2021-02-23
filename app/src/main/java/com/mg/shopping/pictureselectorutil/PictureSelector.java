package com.mg.shopping.pictureselectorutil;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.mg.shopping.R;

import java.io.IOException;

public class PictureSelector {
    public static final int REQUEST_CODE_CAMERA = 0;
    public static final int REQUEST_CODE_GALLERY = 1;
    private Context context;
    private Activity activity;
    private ImagePickerCallback imagePickerCallback;

    public PictureSelector(Context context, Activity activity, ImagePickerCallback imagePickerCallback) {
        this.context = context;
        this.activity = activity;
        this.imagePickerCallback = imagePickerCallback;
    }

    public PictureSelector setImagePickerCallback(ImagePickerCallback imagePickerCallback) {
        this.imagePickerCallback = imagePickerCallback;
        return this;
    }

    public void selectPictureViaCamera() {

        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activity.startActivityForResult(takePicture, REQUEST_CODE_CAMERA);//zero can be replaced with any action code

    }

    public void selectPictureViaGallery() {

        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        activity.startActivityForResult(intent, REQUEST_CODE_GALLERY);//one can be replaced with any action code

    }

    public void showImagePickerDialog() {

        final Dialog dialog = new Dialog(context);
        dialog.getWindow().addFlags(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.colorTransparent);
        dialog.setContentView(R.layout.custom_dialog_layout);

        LinearLayout layoutCamera = dialog.findViewById(R.id.layout_camera);
        layoutCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPictureViaCamera();
                dialog.dismiss();
            }
        });

        LinearLayout layoutGallery = dialog.findViewById(R.id.layout_gallery);
        layoutGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectPictureViaGallery();
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    public void handleResponse(Intent data, int requestCode, int resultCode) {

        if (requestCode == REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK) {

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            if (imagePickerCallback!=null){
                imagePickerCallback.onImageHandled(photo);
            }

        }
        else if (requestCode == REQUEST_CODE_GALLERY && resultCode == Activity.RESULT_OK) {

            Uri selectedImage = data.getData();
            try {

                Bitmap photo = MediaStore.Images.Media.getBitmap(context.getContentResolver(), selectedImage);
                if (imagePickerCallback!=null){
                    imagePickerCallback.onImageHandled(photo);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    //Our interface to delegate some behavior
    public interface ImagePickerCallback {
        void onImageHandled(Bitmap image);

        void onImageError();
    }





}
