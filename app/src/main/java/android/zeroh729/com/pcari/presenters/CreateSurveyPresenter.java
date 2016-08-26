package android.zeroh729.com.pcari.presenters;

import android.zeroh729.com.pcari.data.model.question.DemographicQuestion;
import android.zeroh729.com.pcari.data.model.question.QualitativeQuestion;
import android.zeroh729.com.pcari.data.model.question.QuantitativeQuestion;
import android.zeroh729.com.pcari.interactor.FirebaseInteractor.CreateSurveySystemImpl;

import java.util.ArrayList;

public class CreateSurveyPresenter implements BasePresenter {
    public CreateSurveySystem system;
    public CreateSurveyScreen screen;

    public CreateSurveyPresenter(CreateSurveyScreen screen) {
        this.screen = screen;
        system = new CreateSurveySystemImpl();
    }

    @Override
    public void setup() {
        screen.updateDemoList(system.getDemographicQuestions());
        screen.updateQualList(system.getQualtitativeQuestions());
        screen.updateQuanList(system.getQuantitativeQuestions());
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
        system.addDemographicQ(q);
        screen.updateDemoList(system.getDemographicQuestions());
    }

    public void onClickAddQuantitativeQ(){
        screen.showAddQuantitativeForm();
    }

    public void onSubmitQuantitativeQ(final QuantitativeQuestion q){
        system.addQuantitativeQ(q);
        screen.updateQuanList(system.getQuantitativeQuestions());
    }

    public void onClickAddQualitativeQ(){
        screen.showAddQualitativeForm();
    }

    public void onSubmitQualtitativeQ(final QualitativeQuestion q){
        system.addQualtitativeQ(q);
        screen.updateQualList(system.getQualtitativeQuestions());
    }

    public void onClickSubmitSurvey(String surveyName){
        if(surveyName.trim().isEmpty()){
            screen.showError("Survey name should not be empty!");
            return;
        }
        if(system.getDemographicQuestions().size() < 1){
            screen.showError("Add at least 1 demographic question!");
            return;
        }

        if(system.getQuantitativeQuestions().size() < 6){
            screen.showError("Add at least 6 quantitative questions!");
            return;
        }

        if(system.getQualtitativeQuestions().size() < 1){
            screen.showError("Add at least 1 qualitative question!");
            return;
        }

        system.setSurveyName(surveyName);
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

    public ArrayList<DemographicQuestion> getDemoQuestions(){
        return system.getDemographicQuestions();
    }

    public ArrayList<QualitativeQuestion> getQualQuestions(){
        return system.getQualtitativeQuestions();
    }

    public ArrayList<QuantitativeQuestion> getQuanQuestions(){
        return system.getQuantitativeQuestions();
    }


    public interface CreateSurveySystem{
        void addDemographicQ(DemographicQuestion q);

        void addQuantitativeQ(QuantitativeQuestion q);

        void addQualtitativeQ(QualitativeQuestion q);

        ArrayList<DemographicQuestion> getDemographicQuestions();

        ArrayList<QualitativeQuestion> getQualtitativeQuestions();

        ArrayList<QuantitativeQuestion> getQuantitativeQuestions();

        void uploadSurvey(Callback callback);

        void setSurveyName(String surveyName);
    }

    public interface CreateSurveyScreen{
        void showAddDemographicForm();

        void showAddQuantitativeForm();

        void showAddQualitativeForm();

        void updateDemoList(ArrayList<DemographicQuestion> demographicQuestions);
        void updateQuanList(ArrayList<QuantitativeQuestion> quantitativeQuestions);
        void updateQualList(ArrayList<QualitativeQuestion> qualitativeQuestions);

        void showLoadingUploadSurvey();

        void hideLoadingUploadSurvey();

        void navigateToManageMySurveys();

        void showError(String s);
    }
}
