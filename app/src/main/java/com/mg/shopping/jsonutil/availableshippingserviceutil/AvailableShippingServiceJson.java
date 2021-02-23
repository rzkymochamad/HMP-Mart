package com.mg.shopping.jsonutil.availableshippingserviceutil;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mg.shopping.jsonutil.specificproductutil.AvailableShipping;

import java.util.List;

public class AvailableShippingServiceJson implements Parcelable
{

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("list_of_data")
    @Expose
    private List<AvailableShipping> listOfData = null;

    public AvailableShippingServiceJson() {
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

    public List<AvailableShipping> getListOfData() {
        return listOfData;
    }

    public void setListOfData(List<AvailableShipping> listOfData) {
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
        dest.writeTypedList(this.listOfData);
    }

    protected AvailableShippingServiceJson(Parcel in) {
        this.code = in.readString();
        this.message = in.readString();
        this.listOfData = in.createTypedArrayList(AvailableShipping.CREATOR);
    }

    public static final Creator<AvailableShippingServiceJson> CREATOR = new Creator<AvailableShippingServiceJson>() {
        @Override
        public AvailableShippingServiceJson createFromParcel(Parcel source) {
            return new AvailableShippingServiceJson(source);
        }

        @Override
        public AvailableShippingServiceJson[] newArray(int size) {
            return new AvailableShippingServiceJson[size];
        }
    };
}
