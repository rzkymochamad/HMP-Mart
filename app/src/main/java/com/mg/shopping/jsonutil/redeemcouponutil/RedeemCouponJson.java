package com.mg.shopping.jsonutil.redeemcouponutil;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RedeemCouponJson implements Parcelable
{

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("list_of_data")
    @Expose
    private List<CouponResponseObject> listOfData = null;

    public RedeemCouponJson() {
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

    public List<CouponResponseObject> getListOfData() {
        return listOfData;
    }

    public void setListOfData(List<CouponResponseObject> listOfData) {
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

    protected RedeemCouponJson(Parcel in) {
        this.code = in.readString();
        this.message = in.readString();
        this.listOfData = in.createTypedArrayList(CouponResponseObject.CREATOR);
    }

    public static final Creator<RedeemCouponJson> CREATOR = new Creator<RedeemCouponJson>() {
        @Override
        public RedeemCouponJson createFromParcel(Parcel source) {
            return new RedeemCouponJson(source);
        }

        @Override
        public RedeemCouponJson[] newArray(int size) {
            return new RedeemCouponJson[size];
        }
    };
}
