package com.mg.shopping.jsonutil.currencyutil;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListOfCurrency implements Parcelable
{

    @SerializedName("Country")
    @Expose
    private String country;
    @SerializedName("CountryCode")
    @Expose
    private String countryCode;
    @SerializedName("Currency")
    @Expose
    private String currency;
    @SerializedName("Code")
    @Expose
    private String code;

    public ListOfCurrency() {
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.country);
        dest.writeString(this.countryCode);
        dest.writeString(this.currency);
        dest.writeString(this.code);
    }

    protected ListOfCurrency(Parcel in) {
        this.country = in.readString();
        this.countryCode = in.readString();
        this.currency = in.readString();
        this.code = in.readString();
    }

    public static final Creator<ListOfCurrency> CREATOR = new Creator<ListOfCurrency>() {
        @Override
        public ListOfCurrency createFromParcel(Parcel source) {
            return new ListOfCurrency(source);
        }

        @Override
        public ListOfCurrency[] newArray(int size) {
            return new ListOfCurrency[size];
        }
    };
}
