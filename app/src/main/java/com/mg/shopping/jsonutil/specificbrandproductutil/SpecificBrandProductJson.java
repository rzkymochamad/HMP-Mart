package com.mg.shopping.jsonutil.specificbrandproductutil;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SpecificBrandProductJson implements Parcelable
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

    @SerializedName("list_of_latest_data")
    @Expose
    private List<LatestDatum> listOfLatestData = new ArrayList<>();

    @SerializedName("list_of_sale_data")
    @Expose
    private List<SaleDatum> listOfSaleData = new ArrayList<>();

    @SerializedName("list_of_categories")
    @Expose
    private List<CategoriesDatum> listOfCategories = new ArrayList<>();

    public SpecificBrandProductJson() {
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

    public List<LatestDatum> getListOfLatestData() {
        return listOfLatestData;
    }

    public SpecificBrandProductJson setListOfLatestData(List<LatestDatum> listOfLatestData) {
        this.listOfLatestData = listOfLatestData;
        return this;
    }

    public List<SaleDatum> getListOfSaleData() {
        return listOfSaleData;
    }

    public SpecificBrandProductJson setListOfSaleData(List<SaleDatum> listOfSaleData) {
        this.listOfSaleData = listOfSaleData;
        return this;
    }

    public List<CategoriesDatum> getListOfCategories() {
        return listOfCategories;
    }

    public SpecificBrandProductJson setListOfCategories(List<CategoriesDatum> listOfCategories) {
        this.listOfCategories = listOfCategories;
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
        dest.writeTypedList(this.listOfLatestData);
        dest.writeTypedList(this.listOfSaleData);
        dest.writeTypedList(this.listOfCategories);
    }

    protected SpecificBrandProductJson(Parcel in) {
        this.code = in.readString();
        this.message = in.readString();
        this.listOfData = in.createTypedArrayList(ListOfDatum.CREATOR);
        this.listOfLatestData = in.createTypedArrayList(LatestDatum.CREATOR);
        this.listOfSaleData = in.createTypedArrayList(SaleDatum.CREATOR);
        this.listOfCategories = in.createTypedArrayList(CategoriesDatum.CREATOR);
    }

    public static final Creator<SpecificBrandProductJson> CREATOR = new Creator<SpecificBrandProductJson>() {
        @Override
        public SpecificBrandProductJson createFromParcel(Parcel source) {
            return new SpecificBrandProductJson(source);
        }

        @Override
        public SpecificBrandProductJson[] newArray(int size) {
            return new SpecificBrandProductJson[size];
        }
    };
}
