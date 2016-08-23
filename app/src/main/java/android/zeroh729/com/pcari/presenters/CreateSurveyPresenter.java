package android.zeroh729.com.pcari.presenters;

import android.zeroh729.com.pcari.data.model.Survey;
import android.zeroh729.com.pcari.data.model.question.DemographicQuestion;
import android.zeroh729.com.pcari.data.model.question.QuantitativeQuestion;
import android.zeroh729.com.pcari.data.model.response.QualitativeResponse;

import java.util.ArrayList;

public class CreateSurveyPresenter implements BasePresenter {
    public CreateSurveySystem system;
    public CreateSurveyScreen screen;

    @Override
    public void setup() {

    }

    @Override
    public void updateState() {

    }

    @Override
    public void setState(int state) {

    }

    public void onClickAddDemographicQ(){
        screen.showAddDemographicForm();
    }

    public void onSubmitDemographicQ(final DemographicQuestion q){
        system.checkAddedDemographicQ(q, new Callback() {
            @Override
            public void onSuccess() {
                system.addDemographicQ(q);
                screen.updateList(system.getDemographicQuestions());
            }

            @Override
            public void onFail(int errorCode) {

            }
        });
    }

    public void onClickAddQuantitativeQ(){
        screen.showAddQuantitativeForm();
    }

    public void onSubmitQuantitativeQ(final QuantitativeQuestion q){
        system.checkAddedQuantitativeQ(q, new Callback() {
            @Override
            public void onSuccess() {
                system.addQuantitativeQ(q);
                screen.updateList(system.getQuantitativeQuestions());
            }

            @Override
            public void onFail(int errorCode) {

            }
        });
    }

    public void onClickAddQualitativeQ(){
        screen.showAddQualitativeForm();
    }

    public void onSubmitQualtitativeQ(final QualitativeResponse q){
        system.checkAddedQualtitativeQ(q, new Callback() {
            @Override
            public void onSuccess() {
                system.addQualtitativeQ(q);
                screen.updateList(system.getQualtitativeQuestions());
            }

            @Override
            public void onFail(int errorCode) {

            }
        });
    }

    public void onClickSubmitSurvey(String surveyName){
        system.setSurveyName(surveyName);
        if(system.getDemographicQuestions().size() >= 1 && system.getQuantitativeQuestions().size() >= 6 && system.getQualtitativeQuestions().size() >= 1){
            screen.showLoadingUploadSurvey();
            system.uploadSurvey(new Callback() {
                @Override
                public void onSuccess() {
                    screen.hideLoadingUploadSurvey();
                    screen.navigateToManageMySurveys();
                }

                @Override
                public void onFail(int errorCode) {
                    screen.hideLoadingUploadSurvey();
                }
            });
        }
    }


    public interface CreateSurveySystem{

        void addDemographicQ(DemographicQuestion q);

        void checkAddedDemographicQ(DemographicQuestion q, Callback callback);

        ArrayList<DemographicQuestion> getDemographicQuestions();

        void addQuantitativeQ(QuantitativeQuestion q);

        ArrayList<DemographicQuestion> getQualtitativeQuestions();

        void addQualtitativeQ(QualitativeResponse q);

        void checkAddedQualtitativeQ(QualitativeResponse q, Callback callback);

        ArrayList<DemographicQuestion> getQuantitativeQuestions();

        void checkAddedQuantitativeQ(QuantitativeQuestion q, Callback callback);

        void uploadSurvey(Callback callback);

        void setSurveyName(String surveyName);
    }

    public interface CreateSurveyScreen{
        void showAddDemographicForm();

        void showAddQuantitativeForm();

        void showAddQualitativeForm();

        void updateList(ArrayList<DemographicQuestion> demographicQuestions);

        void showLoadingUploadSurvey();

        void hideLoadingUploadSurvey();

        void navigateToManageMySurveys();
    }
}
