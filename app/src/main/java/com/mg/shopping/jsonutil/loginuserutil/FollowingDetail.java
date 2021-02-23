
package com.mg.shopping.jsonutil.loginuserutil;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FollowingDetail implements Parcelable
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

    public FollowingDetail() {
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

    protected FollowingDetail(Parcel in) {
        this.id = in.readString();
        this.key = in.readString();
        this.value = in.readString();
    }

    public static final Creator<FollowingDetail> CREATOR = new Creator<FollowingDetail>() {
        @Override
        public FollowingDetail createFromParcel(Parcel source) {
            return new FollowingDetail(source);
        }

        @Override
        public FollowingDetail[] newArray(int size) {
            return new FollowingDetail[size];
        }
    };
}
