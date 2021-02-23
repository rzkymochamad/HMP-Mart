
package com.mg.shopping.jsonutil.specificproductutil;

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
    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("price")
    @Expose
    private String price;

    @SerializedName("processing_time")
    @Expose
    private String processingTime;

    @SerializedName("discount")
    @Expose
    private String discount;

    @SerializedName("avg_rating")
    @Expose
    private String avgRating;

    @SerializedName("rating")
    @Expose
    private List<Rating> rating = null;
    @SerializedName("sold")
    @Expose
    private String sold;
    @SerializedName("attribute")
    @Expose
    private List<Attribute> attribute = null;
    @SerializedName("shipping")
    @Expose
    private List<AvailableShipping> shipping = null;
    @SerializedName("faq")
    @Expose
    private List<Faq> faq = null;
    @SerializedName("related_product")
    @Expose
    private List<com.mg.shopping.jsonutil.listofproductutil.ListOfDatum> relatedProduct = null;

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

    public String getCoverImage() {
        return coverImage;
    }

    public ListOfDatum setCoverImage(String coverImage) {
        this.coverImage = coverImage;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ListOfDatum setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscount() {
        return discount;
    }

    public ListOfDatum setDiscount(String discount) {
        this.discount = discount;
        return this;
    }

    public String getAvgRating() {
        return avgRating;
    }

    public ListOfDatum setAvgRating(String avgRating) {
        this.avgRating = avgRating;
        return this;
    }

    public List<Rating> getRating() {
        return rating;
    }

    public void setRating(List<Rating> rating) {
        this.rating = rating;
    }

    public String getSold() {
        return sold;
    }

    public void setSold(String sold) {
        this.sold = sold;
    }

    public List<Attribute> getAttribute() {
        return attribute;
    }

    public void setAttribute(List<Attribute> attribute) {
        this.attribute = attribute;
    }

    public List<AvailableShipping> getShipping() {
        return shipping;
    }

    public ListOfDatum setShipping(List<AvailableShipping> shipping) {
        this.shipping = shipping;
        return this;
    }

    public List<Faq> getFaq() {
        return faq;
    }

    public void setFaq(List<Faq> faq) {
        this.faq = faq;
    }

    public List<com.mg.shopping.jsonutil.listofproductutil.ListOfDatum> getRelatedProduct() {
        return relatedProduct;
    }

    public ListOfDatum setRelatedProduct(List<com.mg.shopping.jsonutil.listofproductutil.ListOfDatum> relatedProduct) {
        this.relatedProduct = relatedProduct;
        return this;
    }

    public String getProcessingTime() {
        return processingTime;
    }

    public ListOfDatum setProcessingTime(String processingTime) {
        this.processingTime = processingTime;
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
        dest.writeString(this.coverImage);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeString(this.price);
        dest.writeString(this.processingTime);
        dest.writeString(this.discount);
        dest.writeString(this.avgRating);
        dest.writeTypedList(this.rating);
        dest.writeString(this.sold);
        dest.writeTypedList(this.attribute);
        dest.writeTypedList(this.shipping);
        dest.writeTypedList(this.faq);
        dest.writeTypedList(this.relatedProduct);
    }

    protected ListOfDatum(Parcel in) {
        this.id = in.readString();
        this.image = in.createTypedArrayList(Image.CREATOR);
        this.brand = in.createTypedArrayList(Brand.CREATOR);
        this.coverImage = in.readString();
        this.name = in.readString();
        this.description = in.readString();
        this.price = in.readString();
        this.processingTime = in.readString();
        this.discount = in.readString();
        this.avgRating = in.readString();
        this.rating = in.createTypedArrayList(Rating.CREATOR);
        this.sold = in.readString();
        this.attribute = in.createTypedArrayList(Attribute.CREATOR);
        this.shipping = in.createTypedArrayList(AvailableShipping.CREATOR);
        this.faq = in.createTypedArrayList(Faq.CREATOR);
        this.relatedProduct = in.createTypedArrayList(com.mg.shopping.jsonutil.listofproductutil.ListOfDatum.CREATOR);
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
