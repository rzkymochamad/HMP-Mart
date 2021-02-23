package com.mg.shopping.jsonutil.redeemcouponutil;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CouponResponseObject implements Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("term_id")
    @Expose
    private String termId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("tagline")
    @Expose
    private String tagline;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("offer")
    @Expose
    private String offer;
    @SerializedName("nature")
    @Expose
    private String nature;

    @SerializedName("price_discount_value")
    @Expose
    private String priceDiscountValue;

    @SerializedName("duration_limit")
    @Expose
    private String durationLimit;
    @SerializedName("user_limit")
    @Expose
    private String userLimit;

    public CouponResponseObject() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTermId() {
        return termId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getPriceDiscountValue() {
        return priceDiscountValue;
    }


    public String getDurationLimit() {
        return durationLimit;
    }

    public void setDurationLimit(String durationLimit) {
        this.durationLimit = durationLimit;
    }

    public String getUserLimit() {
        return userLimit;
    }

    public void setUserLimit(String userLimit) {
        this.userLimit = userLimit;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.termId);
        dest.writeString(this.name);
        dest.writeString(this.tagline);
        dest.writeString(this.code);
        dest.writeString(this.type);
        dest.writeString(this.offer);
        dest.writeString(this.nature);
        dest.writeString(this.priceDiscountValue);
        dest.writeString(this.durationLimit);
        dest.writeString(this.userLimit);
    }

    protected CouponResponseObject(Parcel in) {
        this.id = in.readString();
        this.termId = in.readString();
        this.name = in.readString();
        this.tagline = in.readString();
        this.code = in.readString();
        this.type = in.readString();
        this.offer = in.readString();
        this.nature = in.readString();
        this.priceDiscountValue = in.readString();
        this.durationLimit = in.readString();
        this.userLimit = in.readString();
    }

    public static final Creator<CouponResponseObject> CREATOR = new Creator<CouponResponseObject>() {
        @Override
        public CouponResponseObject createFromParcel(Parcel source) {
            return new CouponResponseObject(source);
        }

        @Override
        public CouponResponseObject[] newArray(int size) {
            return new CouponResponseObject[size];
        }
    };
}
