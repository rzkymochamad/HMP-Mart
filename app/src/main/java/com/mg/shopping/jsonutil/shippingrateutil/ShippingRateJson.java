package com.mg.shopping.jsonutil.shippingrateutil;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShippingRateJson implements Parcelable {

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("list_of_data")
    @Expose
    private List<Shipping> shippingList = null;

    public String getCode() {
        return code;
    }

    public ShippingRateJson setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ShippingRateJson setMessage(String message) {
        this.message = message;
        return this;
    }

    public List<Shipping> getShippingList() {
        return shippingList;
    }

    public ShippingRateJson setShippingList(List<Shipping> shippingList) {
        this.shippingList = shippingList;
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
        dest.writeTypedList(this.shippingList);
    }

    public ShippingRateJson() {
    }

    protected ShippingRateJson(Parcel in) {
        this.code = in.readString();
        this.message = in.readString();
        this.shippingList = in.createTypedArrayList(Shipping.CREATOR);
    }

    public static final Parcelable.Creator<ShippingRateJson> CREATOR = new Parcelable.Creator<ShippingRateJson>() {
        @Override
        public ShippingRateJson createFromParcel(Parcel source) {
            return new ShippingRateJson(source);
        }

        @Override
        public ShippingRateJson[] newArray(int size) {
            return new ShippingRateJson[size];
        }
    };
}
