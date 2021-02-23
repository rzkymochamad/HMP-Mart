package com.mg.shopping.jsonutil.currencyutil;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrencyJson implements Parcelable
{

    @SerializedName("ListOfCurrency")
    @Expose
    private List<ListOfCurrency> listOfCurrency = null;

    public CurrencyJson() {
    }

    public List<ListOfCurrency> getListOfCurrency() {
        return listOfCurrency;
    }

    public void setListOfCurrency(List<ListOfCurrency> listOfCurrency) {
        this.listOfCurrency = listOfCurrency;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.listOfCurrency);
    }

    protected CurrencyJson(Parcel in) {
        this.listOfCurrency = in.createTypedArrayList(ListOfCurrency.CREATOR);
    }

    public static final Creator<CurrencyJson> CREATOR = new Creator<CurrencyJson>() {
        @Override
        public CurrencyJson createFromParcel(Parcel source) {
            return new CurrencyJson(source);
        }

        @Override
        public CurrencyJson[] newArray(int size) {
            return new CurrencyJson[size];
        }
    };
}
