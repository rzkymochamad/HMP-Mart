package com.mg.shopping.jsonutil.hotdatautil;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListOfBrand implements Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("cover_photo")
    @Expose
    private String coverPhoto;


    @SerializedName("items")
    @Expose
    private String items;

    @SerializedName("reviews")
    @Expose
    private String reviews;

    @SerializedName("rating")
    @Expose
    private String rating;

    public ListOfBrand() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCoverPhoto() {
        return coverPhoto;
    }

    public ListOfBrand setCoverPhoto(String coverPhoto) {
        this.coverPhoto = coverPhoto;
        return this;
    }

    public String getItems() {
        return items;
    }

    public ListOfBrand setItems(String items) {
        this.items = items;
        return this;
    }

    public String getReviews() {
        return reviews;
    }

    public ListOfBrand setReviews(String reviews) {
        this.reviews = reviews;
        return this;
    }

    public String getRating() {
        return rating;
    }

    public ListOfBrand setRating(String rating) {
        this.rating = rating;
        return this;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.image);
        dest.writeString(this.coverPhoto);
        dest.writeString(this.items);
        dest.writeString(this.reviews);
        dest.writeString(this.rating);
    }

    protected ListOfBrand(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.image = in.readString();
        this.coverPhoto = in.readString();
        this.items = in.readString();
        this.reviews = in.readString();
        this.rating = in.readString();
    }

    public static final Creator<ListOfBrand> CREATOR = new Creator<ListOfBrand>() {
        @Override
        public ListOfBrand createFromParcel(Parcel source) {
            return new ListOfBrand(source);
        }

        @Override
        public ListOfBrand[] newArray(int size) {
            return new ListOfBrand[size];
        }
    };
}
