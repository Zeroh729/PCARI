package android.zeroh729.com.pcari.data.model.response;

import android.os.Parcel;
import android.os.Parcelable;
import android.zeroh729.com.pcari.data.model.Coordinates;
import android.zeroh729.com.pcari.data.model.Rating;
import android.zeroh729.com.pcari.data.model.question.DemographicQuestion;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;

public class SurveyResponse extends RealmObject implements Parcelable{
    private String id;
    private String surveyId;

    @Ignore
    private Coordinates coordinate;

    private RealmList<DemographicResponse> demographicResponses;
    private RealmList<QualitativeResponse> qualitativeResponses;
    private RealmList<QuantitativeResponse> quantitativeResponses;
    private RealmList<Rating> ratings;
    private String dateCreated;
    private boolean isFinishedUploading;

    public SurveyResponse() {
        demographicResponses = new RealmList<>();
        qualitativeResponses = new RealmList<>();
        quantitativeResponses = new RealmList<>();
        ratings = new RealmList<>();
        isFinishedUploading = false; //TODO: update this if all uploads are successful
    }

    protected SurveyResponse(Parcel in) {
        id = in.readString();
        coordinate = in.readParcelable(Coordinates.class.getClassLoader());
        ArrayList<DemographicResponse> tempDemographicResponses = in.createTypedArrayList(DemographicResponse.CREATOR);
        ArrayList<QualitativeResponse> tempQualitativeResponses = in.createTypedArrayList(QualitativeResponse.CREATOR);
        ArrayList<QuantitativeResponse> tempQuantitativeResponses = in.createTypedArrayList(QuantitativeResponse.CREATOR);
        ArrayList<Rating> tempRatings = in.createTypedArrayList(Rating.CREATOR);
        dateCreated = in.readString();
        isFinishedUploading = in.readByte() != 0;

        demographicResponses = new RealmList<>(tempDemographicResponses.toArray(new DemographicResponse[tempDemographicResponses.size()]));
        qualitativeResponses = new RealmList<>(tempQualitativeResponses.toArray(new QualitativeResponse[tempQualitativeResponses.size()]));
        quantitativeResponses = new RealmList<>(tempQuantitativeResponses.toArray(new QuantitativeResponse[tempQuantitativeResponses.size()]));
        ratings = new RealmList<>(tempRatings.toArray(new Rating[tempRatings.size()]));
    }

    public static final Creator<SurveyResponse> CREATOR = new Creator<SurveyResponse>() {
        @Override
        public SurveyResponse createFromParcel(Parcel in) {
            return new SurveyResponse(in);
        }

        @Override
        public SurveyResponse[] newArray(int size) {
            return new SurveyResponse[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }

    public Coordinates getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinates coordinate) {
        this.coordinate = coordinate;
    }

    public RealmList<DemographicResponse> getDemographicResponses() {
        return demographicResponses;
    }

    public void setDemographicResponses(RealmList<DemographicResponse> demographicResponses) {
        this.demographicResponses = demographicResponses;
    }

    public RealmList<QualitativeResponse> getQualitativeResponses() {
        return qualitativeResponses;
    }

    public void setQualitativeResponses(RealmList<QualitativeResponse> qualitativeResponses) {
        this.qualitativeResponses = qualitativeResponses;
    }

    public RealmList<QuantitativeResponse> getQuantitativeResponses() {
        return quantitativeResponses;
    }

    public void setQuantitativeResponses(RealmList<QuantitativeResponse> quantitativeResponses) {
        this.quantitativeResponses = quantitativeResponses;
    }

    public RealmList<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(RealmList<Rating> ratings) {
        this.ratings = ratings;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public boolean isFinishedUploading() {
        return isFinishedUploading;
    }

    public void setFinishedUploading(boolean finishedUploading) {
        isFinishedUploading = finishedUploading;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeParcelable(coordinate, flags);
        dest.writeTypedList(demographicResponses);
        dest.writeTypedList(qualitativeResponses);
        dest.writeTypedList(quantitativeResponses);
        dest.writeTypedList(ratings);
        dest.writeString(dateCreated);
        dest.writeByte((byte) (isFinishedUploading ? 1 : 0));
    }
}
