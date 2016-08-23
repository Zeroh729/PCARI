package android.zeroh729.com.pcari.data.model.question;

import android.os.Parcel;
import android.os.Parcelable;

public class QuantitativeQuestion implements Parcelable{
    private String id;
    private String responseId;
    private int orderNumber;
    private String question;
    private String[] labels;

    protected QuantitativeQuestion(Parcel in) {
        id = in.readString();
        responseId = in.readString();
        orderNumber = in.readInt();
        question = in.readString();
        labels = in.createStringArray();
    }

    public static final Creator<QuantitativeQuestion> CREATOR = new Creator<QuantitativeQuestion>() {
        @Override
        public QuantitativeQuestion createFromParcel(Parcel in) {
            return new QuantitativeQuestion(in);
        }

        @Override
        public QuantitativeQuestion[] newArray(int size) {
            return new QuantitativeQuestion[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResponseId() {
        return responseId;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String[] getLabels() {
        return labels;
    }

    public void setLabels(String[] labels) {
        this.labels = labels;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(responseId);
        dest.writeInt(orderNumber);
        dest.writeString(question);
        dest.writeStringArray(labels);
    }
}
