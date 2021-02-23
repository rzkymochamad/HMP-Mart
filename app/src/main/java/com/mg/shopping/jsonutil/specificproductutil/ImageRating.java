package com.mg.shopping.jsonutil.specificproductutil;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageRating implements Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("image")
    @Expose
    private String image;

    public ImageRating() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.image);
    }

    protected ImageRating(Parcel in) {
        this.id = in.readString();
        this.image = in.readString();
    }

    public static final Creator<ImageRating> CREATOR = new Creator<ImageRating>() {
        @Override
        public ImageRating createFromParcel(Parcel source) {
            return new ImageRating(source);
        }

        @Override
        public ImageRating[] newArray(int size) {
            return new ImageRating[size];
        }
    };
}
