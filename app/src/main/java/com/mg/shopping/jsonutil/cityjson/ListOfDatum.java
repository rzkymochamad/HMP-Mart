
package com.mg.shopping.jsonutil.cityjson;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListOfDatum implements Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;

    public ListOfDatum() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.latitude);
        dest.writeString(this.longitude);
    }

    protected ListOfDatum(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.latitude = in.readString();
        this.longitude = in.readString();
    }

    public static final Creator<ListOfDatum> CREATOR = new Creator<ListOfDatum>() {
        @Override
        public ListOfDatum createFromParcel(Parcel source) {
            return new ListOfDatum(source);
        }

        @Override
        public ListOfDatum[] newArray(int size) {
            return new ListOfDatum[size];
        }
    };
}
