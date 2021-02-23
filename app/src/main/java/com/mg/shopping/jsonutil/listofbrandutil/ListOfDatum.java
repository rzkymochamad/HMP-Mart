
package com.mg.shopping.jsonutil.listofbrandutil;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListOfDatum implements Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("cover_photo")
    @Expose
    private String coverPhoto;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("count")
    @Expose
    private String count;

    @SerializedName("items")
    @Expose
    private String items;

    public ListOfDatum() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(String coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getCount() {
        return count;
    }

    public ListOfDatum setCount(String count) {
        this.count = count;
        return this;
    }

    public String getItems() {
        return items;
    }

    public ListOfDatum setItems(String items) {
        this.items = items;
        return this;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.categoryName);
        dest.writeString(this.name);
        dest.writeString(this.coverPhoto);
        dest.writeString(this.image);
        dest.writeString(this.rating);
        dest.writeString(this.count);
        dest.writeString(this.items);
    }

    protected ListOfDatum(Parcel in) {
        this.id = in.readString();
        this.categoryName = in.readString();
        this.name = in.readString();
        this.coverPhoto = in.readString();
        this.image = in.readString();
        this.rating = in.readString();
        this.count = in.readString();
        this.items = in.readString();
    }

    public static final Creator<ListOfDatum> CREATOR = new Creator<ListOfDatum>() {
        @Override
        public ListOfDatum createFromParcel(Parcel source) {
            return new ListOfDatum(source);
        }

        @Override
        public ListOfDatum[] newArray(int size) {
            return new ListOfDatum[size];
        }
    };
}
