package android.zeroh729.com.pcari.ui.activities;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.zeroh729.com.pcari.Pcari_;
import android.zeroh729.com.pcari.R;
import android.zeroh729.com.pcari.data.model.Admin;
import android.zeroh729.com.pcari.data.model.Rating;
import android.zeroh729.com.pcari.data.model.Survey;
import android.zeroh729.com.pcari.data.model.response.DemographicResponse;
import android.zeroh729.com.pcari.data.model.response.QualitativeResponse;
import android.zeroh729.com.pcari.data.model.response.QuantitativeResponse;
import android.zeroh729.com.pcari.data.model.response.SurveyResponse;
import android.zeroh729.com.pcari.presenters.MainPresenter;
import android.zeroh729.com.pcari.ui.adapters.PublicSurveyListAdapter;
import android.zeroh729.com.pcari.ui.base.BaseActivity;
import android.zeroh729.com.pcari.ui.base.BaseAdapterRecyclerView;
import android.zeroh729.com.pcari.ui.dialogs.LoginDialog;
import android.zeroh729.com.pcari.ui.dialogs.LoginDialog_;
import android.zeroh729.com.pcari.util._;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.main)
public class MainActivity extends BaseActivity implements MainPresenter.MainScreen, BaseAdapterRecyclerView.ClickListener {
    MainPresenter presenter;

    @ViewById(R.id.rv_surveys)
    RecyclerView rv_surveys;

    @Bean
    PublicSurveyListAdapter adapter;

    private LoginDialog loginDialog;
    private final String fragLoginTag = "LoginFrag";

    @AfterViews
    public void afterviews() {
//        addDummyData();
        presenter = new MainPresenter(this);
        presenter.setup();
        adapter.setClickListener(this);
        rv_surveys.setLayoutManager(new LinearLayoutManager(this));
        rv_surveys.setAdapter(adapter);

        loginDialog = (LoginDialog) getSupportFragmentManager().findFragmentByTag(fragLoginTag);
        if (loginDialog == null) {
            loginDialog = new LoginDialog_.FragmentBuilder_().build();
            loginDialog.setListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.onClickLogin(loginDialog.getEt_username().getText().toString(), loginDialog.getEt_password().getText().toString());
                }
            });
        }
    }

    @OptionsItem(R.id.item_create_survey)
    public void onClickCreateSurvey() {
        presenter.onClickCreateSurvey();
    }

//    @OptionsItem(R.id.item_manage_survey)
//    public void onClickManageSurvey(){
//        presenter.onClickManageSurvey();
//    }

    @OptionsItem(R.id.item_response_uploads)
    public void onClickViewResponseUploads() {
        presenter.onClickViewResponseUploads();
    }

    @OptionsItem(R.id.item_logout)
    public void onClickLogout() {
        presenter.onClickLogout();
    }

    @Override
    public void onClick(int index) {
        presenter.onClickSurveyItem(index);
    }

    @Override
    public void showLoginPrompt() {
        loginDialog.show(getSupportFragmentManager(), "");
    }

    @Override
    public void showSuccessLogin() {
        _.showToast("Login success!");
    }

    @Override
    public void hideLoginPrompt() {

    }

    @Override
    public void showError(String errorMessage) {
        _.showToast(errorMessage);
    }

    @Override
    public void navigateToCreateSurveyScreen() {
        CreateSurveyActivity_.intent(this).start();
    }

    @Override
    public void navigateToManageSurveyScreen() {
        ManageSurveyActivity_.intent(this).start();
    }

    @Override
    public void navigateToResponseUploadsScreen() {
        UploadResponsesActivity_.intent(this).start();
    }

    @Override
    public void updateUserView(Admin user) {
        if (user != null)
            _.showToast("Welcome" + (!user.getEmail().isEmpty() ? "" : " " + user.getEmail()));
    }

    @Override
    public void navigateToAnswerSurveyScreen(Survey survey) {
        AnswerSurveyActivity_.intent(this).extra("survey", survey).start();
    }

    @Override
    public void updateSurveyList(ArrayList<Survey> surveys) {
        adapter.setItems(surveys);
        adapter.notifyDataSetChanged();
    }


    public ArrayList<SurveyResponse> getDummyData() {
        ArrayList<SurveyResponse> responses = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            SurveyResponse response1 = new SurveyResponse();
            response1.setSurveyId("surveyIdTemp");
            response1.setId(new Date().getTime() + i + "");

            DateFormat format = new SimpleDateFormat("MMMM dd, yyyy hh:mm:ss");
            response1.setDateCreated(format.format(new Date()));

            RealmList<DemographicResponse> demoResponse1 = new RealmList<>();

            for(int j = 0; j < 30; j++) {
                DemographicResponse demoResponse11 = new DemographicResponse();
                demoResponse11.setAnswer("demo answer 1");
                demoResponse11.setQuestionId(j+"");
                demoResponse11.setId("1");
                demoResponse11.setResponseId(response1.getId());

                demoResponse1.add(demoResponse11);
            }

            response1.setDemographicResponses(demoResponse1);

            RealmList<QuantitativeResponse> quanResponse1 = new RealmList<>();

            for(int j = 0; j < 40; j++) {
                QuantitativeResponse quanResponse11 = new QuantitativeResponse();
                quanResponse11.setAnswer(1);
                quanResponse11.setQuestionId(j+"");
                quanResponse11.setId("1");
                quanResponse11.setResponseId(response1.getId());

                quanResponse1.add(quanResponse11);
            }

            response1.setQuantitativeResponses(quanResponse1);

            RealmList<QualitativeResponse> qualResponse1 = new RealmList<>();

            for(int j = 0; j < 50; j++) {
                QualitativeResponse qualResponse11 = new QualitativeResponse();
                qualResponse11.setAnswer("qual answer 1");
                qualResponse11.setQuestionId(j+"");
                qualResponse11.setId("1");
                qualResponse11.setResponseId(response1.getId());

                qualResponse1.add(qualResponse11);
            }

            response1.setQualitativeResponses(qualResponse1);

            RealmList<Rating> ratings = new RealmList<Rating>();

            for(int j = 0; j < 100; j++) {
                Rating rating = new Rating();
                rating.setRating(2);
                rating.setId(0 + "");
                rating.setQualitativeResId(0 + "");
                rating.setResponseId(response1.getId());

                ratings.add(rating);
            }
            response1.setRatings(ratings);
            response1.setFinishedUploading(false);

            responses.add(response1);
        }

        return responses;
    }

    private void addDummyData(){
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(Pcari_.getInstance()).build();
        Realm realm = Realm.getInstance(realmConfig);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<SurveyResponse> responses = realm.where(SurveyResponse.class).findAll();
                responses.deleteAllFromRealm();
                RealmResults<Survey> surveys = realm.where(Survey.class).findAll();
                surveys.deleteAllFromRealm();
//                for(SurveyResponse response : responses) {
//                    _.log(response.getId() + " - " + response.getSurveyId() + " " + response.isFinishedUploading());
//                }
            }
        });

//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                ArrayList<SurveyResponse> responses = getDummyData();
//                for(SurveyResponse response : responses) {
//                    realm.copyToRealm(response.getDemographicResponses());
//                    realm.copyToRealm(response.getQualitativeResponses());
//                    realm.copyToRealm(response.getQuantitativeResponses());
//                    realm.copyToRealm(response.getRatings());
//                    realm.copyToRealm(response);
//                }
//            }
//        });
    }
}