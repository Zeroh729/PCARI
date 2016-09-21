package android.zeroh729.com.pcari.interactor.services;

import android.app.Service;
import android.content.Intent;
import android.graphics.Point;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.zeroh729.com.pcari.Pcari_;
import android.zeroh729.com.pcari.data.events.UploadStatusEvent;
import android.zeroh729.com.pcari.data.model.Rating;
import android.zeroh729.com.pcari.data.model.response.DemographicResponse;
import android.zeroh729.com.pcari.data.model.response.QualitativeResponse;
import android.zeroh729.com.pcari.data.model.response.QuantitativeResponse;
import android.zeroh729.com.pcari.data.model.response.SurveyResponse;
import android.zeroh729.com.pcari.interactor.firebaseInteractor.DbConstants;
import android.zeroh729.com.pcari.util.AlgorithmUtil;
import android.zeroh729.com.pcari.util.OttoBus;
import android.zeroh729.com.pcari.util._;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.EService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;

@EBean
public class UploadThread implements Runnable{
    private int completeCount = 0;
    private boolean isAllSuccessful;

    private SurveyResponse response;
    private DatabaseReference responseRef;

    private RealmConfiguration realmConfig;
    private Realm realm;

    @Bean
    OttoBus bus;

    public void setResponse(final SurveyResponse response) {
        realmConfig = new RealmConfiguration.Builder(Pcari_.getInstance()).build();
        realm = Realm.getInstance(realmConfig);

        this.response = new SurveyResponse(response);
    }

    @Override
    public void run() {
        uploadsResponse();
    }

    public void uploadsResponse() {
        DatabaseReference surveyRef = FirebaseDatabase.getInstance().getReference().child(DbConstants.CHILD_RESPONSE).child(response.getSurveyId());
        responseRef = surveyRef.child(DbConstants.CHILD_RESPONSE_DETAILS).child(response.getId());
        final String responseId = responseRef.getKey();
        final DatabaseReference demographicRef = surveyRef.child(DbConstants.CHILD_DEMOGRAPHIC);
        final DatabaseReference qualitativeRef = surveyRef.child(DbConstants.CHILD_QUALITATIVE);
        final DatabaseReference quantitativeRef = surveyRef.child(DbConstants.CHILD_QUANTITATIVE);
        final DatabaseReference coordinatesRef = surveyRef.child(DbConstants.CHILD_COORDINATES);
        final DatabaseReference ratingsRef = FirebaseDatabase.getInstance().getReference().child(DbConstants.CHILD_RATINGS).child(response.getSurveyId()).child(responseId).child(response.getId());

        completeCount = 0;
        isAllSuccessful = true;

        responseRef.setValue(initSurveyDbDetails()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                for(DemographicResponse r : response.getDemographicResponses()){
                    HashMap map = new HashMap();
                    map.put(DbConstants.KEY_ANSWER, r.getAnswer());
                    demographicRef.child(r.getQuestionId()).child(responseId).setValue(map).addOnCompleteListener(onCompleteListener);
                }

                int[] quanAnswers = new int[response.getQuantitativeResponses().size()];
                int i = 0;
                for(QuantitativeResponse r : response.getQuantitativeResponses()){
                    quanAnswers[i++] = r.getAnswer();
                    HashMap map = new HashMap();
                    map.put(DbConstants.KEY_ANSWER, r.getAnswer());
                    quantitativeRef.child(r.getQuestionId()).child(responseId).setValue(map).addOnCompleteListener(onCompleteListener);
                }

                ratingsRef.setValue(getRatingValue(response)).addOnCompleteListener(onCompleteListener);

                Point point = AlgorithmUtil.calculatePCA(quanAnswers);
                HashMap map1 = new HashMap();
                map1.put(DbConstants.KEY_X, point.x);
                map1.put(DbConstants.KEY_Y, point.y);
                coordinatesRef.child(responseId).setValue(map1).addOnCompleteListener(onCompleteListener);

                for(QualitativeResponse r : response.getQualitativeResponses()){
                    HashMap map = new HashMap();
                    map.put(DbConstants.KEY_ANSWER, r.getAnswer());
                    qualitativeRef.child(r.getQuestionId()).child(responseId).setValue(map).addOnCompleteListener(onCompleteListener);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
//                callback.onFail(0);
            }
        });
    }

    private HashMap initSurveyDbDetails(){
        HashMap map = new HashMap<>();
        DateFormat format = new SimpleDateFormat("MMMM dd, yyyy hh:mm:ss");

        map.put(DbConstants.KEY_DATE_CREATED, format.format(new Date()));
        map.put(DbConstants.KEY_IS_UPLOAD_DONE, false);
        return map;
    }

    private ArrayList<Integer> getRatingValue(SurveyResponse response){
        ArrayList<Integer> ratingsList = new ArrayList<>();
        RealmList<Rating> ratings = response.getRatings();
        for(int i = 0; i < ratings.size(); i++){
            ratingsList.add(ratings.get(i).getRating());
        }

        return ratingsList;
    }

    private OnCompleteListener onCompleteListener = new OnCompleteListener() {
        @Override
        public void onComplete(@NonNull Task task) {
            completeCount++;
            bus.post(new UploadStatusEvent(response.getId(), completeCount, response.getTotalUploadCount()));

            if(!task.isSuccessful()){
                isAllSuccessful = false;
            }

            if(completeCount == response.getTotalUploadCount()){
                if(isAllSuccessful){
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            response.setFinishedUploading(true);
                            realm.copyToRealmOrUpdate(response);
                        }
                    });

                    responseRef.child(DbConstants.KEY_IS_UPLOAD_DONE).setValue(true);
                    realm.close();
                }else{
                    bus.post(new UploadStatusEvent(response.getId(), -1, response.getTotalUploadCount()));
                    realm.close();
                }
            }
        }
    };

}
