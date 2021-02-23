package com.mg.shopping.jsonutil.listofcouponutil;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ListOfCouponJson implements Parcelable
{

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("list_of_data")
    @Expose
    private List<ListOfDatumCoupon> listOfData = null;

    public ListOfCouponJson() {
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

    public List<ListOfDatumCoupon> getListOfData() {
        return listOfData;
    }

    public void setListOfData(List<ListOfDatumCoupon> listOfData) {
        this.listOfData = listOfData;
    }

    @Override
    public String toString() {
        return "ListOfCouponJson{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", listOfData=" + listOfData +
                '}';
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

    protected ListOfCouponJson(Parcel in) {
        this.code = in.readString();
        this.message = in.readString();
        this.listOfData = in.createTypedArrayList(ListOfDatumCoupon.CREATOR);
    }

    public static final Creator<ListOfCouponJson> CREATOR = new Creator<ListOfCouponJson>() {
        @Override
        public ListOfCouponJson createFromParcel(Parcel source) {
            return new ListOfCouponJson(source);
        }

        @Override
        public ListOfCouponJson[] newArray(int size) {
            return new ListOfCouponJson[size];
        }
    };
}
