package android.zeroh729.com.pcari.data.model.response;

import android.os.Parcel;
import android.os.Parcelable;

public class DemographicResponse implements Parcelable{
    private String id;
    private String questionId;
    private String responseId;
    private String answer;

    public DemographicResponse() {
    }

    protected DemographicResponse(Parcel in) {
        id = in.readString();
        questionId = in.readString();
        responseId = in.readString();
        answer = in.readString();
    }

    public static final Creator<DemographicResponse> CREATOR = new Creator<DemographicResponse>() {
        @Override
        public DemographicResponse createFromParcel(Parcel in) {
            return new DemographicResponse(in);
        }

        @Override
        public DemographicResponse[] newArray(int size) {
            return new DemographicResponse[size];
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

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
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
        dest.writeString(answer);
    }
}
