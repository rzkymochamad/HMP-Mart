package com.mg.shopping.jsonutil.multibillingproductutil;

import android.os.Parcel;
import android.os.Parcelable;
import com.mg.shopping.jsonutil.billingproductutil.ListOfDatum;
import com.mg.shopping.jsonutil.redeemcouponutil.CouponResponseObject;
import com.mg.shopping.jsonutil.shippingrateutil.Shipping;

import java.util.ArrayList;
import java.util.List;

public class MultiBillingProductObject implements Parcelable {

    private ArrayList<ListOfDatum> multiBillingArrayList = new ArrayList<>();
    private ArrayList<String> pictureArrayList = new ArrayList<>();

    private String courierId;
    private String cartId;
    private String productId;
    private String brandId;
    private String categoryId;
    private String attributeId;
    private String productPrice;
    private String quantity;
    private String noOfProduct;

    private String price;
    private String productQuantity;
    private String currencyCode;
    private String currencySymbol;

    private CouponResponseObject couponResponseObject;
    private com.mg.shopping.jsonutil.shippingrateutil.Shipping shippingObject;

    private boolean shippingSelected;
    private boolean shippingInsuranceAdded;
    private boolean trafficInsuranceAdded;

    private double shippingInsuranceFee;
    private double trafficInsuranceFee;

    private boolean discountCalculationNeeded;
    private String totalBill;

    private String shippingInsurance;
    private String tariffInsurance;
    private String discountedAmount;


    public List<String> getPictureArrayList() {
        return pictureArrayList;
    }

    public MultiBillingProductObject setPictureArrayList(List<String> pictureArrayList) {
        this.pictureArrayList.addAll(pictureArrayList);
        return this;
    }

    public String getPrice() {
        return price;
    }

    public MultiBillingProductObject setPrice(String price) {
        this.price = price;
        return this;
    }

    public String getCourierId() {
        return courierId;
    }

    public MultiBillingProductObject setCourierId(String courierId) {
        this.courierId = courierId;
        return this;
    }

    public String getCartId() {
        return cartId;
    }

    public MultiBillingProductObject setCartId(String cartId) {
        this.cartId = cartId;
        return this;
    }

    public String getProductId() {
        return productId;
    }

    public MultiBillingProductObject setProductId(String productId) {
        this.productId = productId;
        return this;
    }

    public String getBrandId() {
        return brandId;
    }

    public MultiBillingProductObject setBrandId(String brandId) {
        this.brandId = brandId;
        return this;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public MultiBillingProductObject setCategoryId(String categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public String getAttributeId() {
        return attributeId;
    }

    public MultiBillingProductObject setAttributeId(String attributeId) {
        this.attributeId = attributeId;
        return this;
    }

    public String getNoOfProduct() {
        return noOfProduct;
    }

    public MultiBillingProductObject setNoOfProduct(String noOfProduct) {
        this.noOfProduct = noOfProduct;
        return this;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public MultiBillingProductObject setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
        return this;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public MultiBillingProductObject setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
        return this;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public MultiBillingProductObject setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
        return this;
    }

    public CouponResponseObject getCouponResponseObject() {
        return couponResponseObject;
    }

    public MultiBillingProductObject setCouponResponseObject(CouponResponseObject couponResponseObject) {
        this.couponResponseObject = couponResponseObject;
        return this;
    }

    public Shipping getShippingObject() {
        return shippingObject;
    }

    public MultiBillingProductObject setShippingObject(Shipping shippingObject) {
        this.shippingObject = shippingObject;
        return this;
    }

    public boolean isShippingSelected() {
        return shippingSelected;
    }

    public MultiBillingProductObject setShippingSelected(boolean shippingSelected) {
        this.shippingSelected = shippingSelected;
        return this;
    }

    public boolean isShippingInsuranceAdded() {
        return shippingInsuranceAdded;
    }

    public MultiBillingProductObject setShippingInsuranceAdded(boolean shippingInsuranceAdded) {
        this.shippingInsuranceAdded = shippingInsuranceAdded;
        return this;
    }

    public boolean isTrafficInsuranceAdded() {
        return trafficInsuranceAdded;
    }

    public MultiBillingProductObject setTrafficInsuranceAdded(boolean trafficInsuranceAdded) {
        this.trafficInsuranceAdded = trafficInsuranceAdded;
        return this;
    }

    public double getShippingInsuranceFee() {
        return shippingInsuranceFee;
    }

    public MultiBillingProductObject setShippingInsuranceFee(double shippingInsuranceFee) {
        this.shippingInsuranceFee = shippingInsuranceFee;
        return this;
    }

    public double getTrafficInsuranceFee() {
        return trafficInsuranceFee;
    }

    public MultiBillingProductObject setTrafficInsuranceFee(double trafficInsuranceFee) {
        this.trafficInsuranceFee = trafficInsuranceFee;
        return this;
    }

    public boolean isDiscountCalculationNeeded() {
        return discountCalculationNeeded;
    }

    public MultiBillingProductObject setDiscountCalculationNeeded(boolean discountCalculationNeeded) {
        this.discountCalculationNeeded = discountCalculationNeeded;
        return this;
    }

    public String getTotalBill() {
        return totalBill;
    }

    public MultiBillingProductObject setTotalBill(String totalBill) {
        this.totalBill = totalBill;
        return this;
    }

    public List<ListOfDatum> getMultiBillingArrayList() {
        return multiBillingArrayList;
    }

    public MultiBillingProductObject setMultiBillingArrayList(List<ListOfDatum> multiBillingArrayList) {
        this.multiBillingArrayList.addAll(multiBillingArrayList);
        return this;
    }

    public String getShippingInsurance() {
        return shippingInsurance;
    }

    public MultiBillingProductObject setShippingInsurance(String shippingInsurance) {
        this.shippingInsurance = shippingInsurance;
        return this;
    }

    public String getTariffInsurance() {
        return tariffInsurance;
    }

    public MultiBillingProductObject setTariffInsurance(String tariffInsurance) {
        this.tariffInsurance = tariffInsurance;
        return this;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public MultiBillingProductObject setProductPrice(String productPrice) {
        this.productPrice = productPrice;
        return this;
    }

    public String getQuantity() {
        return quantity;
    }

    public MultiBillingProductObject setQuantity(String quantity) {
        this.quantity = quantity;
        return this;
    }

    public String getDiscountedAmount() {
        return discountedAmount;
    }

    public MultiBillingProductObject setDiscountedAmount(String discountedAmount) {
        this.discountedAmount = discountedAmount;
        return this;
    }

    public MultiBillingProductObject() {
    }


    @Override
    public String toString() {
        return "MultiBillingProductObject{" +
                "multiBillingArrayList=" + multiBillingArrayList +
                ", pictureArrayList=" + pictureArrayList +
                ", courierId='" + courierId + '\'' +
                ", cartId='" + cartId + '\'' +
                ", productId='" + productId + '\'' +
                ", brandId='" + brandId + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", attributeId='" + attributeId + '\'' +
                ", productPrice='" + productPrice + '\'' +
                ", quantity='" + quantity + '\'' +
                ", noOfProduct='" + noOfProduct + '\'' +
                ", price='" + price + '\'' +
                ", productQuantity='" + productQuantity + '\'' +
                ", currencyCode='" + currencyCode + '\'' +
                ", currencySymbol='" + currencySymbol + '\'' +
                ", couponResponseObject=" + couponResponseObject +
                ", shippingObject=" + shippingObject +
                ", shippingSelected=" + shippingSelected +
                ", shippingInsuranceAdded=" + shippingInsuranceAdded +
                ", trafficInsuranceAdded=" + trafficInsuranceAdded +
                ", shippingInsuranceFee=" + shippingInsuranceFee +
                ", trafficInsuranceFee=" + trafficInsuranceFee +
                ", discountCalculationNeeded=" + discountCalculationNeeded +
                ", totalBill='" + totalBill + '\'' +
                ", shippingInsurance='" + shippingInsurance + '\'' +
                ", tariffInsurance='" + tariffInsurance + '\'' +
                ", discountedAmount='" + discountedAmount + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.multiBillingArrayList);
        dest.writeStringList(this.pictureArrayList);
        dest.writeString(this.courierId);
        dest.writeString(this.cartId);
        dest.writeString(this.productId);
        dest.writeString(this.brandId);
        dest.writeString(this.categoryId);
        dest.writeString(this.attributeId);
        dest.writeString(this.productPrice);
        dest.writeString(this.quantity);
        dest.writeString(this.noOfProduct);
        dest.writeString(this.price);
        dest.writeString(this.productQuantity);
        dest.writeString(this.currencyCode);
        dest.writeString(this.currencySymbol);
        dest.writeParcelable(this.couponResponseObject, flags);
        dest.writeParcelable(this.shippingObject, flags);
        dest.writeByte(this.shippingSelected ? (byte) 1 : (byte) 0);
        dest.writeByte(this.shippingInsuranceAdded ? (byte) 1 : (byte) 0);
        dest.writeByte(this.trafficInsuranceAdded ? (byte) 1 : (byte) 0);
        dest.writeDouble(this.shippingInsuranceFee);
        dest.writeDouble(this.trafficInsuranceFee);
        dest.writeByte(this.discountCalculationNeeded ? (byte) 1 : (byte) 0);
        dest.writeString(this.totalBill);
        dest.writeString(this.shippingInsurance);
        dest.writeString(this.tariffInsurance);
        dest.writeString(this.discountedAmount);
    }

    protected MultiBillingProductObject(Parcel in) {
        this.multiBillingArrayList = in.createTypedArrayList(ListOfDatum.CREATOR);
        this.pictureArrayList = in.createStringArrayList();
        this.courierId = in.readString();
        this.cartId = in.readString();
        this.productId = in.readString();
        this.brandId = in.readString();
        this.categoryId = in.readString();
        this.attributeId = in.readString();
        this.productPrice = in.readString();
        this.quantity = in.readString();
        this.noOfProduct = in.readString();
        this.price = in.readString();
        this.productQuantity = in.readString();
        this.currencyCode = in.readString();
        this.currencySymbol = in.readString();
        this.couponResponseObject = in.readParcelable(CouponResponseObject.class.getClassLoader());
        this.shippingObject = in.readParcelable(Shipping.class.getClassLoader());
        this.shippingSelected = in.readByte() != 0;
        this.shippingInsuranceAdded = in.readByte() != 0;
        this.trafficInsuranceAdded = in.readByte() != 0;
        this.shippingInsuranceFee = in.readDouble();
        this.trafficInsuranceFee = in.readDouble();
        this.discountCalculationNeeded = in.readByte() != 0;
        this.totalBill = in.readString();
        this.shippingInsurance = in.readString();
        this.tariffInsurance = in.readString();
        this.discountedAmount = in.readString();
    }

    public static final Creator<MultiBillingProductObject> CREATOR = new Creator<MultiBillingProductObject>() {
        @Override
        public MultiBillingProductObject createFromParcel(Parcel source) {
            return new MultiBillingProductObject(source);
        }

        @Override
        public MultiBillingProductObject[] newArray(int size) {
            return new MultiBillingProductObject[size];
        }
    };
}
