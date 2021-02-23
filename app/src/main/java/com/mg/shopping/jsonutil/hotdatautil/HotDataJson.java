package com.mg.shopping.jsonutil.hotdatautil;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mg.shopping.jsonutil.listofproductutil.ListOfDatum;

public class HotDataJson implements Parcelable
{

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("list_of_data")
    @Expose
    private List<com.mg.shopping.jsonutil.listofproductutil.ListOfDatum> listOfData = null;
    @SerializedName("list_of_featured")
    @Expose
    private List<ListOfFeatured> listOfFeatured = null;
    @SerializedName("list_of_brand")
    @Expose
    private List<ListOfBrand> listOfBrand = null;

    public HotDataJson() {
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

    public List<com.mg.shopping.jsonutil.listofproductutil.ListOfDatum> getListOfData() {
        return listOfData;
    }

    public HotDataJson setListOfData(List<com.mg.shopping.jsonutil.listofproductutil.ListOfDatum> listOfData) {
        this.listOfData = listOfData;
        return this;
    }

    public List<ListOfFeatured> getListOfFeatured() {
        return listOfFeatured;
    }

    public void setListOfFeatured(List<ListOfFeatured> listOfFeatured) {
        this.listOfFeatured = listOfFeatured;
    }

    public List<ListOfBrand> getListOfBrand() {
        return listOfBrand;
    }

    public void setListOfBrand(List<ListOfBrand> listOfBrand) {
        this.listOfBrand = listOfBrand;
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
        dest.writeTypedList(this.listOfFeatured);
        dest.writeTypedList(this.listOfBrand);
    }

    protected HotDataJson(Parcel in) {
        this.code = in.readString();
        this.message = in.readString();
        this.listOfData = in.createTypedArrayList(ListOfDatum.CREATOR);
        this.listOfFeatured = in.createTypedArrayList(ListOfFeatured.CREATOR);
        this.listOfBrand = in.createTypedArrayList(ListOfBrand.CREATOR);
    }

    public static final Creator<HotDataJson> CREATOR = new Creator<HotDataJson>() {
        @Override
        public HotDataJson createFromParcel(Parcel source) {
            return new HotDataJson(source);
        }

        @Override
        public HotDataJson[] newArray(int size) {
            return new HotDataJson[size];
        }
    };
}
