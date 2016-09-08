package android.zeroh729.com.pcari.data.model.response;

import android.os.Parcel;
import android.os.Parcelable;

public class QuantitativeResponse implements Parcelable{
    private String id;
    private String questionId;
    private String responseId;
    private int answer;

    public QuantitativeResponse() {
    }

    protected QuantitativeResponse(Parcel in) {
        id = in.readString();
        questionId = in.readString();
        responseId = in.readString();
        answer = in.readInt();
    }

    public static final Creator<QuantitativeResponse> CREATOR = new Creator<QuantitativeResponse>() {
        @Override
        public QuantitativeResponse createFromParcel(Parcel in) {
            return new QuantitativeResponse(in);
        }

        @Override
        public QuantitativeResponse[] newArray(int size) {
            return new QuantitativeResponse[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getResponseId() {
        return responseId;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(questionId);
        dest.writeString(responseId);
        dest.writeInt(answer);
    }
}
