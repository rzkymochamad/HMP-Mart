
package com.mg.shopping.jsonutil.loginuserutil;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FavouriteDetail implements Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("value")
    @Expose
    private String value;

    public FavouriteDetail() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.key);
        dest.writeString(this.value);
    }

    protected FavouriteDetail(Parcel in) {
        this.id = in.readString();
        this.key = in.readString();
        this.value = in.readString();
    }

    public static final Creator<FavouriteDetail> CREATOR = new Creator<FavouriteDetail>() {
        @Override
        public FavouriteDetail createFromParcel(Parcel source) {
            return new FavouriteDetail(source);
        }

        @Override
        public FavouriteDetail[] newArray(int size) {
            return new FavouriteDetail[size];
        }
    };
}
