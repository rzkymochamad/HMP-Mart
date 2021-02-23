
package com.mg.shopping.jsonutil.stylejsonutil;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Attribute implements Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("meta_key")
    @Expose
    private String metaKey;
    @SerializedName("meta_name")
    @Expose
    private String metaName;
    @SerializedName("meta_value")
    @Expose
    private String metaValue;
    @SerializedName("meta_price")
    @Expose
    private String metaPrice;

    public Attribute() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMetaKey() {
        return metaKey;
    }

    public void setMetaKey(String metaKey) {
        this.metaKey = metaKey;
    }

    public String getMetaName() {
        return metaName;
    }

    public void setMetaName(String metaName) {
        this.metaName = metaName;
    }

    public String getMetaValue() {
        return metaValue;
    }

    public void setMetaValue(String metaValue) {
        this.metaValue = metaValue;
    }

    public String getMetaPrice() {
        return metaPrice;
    }

    public void setMetaPrice(String metaPrice) {
        this.metaPrice = metaPrice;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.metaKey);
        dest.writeString(this.metaName);
        dest.writeString(this.metaValue);
        dest.writeString(this.metaPrice);
    }

    protected Attribute(Parcel in) {
        this.id = in.readString();
        this.metaKey = in.readString();
        this.metaName = in.readString();
        this.metaValue = in.readString();
        this.metaPrice = in.readString();
    }

    public static final Creator<Attribute> CREATOR = new Creator<Attribute>() {
        @Override
        public Attribute createFromParcel(Parcel source) {
            return new Attribute(source);
        }

        @Override
        public Attribute[] newArray(int size) {
            return new Attribute[size];
        }
    };
}
