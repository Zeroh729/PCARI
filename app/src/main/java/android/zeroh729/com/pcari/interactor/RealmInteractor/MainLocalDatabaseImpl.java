package android.zeroh729.com.pcari.interactor.realmInteractor;

import android.zeroh729.com.pcari.Pcari;
import android.zeroh729.com.pcari.Pcari_;
import android.zeroh729.com.pcari.data.model.Survey;
import android.zeroh729.com.pcari.data.model.response.SurveyResponse;
import android.zeroh729.com.pcari.presenters.BasePresenter;
import android.zeroh729.com.pcari.presenters.MainPresenter;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;

public class MainLocalDatabaseImpl implements MainPresenter.MainLocalDatabase{
    private Realm realm;
    private RealmConfiguration realmConfig;

    public MainLocalDatabaseImpl() {
        realmConfig = new RealmConfiguration.Builder(Pcari_.getInstance()).build();
        realm = Realm.getInstance(realmConfig);
    }

    @Override
    public void saveSurveys(final ArrayList<Survey> surveys) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (Survey survey : surveys) {
                    realm.copyToRealmOrUpdate(survey);
                }
            }
        });
    }
}
