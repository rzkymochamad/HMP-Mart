package com.mg.shopping.jsonutil.featuredcategoriesproductutil;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeaturedCategoriesProductJson implements Parcelable
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
    @SerializedName("list_of_categories")
    @Expose
    private List<ListOfCategory> listOfCategories = new ArrayList<>();

    public FeaturedCategoriesProductJson() {
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

    public List<ListOfCategory> getListOfCategories() {
        return listOfCategories;
    }

    public void setListOfCategories(List<ListOfCategory> listOfCategories) {
        this.listOfCategories = listOfCategories;
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
        dest.writeTypedList(this.listOfCategories);
    }

    protected FeaturedCategoriesProductJson(Parcel in) {
        this.code = in.readString();
        this.message = in.readString();
        this.listOfData = in.createTypedArrayList(ListOfDatum.CREATOR);
        this.listOfCategories = in.createTypedArrayList(ListOfCategory.CREATOR);
    }

    public static final Creator<FeaturedCategoriesProductJson> CREATOR = new Creator<FeaturedCategoriesProductJson>() {
        @Override
        public FeaturedCategoriesProductJson createFromParcel(Parcel source) {
            return new FeaturedCategoriesProductJson(source);
        }

        @Override
        public FeaturedCategoriesProductJson[] newArray(int size) {
            return new FeaturedCategoriesProductJson[size];
        }
    };
}
