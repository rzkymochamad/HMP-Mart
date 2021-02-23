package com.mg.shopping.jsonutil.billingproductutil;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mg.shopping.jsonutil.redeemcouponutil.CouponResponseObject;
import com.mg.shopping.jsonutil.specificproductutil.Shipping;

public class ListOfDatum implements Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("cart_id")
    @Expose
    private String cartId;

    @SerializedName("sub_category_id")
    @Expose
    private String subCategoryId;

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
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("quantity")
    @Expose
    private String quantity;

    @SerializedName("attribute")
    @Expose
    private List<Attribute> attribute = null;

    @SerializedName("shipping")
    @Expose
    private List<Shipping> shipping = null;

    @SerializedName("shipping_insurance")
    @Expose
    private String shippingInsurance;
    @SerializedName("tariff_insurance")
    @Expose
    private String tariffInsurance;

    @SerializedName("courier_id")
    @Expose
    private String courierId;

    private CouponResponseObject couponResponseObject;
    private com.mg.shopping.jsonutil.shippingrateutil.Shipping shippingObject;

    private boolean shippingSelected;
    private boolean shippingInsuranceAdded;
    private boolean trafficInsuranceAdded;

    private double shippingInsuranceFee;
    private double trafficInsuranceFee;

    private boolean discountCalculationNeeded;
    private String totalBill;

    private boolean alreadyMatched;
    private String discountedAmount;

    private boolean isBilingSection = true;



    public ListOfDatum() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCartId() {
        return cartId;
    }

    public ListOfDatum setCartId(String cartId) {
        this.cartId = cartId;
        return this;
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public ListOfDatum setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
        return this;
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

    public List<Attribute> getAttribute() {
        return attribute;
    }

    public void setAttribute(List<Attribute> attribute) {
        this.attribute = attribute;
    }

    public List<Shipping> getShipping() {
        return shipping;
    }

    public ListOfDatum setShipping(List<Shipping> shipping) {
        this.shipping = shipping;
        return this;
    }

    public String getShippingInsurance() {
        return shippingInsurance;
    }

    public void setShippingInsurance(String shippingInsurance) {
        this.shippingInsurance = shippingInsurance;
    }

    public String getTariffInsurance() {
        return tariffInsurance;
    }

    public void setTariffInsurance(String tariffInsurance) {
        this.tariffInsurance = tariffInsurance;
    }

    public CouponResponseObject getCouponResponseObject() {
        return couponResponseObject;
    }

    public ListOfDatum setCouponResponseObject(CouponResponseObject couponResponseObject) {
        this.couponResponseObject = couponResponseObject;
        return this;
    }

    public com.mg.shopping.jsonutil.shippingrateutil.Shipping getShippingObject() {
        return shippingObject;
    }

    public ListOfDatum setShippingObject(com.mg.shopping.jsonutil.shippingrateutil.Shipping shippingObject) {
        this.shippingObject = shippingObject;
        return this;
    }

    public boolean isShippingSelected() {
        return shippingSelected;
    }

    public ListOfDatum setShippingSelected(boolean shippingSelected) {
        this.shippingSelected = shippingSelected;
        return this;
    }

    public boolean isShippingInsuranceAdded() {
        return shippingInsuranceAdded;
    }

    public ListOfDatum setShippingInsuranceAdded(boolean shippingInsuranceAdded) {
        this.shippingInsuranceAdded = shippingInsuranceAdded;
        return this;
    }

    public boolean isTrafficInsuranceAdded() {
        return trafficInsuranceAdded;
    }

    public ListOfDatum setTrafficInsuranceAdded(boolean trafficInsuranceAdded) {
        this.trafficInsuranceAdded = trafficInsuranceAdded;
        return this;
    }

    public double getShippingInsuranceFee() {
        return shippingInsuranceFee;
    }

    public ListOfDatum setShippingInsuranceFee(double shippingInsuranceFee) {
        this.shippingInsuranceFee = shippingInsuranceFee;
        return this;
    }

    public double getTrafficInsuranceFee() {
        return trafficInsuranceFee;
    }

    public ListOfDatum setTrafficInsuranceFee(double trafficInsuranceFee) {
        this.trafficInsuranceFee = trafficInsuranceFee;
        return this;
    }

    public String getTotalBill() {
        return totalBill;
    }

    public ListOfDatum setTotalBill(String totalBill) {
        this.totalBill = totalBill;
        return this;
    }

    public boolean isAlreadyMatched() {
        return alreadyMatched;
    }

    public ListOfDatum setAlreadyMatched(boolean alreadyMatched) {
        this.alreadyMatched = alreadyMatched;
        return this;
    }

    public boolean isDiscountCalculationNeeded() {
        return discountCalculationNeeded;
    }

    public ListOfDatum setDiscountCalculationNeeded(boolean discountCalculationNeeded) {
        this.discountCalculationNeeded = discountCalculationNeeded;
        return this;
    }

    public String getCourierId() {
        return courierId;
    }

    public ListOfDatum setCourierId(String courierId) {
        this.courierId = courierId;
        return this;
    }


    public String getDiscountedAmount() {
        return discountedAmount;
    }

    public ListOfDatum setDiscountedAmount(String discountedAmount) {
        this.discountedAmount = discountedAmount;
        return this;
    }

    public boolean isBilingSection() {
        return isBilingSection;
    }

    public ListOfDatum setBilingSection(boolean bilingSection) {
        isBilingSection = bilingSection;
        return this;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.cartId);
        dest.writeString(this.subCategoryId);
        dest.writeTypedList(this.image);
        dest.writeTypedList(this.brand);
        dest.writeString(this.name);
        dest.writeString(this.price);
        dest.writeString(this.rating);
        dest.writeString(this.quantity);
        dest.writeTypedList(this.attribute);
        dest.writeTypedList(this.shipping);
        dest.writeString(this.shippingInsurance);
        dest.writeString(this.tariffInsurance);
        dest.writeString(this.courierId);
        dest.writeParcelable(this.couponResponseObject, flags);
        dest.writeParcelable(this.shippingObject, flags);
        dest.writeByte(this.shippingSelected ? (byte) 1 : (byte) 0);
        dest.writeByte(this.shippingInsuranceAdded ? (byte) 1 : (byte) 0);
        dest.writeByte(this.trafficInsuranceAdded ? (byte) 1 : (byte) 0);
        dest.writeDouble(this.shippingInsuranceFee);
        dest.writeDouble(this.trafficInsuranceFee);
        dest.writeByte(this.discountCalculationNeeded ? (byte) 1 : (byte) 0);
        dest.writeString(this.totalBill);
        dest.writeByte(this.alreadyMatched ? (byte) 1 : (byte) 0);
        dest.writeString(this.discountedAmount);
        dest.writeByte(this.isBilingSection ? (byte) 1 : (byte) 0);
    }

    protected ListOfDatum(Parcel in) {
        this.id = in.readString();
        this.cartId = in.readString();
        this.subCategoryId = in.readString();
        this.image = in.createTypedArrayList(Image.CREATOR);
        this.brand = in.createTypedArrayList(Brand.CREATOR);
        this.name = in.readString();
        this.price = in.readString();
        this.rating = in.readString();
        this.quantity = in.readString();
        this.attribute = in.createTypedArrayList(Attribute.CREATOR);
        this.shipping = in.createTypedArrayList(Shipping.CREATOR);
        this.shippingInsurance = in.readString();
        this.tariffInsurance = in.readString();
        this.courierId = in.readString();
        this.couponResponseObject = in.readParcelable(CouponResponseObject.class.getClassLoader());
        this.shippingObject = in.readParcelable(com.mg.shopping.jsonutil.shippingrateutil.Shipping.class.getClassLoader());
        this.shippingSelected = in.readByte() != 0;
        this.shippingInsuranceAdded = in.readByte() != 0;
        this.trafficInsuranceAdded = in.readByte() != 0;
        this.shippingInsuranceFee = in.readDouble();
        this.trafficInsuranceFee = in.readDouble();
        this.discountCalculationNeeded = in.readByte() != 0;
        this.totalBill = in.readString();
        this.alreadyMatched = in.readByte() != 0;
        this.discountedAmount = in.readString();
        this.isBilingSection = in.readByte() != 0;
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

    @Override
    public String toString() {
        return "ListOfDatum{" +
                "id='" + id + '\'' +
                ", cartId='" + cartId + '\'' +
                ", subCategoryId='" + subCategoryId + '\'' +
                ", image=" + image +
                ", brand=" + brand +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", rating='" + rating + '\'' +
                ", quantity='" + quantity + '\'' +
                ", attribute=" + attribute +
                ", shipping=" + shipping +
                ", shippingInsurance='" + shippingInsurance + '\'' +
                ", tariffInsurance='" + tariffInsurance + '\'' +
                ", courierId='" + courierId + '\'' +
                ", couponResponseObject=" + couponResponseObject +
                ", shippingObject=" + shippingObject +
                ", shippingSelected=" + shippingSelected +
                ", shippingInsuranceAdded=" + shippingInsuranceAdded +
                ", trafficInsuranceAdded=" + trafficInsuranceAdded +
                ", shippingInsuranceFee=" + shippingInsuranceFee +
                ", trafficInsuranceFee=" + trafficInsuranceFee +
                ", discountCalculationNeeded=" + discountCalculationNeeded +
                ", totalBill='" + totalBill + '\'' +
                ", alreadyMatched=" + alreadyMatched +
                ", discountedAmount='" + discountedAmount + '\'' +
                ", isBilingSection=" + isBilingSection +
                '}';
    }
}
