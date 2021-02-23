package com.mg.shopping.jsonutil.specificbrandproductutil;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SaleDatum implements Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("images")
    @Expose
    private List<Image> images = null;
    @SerializedName("brand")
    @Expose
    private List<Brand> brand = null;
    @SerializedName("cover_image")
    @Expose
    private String coverImage;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("sold")
    @Expose
    private String sold;

    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("days_left")
    @Expose
    private String daysLeft;

    public SaleDatum() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<Brand> getBrand() {
        return brand;
    }

    public void setBrand(List<Brand> brand) {
        this.brand = brand;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSold() {
        return sold;
    }

    public void setSold(String sold) {
        this.sold = sold;
    }


    public SaleDatum setRating(String rating) {
        this.rating = rating;
        return this;
    }

    public String getRating() {
        return rating;
    }

    public String getDiscount() {
        return discount;
    }

    public SaleDatum setDiscount(String discount) {
        this.discount = discount;
        return this;
    }

    public String getType() {
        return type;
    }

    public SaleDatum setType(String type) {
        this.type = type;
        return this;
    }

    public String getDaysLeft() {
        return daysLeft;
    }

    public SaleDatum setDaysLeft(String daysLeft) {
        this.daysLeft = daysLeft;
        return this;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeTypedList(this.images);
        dest.writeTypedList(this.brand);
        dest.writeString(this.coverImage);
        dest.writeString(this.name);
        dest.writeString(this.price);
        dest.writeString(this.rating);
        dest.writeString(this.sold);
        dest.writeString(this.discount);
        dest.writeString(this.type);
        dest.writeString(this.daysLeft);
    }

    protected SaleDatum(Parcel in) {
        this.id = in.readString();
        this.images = in.createTypedArrayList(Image.CREATOR);
        this.brand = in.createTypedArrayList(Brand.CREATOR);
        this.coverImage = in.readString();
        this.name = in.readString();
        this.price = in.readString();
        this.rating = in.readString();
        this.sold = in.readString();
        this.discount = in.readString();
        this.type = in.readString();
        this.daysLeft = in.readString();
    }

    public static final Creator<SaleDatum> CREATOR = new Creator<SaleDatum>() {
        @Override
        public SaleDatum createFromParcel(Parcel source) {
            return new SaleDatum(source);
        }

        @Override
        public SaleDatum[] newArray(int size) {
            return new SaleDatum[size];
        }
    };
}
