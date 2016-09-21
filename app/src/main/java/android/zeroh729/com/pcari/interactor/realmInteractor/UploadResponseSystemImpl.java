package android.zeroh729.com.pcari.interactor.realmInteractor;

import android.zeroh729.com.pcari.Pcari_;
import android.zeroh729.com.pcari.data.events.UploadStatusEvent;
import android.zeroh729.com.pcari.data.model.Survey;
import android.zeroh729.com.pcari.data.model.response.SurveyResponse;
import android.zeroh729.com.pcari.presenters.BasePresenter;
import android.zeroh729.com.pcari.presenters.UploadResponsePresenter;
import android.zeroh729.com.pcari.util.OttoBus;
import android.zeroh729.com.pcari.util._;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.HashMap;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class UploadResponseSystemImpl implements UploadResponsePresenter.UploadResponseSystem{
    private ArrayList<SurveyResponse> responses = new ArrayList<>();
    private HashMap<String, String> surveyNameMap = new HashMap<>();
    private HashMap<String, UploadStatusEvent> statuses = new HashMap<>();

    private RealmConfiguration realmConfig;
    private Realm realm;

    public UploadResponseSystemImpl() {
        realmConfig = new RealmConfiguration.Builder(Pcari_.getInstance()).build();
        realm = Realm.getInstance(realmConfig);

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Survey> surveyResults = realm.where(Survey.class).findAll();
                for(Survey survey : surveyResults){
                    surveyNameMap.put(survey.getId(), survey.getName());
                }
            }
        });
    }

    @Override
    public void fetchUnfinishedUploads(final BasePresenter.DataCallback<SurveyResponse> dataCallback) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<SurveyResponse> responseResults = realm.where(SurveyResponse.class).findAll();
                for(SurveyResponse response : responseResults){
                    int progress = 0;

                    if(response.isFinishedUploading()){
                        progress = response.getTotalUploadCount();
                    }

                    statuses.put(response.getId(), new UploadStatusEvent(response.getId(), progress, response.getTotalUploadCount()));
                    responses.add(response);
                }

                dataCallback.onSuccess(responses);
            }
        });
    }

    @Override
    public ArrayList<SurveyResponse> getResponses() {
        return responses;
    }

    @Override
    public void uploadInBackground(SurveyResponse response) {
        _.showToast("Uploading in background");
    }

    @Override
    public HashMap<String, String> getSurveyNameMap() {
        return surveyNameMap;
    }

    @Override
    public HashMap<String, UploadStatusEvent> getUploadStatuses() {
        return statuses;
    }
}
