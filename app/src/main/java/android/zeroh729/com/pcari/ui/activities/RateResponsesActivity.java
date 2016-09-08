package android.zeroh729.com.pcari.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.zeroh729.com.pcari.R;
import android.zeroh729.com.pcari.data.model.Coordinates;
import android.zeroh729.com.pcari.data.model.Rating;
import android.zeroh729.com.pcari.data.model.Survey;
import android.zeroh729.com.pcari.data.model.question.DemographicQuestion;
import android.zeroh729.com.pcari.data.model.question.QualitativeQuestion;
import android.zeroh729.com.pcari.data.model.response.QualitativeResponse;
import android.zeroh729.com.pcari.presenters.RateResponsePresenter;
import android.zeroh729.com.pcari.ui.base.BaseActivityDetailsList;
import android.zeroh729.com.pcari.ui.fragments.DataChartFragment;
import android.zeroh729.com.pcari.ui.fragments.DataChartFragment_;
import android.zeroh729.com.pcari.ui.fragments.RateQuestionFragment;
import android.zeroh729.com.pcari.ui.fragments.RateQuestionFragment_;
import android.zeroh729.com.pcari.ui.views.RateResponseRow;
import android.zeroh729.com.pcari.ui.views.RateResponseRow_;
import android.zeroh729.com.pcari.util.ScreenUtil;
import android.zeroh729.com.pcari.util._;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.FragmentById;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lecho.lib.hellocharts.model.BubbleValue;

@EActivity(R.layout.activity_rate_responses)
public class RateResponsesActivity extends BaseActivityDetailsList<Coordinates> implements RateResponsePresenter.RateResponseScreen {
    private RateResponsePresenter presenter;

    @FragmentById(R.id.frag_dataview)
    DataChartFragment frag_list;

    @FragmentById(R.id.frag_rate)
    RateQuestionFragment frag_details;

    @Extra("survey")
    Survey survey;

    @Extra("responseId")
    String responseId;

    private DataChartFragment frag_list_portrait;
    private RateQuestionFragment frag_details_portrait;

    private String selectedId;
    private ArrayList<Coordinates> coordinates;
    private ArrayList<RateResponseRow> rateRowViews;

    private String surveyKey = "survey";
    private String responseKey = "responseId";
    private String selectedIdKey = "selectedId";
    private String coordinatesKey = "coordinatesKey";

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(surveyKey, survey);
        outState.putString(responseKey, responseId);
        outState.putString(selectedIdKey, selectedId);
        outState.putParcelableArrayList(coordinatesKey, coordinates);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getDummyData();
        if(savedInstanceState != null){
            survey = savedInstanceState.getParcelable(surveyKey);
            responseId = savedInstanceState.getString(responseKey);
            selectedId = savedInstanceState.getString(selectedIdKey);
            coordinates = savedInstanceState.getParcelableArrayList(coordinatesKey);
        }
    }

    @AfterViews
    public void afterviews(){
        if(findViewById(R.id.fragment_container) != null){
            frag_list_portrait = (DataChartFragment) getSupportFragmentManager().findFragmentByTag("chartFrag");

            if(frag_list_portrait == null) {
                frag_list_portrait = new DataChartFragment_();
                frag_list_portrait.setArguments(getIntent().getExtras());
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, frag_list_portrait, "chartFrag")
                        .commit();
            }

            frag_list_portrait.setCallback(new DataChartFragment.ClickListener() {
                @Override
                public void onClick(int index) {
                    onItemSelect(index);
                    presenter.onClickNode(getSelectedData());
                }
            });
        } else if(frag_list != null){
            frag_list.setCallback( new DataChartFragment.ClickListener() {
                @Override
                public void onClick(int index) {
                    onItemSelect(index);
                    presenter.onClickNode(getSelectedData());
                }
            });
        }

        presenter = new RateResponsePresenter(this, survey, responseId);
        presenter.setup();

        if(getSelectedIndex()!=-1){
            onItemSelect(getSelectedIndex());
        }

        displayChart(getDataList());
    }

    @Override
    protected ArrayList<Coordinates> fetchData() {
        if(coordinates == null){
            coordinates = presenter.getCoordinates();
        }
        return coordinates;
    }

    @Override
    public void onActivityItemSelect(int index) {
        if(frag_details != null && ScreenUtil.isScreenLandscape(this)){
            frag_details.setResponses(new ArrayList<QualitativeResponse>());
            frag_details.setListener(new RateQuestionFragment.ClickListener() {
                @Override
                public void onClick() {
                    presenter.onClickRateResponseSubmit(getSelectedData().getResponseId(), getRatings());
                }
            });
            frag_details.bind(getSelectedData());
        }else{
            Bundle args = new Bundle();
            args.putParcelable("survey", getSelectedData());

            frag_details_portrait = new RateQuestionFragment_();
            frag_details_portrait.setArguments(args);
            frag_details_portrait.setListener(new RateQuestionFragment.ClickListener() {
                @Override
                public void onClick() {
                    presenter.onClickRateResponseSubmit(getSelectedData().getResponseId(), getRatings());
                }
            });

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, frag_details_portrait, "new one")
                    .commit();
        }
    }

    private ArrayList<Rating> getRatings(){
        ArrayList<Rating> ratings = new ArrayList<>();
        for(RateResponseRow row : rateRowViews){
            Rating rating = new Rating();
            rating.setId(responseId);
            rating.setRating(row.getRg_rating().getCheckedRadioButtonId());
            ratings.add(rating);
        }
        return ratings;
    }

    @Override
    protected void displayNoViewSelected() {
        if(frag_details != null && ScreenUtil.isScreenLandscape(this)) {
            frag_details.bind(null);
        }else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, frag_list_portrait)
                    .commit();
        }
    }

    @Override
    public void displaySurveyDetails(Survey survey) {
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(survey.getName());
        }
    }

    @Override
    public void displayLoadingIndicator() {

    }

    @Override
    public void hideLoadingIndicator() {

    }

    @Override
    public void displayChart(ArrayList<Coordinates> coordinates) {
        //TWO WAYS OF IMPLEMENTING
        //1. check if coordinates are already drawn on the plane
        //2. remove all points and redraw

        List<BubbleValue> values = new ArrayList<>();
        for(Coordinates coor : coordinates) {
            BubbleValue value = new BubbleValue(coor.getX(), coor.getY(), 0);
            values.add(value);
        }
        if(frag_list != null && ScreenUtil.isScreenLandscape(this)){
            frag_list.setChartDataWithValues(values);
        }else{
            if(frag_list_portrait != null) {
                frag_list_portrait.setChartDataWithValues(values);
            }
        }
    }

    @Override
    public void onBackPressed() {
        onItemSelect(-1);
    }

    @Override
    public void hideLoadingResponse() {
        RateQuestionFragment frag_rate = getFragRate();
        frag_rate.setTextLabel("");
    }

    @Override
    public void displayResponse(ArrayList<QualitativeResponse> responses) {
        RateQuestionFragment frag_rate = getFragRate();
        rateRowViews = new ArrayList<>();
        for(int i = 0; i < survey.getQualitativeQs().size(); i++) {
            RateResponseRow row = RateResponseRow_.build(this);
            QualitativeQuestion q = survey.getQualitativeQs().get(i);
            QualitativeResponse r = responses.get(i);
            row.bind(q, r);
            rateRowViews.add(row);
        }
        frag_rate.setRowViews(rateRowViews);
    }

    @Override
    public void displayLoadingResponse() {
        RateQuestionFragment frag_rate = getFragRate();
        frag_rate.setTextLabel("Loading...");
    }

    @Override
    public void navigateToSuccessScreen() {
        SuccessFeedbackActivity_.intent(this).extra("survey", survey).start();
    }

    @Override
    public void hideResponse() {
        onItemSelect(-1);
    }

    @Override
    public void successUploadFeedback() {
        _.showToast("Feedback sent!");
    }

    private RateQuestionFragment getFragRate(){
        if(findViewById(R.id.fragment_container) != null)
            return frag_details_portrait;
        else
            return frag_details;
    }

    public void getDummyData() {
        survey = new Survey();
        survey.setId("-KQ5_maqqvdgcVgb_09r");
        ArrayList<QualitativeQuestion> qs = new ArrayList<>();
        QualitativeQuestion q = new QualitativeQuestion();
        q.setQuestion("What's happening lately? What is your idea?");
        q.setQuestionForRating("What can you say about this");
        q.setRatingRange(10);
        QualitativeQuestion q2 = new QualitativeQuestion();
        q2.setQuestion("What??");
        q2.setQuestionForRating("What can you do about this?");
        q2.setRatingRange(2);
        qs.add(q);
        qs.add(q2);
        survey.setQualitativeQs(qs);
        survey.setName("Calamity Survey 1");
        responseId = "-KQT0_LKjZe1aDcPgM-G";
    }
}
