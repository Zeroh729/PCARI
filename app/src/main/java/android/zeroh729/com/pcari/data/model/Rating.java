package android.zeroh729.com.pcari.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;

public class Rating extends RealmObject implements Parcelable{
    private String id;
    private String responseId;
    private String qualitativeResId;
    private int rating;

    public Rating() {
    }

    protected Rating(Parcel in) {
        id = in.readString();
        responseId = in.readString();
        qualitativeResId = in.readString();
        rating = in.readInt();
    }

    public static final Creator<Rating> CREATOR = new Creator<Rating>() {
        @Override
        public Rating createFromParcel(Parcel in) {
            return new Rating(in);
        }

        @Override
        public Rating[] newArray(int size) {
            return new Rating[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQualitativeResId() {
        return qualitativeResId;
    }

    public void setQualitativeResId(String qualitativeResId) {
        this.qualitativeResId = qualitativeResId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getResponseId() {
        return responseId;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(responseId);
        dest.writeString(qualitativeResId);
        dest.writeInt(rating);
    }
}
