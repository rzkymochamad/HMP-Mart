
package com.mg.shopping.jsonutil.allcategoryutil;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Brand implements Parcelable
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

    public Brand() {
    }

    public String getId() {
        return id;
    }

    public Brand setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Brand setName(String name) {
        this.name = name;
        return this;
    }

    public String getImage() {
        return image;
    }

    public Brand setImage(String image) {
        this.image = image;
        return this;
    }

    public String getCoverPhoto() {
        return coverPhoto;
    }

    public Brand setCoverPhoto(String coverPhoto) {
        this.coverPhoto = coverPhoto;
        return this;
    }

    public String getItems() {
        return items;
    }

    public Brand setItems(String items) {
        this.items = items;
        return this;
    }

    public String getReviews() {
        return reviews;
    }

    public Brand setReviews(String reviews) {
        this.reviews = reviews;
        return this;
    }

    public String getRating() {
        return rating;
    }

    public Brand setRating(String rating) {
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

    protected Brand(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.image = in.readString();
        this.coverPhoto = in.readString();
        this.items = in.readString();
        this.reviews = in.readString();
        this.rating = in.readString();
    }

    public static final Creator<Brand> CREATOR = new Creator<Brand>() {
        @Override
        public Brand createFromParcel(Parcel source) {
            return new Brand(source);
        }

        @Override
        public Brand[] newArray(int size) {
            return new Brand[size];
        }
    };
}
