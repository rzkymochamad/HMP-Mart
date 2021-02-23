package com.mg.shopping.jsonutil.categoryutil;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryJson implements Parcelable
{

    @SerializedName("CategoryIcon")
    @Expose
    private List<CategoryIcon> categoryIcon = null;

    public CategoryJson() {
    }

    public List<CategoryIcon> getCategoryIcon() {
        return categoryIcon;
    }

    public void setCategoryIcon(List<CategoryIcon> categoryIcon) {
        this.categoryIcon = categoryIcon;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.categoryIcon);
    }

    protected CategoryJson(Parcel in) {
        this.categoryIcon = in.createTypedArrayList(CategoryIcon.CREATOR);
    }

    public static final Creator<CategoryJson> CREATOR = new Creator<CategoryJson>() {
        @Override
        public CategoryJson createFromParcel(Parcel source) {
            return new CategoryJson(source);
        }

        @Override
        public CategoryJson[] newArray(int size) {
            return new CategoryJson[size];
        }
    };
}
