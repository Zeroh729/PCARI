package android.zeroh729.com.pcari.interactor.FirebaseInteractor;

import android.support.annotation.NonNull;
import android.zeroh729.com.pcari.data.model.Coordinates;
import android.zeroh729.com.pcari.data.model.Rating;
import android.zeroh729.com.pcari.data.model.Survey;
import android.zeroh729.com.pcari.data.model.response.QualitativeResponse;
import android.zeroh729.com.pcari.data.model.response.SurveyResponse;
import android.zeroh729.com.pcari.presenters.BasePresenter;
import android.zeroh729.com.pcari.presenters.RateResponsePresenter;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class RateResponseSystemImpl implements RateResponsePresenter.RateResponseSystem {
    private Survey survey;
    private SurveyResponse response;
    private ArrayList<Coordinates> coordinates;
    private ArrayList<QualitativeResponse> selectedResponse;
    private int answerCounter = 0;

    public RateResponseSystemImpl() {
        coordinates = new ArrayList<>();
    }

    @Override
    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    @Override
    public Survey getSurvey() {
        return survey;
    }

    public SurveyResponse getResponse() {
        return response;
    }

    @Override
    public void setResponse(SurveyResponse response) {
        this.response = response;
    }

    @Override
    public ArrayList<Coordinates> getCoordinates() {
        return coordinates;
    }

    @Override
    public int getAnswerCount() {
        return answerCounter;
    }

    @Override
    public void incrementAnswerCount() {
        answerCounter++;
    }

    @Override
    public void loadCoordinates(final CoordinatesFetchCallback fetchCallback) {
        FirebaseDatabase.getInstance().getReference().child(DbConstants.CHILD_RESPONSE).child(survey.getId()).child(DbConstants.CHILD_COORDINATES).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String id = dataSnapshot.getKey();
                int x = (int)(long)dataSnapshot.child(DbConstants.KEY_X).getValue();
                int y = (int)(long)dataSnapshot.child(DbConstants.KEY_Y).getValue();

                Coordinates coordinate = new Coordinates();
                coordinate.setResponseId(id);
                coordinate.setX(x);
                coordinate.setY(y);

                if(!response.getId().equals(id))
                    coordinates.add(coordinate);
                fetchCallback.onSuccess(coordinates);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                fetchCallback.onFail(0);
            }
        });
    }

    @Override
    public void loadResponse(final Coordinates coordinate, final ResponseFetchCallback responseFetchCallback) {
        selectedResponse = new ArrayList<>();
        for (int i = 0; i < survey.getQualitativeQs().size(); i++) {
            final int finalI = i;
            FirebaseDatabase.getInstance().getReference().child(DbConstants.CHILD_RESPONSE).child(survey.getId()).child(DbConstants.CHILD_QUALITATIVE).child(i+"").child(coordinate.getResponseId()).child(DbConstants.KEY_ANSWER).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    QualitativeResponse response = new QualitativeResponse();
                    response.setId(coordinate.getResponseId());
                    response.setResponseId(coordinate.getResponseId());
                    response.setQuestionId(finalI +"");
                    response.setAnswer((String)dataSnapshot.getValue());
                    selectedResponse.add(response);

                    if(selectedResponse.size() >= survey.getQualitativeQs().size()){
                        responseFetchCallback.onSuccess(selectedResponse);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    responseFetchCallback.onFail(0);
                }
            });
        }
    }

    @Override
    public void uploadRatings(String selectedResponseId, ArrayList<Rating> ratings, final BasePresenter.Callback callback) {
        ArrayList<Integer> ratingsList = new ArrayList<>();
        for(int i = 0; i < ratings.size(); i++){
            ratingsList.add(ratings.get(i).getRating());
            for(int j = 0; j < response.getRatings().size(); j++) {
                if (ratings.get(i).getResponseId().equals(response.getRatings().get(j).getResponseId())){
                    response.getRatings().remove(j);
                    break;
                }
            }
            response.getRatings().add(ratings.get(i));
        }

        FirebaseDatabase.getInstance().getReference().child(DbConstants.CHILD_RATINGS).child(survey.getId()).child(selectedResponseId).child(response.getId()).setValue(ratingsList).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    callback.onSuccess();
                }else{
                    callback.onFail(0);
                }
            }
        });
    }
}
