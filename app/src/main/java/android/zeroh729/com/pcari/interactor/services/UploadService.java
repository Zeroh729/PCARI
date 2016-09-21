package android.zeroh729.com.pcari.interactor.services;

import android.app.IntentService;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.zeroh729.com.pcari.Pcari_;
import android.zeroh729.com.pcari.data.events.UploadStatusEvent;
import android.zeroh729.com.pcari.data.model.Rating;
import android.zeroh729.com.pcari.data.model.Survey;
import android.zeroh729.com.pcari.data.model.response.DemographicResponse;
import android.zeroh729.com.pcari.data.model.response.QualitativeResponse;
import android.zeroh729.com.pcari.data.model.response.QuantitativeResponse;
import android.zeroh729.com.pcari.data.model.response.SurveyResponse;
import android.zeroh729.com.pcari.interactor.firebaseInteractor.DbConstants;
import android.zeroh729.com.pcari.presenters.BasePresenter;
import android.zeroh729.com.pcari.util.AlgorithmUtil;
import android.zeroh729.com.pcari.util.OttoBus;
import android.zeroh729.com.pcari.util.OttoBus_;
import android.zeroh729.com.pcari.util._;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EService;
import org.androidannotations.annotations.Extra;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;

@EService
public class UploadService extends Service {
    private RealmConfiguration realmConfig;
    private Realm realm;

    @Bean
    OttoBus bus;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        realmConfig = new RealmConfiguration.Builder(Pcari_.getInstance()).build();
        realm = Realm.getInstance(realmConfig);

        fetchUnfinishedUploads(new BasePresenter.DataCallback<SurveyResponse>(){
            @Override
            public void onSuccess(ArrayList<SurveyResponse> data) {
                for(SurveyResponse response : data) {
                    uploadInBackground(response);
                }
            }

            @Override
            public void onFail(int errorCode) {

            }
        });

        return START_REDELIVER_INTENT;
    }

    public void fetchUnfinishedUploads(BasePresenter.DataCallback<SurveyResponse> dataCallback) {
        ArrayList<SurveyResponse> responses = new ArrayList<>();
        for(final SurveyResponse response : realm.where(SurveyResponse.class).findAll()){
            if(!response.isFinishedUploading()){
                responses.add(response);
            }
        }
        dataCallback.onSuccess(responses);
    }

    public void uploadInBackground(SurveyResponse response) {
        UploadThread_ upload = UploadThread_.getInstance_(Pcari_.getInstance().getContext());
        upload.setResponse(response);
        Thread thread = new Thread(upload);
        thread.start();
    }
}