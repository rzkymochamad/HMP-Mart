package com.mg.shopping.jsonutil.featuredcategoriesutil;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


import java.util.List;

public class FeaturedCategoriesJson implements Parcelable
{

    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("list_of_data")
    @Expose
    private List<ListOfDatum> listOfData = null;

    public FeaturedCategoriesJson() {
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

    public List<ListOfDatum> getListOfData() {
        return listOfData;
    }

    public void setListOfData(List<ListOfDatum> listOfData) {
        this.listOfData = listOfData;
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

    protected FeaturedCategoriesJson(Parcel in) {
        this.code = in.readString();
        this.message = in.readString();
        this.listOfData = in.createTypedArrayList(ListOfDatum.CREATOR);
    }

    public static final Creator<FeaturedCategoriesJson> CREATOR = new Creator<FeaturedCategoriesJson>() {
        @Override
        public FeaturedCategoriesJson createFromParcel(Parcel source) {
            return new FeaturedCategoriesJson(source);
        }

        @Override
        public FeaturedCategoriesJson[] newArray(int size) {
            return new FeaturedCategoriesJson[size];
        }
    };
}
