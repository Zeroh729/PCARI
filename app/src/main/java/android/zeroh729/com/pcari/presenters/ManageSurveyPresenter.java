package android.zeroh729.com.pcari.presenters;

import android.zeroh729.com.pcari.data.model.Survey;
import android.zeroh729.com.pcari.data.model.response.QualitativeResponse;
import android.zeroh729.com.pcari.data.model.response.SurveyResponse;

import java.util.ArrayList;

public class ManageSurveyPresenter implements BasePresenter {
    public ManageSurveySystem system;
    public ManageSurveyScreen screen;

    @Override
    public void setup() {

    }

    @Override
    public void updateState() {

    }

    @Override
    public void setState(int state) {

    }

    public void onSelectSurvey(Survey survey){
        screen.displaySurveyDetails(survey);
        system.loadResponses(survey, new ManageSurveySystem.FetchCallback() {
            @Override
            public void onSuccess(ArrayList<SurveyResponse> responses) {
                screen.displayResponsesTable(responses);
                screen.displayResponsesChart(responses);
            }

            @Override
            public void onFail(int errorCode) {

            }
        });
    }

    public interface ManageSurveySystem{
        void loadResponses(Survey survey, FetchCallback fetchCallback);

        interface FetchCallback{
            void onSuccess(ArrayList<SurveyResponse> responses);
            void onFail(int errorCode);
        }
    }

    public interface ManageSurveyScreen{

        void displaySurveyDetails(Survey survey);

        void displayResponsesChart(ArrayList<SurveyResponse> responses);

        void displayResponsesTable(ArrayList<SurveyResponse> responses);
    }
}
