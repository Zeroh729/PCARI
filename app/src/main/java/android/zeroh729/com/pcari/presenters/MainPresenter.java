package android.zeroh729.com.pcari.presenters;

import android.support.annotation.Nullable;
import android.zeroh729.com.pcari.data.model.Admin;
import android.zeroh729.com.pcari.data.model.Survey;
import android.zeroh729.com.pcari.data.model.response.SurveyResponse;
import android.zeroh729.com.pcari.interactor.firebaseInteractor.MainSystemImpl;
import android.zeroh729.com.pcari.interactor.realmInteractor.MainLocalDatabaseImpl;
import android.zeroh729.com.pcari.util._;

import java.util.ArrayList;

public class MainPresenter implements BasePresenter {
    private MainSystem system;
    private MainScreen screen;
    private MainLocalDatabase localDatabaseSystem;

    public MainPresenter(MainScreen screen) {
        this.screen = screen;
        system = new MainSystemImpl();
        localDatabaseSystem = new MainLocalDatabaseImpl();
    }

    @Override
    public void setup() {
        system.reloadUserData(new Callback() {
            @Override
            public void onSuccess() {
                screen.updateUserView(system.getUser());
            }

            @Override
            public void onFail(int errorCode) {

            }
        });
        system.fetchSurveyList(new Callback() {
            @Override
            public void onSuccess() {
                screen.updateSurveyList(system.getAvailableSurveys());
                localDatabaseSystem.saveSurveys(system.getAvailableSurveys());
            }

            @Override
            public void onFail(int errorCode) {
                _.showToast("error");
            }
        });
    }

    @Override
    public void updateState() {

    }

    @Override
    public void setState(int state) {
        switch (state){
            case onStart:
                system.setup();
                break;
            case onStop:
                system.cleanup();
                break;
        }
    }

    public void onClickCreateSurvey(){
        if(system.isLoggedIn()){
            screen.navigateToCreateSurveyScreen();
        }else{
            screen.showLoginPrompt();
        }
    }

    public void onClickLogin(String email, String password){
        system.login(email, password, new Callback() {
            @Override
            public void onSuccess() {
                screen.showSuccessLogin();
                screen.hideLoginPrompt();
                screen.navigateToCreateSurveyScreen();
            }

            @Override
            public void onFail(int errorCode) {
                screen.showError("Something went wrong");
            }
        });
    }

    public void onClickManageSurvey(){
        if(system.isLoggedIn()){
            screen.navigateToManageSurveyScreen();
        }else{
            screen.showLoginPrompt();
        }
    }

    public void onClickLogout(){
        system.logout();
        screen.updateUserView(system.getUser());
    }

    public void onClickSurveyItem(int index){
        screen.navigateToAnswerSurveyScreen(system.getAvailableSurvey(index));
    }

    public void onClickViewResponseUploads() {
        screen.navigateToResponseUploadsScreen();
    }

    public interface MainSystem{

        void reloadUserData(Callback callback);

        boolean isLoggedIn();

        void login(String email, String password, Callback callback);

        void logout();

        @Nullable
        Admin getUser();

        void setup();

        void cleanup();

        Survey getAvailableSurvey(int index);

        void fetchSurveyList(Callback callback);

        ArrayList<Survey> getAvailableSurveys();
    }

    public interface MainScreen{
        void showLoginPrompt();

        void showSuccessLogin();

        void hideLoginPrompt();

        void showError(String errorMessage);

        void navigateToCreateSurveyScreen();

        void navigateToManageSurveyScreen();

        void updateUserView(Admin user);

        void navigateToAnswerSurveyScreen(Survey survey);

        void updateSurveyList(ArrayList<Survey> surveys);

        void navigateToResponseUploadsScreen();
    }

    public interface MainLocalDatabase{
        void saveSurveys(ArrayList<Survey> surveys);
    }
}
