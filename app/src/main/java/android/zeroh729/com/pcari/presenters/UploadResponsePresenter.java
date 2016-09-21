package android.zeroh729.com.pcari.presenters;

import android.support.annotation.Nullable;
import android.zeroh729.com.pcari.data.events.UploadStatusEvent;
import android.zeroh729.com.pcari.data.model.response.SurveyResponse;
import android.zeroh729.com.pcari.interactor.realmInteractor.UploadResponseSystemImpl;
import android.zeroh729.com.pcari.ui.activities.UploadResponsesActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class UploadResponsePresenter implements BasePresenter {
    private UploadResponsesActivity screen;
    private UploadResponseSystemImpl system;

    public UploadResponsePresenter(UploadResponsesActivity screen) {
        this.screen = screen;
        system = new UploadResponseSystemImpl();
    }

    @Override
    public void setup() {
        system.fetchUnfinishedUploads(new DataCallback<SurveyResponse>(){
            @Override
            public void onSuccess(ArrayList<SurveyResponse> data) {
                screen.displayResponses(data);
            }

            @Override
            public void onFail(int errorCode) {

            }
        });
    }

    @Override
    public void updateState() {

    }

    @Override
    public void setState(int state) {

    }

    public ArrayList<SurveyResponse> getResponses(){
        return system.getResponses();
    }

    public void onUploadStatusEvent(UploadStatusEvent event) {
        SurveyResponse response = getResponseFromId(event.getResponseId());
        if(event.getProgress() == -1){
            system.uploadInBackground(response);
        }else{
            system.getUploadStatuses().put(response.getId(), event);
            screen.updateResponseStatus(getResponseIndexFromId(event.getResponseId()));
        }
    }

    @Nullable
    public SurveyResponse getResponseFromId(String id){
        for(SurveyResponse response : system.getResponses()){
            if(response.getId().equals(id)){
                return response;
            }
        }
        return null;
    }

    public int getResponseIndexFromId(String id){
        for(int i = 0; i <  system.getResponses().size(); i++){
            SurveyResponse response = system.getResponses().get(i);
            if(response.getId().equals(id)){
                return i;
            }
        }
        return -1;
    }

    public HashMap<String, String> getSurveyNameMap() {
        return system.getSurveyNameMap();
    }

    public HashMap<String, UploadStatusEvent> getUploadStatuses() {
        return system.getUploadStatuses();
    }

    public interface UploadResponseScreen{

        void displayResponses(ArrayList<SurveyResponse> data);

        void updateResponseStatus(int responseIndexFromId);
    }

    public interface UploadResponseSystem{

        void fetchUnfinishedUploads(DataCallback<SurveyResponse> dataCallback);

        ArrayList<SurveyResponse> getResponses();

        void uploadInBackground(SurveyResponse response);

        HashMap<String, String> getSurveyNameMap();

        HashMap<String, UploadStatusEvent> getUploadStatuses();

    }

}
