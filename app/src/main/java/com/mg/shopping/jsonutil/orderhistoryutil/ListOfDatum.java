
package com.mg.shopping.jsonutil.orderhistoryutil;

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
    @SerializedName("order_no")
    @Expose
    private String orderNo;
    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("customer_address")
    @Expose
    private String customerAddress;
    @SerializedName("customer_apartment")
    @Expose
    private String customerApartment;
    @SerializedName("customer_city")
    @Expose
    private String customerCity;
    @SerializedName("total_bill")
    @Expose
    private String totalBill;
    @SerializedName("discount_bill")
    @Expose
    private String discountBill;
    @SerializedName("shipping_fee")
    @Expose
    private String shippingFee;
    @SerializedName("currency_code")
    @Expose
    private String currencyCode;
    @SerializedName("currency_symbol")
    @Expose
    private String currencySymbol;
    @SerializedName("courier_logo")
    @Expose
    private String courierLogo;
    @SerializedName("courier_name")
    @Expose
    private String courierName;
    @SerializedName("courier_service")
    @Expose
    private String courierService;
    @SerializedName("courier_delivery_day")
    @Expose
    private String courierDeliveryDay;
    @SerializedName("list_of_product")
    @Expose
    private List<ListOfProduct> listOfProduct = null;

    public ListOfDatum() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerApartment() {
        return customerApartment;
    }

    public void setCustomerApartment(String customerApartment) {
        this.customerApartment = customerApartment;
    }

    public String getCustomerCity() {
        return customerCity;
    }

    public void setCustomerCity(String customerCity) {
        this.customerCity = customerCity;
    }

    public String getTotalBill() {
        return totalBill;
    }

    public void setTotalBill(String totalBill) {
        this.totalBill = totalBill;
    }

    public String getDiscountBill() {
        return discountBill;
    }

    public void setDiscountBill(String discountBill) {
        this.discountBill = discountBill;
    }

    public String getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(String shippingFee) {
        this.shippingFee = shippingFee;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public String getCourierLogo() {
        return courierLogo;
    }

    public void setCourierLogo(String courierLogo) {
        this.courierLogo = courierLogo;
    }

    public String getCourierName() {
        return courierName;
    }

    public void setCourierName(String courierName) {
        this.courierName = courierName;
    }

    public String getCourierService() {
        return courierService;
    }

    public void setCourierService(String courierService) {
        this.courierService = courierService;
    }

    public String getCourierDeliveryDay() {
        return courierDeliveryDay;
    }

    public void setCourierDeliveryDay(String courierDeliveryDay) {
        this.courierDeliveryDay = courierDeliveryDay;
    }

    public List<ListOfProduct> getListOfProduct() {
        return listOfProduct;
    }

    public void setListOfProduct(List<ListOfProduct> listOfProduct) {
        this.listOfProduct = listOfProduct;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.orderNo);
        dest.writeString(this.customerName);
        dest.writeString(this.customerAddress);
        dest.writeString(this.customerApartment);
        dest.writeString(this.customerCity);
        dest.writeString(this.totalBill);
        dest.writeString(this.discountBill);
        dest.writeString(this.shippingFee);
        dest.writeString(this.currencyCode);
        dest.writeString(this.currencySymbol);
        dest.writeString(this.courierLogo);
        dest.writeString(this.courierName);
        dest.writeString(this.courierService);
        dest.writeString(this.courierDeliveryDay);
        dest.writeTypedList(this.listOfProduct);
    }

    protected ListOfDatum(Parcel in) {
        this.id = in.readString();
        this.orderNo = in.readString();
        this.customerName = in.readString();
        this.customerAddress = in.readString();
        this.customerApartment = in.readString();
        this.customerCity = in.readString();
        this.totalBill = in.readString();
        this.discountBill = in.readString();
        this.shippingFee = in.readString();
        this.currencyCode = in.readString();
        this.currencySymbol = in.readString();
        this.courierLogo = in.readString();
        this.courierName = in.readString();
        this.courierService = in.readString();
        this.courierDeliveryDay = in.readString();
        this.listOfProduct = in.createTypedArrayList(ListOfProduct.CREATOR);
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
