
package com.mg.shopping.jsonutil.listofproductutil;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListOfDatum implements Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("images")
    @Expose
    private List<Image> image = null;
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
    private boolean grid = true;
    private boolean isCartIconAvailable = true;

    public ListOfDatum() {
    }

    public String getId() {
        return id;
    }

    public ListOfDatum setId(String id) {
        this.id = id;
        return this;
    }

    public List<Image> getImage() {
        return image;
    }

    public ListOfDatum setImage(List<Image> image) {
        this.image = image;
        return this;
    }

    public List<Brand> getBrand() {
        return brand;
    }

    public ListOfDatum setBrand(List<Brand> brand) {
        this.brand = brand;
        return this;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public ListOfDatum setCoverImage(String coverImage) {
        this.coverImage = coverImage;
        return this;
    }

    public String getName() {
        return name;
    }

    public ListOfDatum setName(String name) {
        this.name = name;
        return this;
    }

    public String getPrice() {
        return price;
    }

    public ListOfDatum setPrice(String price) {
        this.price = price;
        return this;
    }

    public String getRating() {
        return rating;
    }

    public ListOfDatum setRating(String rating) {
        this.rating = rating;
        return this;
    }

    public String getSold() {
        return sold;
    }

    public ListOfDatum setSold(String sold) {
        this.sold = sold;
        return this;
    }

    public boolean isGrid() {
        return grid;
    }

    public ListOfDatum setGrid(boolean grid) {
        this.grid = grid;
        return this;
    }

    public boolean isCartIconAvailable() {
        return isCartIconAvailable;
    }

    public ListOfDatum setCartIconAvailable(boolean cartIconAvailable) {
        isCartIconAvailable = cartIconAvailable;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeTypedList(this.image);
        dest.writeTypedList(this.brand);
        dest.writeString(this.name);
        dest.writeString(this.price);
        dest.writeString(this.rating);
        dest.writeString(this.sold);
        dest.writeByte(this.grid ? (byte) 1 : (byte) 0);
    }

    protected ListOfDatum(Parcel in) {
        this.id = in.readString();
        this.image = in.createTypedArrayList(Image.CREATOR);
        this.brand = in.createTypedArrayList(Brand.CREATOR);
        this.name = in.readString();
        this.price = in.readString();
        this.rating = in.readString();
        this.sold = in.readString();
        this.grid = in.readByte() != 0;
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
