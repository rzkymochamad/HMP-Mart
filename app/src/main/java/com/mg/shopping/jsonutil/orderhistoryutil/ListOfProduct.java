
package com.mg.shopping.jsonutil.orderhistoryutil;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListOfProduct implements Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("brand_id")
    @Expose
    private String brandId;

    @SerializedName("product_id")
    @Expose
    private String productID;


    @SerializedName("brand_name")
    @Expose
    private String brandName;
    @SerializedName("product_name")
    @Expose
    private String productName;

    @SerializedName("rating")
    @Expose
    private String rating;

    @SerializedName("product_image")
    @Expose
    private String productImage;
    @SerializedName("attribute")
    @Expose
    private List<Attribute> attribute = null;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("price")
    @Expose
    private String price;

    public ListOfProduct() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandId() {
        return brandId;
    }

    public ListOfProduct setBrandId(String brandId) {
        this.brandId = brandId;
        return this;
    }

    public String getProductID() {
        return productID;
    }

    public ListOfProduct setProductID(String productID) {
        this.productID = productID;
        return this;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public List<Attribute> getAttribute() {
        return attribute;
    }

    public void setAttribute(List<Attribute> attribute) {
        this.attribute = attribute;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
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

    public ListOfProduct setRating(String rating) {
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
        dest.writeString(this.brandId);
        dest.writeString(this.productID);
        dest.writeString(this.brandName);
        dest.writeString(this.productName);
        dest.writeString(this.rating);
        dest.writeString(this.productImage);
        dest.writeTypedList(this.attribute);
        dest.writeString(this.quantity);
        dest.writeString(this.price);
    }

    protected ListOfProduct(Parcel in) {
        this.id = in.readString();
        this.brandId = in.readString();
        this.productID = in.readString();
        this.brandName = in.readString();
        this.productName = in.readString();
        this.rating = in.readString();
        this.productImage = in.readString();
        this.attribute = in.createTypedArrayList(Attribute.CREATOR);
        this.quantity = in.readString();
        this.price = in.readString();
    }

    public static final Creator<ListOfProduct> CREATOR = new Creator<ListOfProduct>() {
        @Override
        public ListOfProduct createFromParcel(Parcel source) {
            return new ListOfProduct(source);
        }

        @Override
        public ListOfProduct[] newArray(int size) {
            return new ListOfProduct[size];
        }
    };
}
