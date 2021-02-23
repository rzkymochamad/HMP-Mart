package com.mg.shopping.jsonutil.generalresponseutil;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeneralResponseJson implements Parcelable
{

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("user_picture")
    @Expose
    private String userPicture;


    public String getCode() {
        return code;
    }

    public GeneralResponseJson setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public GeneralResponseJson setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getUserPicture() {
        return userPicture;
    }


    public GeneralResponseJson() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeString(this.message);
        dest.writeString(this.userPicture);
    }

    protected GeneralResponseJson(Parcel in) {
        this.code = in.readString();
        this.message = in.readString();
        this.userPicture = in.readString();
    }

    public static final Creator<GeneralResponseJson> CREATOR = new Creator<GeneralResponseJson>() {
        @Override
        public GeneralResponseJson createFromParcel(Parcel source) {
            return new GeneralResponseJson(source);
        }

        @Override
        public GeneralResponseJson[] newArray(int size) {
            return new GeneralResponseJson[size];
        }
    };
}
