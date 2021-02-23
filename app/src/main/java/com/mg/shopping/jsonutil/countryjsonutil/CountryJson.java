package com.mg.shopping.jsonutil.countryjsonutil;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountryJson implements Parcelable
{

    @SerializedName("ListOfCountry")
    @Expose
    private List<ListOfCountry> listOfCountry = null;

    public CountryJson() {
    }

    public List<ListOfCountry> getListOfCountry() {
        return listOfCountry;
    }

    public void setListOfCountry(List<ListOfCountry> listOfCountry) {
        this.listOfCountry = listOfCountry;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.listOfCountry);
    }

    protected CountryJson(Parcel in) {
        this.listOfCountry = in.createTypedArrayList(ListOfCountry.CREATOR);
    }

    public static final Creator<CountryJson> CREATOR = new Creator<CountryJson>() {
        @Override
        public CountryJson createFromParcel(Parcel source) {
            return new CountryJson(source);
        }

        @Override
        public CountryJson[] newArray(int size) {
            return new CountryJson[size];
        }
    };
}
