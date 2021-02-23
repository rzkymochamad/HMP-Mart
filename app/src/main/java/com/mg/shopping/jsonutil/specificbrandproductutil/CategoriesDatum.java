package com.mg.shopping.jsonutil.specificbrandproductutil;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoriesDatum implements Parcelable {

    @SerializedName("category_id")
    @Expose
    private String id;

    @SerializedName("category_name")
    @Expose
    private String name;


    public String getId() {
        return id;
    }

    public CategoriesDatum setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CategoriesDatum setName(String name) {
        this.name = name;
        return this;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
    }

    public CategoriesDatum() {
    }

    protected CategoriesDatum(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<CategoriesDatum> CREATOR = new Parcelable.Creator<CategoriesDatum>() {
        @Override
        public CategoriesDatum createFromParcel(Parcel source) {
            return new CategoriesDatum(source);
        }

        @Override
        public CategoriesDatum[] newArray(int size) {
            return new CategoriesDatum[size];
        }
    };
}
