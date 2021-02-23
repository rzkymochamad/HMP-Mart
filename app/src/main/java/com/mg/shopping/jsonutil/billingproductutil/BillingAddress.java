package com.mg.shopping.jsonutil.billingproductutil;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BillingAddress implements Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("user_name")
    @Expose
    private String userName;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("apartment")
    @Expose
    private String apartment;

    @SerializedName("city")
    @Expose
    private String city;

    @SerializedName("postal_code")
    @Expose
    private String postalCode;

    @SerializedName("country")
    @Expose
    private String country;

    @SerializedName("phone")
    @Expose
    private String phone;

    private boolean selection;

    public BillingAddress() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public BillingAddress setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public BillingAddress setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getApartment() {
        return apartment;
    }

    public BillingAddress setApartment(String apartment) {
        this.apartment = apartment;
        return this;
    }

    public String getCity() {
        return city;
    }

    public BillingAddress setCity(String city) {
        this.city = city;
        return this;
    }



    public String getCountry() {
        return country;
    }

    public BillingAddress setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public BillingAddress setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public boolean isSelection() {
        return selection;
    }

    public BillingAddress setSelection(boolean selection) {
        this.selection = selection;
        return this;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.userName);
        dest.writeString(this.address);
        dest.writeString(this.apartment);
        dest.writeString(this.city);
        dest.writeString(this.postalCode);
        dest.writeString(this.country);
        dest.writeString(this.phone);
        dest.writeByte(this.selection ? (byte) 1 : (byte) 0);
    }

    protected BillingAddress(Parcel in) {
        this.id = in.readString();
        this.userName = in.readString();
        this.address = in.readString();
        this.apartment = in.readString();
        this.city = in.readString();
        this.postalCode = in.readString();
        this.country = in.readString();
        this.phone = in.readString();
        this.selection = in.readByte() != 0;
    }

    public static final Creator<BillingAddress> CREATOR = new Creator<BillingAddress>() {
        @Override
        public BillingAddress createFromParcel(Parcel source) {
            return new BillingAddress(source);
        }

        @Override
        public BillingAddress[] newArray(int size) {
            return new BillingAddress[size];
        }
    };
}
