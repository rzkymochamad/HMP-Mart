
package com.mg.shopping.jsonutil.allcategoryutil;

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
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("brand")
    @Expose
    private List<Brand> brand = null;

    private boolean selected;
    private boolean firstSelection;

    public ListOfDatum() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public List<Brand> getBrand() {
        return brand;
    }

    public ListOfDatum setBrand(List<Brand> brand) {
        this.brand = brand;
        return this;
    }

    public boolean isSelected() {
        return selected;
    }

    public ListOfDatum setSelected(boolean selected) {
        this.selected = selected;
        return this;
    }

    public boolean isFirstSelection() {
        return firstSelection;
    }

    public ListOfDatum setFirstSelection(boolean firstSelection) {
        this.firstSelection = firstSelection;
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
        dest.writeTypedList(this.data);
        dest.writeTypedList(this.brand);
        dest.writeByte(this.selected ? (byte) 1 : (byte) 0);
        dest.writeByte(this.firstSelection ? (byte) 1 : (byte) 0);
    }

    protected ListOfDatum(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.image = in.readString();
        this.data = in.createTypedArrayList(Datum.CREATOR);
        this.brand = in.createTypedArrayList(Brand.CREATOR);
        this.selected = in.readByte() != 0;
        this.firstSelection = in.readByte() != 0;
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
