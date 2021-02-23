
package com.mg.shopping.jsonutil.categoryutil;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryIcon implements Parcelable
{

    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("category_colour_code")
    @Expose
    private String categoryColourCode;
    @SerializedName("category_icon")
    @Expose
    private String categoryIcons;
    @SerializedName("category_type")
    @Expose
    private String categoryType;

    public CategoryIcon() {
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryColourCode() {
        return categoryColourCode;
    }

    public void setCategoryColourCode(String categoryColourCode) {
        this.categoryColourCode = categoryColourCode;
    }

    public String getCategoryIcon() {
        return categoryIcons;
    }

    public void setCategoryIcon(String categoryIcons) {
        this.categoryIcons = categoryIcons;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.categoryName);
        dest.writeString(this.categoryColourCode);
        dest.writeString(this.categoryIcons);
        dest.writeString(this.categoryType);
    }

    protected CategoryIcon(Parcel in) {
        this.categoryName = in.readString();
        this.categoryColourCode = in.readString();
        this.categoryIcons = in.readString();
        this.categoryType = in.readString();
    }

    public static final Creator<CategoryIcon> CREATOR = new Creator<CategoryIcon>() {
        @Override
        public CategoryIcon createFromParcel(Parcel source) {
            return new CategoryIcon(source);
        }

        @Override
        public CategoryIcon[] newArray(int size) {
            return new CategoryIcon[size];
        }
    };
}
