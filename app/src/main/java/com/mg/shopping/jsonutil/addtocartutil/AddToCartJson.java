package com.mg.shopping.jsonutil.addtocartutil;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AddToCartJson implements Parcelable
{

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;

    public AddToCartJson() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeString(this.message);
    }

    protected AddToCartJson(Parcel in) {
        this.code = in.readString();
        this.message = in.readString();
    }

    public static final Creator<AddToCartJson> CREATOR = new Creator<AddToCartJson>() {
        @Override
        public AddToCartJson createFromParcel(Parcel source) {
            return new AddToCartJson(source);
        }

        @Override
        public AddToCartJson[] newArray(int size) {
            return new AddToCartJson[size];
        }
    };
}
