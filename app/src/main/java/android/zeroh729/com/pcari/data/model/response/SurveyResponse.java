package android.zeroh729.com.pcari.data.model.response;

import android.zeroh729.com.pcari.data.model.Coordinates;
import android.zeroh729.com.pcari.data.model.question.DemographicQuestion;

import java.util.ArrayList;

public class SurveyResponse {
    private String id;
    private Coordinates coordinate;
    private ArrayList<DemographicResponse> demographicResponses;
    private ArrayList<QualitativeResponse> qualitativeResponses;
    private ArrayList<QuantitativeResponse> quantitativeResponses;
    private String dateCreated;

    public SurveyResponse() {
        demographicResponses = new ArrayList<>();
        qualitativeResponses = new ArrayList<>();
        quantitativeResponses = new ArrayList<>();

    }

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

    public String getDateCreated() {
        return dateCreated;
    }
}
