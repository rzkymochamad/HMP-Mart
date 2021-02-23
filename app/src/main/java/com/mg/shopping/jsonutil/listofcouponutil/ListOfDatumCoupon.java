package com.mg.shopping.jsonutil.listofcouponutil;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ListOfDatumCoupon implements Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("category_type")
    @Expose
    private String categoryType;

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("tagline")
    @Expose
    private String tagline;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("offer")
    @Expose
    private String offer;
    @SerializedName("coupon_type")
    @Expose
    private String couponType;
    @SerializedName("expire_date")
    @Expose
    private String expireDate;

    public ListOfDatumCoupon() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public ListOfDatumCoupon setCategoryType(String categoryType) {
        this.categoryType = categoryType;
        return this;
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

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    @Override
    public String toString() {
        return "ListOfDatumCoupon{" +
                "id='" + id + '\'' +
                ", categoryType='" + categoryType + '\'' +
                ", name='" + name + '\'' +
                ", tagline='" + tagline + '\'' +
                ", code='" + code + '\'' +
                ", offer='" + offer + '\'' +
                ", couponType='" + couponType + '\'' +
                ", expireDate='" + expireDate + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.categoryType);
        dest.writeString(this.name);
        dest.writeString(this.tagline);
        dest.writeString(this.code);
        dest.writeString(this.offer);
        dest.writeString(this.couponType);
        dest.writeString(this.expireDate);
    }

    protected ListOfDatumCoupon(Parcel in) {
        this.id = in.readString();
        this.categoryType = in.readString();
        this.name = in.readString();
        this.tagline = in.readString();
        this.code = in.readString();
        this.offer = in.readString();
        this.couponType = in.readString();
        this.expireDate = in.readString();
    }

    public static final Creator<ListOfDatumCoupon> CREATOR = new Creator<ListOfDatumCoupon>() {
        @Override
        public ListOfDatumCoupon createFromParcel(Parcel source) {
            return new ListOfDatumCoupon(source);
        }

        @Override
        public ListOfDatumCoupon[] newArray(int size) {
            return new ListOfDatumCoupon[size];
        }
    };
}
