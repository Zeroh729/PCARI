package android.zeroh729.com.pcari.presenters;

import android.zeroh729.com.pcari.data.model.Admin;
import android.zeroh729.com.pcari.data.model.Survey;

public class MainPresenter implements BasePresenter {
    public MainSystem system;
    public MainScreen screen;

    @Override
    public void setup() {
        screen.updateUserView(system.getUser());
    }

    @Override
    public void updateState() {

    }

    @Override
    public void setState(int state) {

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

    public void onClickSurveyItem(Survey survey){
        screen.navigateToAnswerSurveyScreen(survey);
    }

    public interface MainSystem{

        boolean isLoggedIn();

        void login(String email, String password, Callback callback);

        void logout();

        Admin getUser();
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
    }
}
