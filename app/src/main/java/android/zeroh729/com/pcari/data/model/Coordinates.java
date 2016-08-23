package android.zeroh729.com.pcari.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Coordinates implements Parcelable {
    private String responseId;
    private int x;
    private int y;

    public Coordinates() {
    }

    protected Coordinates(Parcel in) {
        responseId = in.readString();
        x = in.readInt();
        y = in.readInt();
    }

    public static final Creator<Coordinates> CREATOR = new Creator<Coordinates>() {
        @Override
        public Coordinates createFromParcel(Parcel in) {
            return new Coordinates(in);
        }

        @Override
        public Coordinates[] newArray(int size) {
            return new Coordinates[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(responseId);
        dest.writeInt(x);
        dest.writeInt(y);
    }

    public String getResponseId() {
        return responseId;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
