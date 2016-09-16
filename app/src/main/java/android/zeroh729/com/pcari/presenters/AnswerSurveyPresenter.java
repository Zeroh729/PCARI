package android.zeroh729.com.pcari.presenters;

import android.zeroh729.com.pcari.data.model.Survey;
import android.zeroh729.com.pcari.data.model.question.DemographicQuestion;
import android.zeroh729.com.pcari.data.model.question.QualitativeQuestion;
import android.zeroh729.com.pcari.data.model.question.QuantitativeQuestion;
import android.zeroh729.com.pcari.data.model.response.SurveyResponse;
import android.zeroh729.com.pcari.interactor.FirebaseInteractor.AnswerSurveySystemImpl;

import java.util.ArrayList;
import java.util.Collections;

public class AnswerSurveyPresenter implements BasePresenter{
    public AnswerSurveySystemImpl system;
    public AnswerSurveyScreen screen;

    public AnswerSurveyPresenter(AnswerSurveyScreen screen, Survey survey) {
        this.screen = screen;
        system = new AnswerSurveySystemImpl();
        system.setSurvey(survey);
    }

    @Override
    public void setup() {
        screen.displaySurveyDetails(system.getSurvey());
        screen.showLoadingIndicator();
        system.loadSurveyQuestions(new AnswerSurveySystem.FetchCallback() {
            @Override
            public void onSuccess(Survey survey) {
                screen.hideLoadingIndicator();
                screen.displayDemographicQs(survey.getDemographicQs());
                screen.displayQuantitativeQs(survey.getQuantitativeQs());
                screen.displayQualitativeQs(survey.getQualitativeQs());
            }

            @Override
            public void onFail(int errorCode) {
                screen.hideLoadingIndicator();
            }
        });
    }

    @Override
    public void updateState() {

    }

    @Override
    public void setState(int state) {

    }

    public void onClickSubmit(SurveyResponse response){
        screen.showLoadingIndicator();
        system.setDateCreated(response);
        system.uploadsResponse(response, new Callback() {
            @Override
            public void onSuccess() {
                screen.navigateToRateResponseScreen();
                screen.hideLoadingIndicator();
            }

            @Override
            public void onFail(int errorCode) {
                screen.hideLoadingIndicator();
            }
        });
    }

    public interface AnswerSurveySystem{
        void loadSurveyQuestions(FetchCallback fetchCallback);

        Survey getSurvey();

        void setSurvey(Survey survey);

        void uploadsResponse(SurveyResponse response, Callback callback);

        interface FetchCallback{
            void onSuccess(Survey survey);
            void onFail(int errorCode);
        }

        void setDateCreated(SurveyResponse response);
    }

    public interface AnswerSurveyScreen{

        void displaySurveyDetails(Survey survey);

        void displayDemographicQs(ArrayList<DemographicQuestion> demographicQs);

        void displayQuantitativeQs(ArrayList<QuantitativeQuestion> quantitativeQs);

        void displayQualitativeQs(ArrayList<QualitativeQuestion> qualitativeQs);

        void navigateToRateResponseScreen();

        void hideLoadingIndicator();

        void showLoadingIndicator();
    }
}
