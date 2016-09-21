package android.zeroh729.com.pcari.data.model.response;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.zeroh729.com.pcari.data.model.Coordinates;
import android.zeroh729.com.pcari.data.model.Rating;
import android.zeroh729.com.pcari.data.model.question.DemographicQuestion;
import android.zeroh729.com.pcari.data.model.question.QualitativeQuestion;
import android.zeroh729.com.pcari.data.model.question.QuantitativeQuestion;
import android.zeroh729.com.pcari.interactor.services.UploadService;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class SurveyResponse extends RealmObject implements Parcelable{
    @PrimaryKey
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

    private static final String KEY_ID = "idKey";
    private static final String KEY_SURVEYID = "surveyIdKey";
    private static final String KEY_DEMOGRAPHIC_RESPONSES = "demographicResponsesKey";
    private static final String KEY_QUALITATIVE_RESPONSES = "qualitativeResponsesKey";
    private static final String KEY_QUANTITATIVE_RESPONSES = "quantitativeResponsesKey";
    private static final String KEY_RATINGS = "ratingsKey";
    private static final String KEY_DATECREATED = "dateCreatedKey";
    private static final String KEY_ISFINISHED = "isFinishedUploadingKey";


    public SurveyResponse() {
        demographicResponses = new RealmList<>();
        qualitativeResponses = new RealmList<>();
        quantitativeResponses = new RealmList<>();
        ratings = new RealmList<>();
        isFinishedUploading = false; //TODO: update this if all uploads are successful
    }

    public SurveyResponse(SurveyResponse temp){
        this.id = temp.getId();
        this.surveyId = temp.getSurveyId();
        this.coordinate = temp.getCoordinate();
        this.demographicResponses = temp.getDemographicResponses();
        this.qualitativeResponses = temp.getQualitativeResponses();
        this.quantitativeResponses = temp.getQuantitativeResponses();
        this.ratings = temp.getRatings();
        this.dateCreated = temp.getDateCreated();
        this.isFinishedUploading = temp.isFinishedUploading();
    }

    protected SurveyResponse(Parcel in) {
        id = in.readString();
        surveyId = in.readString();
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

    public int getTotalUploadCount(){
        return quantitativeResponses.size() + qualitativeResponses.size() + demographicResponses.size() + 2; //+1 for Coordinate and +1 for Ratings
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(surveyId);
        dest.writeParcelable(coordinate, flags);
        dest.writeTypedList(demographicResponses);
        dest.writeTypedList(qualitativeResponses);
        dest.writeTypedList(quantitativeResponses);
        dest.writeTypedList(ratings);
        dest.writeString(dateCreated);
        dest.writeByte((byte) (isFinishedUploading ? 1 : 0));
    }

    public Intent toIntent(Intent intent){
        intent.putExtra(KEY_ID, id);
        intent.putExtra(KEY_SURVEYID, surveyId);
        ArrayList<DemographicResponse> demographicResponsesTemp = new ArrayList<>();
        ArrayList<QualitativeResponse> qualitativeResponsesTemp = new ArrayList<>();
        ArrayList<QuantitativeResponse> quantitativeResponsesTemp = new ArrayList<>();
        ArrayList<Rating> ratingsTemp = new ArrayList<>();
        demographicResponsesTemp.addAll(demographicResponses);
        qualitativeResponsesTemp.addAll(qualitativeResponses);
        quantitativeResponsesTemp.addAll(quantitativeResponses);
        ratingsTemp.addAll(ratings);
        intent.putExtra(KEY_DEMOGRAPHIC_RESPONSES, demographicResponsesTemp);
        intent.putExtra(KEY_QUALITATIVE_RESPONSES, qualitativeResponsesTemp);
        intent.putExtra(KEY_QUANTITATIVE_RESPONSES, quantitativeResponsesTemp);
        intent.putExtra(KEY_RATINGS, ratingsTemp);
        intent.putExtra(KEY_DATECREATED, dateCreated);
        intent.putExtra(KEY_ISFINISHED, isFinishedUploading);
        return intent;
    }

    public SurveyResponse(Intent intent){
        this.id = intent.getStringExtra(KEY_ID);
        this.surveyId = intent.getStringExtra(KEY_SURVEYID);
        ArrayList<DemographicResponse> demographicResponsesTemp = intent.getParcelableArrayListExtra(KEY_DEMOGRAPHIC_RESPONSES);
        ArrayList<QualitativeResponse> qualitativeResponsesTemp = intent.getParcelableArrayListExtra(KEY_QUALITATIVE_RESPONSES);
        ArrayList<QuantitativeResponse> quantitativeResponsesTemp = intent.getParcelableArrayListExtra(KEY_QUANTITATIVE_RESPONSES);
        ArrayList<Rating> ratingsTemp = intent.getParcelableArrayListExtra(KEY_RATINGS);
        demographicResponses = new RealmList<>();
        qualitativeResponses = new RealmList<>();
        quantitativeResponses = new RealmList<>();
        ratings = new RealmList<>();
        demographicResponses.addAll(demographicResponsesTemp);
        qualitativeResponses.addAll(qualitativeResponsesTemp);
        quantitativeResponses.addAll(quantitativeResponsesTemp);
        ratings.addAll(ratingsTemp);
        this.dateCreated = intent.getStringExtra(KEY_DATECREATED);
        this.isFinishedUploading = intent.getBooleanExtra(KEY_ISFINISHED, false);
    }
}
