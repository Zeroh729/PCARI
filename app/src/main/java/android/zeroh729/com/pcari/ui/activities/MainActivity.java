package android.zeroh729.com.pcari.ui.activities;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.zeroh729.com.pcari.R;
import android.zeroh729.com.pcari.data.model.Admin;
import android.zeroh729.com.pcari.data.model.Survey;
import android.zeroh729.com.pcari.presenters.MainPresenter;
import android.zeroh729.com.pcari.ui.adapters.PublicSurveyListAdapter;
import android.zeroh729.com.pcari.ui.base.BaseActivity;
import android.zeroh729.com.pcari.ui.base.BaseAdapterRecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.main)
public class MainActivity extends BaseActivity implements MainPresenter.MainScreen, BaseAdapterRecyclerView.ClickListener{
    @ViewById(R.id.rv_surveys)
    RecyclerView rv_surveys;

    @Bean
    PublicSurveyListAdapter adapter;

    @AfterViews
    public void afterviews(){
        adapter.setClickListener(this);
        adapter.setItems(getDummyData());
        rv_surveys.setLayoutManager(new LinearLayoutManager(this));
        rv_surveys.setAdapter(adapter);
    }

    @OptionsItem(R.id.item_create_survey)
    public void onClickCreateSurvey(){
        CreateSurveyActivity_.intent(this).start();
    }

    @OptionsItem(R.id.item_manage_survey)
    public void onClickManageSurvey(){
        ManageSurveyActivity_.intent(this).start();
    }

    @OptionsItem(R.id.item_logout)
    public void onClickLogout(){

    }

    @Override
    public void onClick(int index) {

    }

    private ArrayList<Survey> getDummyData(){
        ArrayList<Survey> surveys = new ArrayList<>();
        for(int i = 0 ; i < 50; i++){
            Survey survey = new Survey();
            survey.setName("Title " + i);
            survey.setObjective("Description " + i);
            surveys.add(survey);
        }
        return surveys;
    }

    @Override
    public void showLoginPrompt() {

    }

    @Override
    public void showSuccessLogin() {

    }

    @Override
    public void hideLoginPrompt() {

    }

    @Override
    public void showError(String errorMessage) {

    }

    @Override
    public void navigateToCreateSurveyScreen() {

    }

    @Override
    public void navigateToManageSurveyScreen() {

    }

    @Override
    public void updateUserView(Admin user) {

    }

    @Override
    public void navigateToAnswerSurveyScreen(Survey survey) {

    }
}
