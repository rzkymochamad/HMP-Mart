package com.mg.shopping.jsonutil.saleproductutil;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListOfDatumData implements Parcelable
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
    @SerializedName("days_left")
    @Expose
    private String daysLeft;
    @SerializedName("time_left")
    @Expose
    private String timeLeft;
    @SerializedName("sale_type")
    @Expose
    private String saleType;
    @SerializedName("discount")
    @Expose
    private String discount;

    public ListOfDatumData() {
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getSold() {
        return sold;
    }

    public void setSold(String sold) {
        this.sold = sold;
    }

    public String getDaysLeft() {
        return daysLeft;
    }

    public void setDaysLeft(String daysLeft) {
        this.daysLeft = daysLeft;
    }

    public String getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(String timeLeft) {
        this.timeLeft = timeLeft;
    }

    public String getSaleType() {
        return saleType;
    }

    public void setSaleType(String saleType) {
        this.saleType = saleType;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
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
        dest.writeString(this.daysLeft);
        dest.writeString(this.timeLeft);
        dest.writeString(this.saleType);
        dest.writeString(this.discount);
    }

    protected ListOfDatumData(Parcel in) {
        this.id = in.readString();
        this.images = in.createTypedArrayList(Image.CREATOR);
        this.brand = in.createTypedArrayList(Brand.CREATOR);
        this.coverImage = in.readString();
        this.name = in.readString();
        this.price = in.readString();
        this.rating = in.readString();
        this.sold = in.readString();
        this.daysLeft = in.readString();
        this.timeLeft = in.readString();
        this.saleType = in.readString();
        this.discount = in.readString();
    }

    public static final Creator<ListOfDatumData> CREATOR = new Creator<ListOfDatumData>() {
        @Override
        public ListOfDatumData createFromParcel(Parcel source) {
            return new ListOfDatumData(source);
        }

        @Override
        public ListOfDatumData[] newArray(int size) {
            return new ListOfDatumData[size];
        }
    };
}
