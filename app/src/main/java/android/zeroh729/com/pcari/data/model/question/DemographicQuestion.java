package android.zeroh729.com.pcari.data.model.question;

import android.os.Parcel;
import android.os.Parcelable;

public class DemographicQuestion implements Parcelable{
    private String id;
    private String responseId;
    private int orderNumber;
    private String question;
    private InputType inputType;
    private String[] choices;

    protected DemographicQuestion(Parcel in) {
        id = in.readString();
        responseId = in.readString();
        orderNumber = in.readInt();
        question = in.readString();
        choices = in.createStringArray();
    }

    public static final Creator<DemographicQuestion> CREATOR = new Creator<DemographicQuestion>() {
        @Override
        public DemographicQuestion createFromParcel(Parcel in) {
            return new DemographicQuestion(in);
        }

        @Override
        public DemographicQuestion[] newArray(int size) {
            return new DemographicQuestion[size];
        }
    };

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
        dest.writeStringArray(choices);
    }

    private enum InputType{
        DROPDOWN, NUMBER_FIELD
    }

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

    public InputType getInputType() {
        return inputType;
    }

    public void setInputType(InputType inputType) {
        this.inputType = inputType;
    }

    public String[] getChoices() {
        return choices;
    }

    public void setChoices(String[] choices) {
        this.choices = choices;
    }
}
