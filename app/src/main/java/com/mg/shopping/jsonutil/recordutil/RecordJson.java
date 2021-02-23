package com.mg.shopping.jsonutil.recordutil;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecordJson implements Parcelable
{

    @SerializedName("records")
    @Expose
    private List<Record> records = null;

    public RecordJson() {
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.records);
    }

    protected RecordJson(Parcel in) {
        this.records = in.createTypedArrayList(Record.CREATOR);
    }

    public static final Creator<RecordJson> CREATOR = new Creator<RecordJson>() {
        @Override
        public RecordJson createFromParcel(Parcel source) {
            return new RecordJson(source);
        }

        @Override
        public RecordJson[] newArray(int size) {
            return new RecordJson[size];
        }
    };
}
