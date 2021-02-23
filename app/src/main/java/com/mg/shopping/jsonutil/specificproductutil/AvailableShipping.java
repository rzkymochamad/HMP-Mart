package com.mg.shopping.jsonutil.specificproductutil;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AvailableShipping implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("logo")
    @Expose
    private String logo;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("estimated_delivery_days")
    @Expose
    private String estimatedDeliveryDays;

    private boolean selection;
    private boolean dialog;


    public String getId() {
        return id;
    }

    public AvailableShipping setId(String id) {
        this.id = id;
        return this;
    }

    public String getLogo() {
        return logo;
    }

    public AvailableShipping setLogo(String logo) {
        this.logo = logo;
        return this;
    }

    public String getName() {
        return name;
    }

    public AvailableShipping setName(String name) {
        this.name = name;
        return this;
    }

    public String getEstimatedDeliveryDays() {
        return estimatedDeliveryDays;
    }

    public AvailableShipping setEstimatedDeliveryDays(String estimatedDeliveryDays) {
        this.estimatedDeliveryDays = estimatedDeliveryDays;
        return this;
    }

    public AvailableShipping() {
    }


    public boolean isSelection() {
        return selection;
    }

    public AvailableShipping setSelection(boolean selection) {
        this.selection = selection;
        return this;
    }

    public boolean isDialog() {
        return dialog;
    }

    public AvailableShipping setDialog(boolean dialog) {
        this.dialog = dialog;
        return this;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.logo);
        dest.writeString(this.name);
        dest.writeString(this.estimatedDeliveryDays);
        dest.writeByte(this.selection ? (byte) 1 : (byte) 0);
        dest.writeByte(this.dialog ? (byte) 1 : (byte) 0);
    }

    protected AvailableShipping(Parcel in) {
        this.id = in.readString();
        this.logo = in.readString();
        this.name = in.readString();
        this.estimatedDeliveryDays = in.readString();
        this.selection = in.readByte() != 0;
        this.dialog = in.readByte() != 0;
    }

    public static final Creator<AvailableShipping> CREATOR = new Creator<AvailableShipping>() {
        @Override
        public AvailableShipping createFromParcel(Parcel source) {
            return new AvailableShipping(source);
        }

        @Override
        public AvailableShipping[] newArray(int size) {
            return new AvailableShipping[size];
        }
    };
}




