package android.zeroh729.com.pcari.presenters;

import android.zeroh729.com.pcari.data.model.Coordinates;
import android.zeroh729.com.pcari.data.model.Rating;
import android.zeroh729.com.pcari.data.model.Survey;
import android.zeroh729.com.pcari.data.model.response.QualitativeResponse;

import java.util.ArrayList;

public class RateResponsePresenter implements BasePresenter {
    private RateResponseSystem system;
    private RateResponseScreen screen;

    public RateResponsePresenter(RateResponseScreen screen, Survey survey){
        this.screen = screen;
        screen.displaySurveyDetails(survey);
        system.setSurvey(survey);
    }

    @Override
    public void setup() {
        screen.displayLoadingIndicator();
        system.loadCoordinates(new RateResponseSystem.CoordinatesFetchCallback() {
            @Override
            public void onSuccess(ArrayList<Coordinates> coordinates) {
                screen.hideLoadingIndicator();
                screen.displayChart(coordinates);
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

    public void onClickNode(Coordinates coordinate){
        screen.displayLoadingResponse();
        system.loadResponse(coordinate, new RateResponseSystem.ResponseFetchCallback() {
            @Override
            public void onSuccess(ArrayList<QualitativeResponse> response) {
                screen.hideLoadingResponse();
                screen.displayResponse(response);
            }

            @Override
            public void onFail(int errorCode) {

            }
        });
    }

    public void onClickRateResponseSubmit(ArrayList<Rating> ratings){
        system.uploadRatings(ratings, new Callback() {
            @Override
            public void onSuccess() {
                screen.successUploadFeedback();
                if(system.hasAnswered2Surveys()){
                    screen.navigateToSuccessScreen();
                }else{
                    screen.hideResponse();
                }
            }

            @Override
            public void onFail(int errorCode) {

            }
        });
    }

    public interface RateResponseSystem{

        void setSurvey(Survey survey);

        void loadCoordinates(CoordinatesFetchCallback fetchCallback);

        void loadResponse(Coordinates coordinate, ResponseFetchCallback responseFetchCallback);

        boolean hasAnswered2Surveys();

        void uploadRatings(ArrayList<Rating> ratings, Callback callback);

        interface CoordinatesFetchCallback {
            void onSuccess(ArrayList<Coordinates> coordinates);
            void onFail(int errorCode);
        }
        interface ResponseFetchCallback{
            void onSuccess(ArrayList<QualitativeResponse> response);
            void onFail(int errorCode);
        }
    }

    public interface RateResponseScreen{

        void displaySurveyDetails(Survey survey);

        void displayLoadingIndicator();

        void hideLoadingIndicator();

        void displayChart(ArrayList<Coordinates> coordinates);

        void hideLoadingResponse();

        void displayResponse(ArrayList<QualitativeResponse> response);

        void displayLoadingResponse();

        void navigateToSuccessScreen();

        void hideResponse();

        void successUploadFeedback();
    }
}
