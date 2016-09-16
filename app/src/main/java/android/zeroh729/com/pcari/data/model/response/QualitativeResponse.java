package android.zeroh729.com.pcari.data.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;

public class QualitativeResponse extends RealmObject implements Parcelable{
    private String id;
    private String questionId;
    private String responseId;
    private String answer;

    public QualitativeResponse() {
    }

    protected QualitativeResponse(Parcel in) {
        id = in.readString();
        questionId = in.readString();
        responseId = in.readString();
        answer = in.readString();
    }

    public static final Creator<QualitativeResponse> CREATOR = new Creator<QualitativeResponse>() {
        @Override
        public QualitativeResponse createFromParcel(Parcel in) {
            return new QualitativeResponse(in);
        }

        @Override
        public QualitativeResponse[] newArray(int size) {
            return new QualitativeResponse[size];
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
