package android.zeroh729.com.pcari.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.zeroh729.com.pcari.R;
import android.zeroh729.com.pcari.data.model.Coordinates;
import android.zeroh729.com.pcari.data.model.Survey;
import android.zeroh729.com.pcari.data.model.response.QualitativeResponse;
import android.zeroh729.com.pcari.presenters.RateResponsePresenter;
import android.zeroh729.com.pcari.ui.base.BaseActivityDetailsList;
import android.zeroh729.com.pcari.ui.fragments.DataChartFragment;
import android.zeroh729.com.pcari.ui.fragments.DataChartFragment_;
import android.zeroh729.com.pcari.ui.fragments.RateQuestionFragment;
import android.zeroh729.com.pcari.ui.fragments.RateQuestionFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.FragmentById;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

@EActivity(R.layout.activity_rate_responses)
public class RateResponsesActivity extends BaseActivityDetailsList<Coordinates> implements RateResponsePresenter.RateResponseScreen {
    @FragmentById(R.id.frag_rate)
    DataChartFragment frag_list;

    @FragmentById(R.id.frag_dataview)
    RateQuestionFragment frag_details;

    @Extra("surveyId")
    String surveyId;

    private String selectedId;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("surveyId", surveyId);
        outState.putString("selectedId", selectedId);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            surveyId = savedInstanceState.getString("surveyId");
            selectedId = savedInstanceState.getString("selectedId");
        }
    }

    @AfterViews
    public void afterviews(){
        if(findViewById(R.id.fragment_container) != null && getSavedInstanceState() != null){
            DataChartFragment dataChartFragment = new DataChartFragment_();
            dataChartFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, dataChartFragment)
                    .commit();
        }
    }

    @Override
    protected ArrayList<Coordinates> fetchData() {
        return getDummyData();
    }

    @Override
    public void onActivityItemSelect(int index) {
        if(frag_details != null){
            frag_details.setResponses(new ArrayList<QualitativeResponse>());
        }else{
            Bundle args = new Bundle();
            args.putParcelable("survey", getSelectedData());

            RateQuestionFragment newFrag_details = new RateQuestionFragment_();
            newFrag_details.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, newFrag_details, "new one")
                .addToBackStack(null)
                .commit();
        }
    }

    private ArrayList<Coordinates> getDummyData(){
        ArrayList<Coordinates> coordinates = new ArrayList<>();
        for(int i = 0 ; i < 50; i++){
            Coordinates coordinate = new Coordinates();
            coordinate.setResponseId("" + i);
            coordinate.setX(1 + i);
            coordinate.setY(new Random().nextInt(100));
            coordinates.add(coordinate);
        }
        return coordinates;
    }

    @Override
    public void displaySurveyDetails(Survey survey) {

    }

    @Override
    public void displayLoadingIndicator() {

    }

    @Override
    public void hideLoadingIndicator() {

    }

    @Override
    public void displayChart(ArrayList<Coordinates> coordinates) {

    }

    @Override
    public void hideLoadingResponse() {

    }

    @Override
    public void displayResponse(ArrayList<QualitativeResponse> response) {

    }

    @Override
    public void displayLoadingResponse() {

    }

    @Override
    public void navigateToSuccessScreen() {

    }

    @Override
    public void hideResponse() {

    }

    @Override
    public void successUploadFeedback() {

    }
}
