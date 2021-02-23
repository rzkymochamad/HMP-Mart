
package com.mg.shopping.jsonutil.paymentmethodutil;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentMethod implements Parcelable
{

    @SerializedName("payment_method_name")
    @Expose
    private String paymentMethodName;
    @SerializedName("payment_method_icon")
    @Expose
    private String paymentMethodIcon;

    public PaymentMethod() {
    }

    public String getPaymentMethodName() {
        return paymentMethodName;
    }

    public void setPaymentMethodName(String paymentMethodName) {
        this.paymentMethodName = paymentMethodName;
    }

    public String getPaymentMethodIcon() {
        return paymentMethodIcon;
    }

    public void setPaymentMethodIcon(String paymentMethodIcon) {
        this.paymentMethodIcon = paymentMethodIcon;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.paymentMethodName);
        dest.writeString(this.paymentMethodIcon);
    }

    protected PaymentMethod(Parcel in) {
        this.paymentMethodName = in.readString();
        this.paymentMethodIcon = in.readString();
    }

    public static final Creator<PaymentMethod> CREATOR = new Creator<PaymentMethod>() {
        @Override
        public PaymentMethod createFromParcel(Parcel source) {
            return new PaymentMethod(source);
        }

        @Override
        public PaymentMethod[] newArray(int size) {
            return new PaymentMethod[size];
        }
    };
}
