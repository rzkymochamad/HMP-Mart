package com.mg.shopping.jsonutil.billingproductutil;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BillingProductJson implements Parcelable
{

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("list_of_data")
    @Expose
    private List<ListOfDatum> listOfData = new ArrayList<>();

    @SerializedName("payment_method")
    @Expose
    private List<PaymentMethod> paymentMethods = new ArrayList<>();

    @SerializedName("list_of_billing_address")
    @Expose
    private List<BillingAddress> billingAddress = new ArrayList<>();

    public BillingProductJson() {
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

    public List<ListOfDatum> getListOfData() {
        return listOfData;
    }

    public void setListOfData(List<ListOfDatum> listOfData) {
        this.listOfData = listOfData;
    }

    public List<PaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }

    public BillingProductJson setPaymentMethods(List<PaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
        return this;
    }

    public List<BillingAddress> getBillingAddress() {
        return billingAddress;
    }

    public BillingProductJson setBillingAddress(List<BillingAddress> billingAddress) {
        this.billingAddress = billingAddress;
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
        dest.writeTypedList(this.paymentMethods);
        dest.writeTypedList(this.billingAddress);
    }

    protected BillingProductJson(Parcel in) {
        this.code = in.readString();
        this.message = in.readString();
        this.listOfData = in.createTypedArrayList(ListOfDatum.CREATOR);
        this.paymentMethods = in.createTypedArrayList(PaymentMethod.CREATOR);
        this.billingAddress = in.createTypedArrayList(BillingAddress.CREATOR);
    }

    public static final Creator<BillingProductJson> CREATOR = new Creator<BillingProductJson>() {
        @Override
        public BillingProductJson createFromParcel(Parcel source) {
            return new BillingProductJson(source);
        }

        @Override
        public BillingProductJson[] newArray(int size) {
            return new BillingProductJson[size];
        }
    };
}
