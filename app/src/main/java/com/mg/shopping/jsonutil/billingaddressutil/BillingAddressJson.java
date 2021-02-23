package com.mg.shopping.jsonutil.billingaddressutil;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mg.shopping.jsonutil.billingproductutil.BillingAddress;

public class BillingAddressJson implements Parcelable
{

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("list_of_data")
    @Expose
    private List<BillingAddress> listOfData = null;

    public BillingAddressJson() {
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

    public List<BillingAddress> getListOfData() {
        return listOfData;
    }

    public BillingAddressJson setListOfData(List<BillingAddress> listOfData) {
        this.listOfData = listOfData;
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
    }

    protected BillingAddressJson(Parcel in) {
        this.code = in.readString();
        this.message = in.readString();
        this.listOfData = in.createTypedArrayList(BillingAddress.CREATOR);
    }

    public static final Creator<BillingAddressJson> CREATOR = new Creator<BillingAddressJson>() {
        @Override
        public BillingAddressJson createFromParcel(Parcel source) {
            return new BillingAddressJson(source);
        }

        @Override
        public BillingAddressJson[] newArray(int size) {
            return new BillingAddressJson[size];
        }
    };
}
