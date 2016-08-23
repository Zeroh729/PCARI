package android.zeroh729.com.pcari.data.model.question;

import android.os.Parcel;
import android.os.Parcelable;

public class QualitativeQuestion implements Parcelable{
    private String id;
    private String responseId;
    private int orderNumber;
    private String question;
    private String questionForRating;

    protected QualitativeQuestion(Parcel in) {
        id = in.readString();
        responseId = in.readString();
        orderNumber = in.readInt();
        question = in.readString();
        questionForRating = in.readString();
    }

    public static final Creator<QualitativeQuestion> CREATOR = new Creator<QualitativeQuestion>() {
        @Override
        public QualitativeQuestion createFromParcel(Parcel in) {
            return new QualitativeQuestion(in);
        }

        @Override
        public QualitativeQuestion[] newArray(int size) {
            return new QualitativeQuestion[size];
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

    public String getQuestionForRating() {
        return questionForRating;
    }

    public void setQuestionForRating(String questionForRating) {
        this.questionForRating = questionForRating;
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
        dest.writeString(questionForRating);
    }
}
