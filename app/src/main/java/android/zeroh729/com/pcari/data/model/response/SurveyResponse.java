package android.zeroh729.com.pcari.data.model.response;

import android.os.Parcel;
import android.os.Parcelable;
import android.zeroh729.com.pcari.data.model.Coordinates;
import android.zeroh729.com.pcari.data.model.Rating;
import android.zeroh729.com.pcari.data.model.question.DemographicQuestion;

import java.util.ArrayList;

public class SurveyResponse implements Parcelable{
    private String id;
    private Coordinates coordinate;
    private ArrayList<DemographicResponse> demographicResponses;
    private ArrayList<QualitativeResponse> qualitativeResponses;
    private ArrayList<QuantitativeResponse> quantitativeResponses;
    private ArrayList<Rating> ratings;
    private String dateCreated;

    public SurveyResponse() {
        demographicResponses = new ArrayList<>();
        qualitativeResponses = new ArrayList<>();
        quantitativeResponses = new ArrayList<>();
        ratings = new ArrayList<>();
    }

    protected SurveyResponse(Parcel in) {
        id = in.readString();
        coordinate = in.readParcelable(Coordinates.class.getClassLoader());
        demographicResponses = in.createTypedArrayList(DemographicResponse.CREATOR);
        qualitativeResponses = in.createTypedArrayList(QualitativeResponse.CREATOR);
        quantitativeResponses = in.createTypedArrayList(QuantitativeResponse.CREATOR);
        dateCreated = in.readString();
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

    public Coordinates getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinates coordinate) {
        this.coordinate = coordinate;
    }

    public ArrayList<DemographicResponse> getDemographicResponses() {
        return demographicResponses;
    }

    public void setDemographicResponses(ArrayList<DemographicResponse> demographicResponses) {
        this.demographicResponses = demographicResponses;
    }

    public ArrayList<QualitativeResponse> getQualitativeResponses() {
        return qualitativeResponses;
    }

    public void setQualitativeResponses(ArrayList<QualitativeResponse> qualitativeResponses) {
        this.qualitativeResponses = qualitativeResponses;
    }

    public ArrayList<QuantitativeResponse> getQuantitativeResponses() {
        return quantitativeResponses;
    }

    public void setQuantitativeResponses(ArrayList<QuantitativeResponse> quantitativeResponses) {
        this.quantitativeResponses = quantitativeResponses;
    }

    public ArrayList<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(ArrayList<Rating> ratings) {
        this.ratings = ratings;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateCreated() {
        return dateCreated;
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
        dest.writeString(dateCreated);
    }
}
