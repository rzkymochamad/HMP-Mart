
package com.mg.shopping.jsonutil.cartproductutil;

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
    @SerializedName("image")
    @Expose
    private List<Image> image = null;
    @SerializedName("brand")
    @Expose
    private List<Brand> brand = null;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price")
    @Expose
    private String price;

    @SerializedName("selector")
    @Expose
    private String selector;

    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("quantity")
    @Expose
    private String quantity;

    @SerializedName("courier_id")
    @Expose
    private String courierId;

    @SerializedName("attribute")
    @Expose
    private List<Attribute> attribute = null;
    boolean selection;


    public ListOfDatum() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Image> getImage() {
        return image;
    }

    public void setImage(List<Image> image) {
        this.image = image;
    }

    public List<Brand> getBrand() {
        return brand;
    }

    public void setBrand(List<Brand> brand) {
        this.brand = brand;
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

    public String getSelector() {
        return selector;
    }

    public ListOfDatum setSelector(String selector) {
        this.selector = selector;
        return this;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCourierId() {
        return courierId;
    }

    public ListOfDatum setCourierId(String courierId) {
        this.courierId = courierId;
        return this;
    }

    public List<Attribute> getAttribute() {
        return attribute;
    }

    public void setAttribute(List<Attribute> attribute) {
        this.attribute = attribute;
    }

    public boolean isSelection() {
        return selection;
    }

    public ListOfDatum setSelection(boolean selection) {
        this.selection = selection;
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
        dest.writeString(this.selector);
        dest.writeString(this.rating);
        dest.writeString(this.quantity);
        dest.writeString(this.courierId);
        dest.writeTypedList(this.attribute);
        dest.writeByte(this.selection ? (byte) 1 : (byte) 0);
    }

    protected ListOfDatum(Parcel in) {
        this.id = in.readString();
        this.image = in.createTypedArrayList(Image.CREATOR);
        this.brand = in.createTypedArrayList(Brand.CREATOR);
        this.name = in.readString();
        this.price = in.readString();
        this.selector = in.readString();
        this.rating = in.readString();
        this.quantity = in.readString();
        this.courierId = in.readString();
        this.attribute = in.createTypedArrayList(Attribute.CREATOR);
        this.selection = in.readByte() != 0;
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
