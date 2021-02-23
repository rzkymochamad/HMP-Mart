package com.mg.shopping.jsonutil.paymentmethodutil;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentMethodJson implements Parcelable
{

    @SerializedName("PaymentMethods")
    @Expose
    private List<PaymentMethod> paymentMethods = null;

    public PaymentMethodJson() {
    }

    public List<PaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(List<PaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.paymentMethods);
    }

    protected PaymentMethodJson(Parcel in) {
        this.paymentMethods = in.createTypedArrayList(PaymentMethod.CREATOR);
    }

    public static final Creator<PaymentMethodJson> CREATOR = new Creator<PaymentMethodJson>() {
        @Override
        public PaymentMethodJson createFromParcel(Parcel source) {
            return new PaymentMethodJson(source);
        }

        @Override
        public PaymentMethodJson[] newArray(int size) {
            return new PaymentMethodJson[size];
        }
    };
}
