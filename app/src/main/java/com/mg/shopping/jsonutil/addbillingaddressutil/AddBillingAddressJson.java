package com.mg.shopping.jsonutil.addbillingaddressutil;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddBillingAddressJson implements Parcelable
{

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("list_of_data")
    @Expose
    private List<Object> listOfData = null;

    public AddBillingAddressJson() {
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

    public List<Object> getListOfData() {
        return listOfData;
    }

    public void setListOfData(List<Object> listOfData) {
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
        dest.writeList(this.listOfData);
    }

    protected AddBillingAddressJson(Parcel in) {
        this.code = in.readString();
        this.message = in.readString();
        this.listOfData = new ArrayList<>();
        in.readList(this.listOfData, Object.class.getClassLoader());
    }

    public static final Creator<AddBillingAddressJson> CREATOR = new Creator<AddBillingAddressJson>() {
        @Override
        public AddBillingAddressJson createFromParcel(Parcel source) {
            return new AddBillingAddressJson(source);
        }

        @Override
        public AddBillingAddressJson[] newArray(int size) {
            return new AddBillingAddressJson[size];
        }
    };
}
