
package com.mg.shopping.jsonutil.recordutil;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Record implements Parcelable
{

    @SerializedName("dateRep")
    @Expose
    private String dateRep;
    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("month")
    @Expose
    private String month;
    @SerializedName("year")
    @Expose
    private String year;
    @SerializedName("cases")
    @Expose
    private String cases;
    @SerializedName("deaths")
    @Expose
    private String deaths;
    @SerializedName("countriesAndTerritories")
    @Expose
    private String countriesAndTerritories;
    @SerializedName("geoId")
    @Expose
    private String geoId;
    @SerializedName("countryterritoryCode")
    @Expose
    private String countryterritoryCode;
    @SerializedName("popData2018")
    @Expose
    private String popData2018;

    public Record() {
    }

    public String getDateRep() {
        return dateRep;
    }

    public void setDateRep(String dateRep) {
        this.dateRep = dateRep;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCases() {
        return cases;
    }

    public void setCases(String cases) {
        this.cases = cases;
    }

    public String getDeaths() {
        return deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }

    public String getCountriesAndTerritories() {
        return countriesAndTerritories;
    }

    public void setCountriesAndTerritories(String countriesAndTerritories) {
        this.countriesAndTerritories = countriesAndTerritories;
    }

    public String getGeoId() {
        return geoId;
    }

    public void setGeoId(String geoId) {
        this.geoId = geoId;
    }

    public String getCountryterritoryCode() {
        return countryterritoryCode;
    }

    public void setCountryterritoryCode(String countryterritoryCode) {
        this.countryterritoryCode = countryterritoryCode;
    }

    public String getPopData2018() {
        return popData2018;
    }

    public void setPopData2018(String popData2018) {
        this.popData2018 = popData2018;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.dateRep);
        dest.writeString(this.day);
        dest.writeString(this.month);
        dest.writeString(this.year);
        dest.writeString(this.cases);
        dest.writeString(this.deaths);
        dest.writeString(this.countriesAndTerritories);
        dest.writeString(this.geoId);
        dest.writeString(this.countryterritoryCode);
        dest.writeString(this.popData2018);
    }

    protected Record(Parcel in) {
        this.dateRep = in.readString();
        this.day = in.readString();
        this.month = in.readString();
        this.year = in.readString();
        this.cases = in.readString();
        this.deaths = in.readString();
        this.countriesAndTerritories = in.readString();
        this.geoId = in.readString();
        this.countryterritoryCode = in.readString();
        this.popData2018 = in.readString();
    }

    public static final Creator<Record> CREATOR = new Creator<Record>() {
        @Override
        public Record createFromParcel(Parcel source) {
            return new Record(source);
        }

        @Override
        public Record[] newArray(int size) {
            return new Record[size];
        }
    };
}
