package com.mg.shopping.jsonutil.addbillingcard;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class AddBillingCardJson implements Parcelable
{

    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("customer_id")
    @Expose
    private String customerId;


    public AddBillingCardJson() {
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

    public String getCustomerId() {
        return customerId;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeString(this.message);
        dest.writeString(this.customerId);
    }

    protected AddBillingCardJson(Parcel in) {
        this.code = in.readString();
        this.message = in.readString();
        this.customerId = in.readString();
    }

    public static final Creator<AddBillingCardJson> CREATOR = new Creator<AddBillingCardJson>() {
        @Override
        public AddBillingCardJson createFromParcel(Parcel source) {
            return new AddBillingCardJson(source);
        }

        @Override
        public AddBillingCardJson[] newArray(int size) {
            return new AddBillingCardJson[size];
        }
    };
}
