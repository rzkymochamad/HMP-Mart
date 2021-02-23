
package com.mg.shopping.jsonutil.countryjsonutil;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListOfCountry implements Parcelable
{

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("country_code")
    @Expose
    private String countryCode;
    @SerializedName("capital")
    @Expose
    private String capital;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("currency_code")
    @Expose
    private String currencyCode;
    @SerializedName("currency_name")
    @Expose
    private String currencyName;
    @SerializedName("currency_symbol")
    @Expose
    private String currencySymbol;
    @SerializedName("currency_symbol_native")
    @Expose
    private String currencySymbolNative;

    public ListOfCountry() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
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

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public String getCurrencySymbolNative() {
        return currencySymbolNative;
    }

    public void setCurrencySymbolNative(String currencySymbolNative) {
        this.currencySymbolNative = currencySymbolNative;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.countryCode);
        dest.writeString(this.capital);
        dest.writeString(this.latitude);
        dest.writeString(this.longitude);
        dest.writeString(this.currencyCode);
        dest.writeString(this.currencyName);
        dest.writeString(this.currencySymbol);
        dest.writeString(this.currencySymbolNative);
    }

    protected ListOfCountry(Parcel in) {
        this.name = in.readString();
        this.countryCode = in.readString();
        this.capital = in.readString();
        this.latitude = in.readString();
        this.longitude = in.readString();
        this.currencyCode = in.readString();
        this.currencyName = in.readString();
        this.currencySymbol = in.readString();
        this.currencySymbolNative = in.readString();
    }

    public static final Creator<ListOfCountry> CREATOR = new Creator<ListOfCountry>() {
        @Override
        public ListOfCountry createFromParcel(Parcel source) {
            return new ListOfCountry(source);
        }

        @Override
        public ListOfCountry[] newArray(int size) {
            return new ListOfCountry[size];
        }
    };
}
