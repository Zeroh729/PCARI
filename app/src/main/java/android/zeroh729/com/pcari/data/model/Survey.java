package android.zeroh729.com.pcari.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.zeroh729.com.pcari.data.model.question.DemographicQuestion;
import android.zeroh729.com.pcari.data.model.question.QualitativeQuestion;
import android.zeroh729.com.pcari.data.model.question.QuantitativeQuestion;

import java.util.ArrayList;

import io.realm.RealmObject;

/**
 * Created by Admin on 3/8/16.
 */
public class Survey implements Parcelable{
    private String id;
    private String adminID;
    private boolean isAvailable;
    private String name;
    private String objective;
    private ArrayList<DemographicQuestion> demographicQs;
    private ArrayList<QuantitativeQuestion> quantitativeQs;
    private ArrayList<QualitativeQuestion> qualitativeQs;

    public Survey() {
        id = "";
        adminID = "";
        isAvailable = false;
        name = "";
        objective = "";
        demographicQs = new ArrayList<>();
        quantitativeQs = new ArrayList<>();
        qualitativeQs = new ArrayList<>();
    }

    protected Survey(Parcel in) {
        id = in.readString();
        adminID = in.readString();
        isAvailable = in.readByte() != 0;
        name = in.readString();
        objective = in.readString();
        demographicQs = in.createTypedArrayList(DemographicQuestion.CREATOR);
        quantitativeQs = in.createTypedArrayList(QuantitativeQuestion.CREATOR);
        qualitativeQs = in.createTypedArrayList(QualitativeQuestion.CREATOR);
    }

    public static final Creator<Survey> CREATOR = new Creator<Survey>() {
        @Override
        public Survey createFromParcel(Parcel in) {
            return new Survey(in);
        }

        @Override
        public Survey[] newArray(int size) {
            return new Survey[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdminID() {
        return adminID;
    }

    public void setAdminID(String adminID) {
        this.adminID = adminID;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public ArrayList<DemographicQuestion> getDemographicQs() {
        return demographicQs;
    }

    public void setDemographicQs(ArrayList<DemographicQuestion> demographicQs) {
        this.demographicQs = demographicQs;
    }

    public ArrayList<QuantitativeQuestion> getQuantitativeQs() {
        return quantitativeQs;
    }

    public void setQuantitativeQs(ArrayList<QuantitativeQuestion> quantitativeQs) {
        this.quantitativeQs = quantitativeQs;
    }

    public ArrayList<QualitativeQuestion> getQualitativeQs() {
        return qualitativeQs;
    }

    public void setQualitativeQs(ArrayList<QualitativeQuestion> qualitativeQs) {
        this.qualitativeQs = qualitativeQs;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(adminID);
        dest.writeByte((byte) (isAvailable ? 1 : 0));
        dest.writeString(name);
        dest.writeString(objective);
        dest.writeTypedList(demographicQs);
        dest.writeTypedList(quantitativeQs);
        dest.writeTypedList(qualitativeQs);
    }
}