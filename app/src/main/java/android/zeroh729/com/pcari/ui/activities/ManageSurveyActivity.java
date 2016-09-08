package android.zeroh729.com.pcari.ui.activities;

import android.os.Bundle;
import android.util.Log;
import android.zeroh729.com.pcari.R;
import android.zeroh729.com.pcari.data.model.Survey;
import android.zeroh729.com.pcari.data.model.response.SurveyResponse;
import android.zeroh729.com.pcari.presenters.ManageSurveyPresenter;
import android.zeroh729.com.pcari.ui.base.BaseActivityDetailsList;
import android.zeroh729.com.pcari.ui.fragments.ManageSurveyDetailsFragment;
import android.zeroh729.com.pcari.ui.fragments.ManageSurveyDetailsFragment_;
import android.zeroh729.com.pcari.ui.fragments.ManageSurveyListFragment;
import android.zeroh729.com.pcari.ui.fragments.ManageSurveyListFragment_;
import android.zeroh729.com.pcari.util.ScreenUtil;
import android.zeroh729.com.pcari.util._;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentById;

import java.util.ArrayList;


@EActivity(R.layout.activity_manage_survey)
public class ManageSurveyActivity extends BaseActivityDetailsList<Survey> implements ManageSurveyPresenter.ManageSurveyScreen {
    @FragmentById(R.id.frag_list)
    ManageSurveyListFragment frag_list;

    @FragmentById(R.id.frag_details)
    ManageSurveyDetailsFragment frag_details;

    private ArrayList<Survey> surveys;

    @Override
    protected ArrayList<Survey> fetchData() {
        if(surveys == null){
//            surveys = presenter.getSurveys();
        }
        return surveys;
    }

    @AfterViews
    public void afterviews(){
        if(findViewById(R.id.fragment_container) != null && getSavedInstanceState() == null){
            ManageSurveyListFragment surveyListFrag = new ManageSurveyListFragment_();
            surveyListFrag.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, surveyListFrag)
                .commit();
        }
        if(getSelectedIndex() != -1){
            onActivityItemSelect(getSelectedIndex());
        }
    }

    @Override
    public void onActivityItemSelect(int index) {
        _.log("selected : " + index);

        if(frag_details != null && ScreenUtil.isScreenLandscape(this)){
            frag_details.bind(getSelectedData());
        }else{
            if(getSelectedData() != null) {
                Bundle args = new Bundle();
                args.putParcelable("survey", getSelectedData());
                ManageSurveyDetailsFragment newFrag_details = new ManageSurveyDetailsFragment_();
                newFrag_details.setArguments(args);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, newFrag_details, "new one")
                        .addToBackStack(null)
                        .commit();
            }

        }
    }

    @Override
    protected void displayNoViewSelected() {

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
    public void onBackPressed() {
        super.onBackPressed();
        onItemSelect(-1);
    }

    @Override
    public void displaySurveyDetails(Survey survey) {

    }

    @Override
    public void displayResponsesChart(ArrayList<SurveyResponse> responses) {

    }

    @Override
    public void displayResponsesTable(ArrayList<SurveyResponse> responses) {

    }
}
