
package com.mg.shopping.jsonutil.specificproductquestionsutil;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Answer implements Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("answer")
    @Expose
    private String questionAnswer;
    @SerializedName("user")
    @Expose
    private List<User> user = null;

    public Answer() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAnswer() {
        return questionAnswer;
    }

    public void setAnswer(String answer) {
        this.questionAnswer = answer;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.questionAnswer);
        dest.writeTypedList(this.user);
    }

    protected Answer(Parcel in) {
        this.id = in.readString();
        this.questionAnswer = in.readString();
        this.user = in.createTypedArrayList(User.CREATOR);
    }

    public static final Creator<Answer> CREATOR = new Creator<Answer>() {
        @Override
        public Answer createFromParcel(Parcel source) {
            return new Answer(source);
        }

        @Override
        public Answer[] newArray(int size) {
            return new Answer[size];
        }
    };
}
