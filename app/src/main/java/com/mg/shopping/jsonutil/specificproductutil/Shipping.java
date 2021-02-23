package com.mg.shopping.jsonutil.specificproductutil;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Shipping implements Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("shipment_id")
    @Expose
    private String shipmentId;
    @SerializedName("carrier")
    @Expose
    private String carrier;
    @SerializedName("service")
    @Expose
    private String service;
    @SerializedName("rate")
    @Expose
    private String rate;
    @SerializedName("delivery_days")
    @Expose
    private Integer deliveryDays;
    @SerializedName("currency")
    @Expose
    private String currency;

    private boolean selection;

    public Shipping() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShipmentId() {
        return shipmentId;
    }

    public Shipping setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
        return this;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public Integer getDeliveryDays() {
        return deliveryDays;
    }

    public void setDeliveryDays(Integer deliveryDays) {
        this.deliveryDays = deliveryDays;
    }

    public String getCurrency() {
        return currency;
    }

    public Shipping setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public boolean isSelection() {
        return selection;
    }

    public Shipping setSelection(boolean selection) {
        this.selection = selection;
        return this;
    }


    @Override
    public String toString() {
        return "Shipping{" +
                "id='" + id + '\'' +
                ", shipmentId='" + shipmentId + '\'' +
                ", carrier='" + carrier + '\'' +
                ", service='" + service + '\'' +
                ", rate='" + rate + '\'' +
                ", deliveryDays=" + deliveryDays +
                ", currency='" + currency + '\'' +
                ", selection=" + selection +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.shipmentId);
        dest.writeString(this.carrier);
        dest.writeString(this.service);
        dest.writeString(this.rate);
        dest.writeValue(this.deliveryDays);
        dest.writeString(this.currency);
        dest.writeByte(this.selection ? (byte) 1 : (byte) 0);
    }

    protected Shipping(Parcel in) {
        this.id = in.readString();
        this.shipmentId = in.readString();
        this.carrier = in.readString();
        this.service = in.readString();
        this.rate = in.readString();
        this.deliveryDays = (Integer) in.readValue(Integer.class.getClassLoader());
        this.currency = in.readString();
        this.selection = in.readByte() != 0;
    }

    public static final Creator<Shipping> CREATOR = new Creator<Shipping>() {
        @Override
        public Shipping createFromParcel(Parcel source) {
            return new Shipping(source);
        }

        @Override
        public Shipping[] newArray(int size) {
            return new Shipping[size];
        }
    };
}