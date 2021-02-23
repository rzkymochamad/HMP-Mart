package com.mg.shopping.jsonutil.userprofileutil;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserProfileJson implements Parcelable
{

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("list_of_data")
    @Expose
    private List<Detail> listOfData = null;

    public UserProfileJson() {
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

    public List<Detail> getListOfData() {
        return listOfData;
    }

    public UserProfileJson setListOfData(List<Detail> listOfData) {
        this.listOfData = listOfData;
        return this;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeString(this.message);
        dest.writeTypedList(this.listOfData);
    }

    protected UserProfileJson(Parcel in) {
        this.code = in.readString();
        this.message = in.readString();
        this.listOfData = in.createTypedArrayList(Detail.CREATOR);
    }

    public static final Creator<UserProfileJson> CREATOR = new Creator<UserProfileJson>() {
        @Override
        public UserProfileJson createFromParcel(Parcel source) {
            return new UserProfileJson(source);
        }

        @Override
        public UserProfileJson[] newArray(int size) {
            return new UserProfileJson[size];
        }
    };
}
